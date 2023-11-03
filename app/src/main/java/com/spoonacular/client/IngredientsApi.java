package com.spoonacular.client;

import com.spoonacular.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.math.BigDecimal;
import com.spoonacular.models.IngredientsMapBody;
import com.spoonacular.models.InlineResponse20022;
import com.spoonacular.models.InlineResponse20023;
import com.spoonacular.models.InlineResponse20025;
import com.spoonacular.models.InlineResponse20026;
import com.spoonacular.models.InlineResponse20027;
import com.spoonacular.models.InlineResponse20036;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IngredientsApi {
  /**
   * Autocomplete Ingredient Search
   * Autocomplete the entry of an ingredient.
   * @param query The (natural language) search query. (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @param metaInformation Whether to return more meta information about the ingredients. (optional)
   * @param intolerances A comma-separated list of intolerances. All recipes returned must not contain ingredients that are not suitable for people with the intolerances entered. See a full list of supported intolerances. (optional)
   * @param language The language of the input. Either &#x27;en&#x27; or &#x27;de&#x27;. (optional)
   * @return Call&lt;List&lt;InlineResponse20025&gt;&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Autocomplete-Ingredient-Search">Autocomplete Ingredient Search Documentation</a>
   */
  @GET("food/ingredients/autocomplete")
  Call<List<InlineResponse20025>> autocompleteIngredientSearch(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("number") Integer number, @retrofit2.http.Query("metaInformation") Boolean metaInformation, @retrofit2.http.Query("intolerances") String intolerances, @retrofit2.http.Query("language") String language
  );

  /**
   * Compute Ingredient Amount
   * Compute the amount you need of a certain ingredient for a certain nutritional goal. For example, how much pineapple do you have to eat to get 10 grams of protein?
   * @param id The id of the ingredient you want the amount for. (required)
   * @param nutrient The target nutrient. See a list of supported nutrients. (required)
   * @param target The target number of the given nutrient. (required)
   * @param unit The target unit. (optional)
   * @return Call&lt;InlineResponse20023&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Compute-Ingredient-Amount">Compute Ingredient Amount Documentation</a>
   */
  @GET("food/ingredients/{id}/amount")
  Call<InlineResponse20023> computeIngredientAmount(
    @retrofit2.http.Path("id") BigDecimal id, @retrofit2.http.Query("nutrient") String nutrient, @retrofit2.http.Query("target") BigDecimal target, @retrofit2.http.Query("unit") String unit
  );

  /**
   * Get Ingredient Information
   * Use an ingredient id to get all available information about an ingredient, such as its image and supermarket aisle.
   * @param id The item&#x27;s id. (required)
   * @param amount The amount of this ingredient. (optional)
   * @param unit The unit for the given amount. (optional)
   * @return Call&lt;InlineResponse20022&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Ingredient-Information">Get Ingredient Information Documentation</a>
   */
  @GET("food/ingredients/{id}/information")
  Call<InlineResponse20022> getIngredientInformation(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("amount") BigDecimal amount, @retrofit2.http.Query("unit") String unit
  );

  /**
   * Get Ingredient Substitutes
   * Search for substitutes for a given ingredient.
   * @param ingredientName The name of the ingredient you want to replace. (required)
   * @return Call&lt;InlineResponse20027&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Ingredient-Substitutes">Get Ingredient Substitutes Documentation</a>
   */
  @GET("food/ingredients/substitutes")
  Call<InlineResponse20027> getIngredientSubstitutes(
    @retrofit2.http.Query("ingredientName") String ingredientName
  );

  /**
   * Get Ingredient Substitutes by ID
   * Search for substitutes for a given ingredient.
   * @param id The item&#x27;s id. (required)
   * @return Call&lt;InlineResponse20027&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Ingredient-Substitutes-by-ID">Get Ingredient Substitutes by ID Documentation</a>
   */
  @GET("food/ingredients/{id}/substitutes")
  Call<InlineResponse20027> getIngredientSubstitutesByID(
    @retrofit2.http.Path("id") Integer id
  );

  /**
   * Ingredient Search
   * Search for simple whole foods (e.g. fruits, vegetables, nuts, grains, meat, fish, dairy etc.).
   * @param query The (natural language) search query. (optional)
   * @param addChildren Whether to add children of found foods. (optional)
   * @param minProteinPercent The minimum percentage of protein the food must have (between 0 and 100). (optional)
   * @param maxProteinPercent The maximum percentage of protein the food can have (between 0 and 100). (optional)
   * @param minFatPercent The minimum percentage of fat the food must have (between 0 and 100). (optional)
   * @param maxFatPercent The maximum percentage of fat the food can have (between 0 and 100). (optional)
   * @param minCarbsPercent The minimum percentage of carbs the food must have (between 0 and 100). (optional)
   * @param maxCarbsPercent The maximum percentage of carbs the food can have (between 0 and 100). (optional)
   * @param metaInformation Whether to return more meta information about the ingredients. (optional)
   * @param intolerances A comma-separated list of intolerances. All recipes returned must not contain ingredients that are not suitable for people with the intolerances entered. See a full list of supported intolerances. (optional)
   * @param sort The strategy to sort recipes by. See a full list of supported sorting options. (optional)
   * @param sortDirection The direction in which to sort. Must be either &#x27;asc&#x27; (ascending) or &#x27;desc&#x27; (descending). (optional)
   * @param offset The number of results to skip (between 0 and 900). (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @param language The language of the input. Either &#x27;en&#x27; or &#x27;de&#x27;. (optional)
   * @return Call&lt;InlineResponse20026&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Ingredient-Search">Ingredient Search Documentation</a>
   */
  @GET("food/ingredients/search")
  Call<InlineResponse20026> ingredientSearch(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("addChildren") Boolean addChildren, @retrofit2.http.Query("minProteinPercent") BigDecimal minProteinPercent, @retrofit2.http.Query("maxProteinPercent") BigDecimal maxProteinPercent, @retrofit2.http.Query("minFatPercent") BigDecimal minFatPercent, @retrofit2.http.Query("maxFatPercent") BigDecimal maxFatPercent, @retrofit2.http.Query("minCarbsPercent") BigDecimal minCarbsPercent, @retrofit2.http.Query("maxCarbsPercent") BigDecimal maxCarbsPercent, @retrofit2.http.Query("metaInformation") Boolean metaInformation, @retrofit2.http.Query("intolerances") String intolerances, @retrofit2.http.Query("sort") String sort, @retrofit2.http.Query("sortDirection") String sortDirection, @retrofit2.http.Query("offset") Integer offset, @retrofit2.http.Query("number") Integer number, @retrofit2.http.Query("language") String language
  );

  /**
   * Ingredients by ID Image
   * Visualize a recipe&#x27;s ingredient list.
   * @param id The recipe id. (required)
   * @param measure Whether the the measures should be &#x27;us&#x27; or &#x27;metric&#x27;. (optional)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Ingredients-by-ID-Image">Ingredients by ID Image Documentation</a>
   */
  @GET("recipes/{id}/ingredientWidget.png")
  Call<Object> ingredientsByIDImage(
    @retrofit2.http.Path("id") BigDecimal id, @retrofit2.http.Query("measure") String measure
  );

  /**
   * Map Ingredients to Grocery Products
   * Map a set of ingredients to products you can buy in the grocery store.
   * @param body  (required)
   * @return Call&lt;List&lt;InlineResponse20036&gt;&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Map-Ingredients-to-Grocery-Products">Map Ingredients to Grocery Products Documentation</a>
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("food/ingredients/map")
  Call<List<InlineResponse20036>> mapIngredientsToGroceryProducts(
    @retrofit2.http.Body IngredientsMapBody body
  );

  /**
   * Ingredients Widget
   * Visualize ingredients of a recipe. You can play around with that endpoint!
   * @param contentType The content type. (optional)
   * @param language The language of the input. Either &#x27;en&#x27; or &#x27;de&#x27;. (optional)
   * @param accept Accept header. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Ingredients-Widget">Ingredients Widget Documentation</a>
   */
  @Headers({
    "Content-Type:application/x-www-form-urlencoded"
  })
  @POST("recipes/visualizeIngredients")
  Call<String> visualizeIngredients(
    @retrofit2.http.Header("Content-Type") String contentType, @retrofit2.http.Query("language") String language, @retrofit2.http.Header("Accept") String accept
  );

}
