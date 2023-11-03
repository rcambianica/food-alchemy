package com.spoonacular.client;

import com.spoonacular.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.math.BigDecimal;
import com.spoonacular.models.InlineResponse20048;
import com.spoonacular.models.RecipesAnalyzeBody;
import com.spoonacular.models.RecipesAnalyzeBody1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DefaultApi {
  /**
   * Analyze Recipe
   * This endpoint allows you to send raw recipe information, such as title, servings, and ingredients, to then see what we compute (badges, diets, nutrition, and more). This is useful if you have your own recipe data and want to enrich it with our semantic analysis.
   * @param body Example request body. (required)
   * @param language The input language, either \&quot;en\&quot; or \&quot;de\&quot;. (optional)
   * @param includeNutrition Whether nutrition data should be added to correctly parsed ingredients. (optional)
   * @param includeTaste Whether taste data should be added to correctly parsed ingredients. (optional)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Analyze-Recipe">Analyze Recipe Documentation</a>
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("recipes/analyze")
  Call<Object> analyzeRecipe(
    @retrofit2.http.Body RecipesAnalyzeBody body, @retrofit2.http.Query("language") String language, @retrofit2.http.Query("includeNutrition") Boolean includeNutrition, @retrofit2.http.Query("includeTaste") Boolean includeTaste
  );

  /**
   * Create Recipe Card
   * Generate a recipe card for a recipe.
   * @param id The recipe id. (required)
   * @param mask The mask to put over the recipe image (\&quot;ellipseMask\&quot;, \&quot;diamondMask\&quot;, \&quot;starMask\&quot;, \&quot;heartMask\&quot;, \&quot;potMask\&quot;, \&quot;fishMask\&quot;). (optional)
   * @param backgroundImage The background image (\&quot;none\&quot;,\&quot;background1\&quot;, or \&quot;background2\&quot;). (optional)
   * @param backgroundColor The background color for the recipe card as a hex-string. (optional)
   * @param fontColor The font color for the recipe card as a hex-string. (optional)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Create-Recipe-Card">Create Recipe Card Documentation</a>
   */
  @GET("recipes/{id}/card")
  Call<Object> createRecipeCardGet(
    @retrofit2.http.Path("id") BigDecimal id, @retrofit2.http.Query("mask") String mask, @retrofit2.http.Query("backgroundImage") String backgroundImage, @retrofit2.http.Query("backgroundColor") String backgroundColor, @retrofit2.http.Query("fontColor") String fontColor
  );

  /**
   * Search Restaurants
   * Search through thousands of restaurants (in North America) by location, cuisine, budget, and more.
   * @param query The search query. (optional)
   * @param lat The latitude of the user&#x27;s location. (optional)
   * @param lng The longitude of the user&#x27;s location.\&quot;. (optional)
   * @param distance The distance around the location in miles. (optional)
   * @param budget The user&#x27;s budget for a meal in USD. (optional)
   * @param cuisine The cuisine of the restaurant. (optional)
   * @param minRating The minimum rating of the restaurant between 0 and 5. (optional)
   * @param isOpen Whether the restaurant must be open at the time of search. (optional)
   * @param sort How to sort the results, one of the following &#x27;cheapest&#x27;, &#x27;fastest&#x27;, &#x27;rating&#x27;, &#x27;distance&#x27; or the default &#x27;relevance&#x27;. (optional)
   * @param page The page number of results. (optional)
   * @return Call&lt;InlineResponse20048&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-Restaurants">Search Restaurants Documentation</a>
   */
  @GET("food/restaurants/search")
  Call<InlineResponse20048> searchRestaurants(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("lat") BigDecimal lat, @retrofit2.http.Query("lng") BigDecimal lng, @retrofit2.http.Query("distance") BigDecimal distance, @retrofit2.http.Query("budget") BigDecimal budget, @retrofit2.http.Query("cuisine") String cuisine, @retrofit2.http.Query("min-rating") BigDecimal minRating, @retrofit2.http.Query("is-open") Boolean isOpen, @retrofit2.http.Query("sort") String sort, @retrofit2.http.Query("page") BigDecimal page
  );

}
