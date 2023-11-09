package it.unimib.food_alchemy.util;

//import it.unimib.worldnews.database.NewsRoomDatabase;
import android.app.Application;

import it.unimib.food_alchemy.data.database.RecipeRoomDatabase;
import it.unimib.food_alchemy.data.repository.user.IUserRepository;
import it.unimib.food_alchemy.data.repository.user.UserRepository;
import it.unimib.food_alchemy.data.service.SpoonacularApiService;
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

    private ServiceLocator() {}

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
    public RecipeRoomDatabase getRecipeDao(Application application) {
        return RecipeRoomDatabase.getDatabase(application);
    }
     **/

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

        /**
        BaseNewsLocalDataSource newsLocalDataSource =
                new NewsLocalDataSource(getRecipesDao(application), sharedPreferencesUtil,
                        dataEncryptionUtil);
         **/

        return new UserRepository(userRemoteAuthenticationDataSource,
                userDataRemoteDataSource/*, recipesLocalDataSource*/);
    }
}