package it.unimib.food_alchemy.data.source.recipes;

import java.util.List;
import java.util.Set;

public abstract class BaseRecipesRemoteDataSource {
    protected RecipesCallback recipesCallback;

    public void setRecipesCallback(RecipesCallback recipesCallback) {
        this.recipesCallback = recipesCallback;
    }

    public abstract void getRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type, int page);

    public abstract void getRecipe(long id);

    public abstract void getRandomRecipe();
}
