package it.unimib.food_alchemy.data.source.recipes;

import java.util.List;
import java.util.Set;

public abstract class BaseRecipesRemoteDataSource {
    protected RecipesCallback recipesCallback;

    public void setRecipesCallback(RecipesCallback recipesCallback) {
        this.recipesCallback = recipesCallback;
    }

    public abstract void getRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type, int offset);

    public abstract void getRecipeOfTheDay(String diet, Set<String> intolerances);
}
