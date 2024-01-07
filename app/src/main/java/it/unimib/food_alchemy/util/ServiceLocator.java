package it.unimib.food_alchemy.util;

import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.ID_TOKEN;

import android.app.Application;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.data.database.RecipeRoomDatabase;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.data.repository.recipes.RecipesRepository;
import it.unimib.food_alchemy.data.repository.user.IUserRepository;
import it.unimib.food_alchemy.data.repository.user.UserRepository;
import it.unimib.food_alchemy.data.service.SpoonacularApiService;
import it.unimib.food_alchemy.data.source.recipes.BaseFavoriteRecipesDataSource;
import it.unimib.food_alchemy.data.source.recipes.BaseRecipesLocalDataSource;
import it.unimib.food_alchemy.data.source.recipes.BaseRecipesRemoteDataSource;
import it.unimib.food_alchemy.data.source.recipes.FavoriteRecipesDataSource;
import it.unimib.food_alchemy.data.source.recipes.RecipesLocalDataSource;
import it.unimib.food_alchemy.data.source.recipes.RecipesRemoteDataSource;
import it.unimib.food_alchemy.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.food_alchemy.data.source.user.BaseUserDataRemoteDataSource;
import it.unimib.food_alchemy.data.source.user.UserAuthenticationRemoteDataSource;
import it.unimib.food_alchemy.data.source.user.UserDataRemoteDataSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Registry to provide the dependencies for the classes
 *  used in the application.
 */
public class ServiceLocator {

    private static volatile ServiceLocator INSTANCE = null;

    public ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (INSTANCE == null) {
            synchronized(ServiceLocator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceLocator();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * It creates an instance of SpoonacularApiService using Retrofit.
     * @return an instance of SpoonacularApiService.
     */
    public SpoonacularApiService getSpoonacularApiService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SPOONACULAR_API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(SpoonacularApiService.class);
    }

    /**
     * Returns an instance of RecipeRoomDatabase class to manage Room database.
     * @param application Param for accessing the global application state.
     * @return An instance of RecipeRoomDatabase.
     */
    public RecipeRoomDatabase getRecipeDao(Application application) {
        return RecipeRoomDatabase.getDatabase(application);
    }

    /**
     * Returns an instance of IRecipesRepository.
     * @param application Param for accessing the global application state.
     * @return An instance of IRecipesRepository.
     */
    public IRecipesRepository getRecipesRepository(Application application) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);
        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);
        BaseRecipesRemoteDataSource recipesRemoteDataSource = new RecipesRemoteDataSource(application.getString(R.string.spoonacular_api_key));
        BaseRecipesLocalDataSource recipesLocalDataSource = new RecipesLocalDataSource(getRecipeDao(application),
                sharedPreferencesUtil, dataEncryptionUtil);
        BaseFavoriteRecipesDataSource favoriteRecipesDataSource;
        try {
            favoriteRecipesDataSource = new FavoriteRecipesDataSource(dataEncryptionUtil.
                    readSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ID_TOKEN));
        } catch (GeneralSecurityException | IOException e)  {
            return null;
        }


        return new RecipesRepository(recipesRemoteDataSource,
                recipesLocalDataSource, favoriteRecipesDataSource);
    }

    /**
     * Creates an instance of IUserRepository.
     * @return An instance of IUserRepository.
     */
    public IUserRepository getUserRepository(Application application) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);

        BaseUserAuthenticationRemoteDataSource userRemoteAuthenticationDataSource =
                new UserAuthenticationRemoteDataSource();

        BaseUserDataRemoteDataSource userDataRemoteDataSource =
                new UserDataRemoteDataSource(sharedPreferencesUtil);

        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);


        BaseRecipesLocalDataSource recipesLocalDataSource =
                new RecipesLocalDataSource(getRecipeDao(application), sharedPreferencesUtil,
                        dataEncryptionUtil);


        return new UserRepository(userRemoteAuthenticationDataSource,
                userDataRemoteDataSource, recipesLocalDataSource);
    }
}