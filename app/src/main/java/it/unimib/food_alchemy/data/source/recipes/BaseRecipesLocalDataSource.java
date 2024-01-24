package it.unimib.food_alchemy.data.source.recipes;

import java.util.List;

import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;

public abstract class BaseRecipesLocalDataSource {

    protected RecipesCallback recipesCallback;

    public void setRecipesCallback(RecipesCallback recipesCallback) {
        this.recipesCallback = recipesCallback;
    }

    /**
     * Recipes methods
     */
    public abstract void getRecipes();
    public abstract void getFavoriteRecipes();
    public abstract void updateRecipe(Recipe recipe);
    public abstract void deleteFavoriteRecipes();
    public abstract void insertRecipes(RecipeApiResponse recipesApiResponse);
    public abstract void insertRecipes(List<Recipe> recipes);
    public abstract void deleteAllRecipes();
}
