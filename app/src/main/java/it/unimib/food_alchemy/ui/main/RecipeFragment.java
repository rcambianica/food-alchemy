package it.unimib.food_alchemy.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import java.util.List;


import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.databinding.FragmentRecipeBinding;
import it.unimib.food_alchemy.model.AnalyzedInstruction;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.InstructionStep;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.util.Constants;
import it.unimib.food_alchemy.util.ErrorMessagesUtil;
import it.unimib.food_alchemy.util.ServiceLocator;

public class RecipeFragment extends Fragment {
    private FragmentRecipeBinding fragmentRecipeBinding;
    private RecipeViewModel recipeViewModel;

    private List<Recipe> recipesList;
    public RecipeFragment() {
        // Required empty public constructor
    }
    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IRecipesRepository recipesRepository =
                ServiceLocator.getInstance().getRecipesRepository(requireActivity().getApplication());

        if(recipesRepository != null)   {
            recipeViewModel = new ViewModelProvider(
                    requireActivity(),
                    new RecipeViewModelFactory(recipesRepository)).get(RecipeViewModel.class);
        }   else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }
        recipesList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRecipeBinding = FragmentRecipeBinding.inflate(inflater,container,false);
        return fragmentRecipeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Recipe recipe = RecipeFragmentArgs.fromBundle(getArguments()).getRecipe();
        List<String> strings = formatIngredients(recipe.getIngredients());
        Glide.with(fragmentRecipeBinding.imageRecipe.getContext())
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.baseline_access_time_24)
                .into(fragmentRecipeBinding.imageRecipe);
        fragmentRecipeBinding.bigTitle.setText(recipe.getTitle());
        fragmentRecipeBinding.quantity.setText(strings.get(0));
        fragmentRecipeBinding.ingredients.setText(strings.get(1));
        fragmentRecipeBinding.instructions.setText(formatInstructions(recipe.getInstructions()));

        recipeViewModel.getFavoriteRecipesLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result.isSuccess()) {
                    recipesList.clear();
                    recipesList.addAll(((Result.RecipesResponseSuccess)result).getData().getRecipesList());
                    if(recipesList.contains(recipe)){
                        Log.d("TAG", "preferito");
                        recipe.setFavorite(true);
                    }

                    setCheck(recipe);
                } else {
                    ErrorMessagesUtil errorMessagesUtil =
                            new ErrorMessagesUtil(requireActivity().getApplication());
                    Snackbar.make(view, errorMessagesUtil.
                                    getErrorMessage(((Result.Error)result).getMessage()),
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        fragmentRecipeBinding.backButton.setOnClickListener(v -> Navigation.findNavController(requireView()).navigateUp());

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
        } else if (navBackStackEntry != null &&
                navBackStackEntry.getDestination().getId() == R.id.favoritesFragment) {
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.favoritesFragment).setChecked(true);
        }

        fragmentRecipeBinding.favoritesButton.setOnClickListener(v -> {
            recipe.setFavorite(!recipe.isFavorite());
            setCheck(recipe);
            recipeViewModel.updateRecipe(recipe);
            if(!recipe.isFavorite())
                Navigation.findNavController(requireView()).navigateUp();
        });

    }
    private void setCheck(Recipe recipe) {
        if(recipe.isFavorite()) {
            fragmentRecipeBinding.favoritesButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.baseline_star_24));
        } else {
            fragmentRecipeBinding.favoritesButton.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.star_fill0_wght400_grad0_opsz24));
        }
    }

    private List<String> formatIngredients(List<Ingredient> ingredientList)  {
        List<String> strings = new ArrayList<>();
        String amountAndUnit = "";
        String name="";
        for (int i = 0; i < ingredientList.size(); i++)    {
            amountAndUnit = amountAndUnit.concat(ingredientList.get(i).getAmount() + " " + ingredientList.get(i).getUnit() + "\n");
            name = name.concat(ingredientList.get(i).getTitle() + "\n");
        }
        strings.add(amountAndUnit);
        strings.add(name);
        return strings;
    }
    private String formatInstructions(List<AnalyzedInstruction> instructionList)  {
        String out="";
        for (int i = 0; i < instructionList.size(); i++)    {
            List<InstructionStep> steps = instructionList.get(i).getSteps();
            for (int y = 1; y < steps.size(); y++) {
                out = out.concat(y + ". " + steps.get(y).toString());
            }
        }
        if (out.length() == 0) {
            out = "No instructions available";
        }
        return out;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentRecipeBinding = null;
    }
}