package it.unimib.food_alchemy.ui.main;

import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.repository.IRecipesRepository;

public class RecipeViewModel extends ViewModel {

    private final IRecipesRepository recipeRepository;

    public RecipeViewModel(IRecipesRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void fetchRecipes(String query, String type, String diets, List<String> intolerances, List<String> ingredients, int page, long lastUpdate) {
        recipeRepository.fetchRecipes(query, type, diets, intolerances, ingredients, page, lastUpdate);
    }

    public void fetchRecipe(long id) {
        recipeRepository.fetchRecipe(id);
    }

    public void fetchRandomRecipe() {
        recipeRepository.fetchRandomRecipe();
    }

    public void getFavoriteRecipes() {
        recipeRepository.getFavoriteRecipes();
    }

    public void updateRecipe(Recipe recipe) {
        recipeRepository.updateRecipe(recipe);
    }

    public void deleteFavoriteRecipes() {
        recipeRepository.deleteFavoriteRecipes();
    }
}

