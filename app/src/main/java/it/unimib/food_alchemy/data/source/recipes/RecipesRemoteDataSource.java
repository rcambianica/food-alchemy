package it.unimib.food_alchemy.data.source.recipes;

import static it.unimib.food_alchemy.util.Constants.API_KEY_ERROR;
import static it.unimib.food_alchemy.util.Constants.RETROFIT_ERROR;

import androidx.annotation.NonNull;

import it.unimib.food_alchemy.data.service.SpoonacularApiService;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import it.unimib.food_alchemy.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesRemoteDataSource {
    private final SpoonacularApiService recipesApiService;
    private final String apiKey;

    public RecipesRemoteDataSource(String apiKey) {
        this.apiKey = apiKey;
        this.recipesApiService = ServiceLocator.getInstance().getSpoonacularApiService();
    }

    @Override
    public void getNews(String country, int page) {
        Call<RecipeApiResponse> newsResponseCall = recipesApiService.getNews(country, TOP_HEADLINES_PAGE_SIZE_VALUE, page, apiKey);

        newsResponseCall.enqueue(new Callback<RecipeApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApiResponse> call,
                                   @NonNull Response<RecipeApiResponse> response) {

                if (response.body() != null && response.isSuccessful()) {
                    recipesCallback.onSuccessFromRemote(response.body(), System.currentTimeMillis());
                } else {
                    recipesCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApiResponse> call, @NonNull Throwable t) {
                recipesCallback.onFailureFromRemote(new Exception(RETROFIT_ERROR));
            }
        });
    }
}
