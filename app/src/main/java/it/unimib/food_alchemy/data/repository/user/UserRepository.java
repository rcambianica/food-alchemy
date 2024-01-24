package it.unimib.food_alchemy.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.data.source.recipes.BaseRecipesLocalDataSource;
import it.unimib.food_alchemy.data.source.recipes.RecipesCallback;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.model.User;
import it.unimib.food_alchemy.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.food_alchemy.data.source.user.BaseUserDataRemoteDataSource;

/**
 * Repository class to get the user information.
 */
public class UserRepository implements IUserRepository, UserResponseCallback, RecipesCallback {

    private static final String TAG = UserRepository.class.getSimpleName();

    private final BaseUserAuthenticationRemoteDataSource userRemoteDataSource;
    private final BaseUserDataRemoteDataSource userDataRemoteDataSource;
    private final BaseRecipesLocalDataSource recipesLocalDataSource;
    private final MutableLiveData<Result> userMutableLiveData;
    private final MutableLiveData<Result> userFavoriteRecipesMutableLiveData;
    private final MutableLiveData<Result> userPreferencesMutableLiveData;
    private final MutableLiveData<Result> userFridgeItemsMutableLiveData;

    public UserRepository(BaseUserAuthenticationRemoteDataSource userRemoteDataSource,
                          BaseUserDataRemoteDataSource userDataRemoteDataSource,
                          BaseRecipesLocalDataSource recipesLocalDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
        this.userDataRemoteDataSource = userDataRemoteDataSource;
        this.recipesLocalDataSource = recipesLocalDataSource;
        this.userMutableLiveData = new MutableLiveData<>();
        this.userPreferencesMutableLiveData = new MutableLiveData<>();
        this.userFavoriteRecipesMutableLiveData = new MutableLiveData<>();
        this.userFridgeItemsMutableLiveData = new MutableLiveData<>();
        this.userRemoteDataSource.setUserResponseCallback(this);
        this.userDataRemoteDataSource.setUserResponseCallback(this);
        this.recipesLocalDataSource.setRecipesCallback(this);
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

    @Override
    public MutableLiveData<Result> getUserFavoriteRecipes(String idToken) {
        userDataRemoteDataSource.getUserFavoriteRecipes(idToken);
        return userFavoriteRecipesMutableLiveData;
    }

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
        if (user != null) {
            userDataRemoteDataSource.saveUserData(user);
        }
    }

    @Override
    public void onFailureFromAuthentication(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(User user) {
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(user);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromGettingUserFavoritesRecipes(List<Recipe> recipeList) {
        recipesLocalDataSource.insertRecipes(recipeList);
    }

    @Override
    public void onSuccessFromGettingUserPreferences() {
        userPreferencesMutableLiveData.postValue(new Result.UserResponseSuccess(null));
    }


    @Override
    public void onFailureFromRemoteDatabase(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessLogout() {
        recipesLocalDataSource.deleteAllRecipes();
    }

    @Override
    public void onSuccessFromLocal(RecipeApiResponse recipeApiResponse) {
        Result.RecipesResponseSuccess result = new Result.RecipesResponseSuccess(recipeApiResponse);
        userFavoriteRecipesMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessDeletion() {
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(null);
        userMutableLiveData.postValue(result);
    }

    /**
     * Ingredients methods
     */

    @Override
    public void onSuccessFromLocal(List<Ingredient> ingredients) {

    }

    @Override
    public void onSuccessFromLocal(Ingredient ingredient) {

    }

    @Override
    public void onFridgeItemsStatusChanged(Ingredient ingredient, List<Ingredient> fridgeItems) {

    }

    @Override
    public void onFridgeItemsStatusChanged(List<Ingredient> ingredients) {

    }

    @Override
    public void onDeleteFridgeItemsSuccess(List<Ingredient> fridgeItems) {

    }

    @Override
    public void onSuccessSynchronization() {
        userFavoriteRecipesMutableLiveData.postValue(new Result.RecipesResponseSuccess(null));
    }

    @Override
    public void onSuccessFromRemote(RecipeApiResponse recipeApiResponse) {

    }

    @Override
    public void onFailureFromRemote(Exception exception) {

    }

    @Override
    public void onFailureFromLocal(Exception exception) {

    }

    @Override
    public void onRecipesFavoriteStatusChanged(Recipe recipes, List<Recipe> favoriteRecipes) {

    }

    @Override
    public void onRecipesFavoriteStatusChanged(List<Recipe> recipes) {

    }

    @Override
    public void onDeleteFavoriteRecipesSuccess(List<Recipe> favoriteRecipes) {

    }

    @Override
    public void onSuccessFromCloudReading(List<Recipe> recipes) {

    }

    @Override
    public void onSuccessFromCloudWriting(Recipe recipes) {

    }

    @Override
    public void onFailureFromCloud(Exception exception) {

    }
}
