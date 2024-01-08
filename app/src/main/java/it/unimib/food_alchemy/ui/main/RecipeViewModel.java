package it.unimib.food_alchemy.ui.main;

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
    private boolean firstLoading;
    private MutableLiveData<Result> recipesListLiveData;
    private MutableLiveData<Result> favoriteRecipesListLiveData;

    public RecipeViewModel(IRecipesRepository recipesRepository)    {
        this.recipesRepository = recipesRepository;
        this.firstLoading = true;
    }

    /**
     * Return the LiveData object associated with the recipes list to the
     * Fragment/Activity.
     * @return The LiveData object associated with the recipes list.
     */
    public MutableLiveData<Result> getRecipes(String query, String type, String diet, Set<String> intolerances, List<String> ingredients, int page) {
        if(recipesListLiveData == null) {
            fetchRecipes(query, type, diet, intolerances, ingredients, page);
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

    public void fetchRecipes(String query, String type, String diet, Set<String> intolerances, List<String> ingredients, int page)    {
        recipesListLiveData = recipesRepository.fetchRecipes(query, type, diet, intolerances, ingredients, page);
    }

    /**
     * It uses the Repository to get the list of favorite recipes
     * and to associate it with the LiveData object.
     */
    private void getFavoriteRecipes(boolean firstLoading)   {
        favoriteRecipesListLiveData = recipesRepository.getFavoriteRecipes(firstLoading);
    }
    /**
     * Removes the recipe from the list of favorite recipes.
     * @param recipe The recipe to be removed from the list of favorite recipes.
     */
    public void removeFromFavorite(Recipe recipe) {
        recipesRepository.updateRecipe(recipe);
    }

    /**
     * Clears the list of favorite recipes.
     */
    public void deleteAllFavoriteRecipes() {
        recipesRepository.deleteFavoriteRecipes();
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

