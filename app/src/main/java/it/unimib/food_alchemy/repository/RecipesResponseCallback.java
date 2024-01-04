package it.unimib.food_alchemy.repository;

import java.util.List;

import it.unimib.food_alchemy.model.Recipe;

public interface RecipesResponseCallback {
    void onSuccess(List<Recipe> recipes, long lastUpdate);
    void onFailure(String errorMessage);
    void onRecipeFavoriteStatusChanged(Recipe recipe);
}
