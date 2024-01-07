package it.unimib.food_alchemy.data.source.recipes;

import java.util.List;

import it.unimib.food_alchemy.model.Recipe;

public abstract class BaseFavoriteRecipesDataSource {
    protected RecipesCallback recipesCallback;

    public void setRecipesCallback(RecipesCallback recipesCallback) {
        this.recipesCallback = recipesCallback;
    }

    public abstract void getFavoriteRecipes();
    public abstract void addFavoriteRecipes(Recipe recipes);
    public abstract void synchronizeFavoriteRecipes(List<Recipe> notSynchronizedRecipesList);
    public abstract void deleteFavoriteRecipes(Recipe recipes);
    public abstract void deleteAllFavoriteRecipes();
}
