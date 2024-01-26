package it.unimib.food_alchemy.data.source.recipes;

import java.util.List;

import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Recipe;

public abstract class BaseFavoriteRecipesDataSource {
    protected RecipesCallback recipesCallback;

    public void setRecipesCallback(RecipesCallback recipesCallback) {
        this.recipesCallback = recipesCallback;
    }

    public abstract void getFavoriteRecipes();
    public abstract void addFavoriteRecipe(Recipe recipe);
    public abstract void synchronizeFavoriteRecipes(List<Recipe> notSynchronizedRecipesList);
    public abstract void deleteFavoriteRecipe(Recipe recipe);
    public abstract void deleteAllFavoriteRecipes();

    public abstract void getFridgeItems();
    public abstract void addFridgeItem(Ingredient ingredient);
    public abstract void deleteFridgeItem(Ingredient ingredient);
    public abstract void deleteAllFridgeItems();
}
