package it.unimib.food_alchemy.ui.search;

import static it.unimib.food_alchemy.util.Constants.RECIPES_NUMBER_PARAMETER;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_DIET;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import it.unimib.food_alchemy.databinding.FragmentResultsSearchBinding;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import it.unimib.food_alchemy.model.RecipesResponse;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.model.SearchParameters;
import it.unimib.food_alchemy.ui.main.RecipeViewModel;
import it.unimib.food_alchemy.ui.main.RecipeViewModelFactory;
import it.unimib.food_alchemy.ui.search.ResultsSearchFragmentArgs;
import it.unimib.food_alchemy.util.ErrorMessagesUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

public class ResultsSearchFragment extends Fragment {

    private final String TAG = ResultsSearchFragment.class.getSimpleName();
    private FragmentResultsSearchBinding fragmentResultsSearchBinding;
    List<Recipe> recipeList;
    private RecipeViewModel recipeViewModel;
    private RecipesRecyclerViewAdapter recipesRecyclerViewAdapter;
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

        fragmentResultsSearchBinding = FragmentResultsSearchBinding.inflate(inflater, container, false);
        return fragmentResultsSearchBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handleOnBackPressed();


        // Set up RecyclerView and Adapter for displaying recipes
        RecyclerView randomRecipesRecyclerView = view.findViewById(R.id.results_search_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);

        // Getting the parameters chosen into the search fragment
        SearchParameters parameters = ResultsSearchFragmentArgs.fromBundle(getArguments()).getParameters();

        // Setting up the Adapter
        recipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(requireActivity().getApplication(), recipeList, new RecipesRecyclerViewAdapter.OnItemClickListener() {

            // Opening the recipe clicked
            @Override
            public void onRecipesItemClick(Recipe recipe) {
                ResultsSearchFragmentDirections.ActionResultsSearchFragmentToRecipeFragment2 action =
                        ResultsSearchFragmentDirections.actionResultsSearchFragmentToRecipeFragment2(recipe);
                Navigation.findNavController(requireView()).navigate(action);
            }
        });
        randomRecipesRecyclerView.setAdapter(recipesRecyclerViewAdapter);
        randomRecipesRecyclerView.setLayoutManager(layoutManager);

        recipeViewModel.getRecipes(null, parameters.getDiet(), parameters.getIntolerances(), parameters.getIngredients(), parameters.getType()).observe(getViewLifecycleOwner(),
                result -> {
                    visibleItemCount = layoutManager.getChildCount();

                    if (result != null) {
                        if (result.isSuccess()) {
                            RecipesResponse recipesResponse = ((Result.RecipesResponseSuccess) result).getData();
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
                        MutableLiveData<Result> recipesListMutableLiveData = recipeViewModel.getRecipesResponseLiveData();

                        if (recipesListMutableLiveData.getValue() != null &&
                                recipesListMutableLiveData.getValue().isSuccess()) {

                            recipeViewModel.setLoading(true);
                            recipeList.add(null);
                            recipesRecyclerViewAdapter.notifyItemRangeInserted(recipeList.size(),
                                    recipeList.size() + 1);
                            //TODO: capire se è necessaria
                            recipesRecyclerViewAdapter.notifyDataSetChanged();
                            int offset = recipeViewModel.getOffset() + RECIPES_NUMBER_PARAMETER;
                            recipeViewModel.setOffset(offset);
                            recipeViewModel.fetchRecipesScroll(null, parameters.getDiet(), parameters.getIntolerances(), parameters.getIngredients(), parameters.getType());
                        }
                    }
                }
            }
        });

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
        fragmentResultsSearchBinding = null;
    }

    private void handleOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                recipeViewModel.getRecipesResponseLiveData().setValue(null);
                if (isEnabled()) {
                    remove();
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    };
}