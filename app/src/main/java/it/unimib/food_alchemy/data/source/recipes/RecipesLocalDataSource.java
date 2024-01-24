package it.unimib.food_alchemy.data.source.recipes;

import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.LAST_UPDATE;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.UNEXPECTED_ERROR;

import android.util.Log;

import java.util.List;

import it.unimib.food_alchemy.data.database.RecipeDao;
import it.unimib.food_alchemy.data.database.RecipeRoomDatabase;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import it.unimib.food_alchemy.util.DataEncryptionUtil;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

public class RecipesLocalDataSource extends BaseRecipesLocalDataSource{

    private final RecipeDao recipesDao;
    private final SharedPreferencesUtil sharedPreferencesUtil;
    private final DataEncryptionUtil dataEncryptionUtil;

    public RecipesLocalDataSource(RecipeRoomDatabase recipesRoomDatabase,
                               SharedPreferencesUtil sharedPreferencesUtil,
                               DataEncryptionUtil dataEncryptionUtil
    ) {
        this.recipesDao = recipesRoomDatabase.recipeDao();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.dataEncryptionUtil = dataEncryptionUtil;
    }

    /**
     * Gets the recipe from the local database.
     * The method is executed with an ExecutorService defined in RecipeRoomDatabase class
     * because the database access cannot been executed in the main thread.
     */

    @Override
    public void getRecipes() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            RecipeApiResponse recipesApiResponse = new RecipeApiResponse();
            recipesApiResponse.setRecipeList(recipesDao.getAllRecipes());
            recipesCallback.onSuccessFromLocal(recipesApiResponse);
        });
    }

    @Override
    public void getFavoriteRecipes() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Recipe> favoriteRecipes = recipesDao.getFavoriteRecipes();
            recipesCallback.onRecipesFavoriteStatusChanged(favoriteRecipes);
        });
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            if (recipe != null) {
                int rowUpdatedCounter = recipesDao.updateSingleFavoriteRecipe(recipe);
                // It means that the update succeeded because only one row had to be updated
                if (rowUpdatedCounter == 1) {
                    Recipe updatedRecipe = recipesDao.getRecipe(recipe.getId());
                    recipesCallback.onRecipesFavoriteStatusChanged(updatedRecipe, recipesDao.getFavoriteRecipes());
                } else {
                    recipesCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
                }
            } else {
                // When the user deleted all favorite recipe from remote
                //TODO Check if it works fine and there are not drawbacks
                List<Recipe> allRecipes = recipesDao.getAllRecipes();
                for (Recipe r : allRecipes) {
                    r.setSynchronized(false);
                    recipesDao.updateSingleFavoriteRecipe(r);
                }
            }
        });
    }

    @Override
    public void deleteFavoriteRecipes() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Recipe> favoriteRecipes = recipesDao.getFavoriteRecipes();
            for (Recipe recipe : favoriteRecipes) {
                recipe.setFavorite(false);
            }
            int updatedRowsNumber = recipesDao.updateListFavoritesRecipes(favoriteRecipes);

            // It means that the update succeeded because the number of updated rows is
            // equal to the number of the original favorite recipe
            if (updatedRowsNumber == favoriteRecipes.size()) {
                recipesCallback.onDeleteFavoriteRecipesSuccess(favoriteRecipes);
            } else {
                recipesCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
            }
        });
    }

    /**
     * Saves the recipe in the local database.
     * The method is executed with an ExecutorService defined in RecipeRoomDatabase class
     * because the database access cannot been executed in the main thread.
     * @param recipesApiResponse the list of recipe to be written in the local database.
     */
    @Override
    public void insertRecipes(RecipeApiResponse recipesApiResponse) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            // Reads the recipe from the database
            List<Recipe> allRecipes = recipesDao.getAllRecipes();
            List<Recipe> recipesList = recipesApiResponse.getRecipesList();
            if (recipesList != null) {

                recipesDao.insertRecipesList(recipesList);


                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME,
                        LAST_UPDATE, String.valueOf(System.currentTimeMillis()));

                recipesCallback.onSuccessFromLocal(recipesApiResponse);
            }
            else    {
                Log.d("recipes_local_data_source", "recipesList nulla");
            }
        });
    }

    /**
     * Saves the recipe in the local database.
     * The method is executed with an ExecutorService defined in RecipeRoomDatabase class
     * because the database access cannot been executed in the main thread.
     * @param recipesList the list of recipe to be written in the local database.
     */
    @Override
    public void insertRecipes(List<Recipe> recipesList) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            if (recipesList != null) {

                // Reads the recipe from the database
                List<Recipe> allRecipes = recipesDao.getAllRecipes();

                recipesDao.insertRecipesList(recipesList);

                RecipeApiResponse recipesApiResponse = new RecipeApiResponse();
                recipesApiResponse.setRecipeList(recipesList);
                recipesCallback.onSuccessSynchronization();
            }
        });
    }

    @Override
    public void deleteAllRecipes() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            int recipesCounter = recipesDao.getAllRecipes().size();
            int recipesDeletedRecipes = recipesDao.deleteAllRecipes();

            // It means that everything has been deleted
            if (recipesCounter == recipesDeletedRecipes) {
                sharedPreferencesUtil.deleteAll(SHARED_PREFERENCES_FILE_NAME);
                dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                recipesCallback.onSuccessDeletion();
            }
        });
    }

    @Override
    public void getIngredients() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            recipesCallback.onSuccessFromLocal(recipesDao.getAllIngredients());
        });
    }

    @Override
    public void getFridgeItems() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Ingredient> fridgeItems = recipesDao.getFridgeItems();
            recipesCallback.onFridgeItemsStatusChanged(fridgeItems);
        });
    }

    @Override
    public void updateIngredient(Ingredient ingredient) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            int rowUpdatedCounter = recipesDao.updateSingleFridgeItem(ingredient);

            if(rowUpdatedCounter == 1)  {
                Ingredient updatedIngredient = recipesDao.getIngredient(ingredient.getId());
                recipesCallback.onFridgeItemsStatusChanged(updatedIngredient, recipesDao.getFridgeItems());
            } else {
                recipesCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
            }
        });
    }

    @Override
    public void deleteFridgeItems() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Ingredient> fridgeItems = recipesDao.getFridgeItems();
            for(Ingredient ingredient : fridgeItems)    {
                ingredient.setFridgeItem(false);
            }
            int updatedRowsNumber = recipesDao.updateListFridgeItems(fridgeItems);

            if(updatedRowsNumber == fridgeItems.size()) {
                recipesCallback.onDeleteFridgeItemsSuccess(fridgeItems);
            } else {
                recipesCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
            }
        });
    }

    @Override
    public void insertIngredient(Ingredient ingredient) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {

            List<Ingredient> allIngredients = recipesDao.getAllIngredients();
            if(ingredient != null)  {

                long insertedIngredientId = recipesDao.insertIngredient(ingredient);
                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME,
                        LAST_UPDATE, String.valueOf(System.currentTimeMillis()));

                recipesCallback.onSuccessFromLocal(ingredient);
            }
        });
    }
}
