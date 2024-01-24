package it.unimib.food_alchemy.data.repository.recipes;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.Result;

public interface IRecipesRepository {
    MutableLiveData<Result> fetchRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients ,String type, int offset, long lastUpdate);

    void fetchRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type, int offset);

    MutableLiveData<Result> getFavoriteRecipes(boolean firstLoading);

    void updateRecipe(Recipe recipe);

    void deleteFavoriteRecipes();
}
