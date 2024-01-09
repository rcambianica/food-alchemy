package it.unimib.food_alchemy.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.databinding.FragmentRecipeBinding;
import it.unimib.food_alchemy.model.AnalyzedInstruction;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.InstructionStep;
import it.unimib.food_alchemy.model.Recipe;

public class RecipeFragment extends Fragment {

    private final String TAG = FragmentRecipeBinding.class.getSimpleName();
    private FragmentRecipeBinding fragmentRecipeBinding;

    public RecipeFragment() {
        // Required empty public constructor
    }
    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRecipeBinding = FragmentRecipeBinding.inflate(inflater,container,false);
        return fragmentRecipeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Recipe recipe = RecipeFragmentArgs.fromBundle(getArguments()).getRecipe();

        Glide.with(fragmentRecipeBinding.imageRecipe.getContext())
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.baseline_access_time_24)
                .into(fragmentRecipeBinding.imageRecipe);

        fragmentRecipeBinding.bigTitle.setText(recipe.getTitle());
        fragmentRecipeBinding.ingredients.setText(formatIngredients(recipe.getIngredients()));

        fragmentRecipeBinding.instructions.setText(formatInstructions(recipe.getInstructions()));

        fragmentRecipeBinding.backButton.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigateUp();
        });

        NavBackStackEntry navBackStackEntry = Navigation.
                findNavController(view).getPreviousBackStackEntry();

        if (navBackStackEntry != null &&
                navBackStackEntry.getDestination().getId() == R.id.homeFragment) {
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.homeFragment).setChecked(true);
        } else if (navBackStackEntry != null &&
                navBackStackEntry.getDestination().getId() == R.id.randomFragment) {
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.homeFragment).setChecked(true);
        }
    }

    private String formatIngredients(List<Ingredient> ingredientList)  {
        String out="";
        for (int i = 0; i < ingredientList.size(); i++)    {
            out = out.concat(ingredientList.get(i).toString());
        }
        return out;
    }
    private String formatInstructions(List<AnalyzedInstruction> instructionList)  {
        String out="";
        for (int i = 0; i < instructionList.size(); i++)    {
            List<InstructionStep> steps = instructionList.get(i).getSteps();
            for (int y = 0; y < steps.size(); y++) {
                out = String.valueOf(i) + ". " + out.concat(steps.get(y).toString());
            }
        }
        return out;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentRecipeBinding = null;
    }
}