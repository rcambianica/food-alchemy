package it.unimib.food_alchemy.ui.main;

import static it.unimib.food_alchemy.util.Constants.RECIPES_NUMBER_PARAMETER;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Button;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.RecipesRecyclerViewAdapter;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.databinding.FragmentHomeBinding;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import it.unimib.food_alchemy.model.RecipesResponse;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.util.ErrorMessagesUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    private final boolean[] buttonState = {false, false, false, false};
    private static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding fragmentHomeBinding;
    private List<Recipe> recipeList;
    private String type = "main course";
    private RecipeViewModel recipeViewModel;
    private RecipesRecyclerViewAdapter recipesRecyclerViewAdapter;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private int totalItemCount; // Total number of recipes
    private int lastVisibleItem; // The position of the last visible recipe item
    private int visibleItemCount; // Number or total visible recipes items
    private final int threshold = 1;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {return new HomeFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IRecipesRepository recipesRepository =
                ServiceLocator.getInstance().getRecipesRepository(requireActivity().getApplication());

        if(recipesRepository != null)   {
            recipeViewModel = new ViewModelProvider(requireActivity(),
                    new RecipeViewModelFactory(recipesRepository)).get(RecipeViewModel.class);
        }   else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }
        recipeList = new ArrayList<>();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container, false);
        return fragmentHomeBinding.getRoot();
    }

    private void toggleButtonChecked(final int clickedButton) {
        Button[] buttons = {
                fragmentHomeBinding.bottoneMainCourse,
                fragmentHomeBinding.bottoneSideDish,
                fragmentHomeBinding.bottoneDessert,
                fragmentHomeBinding.bottoneAppetizer
        };

        for (int i = 0; i < buttons.length; i++) {
            if (i == clickedButton) {
                // Toggle the state for the clicked button
                buttonState[i] = !buttonState[i];
                if (buttonState[i]) {
                    buttons[i].setBackgroundResource(R.drawable.custom_button_checked);
                } else {
                    buttons[i].setBackgroundResource(R.drawable.custom_button);
                }
            } else {
                // Turn off other buttons
                buttonState[i] = false;
                buttons[i].setBackgroundResource(R.drawable.custom_button);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Set<String> intolerances = new HashSet<>();
        intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_INTOLERANCES);

        RecyclerView recipesRecyclerView = view.findViewById(R.id.recipesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false);

        recipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(requireActivity().getApplication(),
                recipeList,
                new RecipesRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onRecipesItemClick(Recipe recipe) {
                        //guardare penultima lezione
                    }
                });

        recipesRecyclerView.setAdapter(recipesRecyclerViewAdapter);
        recipesRecyclerView.setLayoutManager(layoutManager);

        fragmentHomeBinding.searchButton.setOnClickListener(v ->
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_searchFragment));
        fragmentHomeBinding.bottoneMainCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "main course";
                Log.d(TAG, "bottone main course cliccato");
                toggleButtonChecked(0);
            }
        });
        fragmentHomeBinding.bottoneSideDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "side dish";
                Log.d(TAG, "bottone side dish cliccato");
                toggleButtonChecked(1);
            }
        });
        fragmentHomeBinding.bottoneDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "dessert";
                Log.d(TAG, "bottone dessert cliccato");
                toggleButtonChecked(2);
            }
        });
        fragmentHomeBinding.bottoneAppetizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "appetizer";
                Log.d(TAG, "bottone appetizer cliccato");
                toggleButtonChecked(3);
            }
        });

        fragmentHomeBinding.progressBar.setVisibility(View.VISIBLE);

        recipeViewModel.getRecipes(null, type,null,intolerances,null,0).observe(getViewLifecycleOwner(),
                result -> {
                    Log.d(TAG, "Ciao");
                    if(result.isSuccess())  {
                        RecipesResponse recipesResponse = ((Result.RecipesResponseSuccess) result).getData();
                        List<Recipe> fetchedRecipes = recipesResponse.getRecipesList();

                        if(!recipeViewModel.isLoading())    {
                            if(recipeViewModel.isFirstLoading())    {
                                recipeViewModel.setTotalResults(((RecipeApiResponse) recipesResponse).getTotalResults());
                                recipeViewModel.setFirstLoading(false);
                                this.recipeList.addAll(fetchedRecipes);
                                recipesRecyclerViewAdapter.notifyItemRangeInserted(0,
                                        this.recipeList.size());
                            }   else{
                                recipeList.clear();
                                recipeList.addAll(fetchedRecipes);
                                recipesRecyclerViewAdapter.notifyItemRangeInserted(0, fetchedRecipes.size());
                            }
                            fragmentHomeBinding.progressBar.setVisibility(View.GONE);
                        }   else {
                            recipeViewModel.setLoading(false);
                            recipeViewModel.setCurrentResults(recipeList.size());

                            int initialSize = recipeList.size();

                            for(int i = 0; i < recipeList.size(); i++)  {
                                if(recipeList.get(i) == null)   {
                                    recipeList.remove(recipeList.get(i));
                                }
                            }
                            int startIndex = recipeViewModel.getOffset();

                            for(int i = startIndex; i < fetchedRecipes.size(); i++) {
                                recipeList.add(fetchedRecipes.get(i));
                            }
                            recipesRecyclerViewAdapter.notifyItemRangeInserted(initialSize, recipeList.size());
                        }
                    }   else {
                        ErrorMessagesUtil errorMessagesUtil =
                                new ErrorMessagesUtil(requireActivity().getApplication());
                        Snackbar.make(view, errorMessagesUtil.
                                        getErrorMessage(((Result.Error)result).getMessage()),
                                Snackbar.LENGTH_SHORT).show();
                        fragmentHomeBinding.progressBar.setVisibility(View.GONE);
                    }
                });

        recipesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx,dy);
                boolean isConnected = isConnected();

                if(isConnected && totalItemCount != recipeViewModel.getTotalResults())  {
                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    visibleItemCount = layoutManager.getChildCount();

                    if(totalItemCount == visibleItemCount ||
                                (totalItemCount <= (lastVisibleItem + threshold) &&
                                        dy > 0 && !recipeViewModel.isLoading()
                                ) &&
                        recipeViewModel.getRecipesResponseLiveData().getValue() != null &&
                        recipeViewModel.getCurrentResults() != recipeViewModel.getTotalResults()
                    )   {
                        MutableLiveData<Result> recipesListMutableLiveData = recipeViewModel.getRecipesResponseLiveData();

                        if(recipesListMutableLiveData.getValue() != null &&
                            recipesListMutableLiveData.getValue().isSuccess())  {

                            recipeViewModel.setLoading(true);
                            recipeList.add(null);
                            recipesRecyclerViewAdapter.notifyItemRangeInserted(recipeList.size(),
                                    recipeList.size() + 1);

                            int offset = recipeViewModel.getOffset() + RECIPES_NUMBER_PARAMETER;
                            recipeViewModel.setOffset(offset);
                            recipeViewModel.fetchRecipes(null,type,null,null,null);
                        }
                    }
                }
            }
        });
    }

    /**
     * Checks if the device is connected to Internet.
     * See: https://developer.android.com/training/monitoring-device-state/connectivity-status-type#DetermineConnection
     * @return true if the device is connected to Internet; false otherwise.
     */
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recipeViewModel.setFirstLoading(true);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentHomeBinding = null;
    }
}