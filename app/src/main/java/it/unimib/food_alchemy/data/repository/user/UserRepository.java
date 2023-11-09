package it.unimib.food_alchemy.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import java.util.Set;

import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.model.User;
import it.unimib.food_alchemy.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.food_alchemy.data.source.user.BaseUserDataRemoteDataSource;

/**
 * Manca implementazione repository recipes, di conseguenza anche recipeCallback etc...
 */
public class UserRepository implements IUserRepository, UserResponseCallback/*, RecipeCallback*/{

    private static final String TAG = UserRepository.class.getSimpleName();

    private final BaseUserAuthenticationRemoteDataSource userRemoteDataSource;
    private final BaseUserDataRemoteDataSource userDataRemoteDataSource;
    //private final BaseRecipesLocalDataSource recipesLocalDataSource;
    private final MutableLiveData<Result> userMutableLiveData;
    private final MutableLiveData<Result> userFavoriteRecipesMutableLiveData;
    private final MutableLiveData<Result> userPreferencesMutableLiveData;

    public UserRepository(BaseUserAuthenticationRemoteDataSource userRemoteDataSource,
                          BaseUserDataRemoteDataSource userDataRemoteDataSource/*,
                          BaseRecipesLocalDataSource recipesLocalDataSource*/) {
        this.userRemoteDataSource = userRemoteDataSource;
        this.userDataRemoteDataSource = userDataRemoteDataSource;
        //this.recipesLocalDataSource = recipesLocalDataSource;
        this.userMutableLiveData = new MutableLiveData<>();
        this.userPreferencesMutableLiveData = new MutableLiveData<>();
        this.userFavoriteRecipesMutableLiveData = new MutableLiveData<>();
        this.userRemoteDataSource.setUserResponseCallback(this);
        this.userDataRemoteDataSource.setUserResponseCallback(this);
        //this.recipesLocalDataSource.setRecipesCallback(this);
    }

    @Override
    public MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered) {
        if (isUserRegistered) {
            signIn(email, password);
        } else {
            signUp(email, password);
        }
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getGoogleUser(String idToken) {
        signInWithGoogle(idToken);
        return userMutableLiveData;
    }
    /*
    @Override
    public MutableLiveData<Result> getUserFavoriteRecipes(String idToken) {
        userDataRemoteDataSource.getUserFavoriteRecipes(idToken);
        return userFavoriteRecipesMutableLiveData;
    }*/

    @Override
    public MutableLiveData<Result> getUserPreferences(String idToken) {
        userDataRemoteDataSource.getUserPreferences(idToken);
        return userPreferencesMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> logout() {
        userRemoteDataSource.logout();
        return userMutableLiveData;
    }

    @Override
    public User getLoggedUser() {
        return userRemoteDataSource.getLoggedUser();
    }

    @Override
    public void signUp(String email, String password) {
        userRemoteDataSource.signUp(email, password);
    }

    @Override
    public void signIn(String email, String password) {
        userRemoteDataSource.signIn(email, password);
    }

    @Override
    public void signInWithGoogle(String token) {
        userRemoteDataSource.signInWithGoogle(token);
    }

    @Override
    public void saveUserPreferences(String diet, Set<String> intolerances, String idToken) {
        userDataRemoteDataSource.saveUserPreferences(diet, intolerances, idToken);
    }

    @Override
    public void onSuccessFromAuthentication(User user) {

    }

    @Override
    public void onFailureFromAuthentication(String message) {

    }

    @Override
    public void onSuccessFromRemoteDatabase(User user) {

    }

    @Override
    public void onSuccessFromGettingUserPreferences() {

    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {

    }

    @Override
    public void onSuccessLogout() {

    }
}
