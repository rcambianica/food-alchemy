package it.unimib.food_alchemy.data.source.recipes;

import static it.unimib.food_alchemy.util.Constants.API_KEY_ERROR;
import static it.unimib.food_alchemy.util.Constants.RECIPES_NUMBER_PARAMETER;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.data.service.SpoonacularApiService;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import it.unimib.food_alchemy.util.ServiceLocator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesRemoteDataSource extends BaseRecipesRemoteDataSource{
    private final SpoonacularApiService recipesApiService;
    private final String apiKey;

    public RecipesRemoteDataSource(String apiKey) {
        this.apiKey = apiKey;
        this.recipesApiService = ServiceLocator.getInstance().getSpoonacularApiService();
    }

    @Override
    public void getRecipes(String query, String diet, Set<String> intolerances, List<String> ingredients, String type,  int offset) {

        Call<RecipeApiResponse> recipeResponseCall = recipesApiService
                .searchRecipes(query,
                        diet,
                        intolerances == null ? null : String.join(",", intolerances),
                        ingredients == null ? null : String.join(",",ingredients),
                        type,
                        null,
                        offset,
                        RECIPES_NUMBER_PARAMETER,
                        true,
                        true,
                        true,
                        apiKey);
        recipeResponseCall.enqueue(new Callback<RecipeApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApiResponse> call, @NonNull Response<RecipeApiResponse> response) {
                if(response.body() != null && response.isSuccessful())  {
                    recipesCallback.onSuccessFromRemote(response.body());
                } else {
                    recipesCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApiResponse> call, @NonNull Throwable t) {
                recipesCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
            }
        });
    }

    @Override
    public void getRecipeOfTheDay() {
        Call<RecipeApiResponse> recipeResponseCall = recipesApiService
                .getRecipeOfTheDay(1,apiKey);

        recipeResponseCall.enqueue(new Callback<RecipeApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeApiResponse> call, @NonNull Response<RecipeApiResponse> response) {
                if(response.body() != null && response.isSuccessful())  {
                    recipesCallback.onSuccessFromRemote(response.body());
                } else {
                    recipesCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RecipeApiResponse> call, @NonNull Throwable t) {
                recipesCallback.onFailureFromRemote(new Exception(API_KEY_ERROR));
            }
        });
    }
}
