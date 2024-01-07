package it.unimib.food_alchemy.data.source.recipes;

import java.util.List;

public abstract class BaseRecipesRemoteDataSource {
    protected RecipesCallback recipesCallback;

    public void setRecipesCallback(RecipesCallback recipesCallback) {
        this.recipesCallback = recipesCallback;
    }

    public abstract void getRecipes(String query, String type, String diet, List<String> intolerances, List<String> ingredients, int page);

    public abstract void getRecipe(long id);

    public abstract void getRandomRecipe();
}
