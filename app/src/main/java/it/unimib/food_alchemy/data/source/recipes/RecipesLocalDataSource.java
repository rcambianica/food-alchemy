package it.unimib.food_alchemy.data.source.recipes;

import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.UNEXPECTED_ERROR;

import java.util.List;

import it.unimib.food_alchemy.data.database.RecipeDao;
import it.unimib.food_alchemy.data.database.RecipeRoomDatabase;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import it.unimib.food_alchemy.util.DataEncryptionUtil;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

public class RecipesLocalDataSource {

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
    public void getNews() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            //TODO Fix this instruction
            RecipeApiResponse recipesApiResponse = new RecipeApiResponse();
            recipesApiResponse.setResults(recipesDao.getAll());
            recipesCallback.onSuccessFromLocal(recipesApiResponse);
        });
    }

    @Override
    public void getFavoriteNews() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Recipe> favoriteNews = recipesDao.getFavoriteRecipes();
            recipesCallback.onNewsFavoriteStatusChanged(favoriteNews);
        });
    }

    @Override
    public void updateNews(Recipe recipe) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            if (recipe != null) {
                int rowUpdatedCounter = recipesDao.updateRecipe(recipe);
                // It means that the update succeeded because only one row had to be updated
                if (rowUpdatedCounter == 1) {
                    Recipe updatedNews = recipesDao.getRecipeById(recipe.getId());
                    recipesCallback.onNewsFavoriteStatusChanged(updatedNews, recipesDao.getFavoriteRecipes());
                } else {
                    recipesCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
                }
            } else {
                // When the user deleted all favorite recipe from remote
                //TODO Check if it works fine and there are not drawbacks
                List<Recipe> allRecipes = recipesDao.getAll();
                for (Recipe n : allRecipes) {
                    n.setSynchronized(false);
                    recipesDao.updateRecipe(n);
                }
            }
        });
    }

    @Override
    public void deleteFavoriteNews() {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Recipe> favoriteNews = recipesDao.getFavoriteRecipes();
            for (Recipe recipe : favoriteNews) {
                recipe.setFavorite(false);
            }
            int updatedRowsNumber = recipesDao.updateRecipes(favoriteNews);

            // It means that the update succeeded because the number of updated rows is
            // equal to the number of the original favorite recipe
            if (updatedRowsNumber == favoriteNews.size()) {
                recipesCallback.onDeleteFavoriteNewsSuccess(favoriteNews);
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
    public void insertNews(RecipeApiResponse recipesApiResponse) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            // Reads the recipe from the database
            List<Recipe> allRecipes = recipesDao.getAll();
            List<Recipe> recipesList = recipesApiResponse.getRecipesList();

            if (recipesList != null) {

                // Checks if the recipe just downloaded has already been downloaded earlier
                // in order to preserve the recipe status (marked as favorite or not)
                for (Recipe recipe : allRecipes) {
                    // This check works because Recipe and NewsSource classes have their own
                    // implementation of equals(Object) and hashCode() methods
                    if (recipesList.contains(recipe)) {
                        // The primary key and the favorite status is contained only in the Recipe objects
                        // retrieved from the database, and not in the Recipe objects downloaded from the
                        // Web Service. If the same recipe was already downloaded earlier, the following
                        // line of code replaces the the Recipe object in recipesList with the corresponding
                        // Recipe object saved in the database, so that it has the primary key and the
                        // favorite status.
                        recipesList.set(recipesList.indexOf(recipe), recipe);
                    }
                }

                // Writes the recipe in the database and gets the associated primary keys
                List<Long> insertedRecipesIds = recipesDao.insertAll(recipesList);
                for (int i = 0; i < recipesList.size(); i++) {
                    // Adds the primary key to the corresponding object Recipe just downloaded so that
                    // if the user marks the recipe as favorite (and vice-versa), we can use its id
                    // to know which recipe in the database must be marked as favorite/not favorite
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
    public void insertNews(List<Recipe> recipesList) {
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> {
            if (recipesList != null) {

                // Reads the recipe from the database
                List<Recipe> allRecipes = recipesDao.getAll();

                // Checks if the recipe just downloaded has already been downloaded earlier
                // in order to preserve the recipe status (marked as favorite or not)
                for (Recipe recipe : allRecipes) {
                    // This check works because Recipe and NewsSource classes have their own
                    // implementation of equals(Object) and hashCode() methods
                    if (recipesList.contains(recipe)) {
                        // The primary key and the favorite status is contained only in the Recipe objects
                        // retrieved from the database, and not in the Recipe objects downloaded from the
                        // Web Service. If the same recipe was already downloaded earlier, the following
                        // line of code replaces the the Recipe object in recipesList with the corresponding
                        // Recipe object saved in the database, so that it has the primary key and the
                        // favorite status.
                        recipe.setSynchronized(true);
                        recipesList.set(recipesList.indexOf(recipe), recipe);
                    }
                }

                // Writes the recipe in the database and gets the associated primary keys
                List<Long> insertedRecipesIds = recipesDao.insertAll(recipesList);
                for (int i = 0; i < recipesList.size(); i++) {
                    // Adds the primary key to the corresponding object Recipe just downloaded so that
                    // if the user marks the recipe as favorite (and vice-versa), we can use its id
                    // to know which recipe in the database must be marked as favorite/not favorite
                    recipesList.get(i).setId(insertedRecipesIds.get(i));
                }

                RecipeApiResponse recipesApiResponse = new RecipeApiResponse();
                recipesApiResponse.setResults(recipesList);
                recipesCallback.onSuccessSynchronization();
            }
        });
    }
}
