package it.unimib.food_alchemy.data.source.recipes;

import java.util.List;

import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.IngredientsResponse;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;

public interface RecipesCallback {
    void onSuccessFromRemote(RecipeApiResponse recipeApiResponse);
    void onFailureFromRemote(Exception exception);
    void onSuccessFromLocal(RecipeApiResponse recipeApiResponse);
    void onFailureFromLocal(Exception exception);
    void onRecipesFavoriteStatusChanged(Recipe recipes, List<Recipe> favoriteRecipes);
    void onRecipesFavoriteStatusChanged(List<Recipe> recipes);
    void onDeleteFavoriteRecipesSuccess(List<Recipe> favoriteRecipes);
    void onSuccessFromCloudReadingFavoriteRecipes(List<Recipe> recipes);
    void onSuccessFromCloudWritingFavoriteRecipes(Recipe recipes);
    void onFailureFromCloud(Exception exception);
    void onSuccessSynchronization();
    void onSuccessDeletion();

    /**
     * Ingredients methods
     */

    void onSuccessFromLocal(List<Ingredient> ingredients);
    void onFridgeItemsStatusChanged(Ingredient ingredient, List<Ingredient> fridgeItems);
    void onFridgeItemsStatusChanged(List<Ingredient> ingredients);
    void onSuccessFromCloudReadingFridgeItems(List<Ingredient> ingredients);
    void onSuccessFromCloudWritingFridgeItems(Ingredient ingredient);

}
