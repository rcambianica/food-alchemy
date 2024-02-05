package it.unimib.food_alchemy.ui.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Set;

import it.unimib.food_alchemy.data.repository.user.IUserRepository;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.model.User;

public class UserViewModel extends ViewModel {

    private final IUserRepository userRepository;
    private MutableLiveData<Result> userMutableLiveData;
    private MutableLiveData<Result> userFavoriteRecipesMutableLiveData;
    private MutableLiveData<Result> resetPassword;
    private MutableLiveData<Result> userPreferencesMutableLiveData;
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

    public MutableLiveData<Result> forgotPassword(String mail) {

        resetPassword = userRepository.forgotPassword(mail);
        return resetPassword;
    }

    private void getUserData(String email, String password, boolean isUserRegistered) {
        userMutableLiveData = userRepository.getUser(email, password, isUserRegistered);
    }

    private void getUserData(String token) {
        userMutableLiveData = userRepository.getGoogleUser(token);
    }
}