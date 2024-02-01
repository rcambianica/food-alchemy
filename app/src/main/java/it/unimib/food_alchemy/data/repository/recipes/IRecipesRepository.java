package it.unimib.food_alchemy.data.repository.recipes;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.Result;

public interface IRecipesRepository {
    MutableLiveData<Result> fetchRecipesLiveData(String query, String diet, Set<String> intolerances, List<String> ingredients ,String type, int offset);

    MutableLiveData<Result> fetchRandomRecipe(String diet, Set<String> intolerances);

    void fetchRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type, int offset);

    MutableLiveData<Result> fetchRecipesByType(String type, int offset, long lastUpdate);
    void fetchRecipesByType(String type, int offset);

    MutableLiveData<Result> getFavoriteRecipes();

    void updateRecipe(Recipe recipe);

    void deleteFavoriteRecipes();

    /**
     * Ingredients methods
     */

    MutableLiveData<Result> fetchFridgeItems(boolean firstLoading);
    MutableLiveData<Result> getFridgeItemsSelected();
    void insertFridgeItem(Ingredient ingredient);
    void deleteFridgeItem(Ingredient ingredient);
    void updateFridgeItem(Ingredient ingredient);
    void deleteFridgeItems();
}
