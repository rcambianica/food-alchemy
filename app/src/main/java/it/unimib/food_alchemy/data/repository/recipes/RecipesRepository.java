package it.unimib.food_alchemy.data.repository.recipes;

import static it.unimib.food_alchemy.util.Constants.FRESH_TIMEOUT;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.data.source.recipes.BaseFavoriteRecipesDataSource;
import it.unimib.food_alchemy.data.source.recipes.BaseRecipesLocalDataSource;
import it.unimib.food_alchemy.data.source.recipes.BaseRecipesRemoteDataSource;
import it.unimib.food_alchemy.data.source.recipes.RecipesCallback;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.IngredientsResponse;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import it.unimib.food_alchemy.model.RecipesResponse;
import it.unimib.food_alchemy.model.Result;

public class RecipesRepository implements IRecipesRepository, RecipesCallback {
    private static final String TAG = RecipesRepository.class.getSimpleName();

    private final MutableLiveData<Result> allRecipesMutableLiveData;
    private final MutableLiveData<Result> favoriteRecipesMutableLiveData;
    private final MutableLiveData<Result> allFridgeItemsMutableLiveData;
    private final MutableLiveData<Result> fridgeItemsSelectedMutableLiveData;
    private final BaseRecipesRemoteDataSource recipesRemoteDataSource;
    private final BaseRecipesLocalDataSource recipesLocalDataSource;
    private final BaseFavoriteRecipesDataSource backupFavoriteRecipesDataSource;


    public RecipesRepository(BaseRecipesRemoteDataSource recipesRemoteDataSource,
                             BaseRecipesLocalDataSource recipesLocalDataSource,
                             BaseFavoriteRecipesDataSource favoriteRecipesDataSource) {

        allRecipesMutableLiveData = new MutableLiveData<>();
        favoriteRecipesMutableLiveData = new MutableLiveData<>();
        allFridgeItemsMutableLiveData = new MutableLiveData<>();
        fridgeItemsSelectedMutableLiveData = new MutableLiveData<>();
        this.recipesRemoteDataSource = recipesRemoteDataSource;
        this.recipesLocalDataSource = recipesLocalDataSource;
        this.backupFavoriteRecipesDataSource = favoriteRecipesDataSource;
        this.recipesRemoteDataSource.setRecipesCallback(this);
        this.recipesLocalDataSource.setRecipesCallback(this);
        this.backupFavoriteRecipesDataSource.setRecipesCallback(this);
    }

