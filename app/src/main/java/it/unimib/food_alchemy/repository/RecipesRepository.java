package it.unimib.food_alchemy.repository;

import static it.unimib.food_alchemy.util.Constants.RECIPES_NUMBER_PARAMETER;
import static it.unimib.food_alchemy.util.Constants.REFRESH_TIMEOUT;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import it.unimib.food_alchemy.BuildConfig;
import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.data.database.RecipeDao;
import it.unimib.food_alchemy.data.database.RecipeRoomDatabase;
import it.unimib.food_alchemy.data.service.SpoonacularApiService;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import it.unimib.food_alchemy.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesRepository implements IRecipesRepository {
    private static final String TAG = RecipesRepository.class.getSimpleName();
    private final Application application;
    private final SpoonacularApiService recipesApiService;
    private final RecipeDao recipesDao;
    private final RecipesResponseCallback recipesResponseCallback;

    public RecipesRepository(Application application, RecipesResponseCallback recipesResponseCallback) {
        this.application = application;
        this.recipesApiService = ServiceLocator.getInstance().getSpoonacularApiService();
        RecipeRoomDatabase recipesRoomDatabase = ServiceLocator.getInstance().getRecipeDao(application);
        this.recipesDao = recipesRoomDatabase.recipeDao();
        this.recipesResponseCallback = recipesResponseCallback;
    }

    @Override
    public void fetchRecipes(String query, String type, String diets, List<String> intolerances, List<String> ingredients, int page, long lastUpdate) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastUpdate > REFRESH_TIMEOUT) {
            Call<RecipeApiResponse> responseCall =
                    recipesApiService.searchRecipes(
                            query,
                            diets,
                            String.join(",", intolerances),
                            String.join(",", ingredients),
                            type,
                            null,
                            page * RECIPES_NUMBER_PARAMETER,
                            RECIPES_NUMBER_PARAMETER,
                            false,
                            false,
                            true,
                            BuildConfig.SPOONACULAR_API_KEY
                    );

            responseCall.enqueue(new Callback<RecipeApiResponse>() {
                @Override
                public void onResponse(@NonNull Call<RecipeApiResponse> call,
                                       @NonNull Response<RecipeApiResponse> response) {

                    if (response.body() != null && response.isSuccessful()) {
                        List<Recipe> recipes = response.body().getResults();
                        saveDataInDatabase(recipes);
                        recipesResponseCallback.onSuccess(recipes, System.currentTimeMillis());
                    } else {
                        recipesResponseCallback.onFailure(application.getString(R.string.error_retrieving_recipes));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RecipeApiResponse> call, @NonNull Throwable t) {
                    recipesResponseCallback.onFailure(t.getMessage());
                }
            });
        } else {
            recipesResponseCallback.onFailure(application.getString(R.string.error_retrieving_recipes));
        }
    }

    @Override
    public void fetchRecipe(long id) {
        Recipe localRecipe = recipesDao.getRecipeById(id);
        if (localRecipe == null) {
            Call<Recipe> responseCall =
                    recipesApiService.getRecipeInformation(
                            id,
                            false,
                            BuildConfig.SPOONACULAR_API_KEY
                    );
            responseCall.enqueue(new Callback<Recipe>() {
                @Override
                public void onResponse(@NonNull Call<Recipe> call,
                                       @NonNull Response<Recipe> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        List<Recipe> recipes = (List<Recipe>) response.body();
                        saveDataInDatabase(recipes);
                        recipesResponseCallback.onSuccess(recipes, System.currentTimeMillis());
                    } else {
                        recipesResponseCallback.onFailure(application.getString(R.string.error_retrieving_recipes));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Recipe> call, @NonNull Throwable t) {
                    recipesResponseCallback.onFailure(t.getMessage());
                }
            });
        }else{
            recipesResponseCallback.onSuccess((List<Recipe>) localRecipe, System.currentTimeMillis());
        }
    }

    @Override
    public void fetchRandomRecipe() {
        Call<RecipeApiResponse> responseCall =
                recipesApiService.searchRecipes(
                        null,
                        null,
                        null,
                        null,
                        null,
                        "random",
                        0,
                        1,
                        false,
                        false,
                        true,
                        BuildConfig.SPOONACULAR_API_KEY
                );

        responseCall.enqueue(new Callback<RecipeApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApiResponse> call,
                                   @NonNull Response<RecipeApiResponse> response) {

                if (response.body() != null && response.isSuccessful()) {
                    List<Recipe> recipes = response.body().getResults();
                    saveDataInDatabase(recipes);
                    recipesResponseCallback.onSuccess(recipes, System.currentTimeMillis());
                } else {
                    Recipe localRecipe = recipesDao.getRandomRecipe();
                    recipesResponseCallback.onSuccess((List<Recipe>) localRecipe, System.currentTimeMillis());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApiResponse> call, @NonNull Throwable t) {
                Recipe localRecipe = recipesDao.getRandomRecipe();
                recipesResponseCallback.onSuccess((List<Recipe>) localRecipe, System.currentTimeMillis());
            }
        });
    }

    @Override
    public void getFavoriteRecipes() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipesResponseCallback.onSuccess(recipesDao.getFavoriteRecipes(), System.currentTimeMillis());
        });
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipesDao.updateRecipe(recipe);
            recipesResponseCallback.onRecipeFavoriteStatusChanged(recipe);
        });
    }

    @Override
    public void deleteFavoriteRecipes() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipesDao.deleteFavoriteRecipes();
            recipesResponseCallback.onSuccess(recipesDao.getFavoriteRecipes(), System.currentTimeMillis());
        });
    }

    /**
     * Saves the recipes in the local database.
     * The method is executed with an ExecutorService defined in RecipesRoomDatabase class
     * because the database access cannot been executed in the main thread.
     * @param recipesList the list of recipes to be written in the local database.
     */
    private void saveDataInDatabase(List<Recipe> recipesList) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipesDao.insertAll(recipesList);
            recipesResponseCallback.onSuccess(recipesList, System.currentTimeMillis());
        });
    }
}
