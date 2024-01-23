package it.unimib.food_alchemy.ui.search;


import static it.unimib.food_alchemy.util.Constants.RECIPES_NUMBER_PARAMETER;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.RecipesRecyclerViewAdapter;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.databinding.FragmentResultsSearchBinding;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.Result;

import it.unimib.food_alchemy.ui.main.RecipeViewModel;
import it.unimib.food_alchemy.ui.main.RecipeViewModelFactory;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

public class ResultsSearchFragment extends Fragment {

    private final String TAG = ResultsSearchFragment.class.getSimpleName();
    private FragmentResultsSearchBinding fragmentResultsSearchBinding;
    List<Recipe> recipeList;
    private RecipeViewModel recipeViewModel;
    private RecipesRecyclerViewAdapter recipesRecyclerViewAdapter;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private int totalItemCount; // Total number of recipes
    private int lastVisibleItem; // The position of the last visible recipe item
    private int visibleItemCount; // Number or total visible recipes items
    private final int threshold = 1;
    public ResultsSearchFragment() {
        // Required empty public constructor
    }

    public static ResultsSearchFragment newInstance() {
        return new ResultsSearchFragment();
    }

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

        fragmentResultsSearchBinding = FragmentResultsSearchBinding.inflate(inflater, container, false);
        return fragmentResultsSearchBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Set<String> intolerances;
        intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_INTOLERANCES);

        RecyclerView RecipesRecyclerView = view.findViewById(R.id.results_search_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false);



        recipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(requireActivity().getApplication(), recipeList, new RecipesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onRecipesItemClick(Recipe recipe) {

            }
        });
        RecipesRecyclerView.setAdapter(recipesRecyclerViewAdapter);
        RecipesRecyclerView.setLayoutManager(layoutManager);

        fragmentResultsSearchBinding.progressBar.setVisibility(View.VISIBLE);


        RecipesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            //TODO sistema qui
                            //recipeViewModel.fetchRecipes(null,null,intolerances,null,type);
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
        recipeViewModel.setLoading(false);
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentResultsSearchBinding = null;
    }
}