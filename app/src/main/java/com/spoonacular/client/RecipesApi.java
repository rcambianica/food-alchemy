package com.spoonacular.client;

import com.spoonacular.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.math.BigDecimal;
import com.spoonacular.models.IngredientsGlycemicLoadBody;
import com.spoonacular.models.InlineResponse200;
import com.spoonacular.models.InlineResponse2001;
import com.spoonacular.models.InlineResponse20010;
import com.spoonacular.models.InlineResponse20011;
import com.spoonacular.models.InlineResponse20012;
import com.spoonacular.models.InlineResponse20013;
import com.spoonacular.models.InlineResponse20014;
import com.spoonacular.models.InlineResponse20015;
import com.spoonacular.models.InlineResponse20016;
import com.spoonacular.models.InlineResponse20017;
import com.spoonacular.models.InlineResponse20018;
import com.spoonacular.models.InlineResponse20019;
import com.spoonacular.models.InlineResponse2002;
import com.spoonacular.models.InlineResponse20020;
import com.spoonacular.models.InlineResponse20021;
import com.spoonacular.models.InlineResponse20024;
import com.spoonacular.models.InlineResponse2003;
import com.spoonacular.models.InlineResponse2004;
import com.spoonacular.models.InlineResponse2005;
import com.spoonacular.models.InlineResponse20055;
import com.spoonacular.models.InlineResponse2006;
import com.spoonacular.models.InlineResponse2007;
import com.spoonacular.models.InlineResponse2008;
import com.spoonacular.models.InlineResponse2009;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RecipesApi {
  /**
   * Analyze a Recipe Search Query
   * Parse a recipe search query to find out its intention.
   * @param q The recipe search query. (required)
   * @return Call&lt;InlineResponse20018&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Analyze-a-Recipe-Search-Query">Analyze a Recipe Search Query Documentation</a>
   */
  @GET("recipes/queries/analyze")
  Call<InlineResponse20018> analyzeARecipeSearchQuery(
    @retrofit2.http.Query("q") String q
  );

  /**
   * Analyze Recipe Instructions
   * This endpoint allows you to break down instructions into atomic steps. Furthermore, each step will contain the ingredients and equipment required. Additionally, all ingredients and equipment from the recipe&#x27;s instructions will be extracted independently of the step they&#x27;re used in.
   * @param contentType The content type. (optional)
   * @return Call&lt;InlineResponse20016&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Analyze-Recipe-Instructions">Analyze Recipe Instructions Documentation</a>
   */
  @Headers({
    "Content-Type:application/x-www-form-urlencoded"
  })
  @POST("recipes/analyzeInstructions")
  Call<InlineResponse20016> analyzeRecipeInstructions(
    @retrofit2.http.Header("Content-Type") String contentType
  );

  /**
   * Autocomplete Recipe Search
   * Autocomplete a partial input to suggest possible recipe names.
   * @param query The (natural language) search query. (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @return Call&lt;List&lt;InlineResponse2007&gt;&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Autocomplete-Recipe-Search">Autocomplete Recipe Search Documentation</a>
   */
  @GET("recipes/autocomplete")
  Call<List<InlineResponse2007>> autocompleteRecipeSearch(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("number") Integer number
  );

  /**
   * Classify Cuisine
   * Classify the recipe&#x27;s cuisine.
   * @param contentType The content type. (optional)
   * @return Call&lt;InlineResponse20017&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Classify-Cuisine">Classify Cuisine Documentation</a>
   */
  @Headers({
    "Content-Type:application/x-www-form-urlencoded"
  })
  @POST("recipes/cuisine")
  Call<InlineResponse20017> classifyCuisine(
    @retrofit2.http.Header("Content-Type") String contentType
  );

  /**
   * Compute Glycemic Load
   * Retrieve the glycemic index for a list of ingredients and compute the individual and total glycemic load.
   * @param body  (required)
   * @param language The language of the input. Either &#x27;en&#x27; or &#x27;de&#x27;. (optional)
   * @return Call&lt;InlineResponse20024&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Compute-Glycemic-Load">Compute Glycemic Load Documentation</a>
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("food/ingredients/glycemicLoad")
  Call<InlineResponse20024> computeGlycemicLoad(
    @retrofit2.http.Body IngredientsGlycemicLoadBody body, @retrofit2.http.Query("language") String language
  );

  /**
   * Convert Amounts
   * Convert amounts like \&quot;2 cups of flour to grams\&quot;.
   * @param ingredientName The ingredient which you want to convert. (required)
   * @param sourceAmount The amount from which you want to convert, e.g. the 2.5 in \&quot;2.5 cups of flour to grams\&quot;. (required)
   * @param sourceUnit The unit from which you want to convert, e.g. the grams in \&quot;2.5 cups of flour to grams\&quot;. You can also use \&quot;piece\&quot;, e.g. \&quot;3.4 oz tomatoes to piece\&quot; (required)
   * @param targetUnit The unit to which you want to convert, e.g. the grams in \&quot;2.5 cups of flour to grams\&quot;. You can also use \&quot;piece\&quot;, e.g. \&quot;3.4 oz tomatoes to piece\&quot; (required)
   * @return Call&lt;InlineResponse20019&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Convert-Amounts">Convert Amounts Documentation</a>
   */
  @GET("recipes/convert")
  Call<InlineResponse20019> convertAmounts(
    @retrofit2.http.Query("ingredientName") String ingredientName, @retrofit2.http.Query("sourceAmount") BigDecimal sourceAmount, @retrofit2.http.Query("sourceUnit") String sourceUnit, @retrofit2.http.Query("targetUnit") String targetUnit
  );

  /**
   * Create Recipe Card
   * Generate a recipe card for a recipe.
   * @param contentType The content type. (optional)
   * @return Call&lt;InlineResponse20015&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Create-Recipe-Card">Create Recipe Card Documentation</a>
   */
  @POST("recipes/visualizeRecipe")
  Call<InlineResponse20015> createRecipeCard(
    @retrofit2.http.Header("Content-Type") String contentType
  );

  /**
   * Equipment by ID Image
   * Visualize a recipe&#x27;s equipment list as an image.
   * @param id The recipe id. (required)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Equipment-by-ID-Image">Equipment by ID Image Documentation</a>
   */
  @GET("recipes/{id}/equipmentWidget.png")
  Call<Object> equipmentByIDImage(
    @retrofit2.http.Path("id") BigDecimal id
  );

  /**
   * Extract Recipe from Website
   * This endpoint lets you extract recipe data such as title, ingredients, and instructions from any properly formatted Website.
   * @param url The URL of the recipe page. (required)
   * @param forceExtraction If true, the extraction will be triggered whether we already know the recipe or not. Use this only if information is missing as this operation is slower. (optional)
   * @param analyze If true, the recipe will be analyzed and classified resolving in more data such as cuisines, dish types, and more. (optional)
   * @param includeNutrition Include nutrition data in the recipe information. Nutrition data is per serving. If you want the nutrition data for the entire recipe, just multiply by the number of servings. (optional, default to false)
   * @param includeTaste Whether taste data should be added to correctly parsed ingredients. (optional, default to false)
   * @return Call&lt;InlineResponse2003&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Extract-Recipe-from-Website">Extract Recipe from Website Documentation</a>
   */
  @GET("recipes/extract")
  Call<InlineResponse2003> extractRecipeFromWebsite(
    @retrofit2.http.Query("url") String url, @retrofit2.http.Query("forceExtraction") Boolean forceExtraction, @retrofit2.http.Query("analyze") Boolean analyze, @retrofit2.http.Query("includeNutrition") Boolean includeNutrition, @retrofit2.http.Query("includeTaste") Boolean includeTaste
  );

  /**
   * Get Analyzed Recipe Instructions
   * Get an analyzed breakdown of a recipe&#x27;s instructions. Each step is enriched with the ingredients and equipment required.
   * @param id The item&#x27;s id. (required)
   * @param stepBreakdown Whether to break down the recipe steps even more. (optional)
   * @return Call&lt;InlineResponse20013&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Analyzed-Recipe-Instructions">Get Analyzed Recipe Instructions Documentation</a>
   */
  @GET("recipes/{id}/analyzedInstructions")
  Call<InlineResponse20013> getAnalyzedRecipeInstructions(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("stepBreakdown") Boolean stepBreakdown
  );

  /**
   * Get Random Recipes
   * Find random (popular) recipes. If you need to filter recipes by diet, nutrition etc. you might want to consider using the complex recipe search endpoint and set the sort request parameter to random.
   * @param limitLicense Whether the recipes should have an open license that allows display with proper attribution. (optional, default to true)
   * @param tags The tags (can be diets, meal types, cuisines, or intolerances) that the recipe must have. (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @return Call&lt;InlineResponse2006&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Random-Recipes">Get Random Recipes Documentation</a>
   */
  @GET("recipes/random")
  Call<InlineResponse2006> getRandomRecipes(
    @retrofit2.http.Query("limitLicense") Boolean limitLicense, @retrofit2.http.Query("tags") String tags, @retrofit2.http.Query("number") Integer number
  );

  /**
   * Equipment by ID
   * Get a recipe&#x27;s equipment list.
   * @param id The item&#x27;s id. (required)
   * @return Call&lt;InlineResponse2009&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Equipment-by-ID">Equipment by ID Documentation</a>
   */
  @GET("recipes/{id}/equipmentWidget.json")
  Call<InlineResponse2009> getRecipeEquipmentByID(
    @retrofit2.http.Path("id") Integer id
  );

  /**
   * Get Recipe Information
   * Use a recipe id to get full information about a recipe, such as ingredients, nutrition, diet and allergen information, etc.
   * @param id The item&#x27;s id. (required)
   * @param includeNutrition Include nutrition data in the recipe information. Nutrition data is per serving. If you want the nutrition data for the entire recipe, just multiply by the number of servings. (optional, default to false)
   * @return Call&lt;InlineResponse2003&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Recipe-Information">Get Recipe Information Documentation</a>
   */
  @GET("recipes/{id}/information")
  Call<InlineResponse2003> getRecipeInformation(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("includeNutrition") Boolean includeNutrition
  );

  /**
   * Get Recipe Information Bulk
   * Get information about multiple recipes at once. This is equivalent to calling the Get Recipe Information endpoint multiple times, but faster.
   * @param ids A comma-separated list of recipe ids. (required)
   * @param includeNutrition Include nutrition data in the recipe information. Nutrition data is per serving. If you want the nutrition data for the entire recipe, just multiply by the number of servings. (optional, default to false)
   * @return Call&lt;List&lt;InlineResponse2004&gt;&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Recipe-Information-Bulk">Get Recipe Information Bulk Documentation</a>
   */
  @GET("recipes/informationBulk")
  Call<List<InlineResponse2004>> getRecipeInformationBulk(
    @retrofit2.http.Query("ids") String ids, @retrofit2.http.Query("includeNutrition") Boolean includeNutrition
  );

  /**
   * Ingredients by ID
   * Get a recipe&#x27;s ingredient list.
   * @param id The item&#x27;s id. (required)
   * @return Call&lt;InlineResponse20011&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Ingredients-by-ID">Ingredients by ID Documentation</a>
   */
  @GET("recipes/{id}/ingredientWidget.json")
  Call<InlineResponse20011> getRecipeIngredientsByID(
    @retrofit2.http.Path("id") Integer id
  );

  /**
   * Nutrition by ID
   * Get a recipe&#x27;s nutrition data.
   * @param id The item&#x27;s id. (required)
   * @return Call&lt;InlineResponse20012&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Nutrition-by-ID">Nutrition by ID Documentation</a>
   */
  @GET("recipes/{id}/nutritionWidget.json")
  Call<InlineResponse20012> getRecipeNutritionWidgetByID(
    @retrofit2.http.Path("id") Integer id
  );

  /**
   * Price Breakdown by ID
   * Get a recipe&#x27;s price breakdown data.
   * @param id The item&#x27;s id. (required)
   * @return Call&lt;InlineResponse20010&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Price-Breakdown-by-ID">Price Breakdown by ID Documentation</a>
   */
  @GET("recipes/{id}/priceBreakdownWidget.json")
  Call<InlineResponse20010> getRecipePriceBreakdownByID(
    @retrofit2.http.Path("id") Integer id
  );

  /**
   * Taste by ID
   * Get a recipe&#x27;s taste. The tastes supported are sweet, salty, sour, bitter, savory, and fatty.
   * @param id The item&#x27;s id. (required)
   * @param normalize Normalize to the strongest taste. (optional, default to true)
   * @return Call&lt;InlineResponse2008&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Taste-by-ID">Taste by ID Documentation</a>
   */
  @GET("recipes/{id}/tasteWidget.json")
  Call<InlineResponse2008> getRecipeTasteByID(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("normalize") Boolean normalize
  );

  /**
   * Get Similar Recipes
   * Find recipes which are similar to the given one.
   * @param id The item&#x27;s id. (required)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @param limitLicense Whether the recipes should have an open license that allows display with proper attribution. (optional, default to true)
   * @return Call&lt;List&lt;InlineResponse2005&gt;&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Similar-Recipes">Get Similar Recipes Documentation</a>
   */
  @GET("recipes/{id}/similar")
  Call<List<InlineResponse2005>> getSimilarRecipes(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("number") Integer number, @retrofit2.http.Query("limitLicense") Boolean limitLicense
  );

  /**
   * Guess Nutrition by Dish Name
   * Estimate the macronutrients of a dish based on its title.
   * @param title The title of the dish. (required)
   * @return Call&lt;InlineResponse20021&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Guess-Nutrition-by-Dish-Name">Guess Nutrition by Dish Name Documentation</a>
   */
  @GET("recipes/guessNutrition")
  Call<InlineResponse20021> guessNutritionByDishName(
    @retrofit2.http.Query("title") String title
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
   * Parse Ingredients
   * Extract an ingredient from plain text.
   * @param contentType The content type. (optional)
   * @param language The language of the input. Either &#x27;en&#x27; or &#x27;de&#x27;. (optional)
   * @return Call&lt;List&lt;InlineResponse20020&gt;&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Parse-Ingredients">Parse Ingredients Documentation</a>
   */
  @Headers({
    "Content-Type:application/x-www-form-urlencoded"
  })
  @POST("recipes/parseIngredients")
  Call<List<InlineResponse20020>> parseIngredients(
    @retrofit2.http.Header("Content-Type") String contentType, @retrofit2.http.Query("language") String language
  );

  /**
   * Price Breakdown by ID Image
   * Visualize a recipe&#x27;s price breakdown.
   * @param id The recipe id. (required)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Price-Breakdown-by-ID-Image">Price Breakdown by ID Image Documentation</a>
   */
  @GET("recipes/{id}/priceBreakdownWidget.png")
  Call<Object> priceBreakdownByIDImage(
    @retrofit2.http.Path("id") BigDecimal id
  );

  /**
   * Quick Answer
   * Answer a nutrition related natural language question.
   * @param q The nutrition related question. (required)
   * @return Call&lt;InlineResponse20055&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Quick-Answer">Quick Answer Documentation</a>
   */
  @GET("recipes/quickAnswer")
  Call<InlineResponse20055> quickAnswer(
    @retrofit2.http.Query("q") String q
  );

  /**
   * Recipe Nutrition by ID Image
   * Visualize a recipe&#x27;s nutritional information as an image.
   * @param id The recipe id. (required)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Recipe-Nutrition-by-ID-Image">Recipe Nutrition by ID Image Documentation</a>
   */
  @GET("recipes/{id}/nutritionWidget.png")
  Call<Object> recipeNutritionByIDImage(
    @retrofit2.http.Path("id") BigDecimal id
  );

  /**
   * Recipe Nutrition Label Image
   * Get a recipe&#x27;s nutrition label as an image.
   * @param id The recipe id. (required)
   * @param showOptionalNutrients Whether to show optional nutrients. (optional)
   * @param showZeroValues Whether to show zero values. (optional)
   * @param showIngredients Whether to show a list of ingredients. (optional)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Recipe-Nutrition-Label-Image">Recipe Nutrition Label Image Documentation</a>
   */
  @GET("recipes/{id}/nutritionLabel.png")
  Call<Object> recipeNutritionLabelImage(
    @retrofit2.http.Path("id") BigDecimal id, @retrofit2.http.Query("showOptionalNutrients") Boolean showOptionalNutrients, @retrofit2.http.Query("showZeroValues") Boolean showZeroValues, @retrofit2.http.Query("showIngredients") Boolean showIngredients
  );

  /**
   * Recipe Nutrition Label Widget
   * Get a recipe&#x27;s nutrition label as an HTML widget.
   * @param id The recipe id. (required)
   * @param defaultCss Whether the default CSS should be added to the response. (optional, default to true)
   * @param showOptionalNutrients Whether to show optional nutrients. (optional)
   * @param showZeroValues Whether to show zero values. (optional)
   * @param showIngredients Whether to show a list of ingredients. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Recipe-Nutrition-Label-Widget">Recipe Nutrition Label Widget Documentation</a>
   */
  @GET("recipes/{id}/nutritionLabel")
  Call<String> recipeNutritionLabelWidget(
    @retrofit2.http.Path("id") BigDecimal id, @retrofit2.http.Query("defaultCss") Boolean defaultCss, @retrofit2.http.Query("showOptionalNutrients") Boolean showOptionalNutrients, @retrofit2.http.Query("showZeroValues") Boolean showZeroValues, @retrofit2.http.Query("showIngredients") Boolean showIngredients
  );

  /**
   * Recipe Taste by ID Image
   * Get a recipe&#x27;s taste as an image. The tastes supported are sweet, salty, sour, bitter, savory, and fatty.
   * @param id The recipe id. (required)
   * @param normalize Normalize to the strongest taste. (optional)
   * @param rgb Red, green, blue values for the chart color. (optional)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Recipe-Taste-by-ID-Image">Recipe Taste by ID Image Documentation</a>
   */
  @GET("recipes/{id}/tasteWidget.png")
  Call<Object> recipeTasteByIDImage(
    @retrofit2.http.Path("id") BigDecimal id, @retrofit2.http.Query("normalize") Boolean normalize, @retrofit2.http.Query("rgb") String rgb
  );

  /**
   * Search Recipes
   * Search through hundreds of thousands of recipes using advanced filtering and ranking. NOTE: This method combines searching by query, by ingredients, and by nutrients into one endpoint.
   * @param query The (natural language) search query. (optional)
   * @param cuisine The cuisine(s) of the recipes. One or more, comma separated (will be interpreted as &#x27;OR&#x27;). See a full list of supported cuisines. (optional)
   * @param excludeCuisine The cuisine(s) the recipes must not match. One or more, comma separated (will be interpreted as &#x27;AND&#x27;). See a full list of supported cuisines. (optional)
   * @param diet The diet for which the recipes must be suitable. See a full list of supported diets. (optional)
   * @param intolerances A comma-separated list of intolerances. All recipes returned must not contain ingredients that are not suitable for people with the intolerances entered. See a full list of supported intolerances. (optional)
   * @param equipment The equipment required. Multiple values will be interpreted as &#x27;or&#x27;. For example, value could be \&quot;blender, frying pan, bowl\&quot;. (optional)
   * @param includeIngredients A comma-separated list of ingredients that should/must be used in the recipes. (optional)
   * @param excludeIngredients A comma-separated list of ingredients or ingredient types that the recipes must not contain. (optional)
   * @param type The type of recipe. See a full list of supported meal types. (optional)
   * @param instructionsRequired Whether the recipes must have instructions. (optional)
   * @param fillIngredients Add information about the ingredients and whether they are used or missing in relation to the query. (optional)
   * @param addRecipeInformation If set to true, you get more information about the recipes returned. (optional)
   * @param addRecipeNutrition If set to true, you get nutritional information about each recipes returned. (optional)
   * @param author The username of the recipe author. (optional)
   * @param tags The tags (can be diets, meal types, cuisines, or intolerances) that the recipe must have. (optional)
   * @param recipeBoxId The id of the recipe box to which the search should be limited to. (optional)
   * @param titleMatch Enter text that must be found in the title of the recipes. (optional)
   * @param maxReadyTime The maximum time in minutes it should take to prepare and cook the recipe. (optional)
   * @param ignorePantry Whether to ignore typical pantry items, such as water, salt, flour, etc. (optional, default to false)
   * @param sort The strategy to sort recipes by. See a full list of supported sorting options. (optional)
   * @param sortDirection The direction in which to sort. Must be either &#x27;asc&#x27; (ascending) or &#x27;desc&#x27; (descending). (optional)
   * @param minCarbs The minimum amount of carbohydrates in grams the recipe must have. (optional)
   * @param maxCarbs The maximum amount of carbohydrates in grams the recipe can have. (optional)
   * @param minProtein The minimum amount of protein in grams the recipe must have. (optional)
   * @param maxProtein The maximum amount of protein in grams the recipe can have. (optional)
   * @param minCalories The minimum amount of calories the recipe must have. (optional)
   * @param maxCalories The maximum amount of calories the recipe can have. (optional)
   * @param minFat The minimum amount of fat in grams the recipe must have. (optional)
   * @param maxFat The maximum amount of fat in grams the recipe can have. (optional)
   * @param minAlcohol The minimum amount of alcohol in grams the recipe must have. (optional)
   * @param maxAlcohol The maximum amount of alcohol in grams the recipe can have. (optional)
   * @param minCaffeine The minimum amount of caffeine in milligrams the recipe must have. (optional)
   * @param maxCaffeine The maximum amount of caffeine in milligrams the recipe can have. (optional)
   * @param minCopper The minimum amount of copper in milligrams the recipe must have. (optional)
   * @param maxCopper The maximum amount of copper in milligrams the recipe can have. (optional)
   * @param minCalcium The minimum amount of calcium in milligrams the recipe must have. (optional)
   * @param maxCalcium The maximum amount of calcium in milligrams the recipe can have. (optional)
   * @param minCholine The minimum amount of choline in milligrams the recipe must have. (optional)
   * @param maxCholine The maximum amount of choline in milligrams the recipe can have. (optional)
   * @param minCholesterol The minimum amount of cholesterol in milligrams the recipe must have. (optional)
   * @param maxCholesterol The maximum amount of cholesterol in milligrams the recipe can have. (optional)
   * @param minFluoride The minimum amount of fluoride in milligrams the recipe must have. (optional)
   * @param maxFluoride The maximum amount of fluoride in milligrams the recipe can have. (optional)
   * @param minSaturatedFat The minimum amount of saturated fat in grams the recipe must have. (optional)
   * @param maxSaturatedFat The maximum amount of saturated fat in grams the recipe can have. (optional)
   * @param minVitaminA The minimum amount of Vitamin A in IU the recipe must have. (optional)
   * @param maxVitaminA The maximum amount of Vitamin A in IU the recipe can have. (optional)
   * @param minVitaminC The minimum amount of Vitamin C milligrams the recipe must have. (optional)
   * @param maxVitaminC The maximum amount of Vitamin C in milligrams the recipe can have. (optional)
   * @param minVitaminD The minimum amount of Vitamin D in micrograms the recipe must have. (optional)
   * @param maxVitaminD The maximum amount of Vitamin D in micrograms the recipe can have. (optional)
   * @param minVitaminE The minimum amount of Vitamin E in milligrams the recipe must have. (optional)
   * @param maxVitaminE The maximum amount of Vitamin E in milligrams the recipe can have. (optional)
   * @param minVitaminK The minimum amount of Vitamin K in micrograms the recipe must have. (optional)
   * @param maxVitaminK The maximum amount of Vitamin K in micrograms the recipe can have. (optional)
   * @param minVitaminB1 The minimum amount of Vitamin B1 in milligrams the recipe must have. (optional)
   * @param maxVitaminB1 The maximum amount of Vitamin B1 in milligrams the recipe can have. (optional)
   * @param minVitaminB2 The minimum amount of Vitamin B2 in milligrams the recipe must have. (optional)
   * @param maxVitaminB2 The maximum amount of Vitamin B2 in milligrams the recipe can have. (optional)
   * @param minVitaminB5 The minimum amount of Vitamin B5 in milligrams the recipe must have. (optional)
   * @param maxVitaminB5 The maximum amount of Vitamin B5 in milligrams the recipe can have. (optional)
   * @param minVitaminB3 The minimum amount of Vitamin B3 in milligrams the recipe must have. (optional)
   * @param maxVitaminB3 The maximum amount of Vitamin B3 in milligrams the recipe can have. (optional)
   * @param minVitaminB6 The minimum amount of Vitamin B6 in milligrams the recipe must have. (optional)
   * @param maxVitaminB6 The maximum amount of Vitamin B6 in milligrams the recipe can have. (optional)
   * @param minVitaminB12 The minimum amount of Vitamin B12 in micrograms the recipe must have. (optional)
   * @param maxVitaminB12 The maximum amount of Vitamin B12 in micrograms the recipe can have. (optional)
   * @param minFiber The minimum amount of fiber in grams the recipe must have. (optional)
   * @param maxFiber The maximum amount of fiber in grams the recipe can have. (optional)
   * @param minFolate The minimum amount of folate in micrograms the recipe must have. (optional)
   * @param maxFolate The maximum amount of folate in micrograms the recipe can have. (optional)
   * @param minFolicAcid The minimum amount of folic acid in micrograms the recipe must have. (optional)
   * @param maxFolicAcid The maximum amount of folic acid in micrograms the recipe can have. (optional)
   * @param minIodine The minimum amount of iodine in micrograms the recipe must have. (optional)
   * @param maxIodine The maximum amount of iodine in micrograms the recipe can have. (optional)
   * @param minIron The minimum amount of iron in milligrams the recipe must have. (optional)
   * @param maxIron The maximum amount of iron in milligrams the recipe can have. (optional)
   * @param minMagnesium The minimum amount of magnesium in milligrams the recipe must have. (optional)
   * @param maxMagnesium The maximum amount of magnesium in milligrams the recipe can have. (optional)
   * @param minManganese The minimum amount of manganese in milligrams the recipe must have. (optional)
   * @param maxManganese The maximum amount of manganese in milligrams the recipe can have. (optional)
   * @param minPhosphorus The minimum amount of phosphorus in milligrams the recipe must have. (optional)
   * @param maxPhosphorus The maximum amount of phosphorus in milligrams the recipe can have. (optional)
   * @param minPotassium The minimum amount of potassium in milligrams the recipe must have. (optional)
   * @param maxPotassium The maximum amount of potassium in milligrams the recipe can have. (optional)
   * @param minSelenium The minimum amount of selenium in micrograms the recipe must have. (optional)
   * @param maxSelenium The maximum amount of selenium in micrograms the recipe can have. (optional)
   * @param minSodium The minimum amount of sodium in milligrams the recipe must have. (optional)
   * @param maxSodium The maximum amount of sodium in milligrams the recipe can have. (optional)
   * @param minSugar The minimum amount of sugar in grams the recipe must have. (optional)
   * @param maxSugar The maximum amount of sugar in grams the recipe can have. (optional)
   * @param minZinc The minimum amount of zinc in milligrams the recipe must have. (optional)
   * @param maxZinc The maximum amount of zinc in milligrams the recipe can have. (optional)
   * @param offset The number of results to skip (between 0 and 900). (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @param limitLicense Whether the recipes should have an open license that allows display with proper attribution. (optional, default to true)
   * @return Call&lt;InlineResponse200&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-Recipes">Search Recipes Documentation</a>
   */
  @GET("recipes/complexSearch")
  Call<InlineResponse200> searchRecipes(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("cuisine") String cuisine, @retrofit2.http.Query("excludeCuisine") String excludeCuisine, @retrofit2.http.Query("diet") String diet, @retrofit2.http.Query("intolerances") String intolerances, @retrofit2.http.Query("equipment") String equipment, @retrofit2.http.Query("includeIngredients") String includeIngredients, @retrofit2.http.Query("excludeIngredients") String excludeIngredients, @retrofit2.http.Query("type") String type, @retrofit2.http.Query("instructionsRequired") Boolean instructionsRequired, @retrofit2.http.Query("fillIngredients") Boolean fillIngredients, @retrofit2.http.Query("addRecipeInformation") Boolean addRecipeInformation, @retrofit2.http.Query("addRecipeNutrition") Boolean addRecipeNutrition, @retrofit2.http.Query("author") String author, @retrofit2.http.Query("tags") String tags, @retrofit2.http.Query("recipeBoxId") BigDecimal recipeBoxId, @retrofit2.http.Query("titleMatch") String titleMatch, @retrofit2.http.Query("maxReadyTime") BigDecimal maxReadyTime, @retrofit2.http.Query("ignorePantry") Boolean ignorePantry, @retrofit2.http.Query("sort") String sort, @retrofit2.http.Query("sortDirection") String sortDirection, @retrofit2.http.Query("minCarbs") BigDecimal minCarbs, @retrofit2.http.Query("maxCarbs") BigDecimal maxCarbs, @retrofit2.http.Query("minProtein") BigDecimal minProtein, @retrofit2.http.Query("maxProtein") BigDecimal maxProtein, @retrofit2.http.Query("minCalories") BigDecimal minCalories, @retrofit2.http.Query("maxCalories") BigDecimal maxCalories, @retrofit2.http.Query("minFat") BigDecimal minFat, @retrofit2.http.Query("maxFat") BigDecimal maxFat, @retrofit2.http.Query("minAlcohol") BigDecimal minAlcohol, @retrofit2.http.Query("maxAlcohol") BigDecimal maxAlcohol, @retrofit2.http.Query("minCaffeine") BigDecimal minCaffeine, @retrofit2.http.Query("maxCaffeine") BigDecimal maxCaffeine, @retrofit2.http.Query("minCopper") BigDecimal minCopper, @retrofit2.http.Query("maxCopper") BigDecimal maxCopper, @retrofit2.http.Query("minCalcium") BigDecimal minCalcium, @retrofit2.http.Query("maxCalcium") BigDecimal maxCalcium, @retrofit2.http.Query("minCholine") BigDecimal minCholine, @retrofit2.http.Query("maxCholine") BigDecimal maxCholine, @retrofit2.http.Query("minCholesterol") BigDecimal minCholesterol, @retrofit2.http.Query("maxCholesterol") BigDecimal maxCholesterol, @retrofit2.http.Query("minFluoride") BigDecimal minFluoride, @retrofit2.http.Query("maxFluoride") BigDecimal maxFluoride, @retrofit2.http.Query("minSaturatedFat") BigDecimal minSaturatedFat, @retrofit2.http.Query("maxSaturatedFat") BigDecimal maxSaturatedFat, @retrofit2.http.Query("minVitaminA") BigDecimal minVitaminA, @retrofit2.http.Query("maxVitaminA") BigDecimal maxVitaminA, @retrofit2.http.Query("minVitaminC") BigDecimal minVitaminC, @retrofit2.http.Query("maxVitaminC") BigDecimal maxVitaminC, @retrofit2.http.Query("minVitaminD") BigDecimal minVitaminD, @retrofit2.http.Query("maxVitaminD") BigDecimal maxVitaminD, @retrofit2.http.Query("minVitaminE") BigDecimal minVitaminE, @retrofit2.http.Query("maxVitaminE") BigDecimal maxVitaminE, @retrofit2.http.Query("minVitaminK") BigDecimal minVitaminK, @retrofit2.http.Query("maxVitaminK") BigDecimal maxVitaminK, @retrofit2.http.Query("minVitaminB1") BigDecimal minVitaminB1, @retrofit2.http.Query("maxVitaminB1") BigDecimal maxVitaminB1, @retrofit2.http.Query("minVitaminB2") BigDecimal minVitaminB2, @retrofit2.http.Query("maxVitaminB2") BigDecimal maxVitaminB2, @retrofit2.http.Query("minVitaminB5") BigDecimal minVitaminB5, @retrofit2.http.Query("maxVitaminB5") BigDecimal maxVitaminB5, @retrofit2.http.Query("minVitaminB3") BigDecimal minVitaminB3, @retrofit2.http.Query("maxVitaminB3") BigDecimal maxVitaminB3, @retrofit2.http.Query("minVitaminB6") BigDecimal minVitaminB6, @retrofit2.http.Query("maxVitaminB6") BigDecimal maxVitaminB6, @retrofit2.http.Query("minVitaminB12") BigDecimal minVitaminB12, @retrofit2.http.Query("maxVitaminB12") BigDecimal maxVitaminB12, @retrofit2.http.Query("minFiber") BigDecimal minFiber, @retrofit2.http.Query("maxFiber") BigDecimal maxFiber, @retrofit2.http.Query("minFolate") BigDecimal minFolate, @retrofit2.http.Query("maxFolate") BigDecimal maxFolate, @retrofit2.http.Query("minFolicAcid") BigDecimal minFolicAcid, @retrofit2.http.Query("maxFolicAcid") BigDecimal maxFolicAcid, @retrofit2.http.Query("minIodine") BigDecimal minIodine, @retrofit2.http.Query("maxIodine") BigDecimal maxIodine, @retrofit2.http.Query("minIron") BigDecimal minIron, @retrofit2.http.Query("maxIron") BigDecimal maxIron, @retrofit2.http.Query("minMagnesium") BigDecimal minMagnesium, @retrofit2.http.Query("maxMagnesium") BigDecimal maxMagnesium, @retrofit2.http.Query("minManganese") BigDecimal minManganese, @retrofit2.http.Query("maxManganese") BigDecimal maxManganese, @retrofit2.http.Query("minPhosphorus") BigDecimal minPhosphorus, @retrofit2.http.Query("maxPhosphorus") BigDecimal maxPhosphorus, @retrofit2.http.Query("minPotassium") BigDecimal minPotassium, @retrofit2.http.Query("maxPotassium") BigDecimal maxPotassium, @retrofit2.http.Query("minSelenium") BigDecimal minSelenium, @retrofit2.http.Query("maxSelenium") BigDecimal maxSelenium, @retrofit2.http.Query("minSodium") BigDecimal minSodium, @retrofit2.http.Query("maxSodium") BigDecimal maxSodium, @retrofit2.http.Query("minSugar") BigDecimal minSugar, @retrofit2.http.Query("maxSugar") BigDecimal maxSugar, @retrofit2.http.Query("minZinc") BigDecimal minZinc, @retrofit2.http.Query("maxZinc") BigDecimal maxZinc, @retrofit2.http.Query("offset") Integer offset, @retrofit2.http.Query("number") Integer number, @retrofit2.http.Query("limitLicense") Boolean limitLicense
  );

  /**
   * Search Recipes by Ingredients
   *  Ever wondered what recipes you can cook with the ingredients you have in your fridge or pantry? This endpoint lets you find recipes that either maximize the usage of ingredients you have at hand (pre shopping) or minimize the ingredients that you don&#x27;t currently have (post shopping).         
   * @param ingredients A comma-separated list of ingredients that the recipes should contain. (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @param limitLicense Whether the recipes should have an open license that allows display with proper attribution. (optional, default to true)
   * @param ranking Whether to maximize used ingredients (1) or minimize missing ingredients (2) first. (optional)
   * @param ignorePantry Whether to ignore typical pantry items, such as water, salt, flour, etc. (optional, default to false)
   * @return Call&lt;List&lt;InlineResponse2001&gt;&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-Recipes-by-Ingredients">Search Recipes by Ingredients Documentation</a>
   */
  @GET("recipes/findByIngredients")
  Call<List<InlineResponse2001>> searchRecipesByIngredients(
    @retrofit2.http.Query("ingredients") String ingredients, @retrofit2.http.Query("number") Integer number, @retrofit2.http.Query("limitLicense") Boolean limitLicense, @retrofit2.http.Query("ranking") BigDecimal ranking, @retrofit2.http.Query("ignorePantry") Boolean ignorePantry
  );

  /**
   * Search Recipes by Nutrients
   * Find a set of recipes that adhere to the given nutritional limits. You may set limits for macronutrients (calories, protein, fat, and carbohydrate) and/or many micronutrients.
   * @param minCarbs The minimum amount of carbohydrates in grams the recipe must have. (optional)
   * @param maxCarbs The maximum amount of carbohydrates in grams the recipe can have. (optional)
   * @param minProtein The minimum amount of protein in grams the recipe must have. (optional)
   * @param maxProtein The maximum amount of protein in grams the recipe can have. (optional)
   * @param minCalories The minimum amount of calories the recipe must have. (optional)
   * @param maxCalories The maximum amount of calories the recipe can have. (optional)
   * @param minFat The minimum amount of fat in grams the recipe must have. (optional)
   * @param maxFat The maximum amount of fat in grams the recipe can have. (optional)
   * @param minAlcohol The minimum amount of alcohol in grams the recipe must have. (optional)
   * @param maxAlcohol The maximum amount of alcohol in grams the recipe can have. (optional)
   * @param minCaffeine The minimum amount of caffeine in milligrams the recipe must have. (optional)
   * @param maxCaffeine The maximum amount of caffeine in milligrams the recipe can have. (optional)
   * @param minCopper The minimum amount of copper in milligrams the recipe must have. (optional)
   * @param maxCopper The maximum amount of copper in milligrams the recipe can have. (optional)
   * @param minCalcium The minimum amount of calcium in milligrams the recipe must have. (optional)
   * @param maxCalcium The maximum amount of calcium in milligrams the recipe can have. (optional)
   * @param minCholine The minimum amount of choline in milligrams the recipe must have. (optional)
   * @param maxCholine The maximum amount of choline in milligrams the recipe can have. (optional)
   * @param minCholesterol The minimum amount of cholesterol in milligrams the recipe must have. (optional)
   * @param maxCholesterol The maximum amount of cholesterol in milligrams the recipe can have. (optional)
   * @param minFluoride The minimum amount of fluoride in milligrams the recipe must have. (optional)
   * @param maxFluoride The maximum amount of fluoride in milligrams the recipe can have. (optional)
   * @param minSaturatedFat The minimum amount of saturated fat in grams the recipe must have. (optional)
   * @param maxSaturatedFat The maximum amount of saturated fat in grams the recipe can have. (optional)
   * @param minVitaminA The minimum amount of Vitamin A in IU the recipe must have. (optional)
   * @param maxVitaminA The maximum amount of Vitamin A in IU the recipe can have. (optional)
   * @param minVitaminC The minimum amount of Vitamin C in milligrams the recipe must have. (optional)
   * @param maxVitaminC The maximum amount of Vitamin C in milligrams the recipe can have. (optional)
   * @param minVitaminD The minimum amount of Vitamin D in micrograms the recipe must have. (optional)
   * @param maxVitaminD The maximum amount of Vitamin D in micrograms the recipe can have. (optional)
   * @param minVitaminE The minimum amount of Vitamin E in milligrams the recipe must have. (optional)
   * @param maxVitaminE The maximum amount of Vitamin E in milligrams the recipe can have. (optional)
   * @param minVitaminK The minimum amount of Vitamin K in micrograms the recipe must have. (optional)
   * @param maxVitaminK The maximum amount of Vitamin K in micrograms the recipe can have. (optional)
   * @param minVitaminB1 The minimum amount of Vitamin B1 in milligrams the recipe must have. (optional)
   * @param maxVitaminB1 The maximum amount of Vitamin B1 in milligrams the recipe can have. (optional)
   * @param minVitaminB2 The minimum amount of Vitamin B2 in milligrams the recipe must have. (optional)
   * @param maxVitaminB2 The maximum amount of Vitamin B2 in milligrams the recipe can have. (optional)
   * @param minVitaminB5 The minimum amount of Vitamin B5 in milligrams the recipe must have. (optional)
   * @param maxVitaminB5 The maximum amount of Vitamin B5 in milligrams the recipe can have. (optional)
   * @param minVitaminB3 The minimum amount of Vitamin B3 in milligrams the recipe must have. (optional)
   * @param maxVitaminB3 The maximum amount of Vitamin B3 in milligrams the recipe can have. (optional)
   * @param minVitaminB6 The minimum amount of Vitamin B6 in milligrams the recipe must have. (optional)
   * @param maxVitaminB6 The maximum amount of Vitamin B6 in milligrams the recipe can have. (optional)
   * @param minVitaminB12 The minimum amount of Vitamin B12 in micrograms the recipe must have. (optional)
   * @param maxVitaminB12 The maximum amount of Vitamin B12 in micrograms the recipe can have. (optional)
   * @param minFiber The minimum amount of fiber in grams the recipe must have. (optional)
   * @param maxFiber The maximum amount of fiber in grams the recipe can have. (optional)
   * @param minFolate The minimum amount of folate in micrograms the recipe must have. (optional)
   * @param maxFolate The maximum amount of folate in micrograms the recipe can have. (optional)
   * @param minFolicAcid The minimum amount of folic acid in micrograms the recipe must have. (optional)
   * @param maxFolicAcid The maximum amount of folic acid in micrograms the recipe can have. (optional)
   * @param minIodine The minimum amount of iodine in micrograms the recipe must have. (optional)
   * @param maxIodine The maximum amount of iodine in micrograms the recipe can have. (optional)
   * @param minIron The minimum amount of iron in milligrams the recipe must have. (optional)
   * @param maxIron The maximum amount of iron in milligrams the recipe can have. (optional)
   * @param minMagnesium The minimum amount of magnesium in milligrams the recipe must have. (optional)
   * @param maxMagnesium The maximum amount of magnesium in milligrams the recipe can have. (optional)
   * @param minManganese The minimum amount of manganese in milligrams the recipe must have. (optional)
   * @param maxManganese The maximum amount of manganese in milligrams the recipe can have. (optional)
   * @param minPhosphorus The minimum amount of phosphorus in milligrams the recipe must have. (optional)
   * @param maxPhosphorus The maximum amount of phosphorus in milligrams the recipe can have. (optional)
   * @param minPotassium The minimum amount of potassium in milligrams the recipe must have. (optional)
   * @param maxPotassium The maximum amount of potassium in milligrams the recipe can have. (optional)
   * @param minSelenium The minimum amount of selenium in micrograms the recipe must have. (optional)
   * @param maxSelenium The maximum amount of selenium in micrograms the recipe can have. (optional)
   * @param minSodium The minimum amount of sodium in milligrams the recipe must have. (optional)
   * @param maxSodium The maximum amount of sodium in milligrams the recipe can have. (optional)
   * @param minSugar The minimum amount of sugar in grams the recipe must have. (optional)
   * @param maxSugar The maximum amount of sugar in grams the recipe can have. (optional)
   * @param minZinc The minimum amount of zinc in milligrams the recipe must have. (optional)
   * @param maxZinc The maximum amount of zinc in milligrams the recipe can have. (optional)
   * @param offset The number of results to skip (between 0 and 900). (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @param random If true, every request will give you a random set of recipes within the requested limits. (optional)
   * @param limitLicense Whether the recipes should have an open license that allows display with proper attribution. (optional, default to true)
   * @return Call&lt;List&lt;InlineResponse2002&gt;&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-Recipes-by-Nutrients">Search Recipes by Nutrients Documentation</a>
   */
  @GET("recipes/findByNutrients")
  Call<List<InlineResponse2002>> searchRecipesByNutrients(
    @retrofit2.http.Query("minCarbs") BigDecimal minCarbs, @retrofit2.http.Query("maxCarbs") BigDecimal maxCarbs, @retrofit2.http.Query("minProtein") BigDecimal minProtein, @retrofit2.http.Query("maxProtein") BigDecimal maxProtein, @retrofit2.http.Query("minCalories") BigDecimal minCalories, @retrofit2.http.Query("maxCalories") BigDecimal maxCalories, @retrofit2.http.Query("minFat") BigDecimal minFat, @retrofit2.http.Query("maxFat") BigDecimal maxFat, @retrofit2.http.Query("minAlcohol") BigDecimal minAlcohol, @retrofit2.http.Query("maxAlcohol") BigDecimal maxAlcohol, @retrofit2.http.Query("minCaffeine") BigDecimal minCaffeine, @retrofit2.http.Query("maxCaffeine") BigDecimal maxCaffeine, @retrofit2.http.Query("minCopper") BigDecimal minCopper, @retrofit2.http.Query("maxCopper") BigDecimal maxCopper, @retrofit2.http.Query("minCalcium") BigDecimal minCalcium, @retrofit2.http.Query("maxCalcium") BigDecimal maxCalcium, @retrofit2.http.Query("minCholine") BigDecimal minCholine, @retrofit2.http.Query("maxCholine") BigDecimal maxCholine, @retrofit2.http.Query("minCholesterol") BigDecimal minCholesterol, @retrofit2.http.Query("maxCholesterol") BigDecimal maxCholesterol, @retrofit2.http.Query("minFluoride") BigDecimal minFluoride, @retrofit2.http.Query("maxFluoride") BigDecimal maxFluoride, @retrofit2.http.Query("minSaturatedFat") BigDecimal minSaturatedFat, @retrofit2.http.Query("maxSaturatedFat") BigDecimal maxSaturatedFat, @retrofit2.http.Query("minVitaminA") BigDecimal minVitaminA, @retrofit2.http.Query("maxVitaminA") BigDecimal maxVitaminA, @retrofit2.http.Query("minVitaminC") BigDecimal minVitaminC, @retrofit2.http.Query("maxVitaminC") BigDecimal maxVitaminC, @retrofit2.http.Query("minVitaminD") BigDecimal minVitaminD, @retrofit2.http.Query("maxVitaminD") BigDecimal maxVitaminD, @retrofit2.http.Query("minVitaminE") BigDecimal minVitaminE, @retrofit2.http.Query("maxVitaminE") BigDecimal maxVitaminE, @retrofit2.http.Query("minVitaminK") BigDecimal minVitaminK, @retrofit2.http.Query("maxVitaminK") BigDecimal maxVitaminK, @retrofit2.http.Query("minVitaminB1") BigDecimal minVitaminB1, @retrofit2.http.Query("maxVitaminB1") BigDecimal maxVitaminB1, @retrofit2.http.Query("minVitaminB2") BigDecimal minVitaminB2, @retrofit2.http.Query("maxVitaminB2") BigDecimal maxVitaminB2, @retrofit2.http.Query("minVitaminB5") BigDecimal minVitaminB5, @retrofit2.http.Query("maxVitaminB5") BigDecimal maxVitaminB5, @retrofit2.http.Query("minVitaminB3") BigDecimal minVitaminB3, @retrofit2.http.Query("maxVitaminB3") BigDecimal maxVitaminB3, @retrofit2.http.Query("minVitaminB6") BigDecimal minVitaminB6, @retrofit2.http.Query("maxVitaminB6") BigDecimal maxVitaminB6, @retrofit2.http.Query("minVitaminB12") BigDecimal minVitaminB12, @retrofit2.http.Query("maxVitaminB12") BigDecimal maxVitaminB12, @retrofit2.http.Query("minFiber") BigDecimal minFiber, @retrofit2.http.Query("maxFiber") BigDecimal maxFiber, @retrofit2.http.Query("minFolate") BigDecimal minFolate, @retrofit2.http.Query("maxFolate") BigDecimal maxFolate, @retrofit2.http.Query("minFolicAcid") BigDecimal minFolicAcid, @retrofit2.http.Query("maxFolicAcid") BigDecimal maxFolicAcid, @retrofit2.http.Query("minIodine") BigDecimal minIodine, @retrofit2.http.Query("maxIodine") BigDecimal maxIodine, @retrofit2.http.Query("minIron") BigDecimal minIron, @retrofit2.http.Query("maxIron") BigDecimal maxIron, @retrofit2.http.Query("minMagnesium") BigDecimal minMagnesium, @retrofit2.http.Query("maxMagnesium") BigDecimal maxMagnesium, @retrofit2.http.Query("minManganese") BigDecimal minManganese, @retrofit2.http.Query("maxManganese") BigDecimal maxManganese, @retrofit2.http.Query("minPhosphorus") BigDecimal minPhosphorus, @retrofit2.http.Query("maxPhosphorus") BigDecimal maxPhosphorus, @retrofit2.http.Query("minPotassium") BigDecimal minPotassium, @retrofit2.http.Query("maxPotassium") BigDecimal maxPotassium, @retrofit2.http.Query("minSelenium") BigDecimal minSelenium, @retrofit2.http.Query("maxSelenium") BigDecimal maxSelenium, @retrofit2.http.Query("minSodium") BigDecimal minSodium, @retrofit2.http.Query("maxSodium") BigDecimal maxSodium, @retrofit2.http.Query("minSugar") BigDecimal minSugar, @retrofit2.http.Query("maxSugar") BigDecimal maxSugar, @retrofit2.http.Query("minZinc") BigDecimal minZinc, @retrofit2.http.Query("maxZinc") BigDecimal maxZinc, @retrofit2.http.Query("offset") Integer offset, @retrofit2.http.Query("number") Integer number, @retrofit2.http.Query("random") Boolean random, @retrofit2.http.Query("limitLicense") Boolean limitLicense
  );

  /**
   * Summarize Recipe
   * Automatically generate a short description that summarizes key information about the recipe.
   * @param id The item&#x27;s id. (required)
   * @return Call&lt;InlineResponse20014&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Summarize-Recipe">Summarize Recipe Documentation</a>
   */
  @GET("recipes/{id}/summary")
  Call<InlineResponse20014> summarizeRecipe(
    @retrofit2.http.Path("id") Integer id
  );

  /**
   * Equipment Widget
   * Visualize the equipment used to make a recipe.
   * @param contentType The content type. (optional)
   * @param accept Accept header. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Equipment-Widget">Equipment Widget Documentation</a>
   */
  @Headers({
    "Content-Type:application/x-www-form-urlencoded"
  })
  @POST("recipes/visualizeEquipment")
  Call<String> visualizeEquipment(
    @retrofit2.http.Header("Content-Type") String contentType, @retrofit2.http.Header("Accept") String accept
  );

  /**
   * Price Breakdown Widget
   * Visualize the price breakdown of a recipe.
   * @param contentType The content type. (optional)
   * @param accept Accept header. (optional)
   * @param language The language of the input. Either &#x27;en&#x27; or &#x27;de&#x27;. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Price-Breakdown-Widget">Price Breakdown Widget Documentation</a>
   */
  @Headers({
    "Content-Type:application/x-www-form-urlencoded"
  })
  @POST("recipes/visualizePriceEstimator")
  Call<String> visualizePriceBreakdown(
    @retrofit2.http.Header("Content-Type") String contentType, @retrofit2.http.Header("Accept") String accept, @retrofit2.http.Query("language") String language
  );

  /**
   * Equipment by ID Widget
   * Visualize a recipe&#x27;s equipment list.
   * @param id The item&#x27;s id. (required)
   * @param defaultCss Whether the default CSS should be added to the response. (optional, default to true)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Equipment-by-ID-Widget">Equipment by ID Widget Documentation</a>
   */
  @GET("recipes/{id}/equipmentWidget")
  Call<String> visualizeRecipeEquipmentByID(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("defaultCss") Boolean defaultCss
  );

  /**
   * Ingredients by ID Widget
   * Visualize a recipe&#x27;s ingredient list.
   * @param id The item&#x27;s id. (required)
   * @param defaultCss Whether the default CSS should be added to the response. (optional, default to true)
   * @param measure Whether the the measures should be &#x27;us&#x27; or &#x27;metric&#x27;. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Ingredients-by-ID-Widget">Ingredients by ID Widget Documentation</a>
   */
  @GET("recipes/{id}/ingredientWidget")
  Call<String> visualizeRecipeIngredientsByID(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("defaultCss") Boolean defaultCss, @retrofit2.http.Query("measure") String measure
  );

  /**
   * Recipe Nutrition Widget
   * Visualize a recipe&#x27;s nutritional information as HTML including CSS.
   * @param contentType The content type. (optional)
   * @param accept Accept header. (optional)
   * @param language The language of the input. Either &#x27;en&#x27; or &#x27;de&#x27;. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Recipe-Nutrition-Widget">Recipe Nutrition Widget Documentation</a>
   */
  @Headers({
    "Content-Type:application/x-www-form-urlencoded"
  })
  @POST("recipes/visualizeNutrition")
  Call<String> visualizeRecipeNutrition(
    @retrofit2.http.Header("Content-Type") String contentType, @retrofit2.http.Header("Accept") String accept, @retrofit2.http.Query("language") String language
  );

  /**
   * Recipe Nutrition by ID Widget
   * Visualize a recipe&#x27;s nutritional information as HTML including CSS.
   * @param id The item&#x27;s id. (required)
   * @param defaultCss Whether the default CSS should be added to the response. (optional, default to true)
   * @param accept Accept header. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Recipe-Nutrition-by-ID-Widget">Recipe Nutrition by ID Widget Documentation</a>
   */
  @GET("recipes/{id}/nutritionWidget")
  Call<String> visualizeRecipeNutritionByID(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("defaultCss") Boolean defaultCss, @retrofit2.http.Header("Accept") String accept
  );

  /**
   * Price Breakdown by ID Widget
   * Visualize a recipe&#x27;s price breakdown.
   * @param id The item&#x27;s id. (required)
   * @param defaultCss Whether the default CSS should be added to the response. (optional, default to true)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Price-Breakdown-by-ID-Widget">Price Breakdown by ID Widget Documentation</a>
   */
  @GET("recipes/{id}/priceBreakdownWidget")
  Call<String> visualizeRecipePriceBreakdownByID(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("defaultCss") Boolean defaultCss
  );

  /**
   * Recipe Taste Widget
   * Visualize a recipe&#x27;s taste information as HTML including CSS. You can play around with that endpoint!
   * @param language The language of the input. Either &#x27;en&#x27; or &#x27;de&#x27;. (optional)
   * @param contentType The content type. (optional)
   * @param accept Accept header. (optional)
   * @param normalize Whether to normalize to the strongest taste. (optional)
   * @param rgb Red, green, blue values for the chart color. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Recipe-Taste-Widget">Recipe Taste Widget Documentation</a>
   */
  @Headers({
    "Content-Type:application/x-www-form-urlencoded"
  })
  @POST("recipes/visualizeTaste")
  Call<String> visualizeRecipeTaste(
    @retrofit2.http.Query("language") String language, @retrofit2.http.Header("Content-Type") String contentType, @retrofit2.http.Header("Accept") String accept, @retrofit2.http.Query("normalize") Boolean normalize, @retrofit2.http.Query("rgb") String rgb
  );

  /**
   * Recipe Taste by ID Widget
   * Get a recipe&#x27;s taste. The tastes supported are sweet, salty, sour, bitter, savory, and fatty.
   * @param id The item&#x27;s id. (required)
   * @param normalize Whether to normalize to the strongest taste. (optional, default to true)
   * @param rgb Red, green, blue values for the chart color. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Recipe-Taste-by-ID-Widget">Recipe Taste by ID Widget Documentation</a>
   */
  @GET("recipes/{id}/tasteWidget")
  Call<String> visualizeRecipeTasteByID(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("normalize") Boolean normalize, @retrofit2.http.Query("rgb") String rgb
  );

}
