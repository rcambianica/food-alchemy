package it.unimib.food_alchemy.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.model.Ingredient;
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
    private MutableLiveData<Result> randomRecipeMutableLiveData;
    private MutableLiveData<Result> fridgeItemsListLiveData;
    private MutableLiveData<Result> fridgeItemsSelectedLiveData;

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
    public MutableLiveData<Result> getRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type) {

        fetchRecipes(query, diet, intolerances, ingredients, type);
        return recipesListLiveData;
    }


    public MutableLiveData<Result> getByTypeRecipes(String diet, Set<String> intolerances, String type) {

            fetchRecipesByType(diet, intolerances, type);

        return recipesListLiveData;
    }

    public MutableLiveData<Result> getRandomRecipe(String diet, Set<String> intolerances) {
        if(randomRecipeMutableLiveData == null) {
            fetchRandomRecipe(diet, intolerances);
            Log.d("TEST", "getRandomRecipe view model in");
        }
        Log.d("TEST", "getRandomRecipe view model out");
        return randomRecipeMutableLiveData;
    }


    /**
     * Returns the LiveData object associated with the
     * list of favorite recipes to the Fragment/Activity.
     * @return The LiveData object associated with the list of favorite recipes.
     */
    public MutableLiveData<Result> getFavoriteRecipesLiveData() {
        if (favoriteRecipesListLiveData == null) {
            getFavoriteRecipes();
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

    public void fetchRecipesScroll(String query, String diet, Set<String> intolerances, List<String> ingredients, String type)  {
        recipesRepository.fetchRecipesScroll(query,diet,intolerances,ingredients, type, offset);
    }

    private void fetchRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type)    {
        recipesListLiveData = recipesRepository.fetchRecipesLiveData(query, diet, intolerances, ingredients, type, offset);
    }

    private void fetchRecipesByType(String diet, Set<String> intolerances, String type) {
        recipesListLiveData = recipesRepository.fetchRecipesLiveDataByType(diet, intolerances, type, offset);
    }

    private void fetchRandomRecipe(String diet, Set<String> intolerances) {
        randomRecipeMutableLiveData = recipesRepository.fetchRandomRecipeLiveData(diet, intolerances);
    }


    /**
     * It uses the Repository to get the list of favorite recipes
     * and to associate it with the LiveData object.
     */
    private void getFavoriteRecipes()   {
        favoriteRecipesListLiveData = recipesRepository.getFavoriteRecipesLiveData();
    }

    /**
     * Clears the list of favorite recipes.
     */
    public void deleteAllFavoriteRecipes() {
        recipesRepository.deleteFavoriteRecipes();
    }

    public void deleteAllRecipes(){recipesRepository.deleteAllRecipes();}

    /**
     * Ingredients methods
     */

    public MutableLiveData<Result> getFridgeItems(boolean firstLoading) {
        if(fridgeItemsListLiveData == null) {
            fetchFridgeItems(firstLoading);
        }
        return fridgeItemsListLiveData;
    }

    public MutableLiveData<Result> getFridgeItemsSelectedLiveData() {
        if(fridgeItemsSelectedLiveData == null) {
            getFridgeItemsSelected();
        }
        return fridgeItemsSelectedLiveData;
    }

    public void updateFridgeItem(Ingredient ingredient) {
        recipesRepository.updateFridgeItem(ingredient);
    }

    private void fetchFridgeItems(boolean firstLoading) {
        fridgeItemsListLiveData = recipesRepository.fetchFridgeItems(firstLoading);
    }

    private void getFridgeItemsSelected()   {
        fridgeItemsSelectedLiveData = recipesRepository.getFridgeItemsSelected();
    }

    public void insertFridgeItem(Ingredient ingredient)  {
        recipesRepository.insertFridgeItem(ingredient);
    }
    public void deleteFridgeItem(Ingredient ingredient) {
        recipesRepository.deleteFridgeItem(ingredient);
    }
    public void updateSelectedFridgeItem(Ingredient ingredient) {
        recipesRepository.updateFridgeItem(ingredient);
    }
    public void deleteAllFridgeItems()  {
        recipesRepository.deleteFridgeItems();
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

    public MutableLiveData<Result> getRandomRecipeListLiveData() {
        return randomRecipeMutableLiveData;
    }
}

