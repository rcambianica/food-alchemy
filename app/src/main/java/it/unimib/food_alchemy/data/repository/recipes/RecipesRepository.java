package it.unimib.food_alchemy.data.repository.recipes;

import static it.unimib.food_alchemy.util.Constants.FRESH_TIMEOUT;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.R;
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
    private final MutableLiveData<Result> randomRecipesMutableLiveData;
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
        randomRecipesMutableLiveData = new MutableLiveData<>();
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

    public MutableLiveData<Result> fetchRecipesLiveData(String query, String diet, Set<String> intolerances, List<String> ingredients, String type, int offset) {
        recipesRemoteDataSource.getRecipes(query, diet,intolerances,ingredients, type, offset);
        return allRecipesMutableLiveData;
    }

    public MutableLiveData<Result> fetchRandomRecipeLiveData(String diet, Set<String> intolerances) {
        recipesRemoteDataSource.getRecipeOfTheDay(diet,intolerances);
        return randomRecipesMutableLiveData;
    }

    @Override
    public void fetchRecipesScroll(String query, String diet, Set<String> intolerances, List<String> ingredients, String type, int offset) {
        recipesRemoteDataSource.getRecipes(query, diet, intolerances, ingredients, type, offset);
    }

    @Override
    public MutableLiveData<Result> fetchRecipesLiveDataByType(String diet, Set<String> intolerances, String type, int offset){
        recipesLocalDataSource.getRecipesByType(type);

        //recipesRemoteDataSource.getRecipes(null, diet, intolerances,null, type, offset);

        return allRecipesMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getFavoriteRecipesLiveData() {
        backupFavoriteRecipesDataSource.getFavoriteRecipes();
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
    public void deleteAllRecipes() {
        recipesLocalDataSource.deleteAllRecipes();
    }

    @Override
    public void deleteFavoriteRecipes() {
        recipesLocalDataSource.deleteFavoriteRecipes();
    }

    @Override
    public MutableLiveData<Result> fetchFridgeItems(boolean isFirstLoading) {
        if(isFirstLoading)  {
            backupFavoriteRecipesDataSource.getFridgeItems();
        } else {
            recipesLocalDataSource.getFridgeItems();
        }

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
        backupFavoriteRecipesDataSource.addFridgeItem(ingredient);
    }

    @Override
    public void deleteFridgeItem(Ingredient ingredient) {
        recipesLocalDataSource.deleteFridgeItem(ingredient);
        backupFavoriteRecipesDataSource.deleteFridgeItem(ingredient);
    }

    @Override
    public void updateFridgeItem(Ingredient ingredient) {
        recipesLocalDataSource.updateFridgeItem(ingredient);

    }

    @Override
    public void deleteFridgeItems() {
        recipesLocalDataSource.deleteAllFridgeItems();
        backupFavoriteRecipesDataSource.deleteAllFridgeItems();
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
        if (recipesApiResponse.isFromRandom()) {
            if (randomRecipesMutableLiveData.getValue() != null && randomRecipesMutableLiveData.getValue().isSuccess()) {
                List<Recipe> recipesList = ((Result.RecipesResponseSuccess) randomRecipesMutableLiveData.getValue()).getData().getRecipesList();
                recipesList.addAll(recipesApiResponse.getRecipesList());
                recipesApiResponse.setRecipeList(recipesList);
                Result.RecipesResponseSuccess result = new Result.RecipesResponseSuccess(recipesApiResponse);
                randomRecipesMutableLiveData.postValue(result);
            } else {
                Result.RecipesResponseSuccess result = new Result.RecipesResponseSuccess(recipesApiResponse);
                randomRecipesMutableLiveData.postValue(result);
            }
        } else {
                Result.RecipesResponseSuccess result = new Result.RecipesResponseSuccess(recipesApiResponse);
                Log.d(TAG, result.getData().toString());
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
    public void onRecipesFavoriteStatusChanged(Recipe recipe, List<Recipe> favoriteRecipes) {
        Result allRecipesResult = allRecipesMutableLiveData.getValue();

        if(allRecipesResult != null && allRecipesResult.isSuccess())    {
            List<Recipe> oldAllRecipes = ((Result.RecipesResponseSuccess)allRecipesResult).getData().getRecipesList();
            for(int i = 0; i < oldAllRecipes.size(); i++)   {
                if(oldAllRecipes.get(i).getId() == recipe.getId()){
                    oldAllRecipes.set(i,recipe);
                    allRecipesMutableLiveData.postValue(allRecipesResult);
                }
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
        if(allRecipesResult != null && allRecipesResult.isSuccess())    {
            List<Recipe> oldAllRecipes = ((Result.RecipesResponseSuccess)allRecipesResult).getData().getRecipesList();
            for(Recipe r: favoriteRecipes)  {
                for(int i = 0; i < oldAllRecipes.size(); i++)   {
                    if(oldAllRecipes.get(i).getId() == r.getId()){
                        oldAllRecipes.set(i, r);
                    }
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
    public void onSuccessFromCloudReadingFavoriteRecipes(List<Recipe> recipesList) {
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
    public void onSuccessFromCloudWritingFavoriteRecipes(Recipe recipes) {
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
        }
        fridgeItemsSelectedMutableLiveData.postValue(new Result.IngredientsResponseSuccess(new IngredientsResponse(fridgeItems)));
    }

    @Override
    public void onFridgeItemsStatusChanged(List<Ingredient> ingredients) {
        Result.IngredientsResponseSuccess result = new Result.IngredientsResponseSuccess(new IngredientsResponse(ingredients));
        fridgeItemsSelectedMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromCloudReadingFridgeItems(List<Ingredient> ingredients) {
        // Fridge Items got from Realtime Database the first time
        if (ingredients != null) {

            recipesLocalDataSource.insertFridgeItems(ingredients);
            allFridgeItemsMutableLiveData.postValue(new Result.IngredientsResponseSuccess(new IngredientsResponse(ingredients)));
        }
    }

    @Override
    public void onSuccessFromCloudWritingFridgeItems(Ingredient ingredient) {
        backupFavoriteRecipesDataSource.getFridgeItems();
    }
}
