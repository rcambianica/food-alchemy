package com.spoonacular.client;

import com.spoonacular.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.math.BigDecimal;
import com.spoonacular.models.InlineResponse20028;
import com.spoonacular.models.InlineResponse20029;
import com.spoonacular.models.InlineResponse20031;
import com.spoonacular.models.InlineResponse20032;
import com.spoonacular.models.InlineResponse20033;
import com.spoonacular.models.InlineResponse20034;
import com.spoonacular.models.InlineResponse20035;
import com.spoonacular.models.ProductsClassifyBatchBody;
import com.spoonacular.models.ProductsClassifyBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProductsApi {
  /**
   * Autocomplete Product Search
   * Generate suggestions for grocery products based on a (partial) query. The matches will be found by looking in the title only.
   * @param query The (partial) search query. (required)
   * @param number The number of results to return (between 1 and 25). (optional)
   * @return Call&lt;InlineResponse20033&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Autocomplete-Product-Search">Autocomplete Product Search Documentation</a>
   */
  @GET("food/products/suggest")
  Call<InlineResponse20033> autocompleteProductSearch(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("number") Integer number
  );

  /**
   * Classify Grocery Product
   * This endpoint allows you to match a packaged food to a basic category, e.g. a specific brand of milk to the category milk.
   * @param body  (required)
   * @param locale The display name of the returned category, supported is en_US (for American English) and en_GB (for British English). (optional)
   * @return Call&lt;InlineResponse20034&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Classify-Grocery-Product">Classify Grocery Product Documentation</a>
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("food/products/classify")
  Call<InlineResponse20034> classifyGroceryProduct(
    @retrofit2.http.Body ProductsClassifyBody body, @retrofit2.http.Query("locale") String locale
  );

  /**
   * Classify Grocery Product Bulk
   * Provide a set of product jsons, get back classified products.
   * @param body  (required)
   * @param locale The display name of the returned category, supported is en_US (for American English) and en_GB (for British English). (optional)
   * @return Call&lt;List&lt;InlineResponse20035&gt;&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Classify-Grocery-Product-Bulk">Classify Grocery Product Bulk Documentation</a>
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("food/products/classifyBatch")
  Call<List<InlineResponse20035>> classifyGroceryProductBulk(
    @retrofit2.http.Body List<ProductsClassifyBatchBody> body, @retrofit2.http.Query("locale") String locale
  );

  /**
   * Get Comparable Products
   * Find comparable products to the given one.
   * @param upc The UPC of the product for which you want to find comparable products. (required)
   * @return Call&lt;InlineResponse20032&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Comparable-Products">Get Comparable Products Documentation</a>
   */
  @GET("food/products/upc/{upc}/comparable")
  Call<InlineResponse20032> getComparableProducts(
    @retrofit2.http.Path("upc") BigDecimal upc
  );

  /**
   * Get Product Information
   * Use a product id to get full information about a product, such as ingredients, nutrition, etc. The nutritional information is per serving.
   * @param id The item&#x27;s id. (required)
   * @return Call&lt;InlineResponse20031&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Product-Information">Get Product Information Documentation</a>
   */
  @GET("food/products/{id}")
  Call<InlineResponse20031> getProductInformation(
    @retrofit2.http.Path("id") Integer id
  );

  /**
   * Product Nutrition by ID Image
   * Visualize a product&#x27;s nutritional information as an image.
   * @param id The id of the product. (required)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Product-Nutrition-by-ID-Image">Product Nutrition by ID Image Documentation</a>
   */
  @GET("food/products/{id}/nutritionWidget.png")
  Call<Object> productNutritionByIDImage(
    @retrofit2.http.Path("id") BigDecimal id
  );

  /**
   * Product Nutrition Label Image
   * Get a product&#x27;s nutrition label as an image.
   * @param id The product id. (required)
   * @param showOptionalNutrients Whether to show optional nutrients. (optional)
   * @param showZeroValues Whether to show zero values. (optional)
   * @param showIngredients Whether to show a list of ingredients. (optional)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Product-Nutrition-Label-Image">Product Nutrition Label Image Documentation</a>
   */
  @GET("food/products/{id}/nutritionLabel.png")
  Call<Object> productNutritionLabelImage(
    @retrofit2.http.Path("id") BigDecimal id, @retrofit2.http.Query("showOptionalNutrients") Boolean showOptionalNutrients, @retrofit2.http.Query("showZeroValues") Boolean showZeroValues, @retrofit2.http.Query("showIngredients") Boolean showIngredients
  );

  /**
   * Product Nutrition Label Widget
   * Get a product&#x27;s nutrition label as an HTML widget.
   * @param id The product id. (required)
   * @param defaultCss Whether the default CSS should be added to the response. (optional, default to true)
   * @param showOptionalNutrients Whether to show optional nutrients. (optional)
   * @param showZeroValues Whether to show zero values. (optional)
   * @param showIngredients Whether to show a list of ingredients. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Product-Nutrition-Label-Widget">Product Nutrition Label Widget Documentation</a>
   */
  @GET("food/products/{id}/nutritionLabel")
  Call<String> productNutritionLabelWidget(
    @retrofit2.http.Path("id") BigDecimal id, @retrofit2.http.Query("defaultCss") Boolean defaultCss, @retrofit2.http.Query("showOptionalNutrients") Boolean showOptionalNutrients, @retrofit2.http.Query("showZeroValues") Boolean showZeroValues, @retrofit2.http.Query("showIngredients") Boolean showIngredients
  );

  /**
   * Search Grocery Products
   * Search packaged food products, such as frozen pizza or Greek yogurt.
   * @param query The (natural language) search query. (optional)
   * @param minCalories The minimum amount of calories the product must have. (optional)
   * @param maxCalories The maximum amount of calories the product can have. (optional)
   * @param minCarbs The minimum amount of carbohydrates in grams the product must have. (optional)
   * @param maxCarbs The maximum amount of carbohydrates in grams the product can have. (optional)
   * @param minProtein The minimum amount of protein in grams the product must have. (optional)
   * @param maxProtein The maximum amount of protein in grams the product can have. (optional)
   * @param minFat The minimum amount of fat in grams the product must have. (optional)
   * @param maxFat The maximum amount of fat in grams the product can have. (optional)
   * @param addProductInformation If set to true, you get more information about the products returned. (optional)
   * @param offset The number of results to skip (between 0 and 900). (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @return Call&lt;InlineResponse20028&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-Grocery-Products">Search Grocery Products Documentation</a>
   */
  @GET("food/products/search")
  Call<InlineResponse20028> searchGroceryProducts(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("minCalories") BigDecimal minCalories, @retrofit2.http.Query("maxCalories") BigDecimal maxCalories, @retrofit2.http.Query("minCarbs") BigDecimal minCarbs, @retrofit2.http.Query("maxCarbs") BigDecimal maxCarbs, @retrofit2.http.Query("minProtein") BigDecimal minProtein, @retrofit2.http.Query("maxProtein") BigDecimal maxProtein, @retrofit2.http.Query("minFat") BigDecimal minFat, @retrofit2.http.Query("maxFat") BigDecimal maxFat, @retrofit2.http.Query("addProductInformation") Boolean addProductInformation, @retrofit2.http.Query("offset") Integer offset, @retrofit2.http.Query("number") Integer number
  );

  /**
   * Search Grocery Products by UPC
   * Get information about a packaged food using its UPC.
   * @param upc The product&#x27;s UPC. (required)
   * @return Call&lt;InlineResponse20029&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-Grocery-Products-by-UPC">Search Grocery Products by UPC Documentation</a>
   */
  @GET("food/products/upc/{upc}")
  Call<InlineResponse20029> searchGroceryProductsByUPC(
    @retrofit2.http.Path("upc") BigDecimal upc
  );

  /**
   * Product Nutrition by ID Widget
   * Visualize a product&#x27;s nutritional information as HTML including CSS.
   * @param id The item&#x27;s id. (required)
   * @param defaultCss Whether the default CSS should be added to the response. (optional, default to true)
   * @param accept Accept header. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Product-Nutrition-by-ID-Widget">Product Nutrition by ID Widget Documentation</a>
   */
  @GET("food/products/{id}/nutritionWidget")
  Call<String> visualizeProductNutritionByID(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("defaultCss") Boolean defaultCss, @retrofit2.http.Header("Accept") String accept
  );

}
