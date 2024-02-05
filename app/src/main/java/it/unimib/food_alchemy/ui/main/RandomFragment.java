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

    // Remember scroll position
    private int savedListPosition = RecyclerView.NO_POSITION;
    private int savedPositionOffset = 0;

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

        // Restore to last viewed recipe (if necessary)
        if (savedListPosition != RecyclerView.NO_POSITION) {
            layoutManager.scrollToPositionWithOffset(savedListPosition, savedPositionOffset);
        }

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

                // Save the last visible recipe
                // to be able to restore the position after coming back
                savedListPosition = layoutManager.findFirstVisibleItemPosition();
                savedPositionOffset = layoutManager.findViewByPosition(savedListPosition).getTop();
            }
        });
        randomRecipesRecyclerView.setAdapter(recipesRecyclerViewAdapter);
        randomRecipesRecyclerView.setLayoutManager(layoutManager);

        recipeViewModel.getByTypeRecipes(diet, intolerances, type).observe(getViewLifecycleOwner(),
                result -> {
                    visibleItemCount = layoutManager.getChildCount();

                    if (result != null && savedListPosition == RecyclerView.NO_POSITION) {
                        if (result.isSuccess()) {
                            RecipesResponse recipesResponse = ((Result.RecipesResponseSuccess) result).getData();
                            recipeViewModel.setTotalResults(((RecipeApiResponse) recipesResponse).getTotalResults());
                            List<Recipe> fetchedRecipes = recipesResponse.getRecipesList();
                            if (!recipeViewModel.isLoading()) {
                                if (recipeViewModel.isFirstLoading()) {
                                    recipeViewModel.setTotalResults(((RecipeApiResponse) recipesResponse).getTotalResults());
                                    recipeViewModel.setFirstLoading(false);
                                    this.recipeList.addAll(fetchedRecipes);

                                    recipesRecyclerViewAdapter.notifyItemRangeInserted(0,
                                            this.recipeList.size());
                                } else {
                                    recipeList.clear();
                                    recipeList.addAll(fetchedRecipes);
                                    recipesRecyclerViewAdapter.notifyDataSetChanged();
                                }
                            } else {
                                recipeViewModel.setLoading(false);
                                recipeViewModel.setCurrentResults(recipeList.size());
                                int initialSize = recipeList.size();

                                for (int i = 0; i < recipeList.size(); i++) {
                                    if (recipeList.get(i) == null) {
                                        recipeList.remove(recipeList.get(i));
                                    }
                                }

                                recipeList.addAll(fetchedRecipes);
                                recipesRecyclerViewAdapter.notifyDataSetChanged();
                                recipesRecyclerViewAdapter.notifyItemRangeInserted(initialSize, recipeList.size());
                            }
                        } else {
                            ErrorMessagesUtil errorMessagesUtil =
                                    new ErrorMessagesUtil(requireActivity().getApplication());
                            Snackbar.make(view, errorMessagesUtil.
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

        // Adding the lazy loading logic by scrolling the recipes
        randomRecipesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isConnected = isConnected();

                if (isConnected) {
                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    visibleItemCount = layoutManager.getChildCount();

                    if (totalItemCount == visibleItemCount ||
                            (totalItemCount <= (lastVisibleItem + threshold) &&
                                    dy > 0 && !recipeViewModel.isLoading()
                            ) &&
                                    recipeViewModel.getRecipesResponseLiveData().getValue() != null
                    ) {
                        MutableLiveData<Result> recipesListMutableLiveData = recipeViewModel.getRecipesResponseLiveData();

                        if (recipesListMutableLiveData.getValue() != null &&
                                recipesListMutableLiveData.getValue().isSuccess()) {
                            savedListPosition = RecyclerView.NO_POSITION;
                            recipeViewModel.setLoading(true);
                            recipeList.add(null);
                            recipesRecyclerViewAdapter.notifyItemRangeInserted(recipeList.size(),
                                    recipeList.size() + 1);
                            recipesRecyclerViewAdapter.notifyDataSetChanged();

                            // Correctly setting the offset based on the number of recipes already seen
                            int offset = (lastVisibleItem/10)*10 + RECIPES_NUMBER_PARAMETER;
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
        recipeViewModel.getRecipesResponseLiveData().setValue(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentRandomBinding = null;
    }
}
