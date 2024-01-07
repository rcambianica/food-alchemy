package it.unimib.food_alchemy.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.FavoritesRecyclerViewAdapter;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.databinding.FragmentFavoritesBinding;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.util.Constants;
import it.unimib.food_alchemy.util.ErrorMessagesUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

/**
 * Fragment that shows the favorites recipes
 */
public class FavoritesFragment extends Fragment {

    private static final String TAG = FavoritesFragment.class.getSimpleName();
    private FragmentFavoritesBinding fragmentFavoritesBinding;
    private List<Recipe> recipesList;
    private FavoritesRecyclerViewAdapter favoritesRecyclerViewAdapter;
    private RecipeViewModel recipesViewModel;
    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {return new FavoritesFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipesList= new ArrayList<>();
        IRecipesRepository recipesRepository = ServiceLocator.getInstance().
                getRecipesRepository(requireActivity().getApplication());
        recipesViewModel = new ViewModelProvider(requireActivity(),
                new RecipeViewModelFactory(recipesRepository)).get(RecipeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFavoritesBinding = FragmentFavoritesBinding.inflate(inflater,container,false);
        return fragmentFavoritesBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewFavorites = fragmentFavoritesBinding.favoritesRecyclerView;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerViewFavorites.setLayoutManager(layoutManager);


         favoritesRecyclerViewAdapter =
                 new FavoritesRecyclerViewAdapter(recipesList, new FavoritesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onFavClick(Recipe recipe) {
                Snackbar.make(view,recipe.getTitle(),Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteButtonPressed(int position) {
                Snackbar.make(view,"Size"+recipesList.size(),Snackbar.LENGTH_SHORT).show();
            }
        });
        recyclerViewFavorites.setAdapter(favoritesRecyclerViewAdapter);

        SharedPreferencesUtil sharedPreferencesUtil =
                new SharedPreferencesUtil(requireActivity().getApplication());

        boolean isFirstLoading = sharedPreferencesUtil.readBooleanData(Constants.SHARED_PREFERENCES_FILE_NAME,
                Constants.SHARED_PREFERENCES_FIRST_LOADING);

        // Observe the LiveData associated with the MutableLiveData containing the favorite news
        // returned by the method getFavoriteNewsLiveData() of NewsViewModel class.
        // Pay attention to which LifecycleOwner you give as value to
        // the method observe(LifecycleOwner, Observer).
        // In this case, getViewLifecycleOwner() refers to
        // androidx.fragment.app.FragmentViewLifecycleOwner and not to the Fragment itself.
        // You can read more details here: https://stackoverflow.com/a/58663143/4255576
        recipesViewModel.getFavoriteRecipesLiveData(isFirstLoading).observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result.isSuccess()) {
                    recipesList.clear();
                    recipesList.addAll(((Result.RecipesResponseSuccess)result).getData().getRecipesList());
                    favoritesRecyclerViewAdapter.notifyDataSetChanged();
                    if (isFirstLoading) {
                        sharedPreferencesUtil.writeBooleanData(Constants.SHARED_PREFERENCES_FILE_NAME,
                                Constants.SHARED_PREFERENCES_FIRST_LOADING, false);
                    }
                } else {
                    ErrorMessagesUtil errorMessagesUtil =
                            new ErrorMessagesUtil(requireActivity().getApplication());
                    Snackbar.make(view, errorMessagesUtil.
                                    getErrorMessage(((Result.Error)result).getMessage()),
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentFavoritesBinding = null;
    }
}