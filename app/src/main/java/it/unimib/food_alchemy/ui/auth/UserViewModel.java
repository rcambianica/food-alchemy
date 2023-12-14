package it.unimib.food_alchemy.ui.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.data.repository.user.IUserRepository;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.model.User;

public class UserViewModel extends ViewModel {

    private final IUserRepository userRepository;
    private MutableLiveData<Result> userMutableLiveData;
    private MutableLiveData<Result> userFavoriteRecipesMutableLiveData;
    private MutableLiveData<Result> userPreferencesMutableLiveData;
    private MutableLiveData<Result> userFridgeItemsMutableLiveData;
    private boolean authenticationError;

    public UserViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
        authenticationError = false;
    }

    public MutableLiveData<Result> getUserMutableLiveData(
            String email, String password, boolean isUserRegistered) {
        if (userMutableLiveData == null) {
            getUserData(email, password, isUserRegistered);
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Result> getGoogleUserMutableLiveData(String token) {
        if (userMutableLiveData == null) {
            getUserData(token);
        }
        return userMutableLiveData;
    }


    public MutableLiveData<Result> getUserFavoriteRecipesMutableLiveData(String idToken) {
        if (userFavoriteRecipesMutableLiveData == null) {
            getUserFavoriteRecipes(idToken);
        }
        return userFavoriteRecipesMutableLiveData;
    }

    public void saveUserPreferences(String diet, Set<String> intolerances, String idToken) {
        if (idToken != null) {
            userRepository.saveUserPreferences(diet, intolerances, idToken);
        }
    }

    public MutableLiveData<Result> getUserPreferences(String idToken) {
        if (idToken != null) {
            userPreferencesMutableLiveData = userRepository.getUserPreferences(idToken);
        }
        return userPreferencesMutableLiveData;
    }

    public void saveUserFridgeItems(List<Ingredient> ingredientList, String idToken)    {
        if(idToken != null) {
            userRepository.saveUserFridgeItems(ingredientList, idToken);
        }
    }

    public MutableLiveData<Result> getUserFridgeItems(String idToken)    {
        if(idToken != null) {
            userFridgeItemsMutableLiveData = userRepository.getUserFridgeItems(idToken);
        }
        return userFridgeItemsMutableLiveData;
    }

    public User getLoggedUser() {
        return userRepository.getLoggedUser();
    }

    public MutableLiveData<Result> logout() {
        if (userMutableLiveData == null) {
            userMutableLiveData = userRepository.logout();
        } else {
            userRepository.logout();
        }

        return userMutableLiveData;
    }


    private void getUserFavoriteRecipes(String idToken) {
        userFavoriteRecipesMutableLiveData = userRepository.getUserFavoriteRecipes(idToken);
    }

    public void getUser(String email, String password, boolean isUserRegistered) {
        userRepository.getUser(email, password, isUserRegistered);
    }

    public boolean isAuthenticationError() {
        return authenticationError;
    }

    public void setAuthenticationError(boolean authenticationError) {
        this.authenticationError = authenticationError;
    }

    private void getUserData(String email, String password, boolean isUserRegistered) {
        userMutableLiveData = userRepository.getUser(email, password, isUserRegistered);
    }

    private void getUserData(String token) {
        userMutableLiveData = userRepository.getGoogleUser(token);
    }
}