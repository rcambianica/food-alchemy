package it.unimib.food_alchemy.data.repository.recipes;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.Result;

public interface IRecipesRepository {
    MutableLiveData<Result> fetchRecipes(String query, String type, String diet, List<String> intolerances, List<String> ingredients, int page, long lastUpdate);

    void fetchRecipes(String query, String type, String diet, List<String> intolerances, List<String> ingredients, int page);

    void fetchRecipe(long id);

    void fetchRandomRecipe();

    MutableLiveData<Result> getFavoriteRecipes(boolean firstLoading);

    void updateRecipe(Recipe recipe);

    void deleteFavoriteRecipes();
}
