package it.unimib.food_alchemy.data.source.recipes;

import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.LAST_UPDATE;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.UNEXPECTED_ERROR;


import java.util.ArrayList;
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
    public void getRecipesByType(String type) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            RecipeApiResponse recipesApiResponse = new RecipeApiResponse();
            recipesApiResponse.setRecipeList(recipesDao.getRecipesByType(type));
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
                for (Recipe recipe : allRecipes) {
                    for(int i = 0; i < recipesList.size(); i++) {
                        if (recipesList.get(i).getId() == recipe.getId())   {
                            recipesList.set(i, recipe);
                        }
                    }
                }

                List<Long> insertedRecipesIds = recipesDao.insertRecipesList(recipesList);
                for (int i = 0; i < recipesList.size(); i++) {
                    recipesList.get(i).setId(insertedRecipesIds.get(i));
                }

                sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME,
                        LAST_UPDATE, String.valueOf(System.currentTimeMillis()));

                recipesCallback.onSuccessFromLocal(recipesApiResponse);
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
                List<Recipe> allRecipes = recipesDao.getAllRecipes();

                for (Recipe recipe : allRecipes) {
                    for(int i = 0; i < recipesList.size(); i++) {
                        if (recipesList.get(i).getId() == recipe.getId()) {
                            recipe.setSynchronized(true);
                            recipesList.set(i, recipe);
                        }
                    }
                }

                List<Long> insertedNewsIds = recipesDao.insertRecipesList(recipesList);
                for (int i = 0; i < recipesList.size(); i++) {
                    recipesList.get(i).setId(insertedNewsIds.get(i));
                }

                recipesCallback.onSuccessSynchronization();
            }
        });
    }

    @Override
    public void deleteAllRecipesLogout() {
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
    public void deleteAllRecipes() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            int recipesCounter = recipesDao.getAllRecipes().size();
            int recipesDeletedRecipes = recipesDao.deleteAllRecipes();

            // It means that everything has been deleted
            if (recipesCounter == recipesDeletedRecipes) {
                recipesCallback.onSuccessDeletion();
            }
        });
    }


    @Override
    public void getFridgeItems() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> recipesCallback.onSuccessFromLocal(recipesDao.getAllFridgeItems()));
    }

    @Override
    public void getFridgeItemsSelected() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> recipesCallback.onFridgeItemsStatusChanged(recipesDao.getFridgeItemsSelected()));
    }

    @Override
    public void updateFridgeItem(Ingredient ingredient) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            int rowUpdatedCounter = recipesDao.updateSingleFridgeItem(ingredient);

            if(rowUpdatedCounter == 1)  {
                Ingredient updatedIngredient = recipesDao.getFridgeItem(ingredient.getId());
                recipesCallback.onFridgeItemsStatusChanged(updatedIngredient, recipesDao.getFridgeItemsSelected());
            } else {
                recipesCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
            }
        });
    }

    @Override
    public void deleteAllFridgeItems() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            int fridgeItemsCounter = recipesDao.getAllFridgeItems().size();
            int deletedFridgeItems = recipesDao.deleteAllFridgeItems();

            // It means that everything has been deleted
            if (fridgeItemsCounter == deletedFridgeItems) {
                recipesCallback.onSuccessFromLocal(recipesDao.getAllFridgeItems());
            }
        });
    }

    @Override
    public void insertFridgeItem(Ingredient ingredient) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {

            List<Ingredient> allIngredients = recipesDao.getAllFridgeItems();
            if(ingredient != null)  {
                recipesDao.insertIngredient(ingredient);
                List<Ingredient> results = new ArrayList<>(allIngredients);
                results.add(ingredient);
                recipesCallback.onSuccessFromLocal(results);
            }
        });
    }

    @Override
    public void insertFridgeItems(List<Ingredient> ingredientsList) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            if (ingredientsList != null) {
                recipesDao.insertIngredientsList(ingredientsList);
                recipesCallback.onSuccessSynchronization();
            }
        });
    }

    @Override
    public void deleteFridgeItem(Ingredient ingredient) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Ingredient> allIngredients = recipesDao.getAllFridgeItems();
            if(ingredient != null)  {
                recipesDao.deleteIngredient(ingredient);
                for(int pos = 0; pos < allIngredients.size(); pos++)   {
                    if(allIngredients.get(pos).getId() == ingredient.getId())    {
                        allIngredients.remove(pos);
                        break;
                    }
                }
                recipesCallback.onSuccessFromLocal(allIngredients);
            }
        });
    }
}
