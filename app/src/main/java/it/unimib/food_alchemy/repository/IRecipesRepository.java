package it.unimib.food_alchemy.repository;

import androidx.room.Query;

import java.util.List;

import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Recipe;

public interface IRecipesRepository {
    void fetchRecipes(String query, String type, String diets, List<String> intolerances, List<String> ingredients, int page, long lastUpdate);

    void fetchRecipe(long id);

    void fetchRandomRecipe();

    void getFavoriteRecipes();

    void updateRecipe(Recipe recipe);

    void deleteFavoriteRecipes();
}
