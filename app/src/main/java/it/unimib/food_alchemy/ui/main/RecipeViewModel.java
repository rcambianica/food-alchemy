package it.unimib.food_alchemy.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.Result;

/**
 * ViewModel to manage the list of Recipes and the list of favorite Recipes
 */
public class RecipeViewModel extends ViewModel {

    private static final String TAG = RecipeViewModel.class.getSimpleName();

    private final IRecipesRepository recipesRepository;
    private int offset;
    private int currentResults;
    private int totalResults;
    private boolean isLoading;
    private boolean firstLoading;
    private MutableLiveData<Result> recipesListLiveData;
    private MutableLiveData<Result> favoriteRecipesListLiveData;

    public RecipeViewModel(IRecipesRepository recipesRepository)    {
        this.recipesRepository = recipesRepository;
        this.offset = 0;
        this.totalResults = 0;
        this.firstLoading = true;
    }

    /**
     * Return the LiveData object associated with the recipes list to the
     * Fragment/Activity.
     * @return The LiveData object associated with the recipes list.
     */
    public MutableLiveData<Result> getRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type, long lastUpdate) {
        if(recipesListLiveData == null) {
            fetchRecipes(query, diet, intolerances, ingredients,type, lastUpdate);
        }
        return recipesListLiveData;
    }

    /**
     * Returns the LiveData object associated with the
     * list of favorite recipes to the Fragment/Activity.
     * @return The LiveData object associated with the list of favorite recipes.
     */
    public MutableLiveData<Result> getFavoriteRecipesLiveData(boolean isFirstLoading) {
        if (favoriteRecipesListLiveData == null) {
            getFavoriteRecipes(isFirstLoading);
        }
        return favoriteRecipesListLiveData;
    }

    /**
     * Updates the recipe status.
     * @param recipe The recipe to be updated.
     */
    public void updateRecipe(Recipe recipe) {
        recipesRepository.updateRecipe(recipe);
    }

    public void fetchRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type)  {
        recipesRepository.fetchRecipes(query,diet,intolerances,ingredients, type, offset);
    }

    private void fetchRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type, long lastUpdate)    {
        recipesListLiveData = recipesRepository.fetchRecipes(query, diet, intolerances, ingredients, type, offset, lastUpdate);
    }

    /**
     * It uses the Repository to get the list of favorite recipes
     * and to associate it with the LiveData object.
     */
    private void getFavoriteRecipes(boolean firstLoading)   {
        favoriteRecipesListLiveData = recipesRepository.getFavoriteRecipes(firstLoading);
    }

    /**
     * Clears the list of favorite recipes.
     */
    public void deleteAllFavoriteRecipes() {
        recipesRepository.deleteFavoriteRecipes();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
    public int getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(int currentResults) {
        this.currentResults = currentResults;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isFirstLoading() {
        return firstLoading;
    }

    public void setFirstLoading(boolean firstLoading) {
        this.firstLoading = firstLoading;
    }

    public MutableLiveData<Result> getRecipesResponseLiveData() {
        return recipesListLiveData;
    }
}

