package it.unimib.food_alchemy.data.source.user;

import java.util.Set;

import it.unimib.food_alchemy.data.repository.user.UserResponseCallback;
import it.unimib.food_alchemy.model.User;

/**
 * Classe per scrivere in un database in remoto, Firebase Realtime Database
 */
public abstract class BaseUserDataRemoteDataSource {
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void saveUserData(User user);
    public abstract void getUserFavoriteRecipes(String idToken);
    public abstract void getUserPreferences(String idToken);
    public abstract void saveUserPreferences(String diet, Set<String> intolerances, String idToken);
}

