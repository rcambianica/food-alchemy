package it.unimib.food_alchemy.ui.main;

import static it.unimib.food_alchemy.util.Constants.RECIPES_NUMBER_PARAMETER;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_DIET;
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
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.RecipesRecyclerViewAdapter;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.databinding.FragmentRandomBinding;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import it.unimib.food_alchemy.model.RecipesResponse;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.util.ErrorMessagesUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

public class RandomFragment extends Fragment {

    private final String TAG = RandomFragment.class.getSimpleName();
    private FragmentRandomBinding fragmentRandomBinding;
    List<Recipe> recipeList;
    private RecipeViewModel recipeViewModel;
    private RecipesRecyclerViewAdapter recipesRecyclerViewAdapter;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private int totalItemCount; // Total number of recipes
    private int lastVisibleItem; // The position of the last visible recipe item
    private int visibleItemCount; // Number or total visible recipes items
    private final int threshold = 1;

    public RandomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RandomFragment.
     */

    public static RandomFragment newInstance() {
        return new RandomFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        IRecipesRepository recipesRepository =
                ServiceLocator.getInstance().getRecipesRepository(requireActivity().getApplication());

        if (recipesRepository != null) {
            recipeViewModel = new ViewModelProvider(requireActivity(),
                    new RecipeViewModelFactory(recipesRepository)).get(RecipeViewModel.class);
        } else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }
        recipeList = new ArrayList<>();
        recipeViewModel.setOffset(0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentRandomBinding = FragmentRandomBinding.inflate(inflater, container, false);
        return fragmentRandomBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve diet and intolerances from shared preferences
        String diet;
        Set<String> intolerances;
        diet = sharedPreferencesUtil.readStringData(SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_DIET);
        intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_INTOLERANCES);

        // Set up RecyclerView and Adapter for displaying recipes
        RecyclerView randomRecipesRecyclerView = view.findViewById(R.id.results_search_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);

        // Getting the type from the discovery button previously clicked
        String type = RandomFragmentArgs.fromBundle(getArguments()).getType();

        // Setting up the Adapter
        recipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(requireActivity().getApplication(), recipeList, new RecipesRecyclerViewAdapter.OnItemClickListener() {

            // Opening the recipe clicked
            @Override
            public void onRecipesItemClick(Recipe recipe) {
                RandomFragmentDirections.ActionRandomFragmentToRecipeFragment action =
                        RandomFragmentDirections.actionRandomFragmentToRecipeFragment(recipe);
                Navigation.findNavController(requireView()).navigate(action);
            }
        });
        randomRecipesRecyclerView.setAdapter(recipesRecyclerViewAdapter);
        randomRecipesRecyclerView.setLayoutManager(layoutManager);

        fragmentRandomBinding.progressBar.setVisibility(View.VISIBLE);