    public MutableLiveData<Result> fetchRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type, int offset, long lastUpdate) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdate > FRESH_TIMEOUT)    {
            recipesRemoteDataSource.getRecipes(query, diet,intolerances,ingredients, type, offset);
        } else {
            recipesLocalDataSource.getRecipes();
        }



        return allRecipesMutableLiveData;
    }

    @Override
    public void fetchRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type, int offset) {
        recipesRemoteDataSource.getRecipes(query, diet, intolerances, ingredients, type, offset);
    }

    @Override
    public MutableLiveData<Result> getFavoriteRecipes(boolean isFirstLoading) {
        // The first time the user launches the app, check if she
        // has previously saved favorite recipes on the cloud
        if (isFirstLoading) {
            backupFavoriteRecipesDataSource.getFavoriteRecipes();
        } else {
            recipesLocalDataSource.getFavoriteRecipes();
        }
        return favoriteRecipesMutableLiveData;
    }



    public void updateRecipe(Recipe recipe) {
        recipesLocalDataSource.updateRecipe(recipe);
        if (recipe.isFavorite()) {
            backupFavoriteRecipesDataSource.addFavoriteRecipe(recipe);
        } else {
            backupFavoriteRecipesDataSource.deleteFavoriteRecipe(recipe);
        }
    }

    @Override
    public void deleteFavoriteRecipes() {
        recipesLocalDataSource.deleteFavoriteRecipes();
    }

    @Override
    public MutableLiveData<Result> fetchFridgeItems() {
        recipesLocalDataSource.getFridgeItems();
        return allFridgeItemsMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getFridgeItemsSelected() {
        recipesLocalDataSource.getFridgeItemsSelected();
        return fridgeItemsSelectedMutableLiveData;
    }

    @Override
    public void insertFridgeItem(Ingredient ingredient) {
        recipesLocalDataSource.insertFridgeItem(ingredient);
    }

    @Override
    public void deleteFridgeItem(Ingredient ingredient) {
        recipesLocalDataSource.deleteFridgeItem(ingredient);
    }

    @Override
    public void updateFridgeItem(Ingredient ingredient) {
        recipesLocalDataSource.updateFridgeItem(ingredient);
    }

    @Override
    public void deleteFridgeItems() {
        recipesLocalDataSource.deleteAllFridgeItems();
    }

    @Override
    public void onSuccessFromRemote(RecipeApiResponse RecipeApiResponse) {
        recipesLocalDataSource.insertRecipes(RecipeApiResponse);
    }

    @Override
    public void onFailureFromRemote(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        allRecipesMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLocal(RecipeApiResponse recipesApiResponse) {
        if (allRecipesMutableLiveData.getValue() != null && allRecipesMutableLiveData.getValue().isSuccess()) {
            List<Recipe> recipesList = ((Result.RecipesResponseSuccess) allRecipesMutableLiveData.getValue()).getData().getRecipesList();
            recipesList.addAll(recipesApiResponse.getRecipesList());
            recipesApiResponse.setRecipeList(recipesList);
            Result.RecipesResponseSuccess result = new Result.RecipesResponseSuccess(recipesApiResponse);
            allRecipesMutableLiveData.postValue(result);
        } else {
            Result.RecipesResponseSuccess result = new Result.RecipesResponseSuccess(recipesApiResponse);
            allRecipesMutableLiveData.postValue(result);
        }
    }

    @Override
    public void onFailureFromLocal(Exception exception) {
        Result.Error resultError = new Result.Error(exception.getMessage());
        allRecipesMutableLiveData.postValue(resultError);
        favoriteRecipesMutableLiveData.postValue(resultError);
    }

    @Override
    public void onRecipesFavoriteStatusChanged(Recipe recipes, List<Recipe> favoriteRecipes) {
        Result allRecipesResult = allRecipesMutableLiveData.getValue();

        if (allRecipesResult != null && allRecipesResult.isSuccess()) {
            List<Recipe> oldAllRecipes = ((Result.RecipesResponseSuccess)allRecipesResult).getData().getRecipesList();
            if (oldAllRecipes.contains(recipes)) {
                oldAllRecipes.set(oldAllRecipes.indexOf(recipes), recipes);
                allRecipesMutableLiveData.postValue(allRecipesResult);
            }
        }
        favoriteRecipesMutableLiveData.postValue(new Result.RecipesResponseSuccess(new RecipesResponse(favoriteRecipes)));
    }

    @Override
    public void onRecipesFavoriteStatusChanged(List<Recipe> recipesList) {

        List<Recipe> notSynchronizedRecipesList = new ArrayList<>();

        for (Recipe recipes : recipesList) {
            if (!recipes.isSynchronized()) {
                notSynchronizedRecipesList.add(recipes);
            }
        }

        if (!notSynchronizedRecipesList.isEmpty()) {
            backupFavoriteRecipesDataSource.synchronizeFavoriteRecipes(notSynchronizedRecipesList);
        }

        favoriteRecipesMutableLiveData.postValue(new Result.RecipesResponseSuccess(new RecipesResponse(recipesList)));
    }

    @Override
    public void onDeleteFavoriteRecipesSuccess(List<Recipe> favoriteRecipes) {
        Result allRecipesResult = allRecipesMutableLiveData.getValue();

        if (allRecipesResult != null && allRecipesResult.isSuccess()) {
            List<Recipe> oldAllRecipes = ((Result.RecipesResponseSuccess)allRecipesResult).getData().getRecipesList();
            for (Recipe recipes : favoriteRecipes) {
                if (oldAllRecipes.contains(recipes)) {
                    oldAllRecipes.set(oldAllRecipes.indexOf(recipes), recipes);
                }
            }
            allRecipesMutableLiveData.postValue(allRecipesResult);
        }

        if (favoriteRecipesMutableLiveData.getValue() != null &&
                favoriteRecipesMutableLiveData.getValue().isSuccess()) {
            favoriteRecipes.clear();
            Result.RecipesResponseSuccess result = new Result.RecipesResponseSuccess(new RecipesResponse(favoriteRecipes));
            favoriteRecipesMutableLiveData.postValue(result);
        }

        backupFavoriteRecipesDataSource.deleteAllFavoriteRecipes();
    }

    @Override
    public void onSuccessFromCloudReading(List<Recipe> recipesList) {
        // Favorite recipes got from Realtime Database the first time
        if (recipesList != null) {
            for (Recipe recipes : recipesList) {
                recipes.setSynchronized(true);
            }
            recipesLocalDataSource.insertRecipes(recipesList);
            favoriteRecipesMutableLiveData.postValue(new Result.RecipesResponseSuccess(new RecipesResponse(recipesList)));
        }
    }

    @Override
    public void onSuccessFromCloudWriting(Recipe recipes) {
        if (recipes != null && !recipes.isFavorite()) {
            recipes.setSynchronized(false);
        }
        recipesLocalDataSource.updateRecipe(recipes);
        backupFavoriteRecipesDataSource.getFavoriteRecipes();
    }

    @Override
    public void onSuccessSynchronization() {
        Log.d(TAG, "Recipe synchronized from remote");
    }

    @Override
    public void onFailureFromCloud(Exception exception) {
    }

    @Override
    public void onSuccessDeletion() {

    }

    /**
     * Ingredients methods
     */

    @Override
    public void onSuccessFromLocal(List<Ingredient> ingredients) {
        Result.IngredientsResponseSuccess result = new Result.IngredientsResponseSuccess(new IngredientsResponse(ingredients));
        allFridgeItemsMutableLiveData.postValue(result);
    }

    @Override
    public void onFridgeItemsStatusChanged(Ingredient ingredient, List<Ingredient> fridgeItems) {
        Result allIngredientsResult = allFridgeItemsMutableLiveData.getValue();

        if(allIngredientsResult != null && allIngredientsResult.isSuccess())    {
            allFridgeItemsMutableLiveData.postValue(allIngredientsResult);
        } else {
            Log.d(TAG, "ELSE");
        }
        fridgeItemsSelectedMutableLiveData.postValue(new Result.IngredientsResponseSuccess(new IngredientsResponse(fridgeItems)));
    }

    @Override
    public void onFridgeItemsStatusChanged(List<Ingredient> ingredients) {
        Result.IngredientsResponseSuccess result = new Result.IngredientsResponseSuccess(new IngredientsResponse(ingredients));
        fridgeItemsSelectedMutableLiveData.postValue(result);
    }
}
