package it.unimib.food_alchemy.service;

import static it.unimib.food_alchemy.util.Constants.RECIPES_ENDPOINT;
import static it.unimib.food_alchemy.util.Constants.RECIPES_QUERY_PARAMETER;

import it.unimib.food_alchemy.model.RecipeApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SpoonacularApiService {
    @GET(RECIPES_ENDPOINT)
    Call<RecipeApiResponse> getRecipes(
            @Query(RECIPES_QUERY_PARAMETER) String query,
            @Query(RECIPES_QUERY_PARAMETER) int number
            //@Header("Authorization") String apiKey
    );
}
