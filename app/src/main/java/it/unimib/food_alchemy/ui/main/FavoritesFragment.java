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
import it.unimib.food_alchemy.util.ServiceLocator;

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
        recipesViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentFavoritesBinding = null;
    }
}