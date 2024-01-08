package it.unimib.food_alchemy.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.RecipesRecyclerViewAdapter;
import it.unimib.food_alchemy.databinding.FragmentRandomBinding;
import it.unimib.food_alchemy.model.Recipe;

public class RandomFragment extends Fragment {

    private FragmentRandomBinding fragmentRandomBinding;
    List<Recipe> recipeList;
    private RecipeViewModel recipeViewModel;

    public RandomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RandomFragment.
     */

    public static RandomFragment newInstance() {
        return new RandomFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentRandomBinding = FragmentRandomBinding.inflate(inflater, container, false);
        return fragmentRandomBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        RecyclerView randomRecipesRecyclerView = fragmentRandomBinding.randomRecyclerview;

        RecipesRecyclerViewAdapter adapter = new RecipesRecyclerViewAdapter(requireActivity().getApplication(), recipeList, new RecipesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onRecipesItemClick(Recipe recipe) {
                Navigation.findNavController(requireView()).navigate(R.id.action_randomFragment_to_recipeFragment);
            }
        });
        randomRecipesRecyclerView.setAdapter(adapter);
        randomRecipesRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        NavBackStackEntry navBackStackEntry = Navigation.
                findNavController(view).getPreviousBackStackEntry();

        // Logic to highlight the correct tab in BottomNavigationView when the user
        // goes to NewsDetailFragment from a tab which is neither CountryNewsFragment
        // nor FavoriteNewsFragment
        if (navBackStackEntry != null &&
                navBackStackEntry.getDestination().getId() == R.id.searchFragment) {
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.homeFragment).setChecked(true);
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentRandomBinding = null;
    }
}