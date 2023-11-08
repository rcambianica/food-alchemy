package it.unimib.food_alchemy.util;

//import it.unimib.worldnews.database.NewsRoomDatabase;
import it.unimib.food_alchemy.data.service.SpoonacularApiService;
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

    /*public RecipeRoomDatabase getRecipeDao(Application application) {
        return RecipeRoomDatabase.getDatabase(application);
    }*/
}