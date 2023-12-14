package it.unimib.food_alchemy.data.repository.user;

import java.util.List;

import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.User;

public interface UserResponseCallback {
    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);
    void onSuccessFromRemoteDatabase(List<Recipe> recipeList);
    void onSuccessFromGettingUserPreferences();
    void onSuccessFromGettingUserFridgeItems();
    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout();
}