        recipeViewModel.getByTypeRecipes(diet, intolerances, type).observe(getViewLifecycleOwner(),
                result -> {
                    Log.d(TAG, "visibleItemCount" +visibleItemCount);
                    visibleItemCount = layoutManager.getChildCount();
                    if(visibleItemCount==0){
                        recipeViewModel.fetchRecipesScroll(null, diet, intolerances, null, type);
                    }

                    if (result != null) {
                        Log.d(TAG, "5");
                        if (result.isSuccess()) {
                            Log.d(TAG, "6");
                            RecipesResponse recipesResponse = ((Result.RecipesResponseSuccess) result).getData();
                            List<Recipe> fetchedRecipes = recipesResponse.getRecipesList();
                            Log.d(TAG, fetchedRecipes.toString());
                            if (!recipeViewModel.isLoading()) {
                                Log.d(TAG, "7");
                                if (recipeViewModel.isFirstLoading()) {
                                    Log.d(TAG, "8");
                                    recipeViewModel.setTotalResults(((RecipeApiResponse) recipesResponse).getTotalResults());
                                    recipeViewModel.setFirstLoading(false);
                                    this.recipeList.addAll(fetchedRecipes);

                                    recipesRecyclerViewAdapter.notifyItemRangeInserted(0,
                                            this.recipeList.size());
                                } else {
                                    Log.d(TAG, "9");
                                    recipeList.clear();
                                    recipeList.addAll(fetchedRecipes);
                                    recipesRecyclerViewAdapter.notifyDataSetChanged();
                                }
                                fragmentRandomBinding.progressBar.setVisibility(View.GONE);
                            } else {
                                Log.d(TAG, "10");
                                recipeViewModel.setLoading(false);
                                recipeViewModel.setCurrentResults(recipeList.size());

                                totalItemCount = layoutManager.getItemCount();
                                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                                visibleItemCount = layoutManager.getChildCount();

                                int initialSize = recipeList.size();

                                Log.d(TAG, "total item count" +totalItemCount);
                                        Log.d(TAG, "lastVisibleItem" +lastVisibleItem);
                                                Log.d(TAG, "visibleItemCount" +visibleItemCount);
                                                        Log.d(TAG, "lunghezza lista" +recipeList.size());

                                for (int i = 0; i < recipeList.size(); i++) {
                                    Log.d(TAG, "lunghezza lista" +recipeList.size());
                                    if (recipeList.get(i) == null) {
                                        recipeList.remove(recipeList.get(i));
                                    }
                                }
                                int startIndex = recipeViewModel.getOffset();

                                Log.d(TAG, "fetch " +fetchedRecipes.size());


                                for (int i = 0; i < fetchedRecipes.size(); i++) {
                                    Log.d(TAG, "entraaaaaaaaaaa qua");
                                    recipeList.add(fetchedRecipes.get(i));
                                }
                                recipesRecyclerViewAdapter.notifyItemRangeInserted(initialSize, recipeList.size());
                            }
                        } else {
                            Log.d(TAG, "1");
                            ErrorMessagesUtil errorMessagesUtil =
                                    new ErrorMessagesUtil(requireActivity().getApplication());
                            Snackbar.make(view, errorMessagesUtil.
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                    Snackbar.LENGTH_SHORT).show();
                            fragmentRandomBinding.progressBar.setVisibility(View.GONE);
                        }
                    }
                });

        // Adding the lazy loading logic by scrolling the recipes
        randomRecipesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isConnected = isConnected();
                Log.d(TAG, "entra");

                if (isConnected) {
                    Log.d(TAG, "1");
                    recipeViewModel.getTotalResults();
                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    visibleItemCount = layoutManager.getChildCount();

                    if (totalItemCount == visibleItemCount ||
                            (totalItemCount <= (lastVisibleItem + threshold) &&
                                    dy > 0 && !recipeViewModel.isLoading()
                            ) &&
                                    recipeViewModel.getRecipesResponseLiveData().getValue() != null
                    ) {
                        Log.d(TAG, "2");
                        MutableLiveData<Result> recipesListMutableLiveData = recipeViewModel.getRecipesResponseLiveData();

                        if (recipesListMutableLiveData.getValue() != null &&
                                recipesListMutableLiveData.getValue().isSuccess()) {
                            Log.d(TAG, "3");

                            recipeViewModel.setLoading(true);
                            recipeList.add(null);
                            recipesRecyclerViewAdapter.notifyItemRangeInserted(recipeList.size(),
                                    recipeList.size() + 1);
                            //TODO: capire se è necessaria
                            recipesRecyclerViewAdapter.notifyDataSetChanged();
                            int offset = recipeViewModel.getOffset() + RECIPES_NUMBER_PARAMETER;
                            recipeViewModel.setOffset(offset);
                            recipeViewModel.fetchRecipesScroll(null, diet, intolerances, null, type);
                        }
                    }
                }
            }
        });

        NavBackStackEntry navBackStackEntry = Navigation.
                findNavController(view).getPreviousBackStackEntry();

        // Logic to highlight the correct tab in BottomNavigationView
        if (navBackStackEntry != null &&
                navBackStackEntry.getDestination().getId() == R.id.searchFragment) {
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.homeFragment).setChecked(true);
        } else if (navBackStackEntry != null &&
                navBackStackEntry.getDestination().getId() == R.id.homeFragment) {
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.homeFragment).setChecked(true);
        }
    }

    // Checks it the device is connected to internet, return true if it is connected, false otherwise
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

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
    public void onDestroyView() {
        super.onDestroyView();
        fragmentRandomBinding = null;
    }
}
