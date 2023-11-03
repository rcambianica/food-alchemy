package com.spoonacular.client;

import com.spoonacular.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.math.BigDecimal;
import com.spoonacular.models.InlineResponse20037;
import com.spoonacular.models.InlineResponse20038;
import com.spoonacular.models.InlineResponse20039;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MenuItemsApi {
  /**
   * Autocomplete Menu Item Search
   * Generate suggestions for menu items based on a (partial) query. The matches will be found by looking in the title only.
   * @param query The (partial) search query. (required)
   * @param number The number of results to return (between 1 and 25). (optional)
   * @return Call&lt;InlineResponse20037&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Autocomplete-Menu-Item-Search">Autocomplete Menu Item Search Documentation</a>
   */
  @GET("food/menuItems/suggest")
  Call<InlineResponse20037> autocompleteMenuItemSearch(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("number") BigDecimal number
  );

  /**
   * Get Menu Item Information
   * Use a menu item id to get all available information about a menu item, such as nutrition.
   * @param id The item&#x27;s id. (required)
   * @return Call&lt;InlineResponse20039&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Menu-Item-Information">Get Menu Item Information Documentation</a>
   */
  @GET("food/menuItems/{id}")
  Call<InlineResponse20039> getMenuItemInformation(
    @retrofit2.http.Path("id") Integer id
  );

  /**
   * Menu Item Nutrition by ID Image
   * Visualize a menu item&#x27;s nutritional information as HTML including CSS.
   * @param id The menu item id. (required)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Menu-Item-Nutrition-by-ID-Image">Menu Item Nutrition by ID Image Documentation</a>
   */
  @GET("food/menuItems/{id}/nutritionWidget.png")
  Call<Object> menuItemNutritionByIDImage(
    @retrofit2.http.Path("id") BigDecimal id
  );

  /**
   * Menu Item Nutrition Label Image
   * Visualize a menu item&#x27;s nutritional label information as an image.
   * @param id The menu item id. (required)
   * @param showOptionalNutrients Whether to show optional nutrients. (optional)
   * @param showZeroValues Whether to show zero values. (optional)
   * @param showIngredients Whether to show a list of ingredients. (optional)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Menu-Item-Nutrition-Label-Image">Menu Item Nutrition Label Image Documentation</a>
   */
  @GET("food/menuItems/{id}/nutritionLabel.png")
  Call<Object> menuItemNutritionLabelImage(
    @retrofit2.http.Path("id") BigDecimal id, @retrofit2.http.Query("showOptionalNutrients") Boolean showOptionalNutrients, @retrofit2.http.Query("showZeroValues") Boolean showZeroValues, @retrofit2.http.Query("showIngredients") Boolean showIngredients
  );

  /**
   * Menu Item Nutrition Label Widget
   * Visualize a menu item&#x27;s nutritional label information as HTML including CSS.
   * @param id The menu item id. (required)
   * @param defaultCss Whether the default CSS should be added to the response. (optional, default to true)
   * @param showOptionalNutrients Whether to show optional nutrients. (optional)
   * @param showZeroValues Whether to show zero values. (optional)
   * @param showIngredients Whether to show a list of ingredients. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Menu-Item-Nutrition-Label-Widget">Menu Item Nutrition Label Widget Documentation</a>
   */
  @GET("food/menuItems/{id}/nutritionLabel")
  Call<String> menuItemNutritionLabelWidget(
    @retrofit2.http.Path("id") BigDecimal id, @retrofit2.http.Query("defaultCss") Boolean defaultCss, @retrofit2.http.Query("showOptionalNutrients") Boolean showOptionalNutrients, @retrofit2.http.Query("showZeroValues") Boolean showZeroValues, @retrofit2.http.Query("showIngredients") Boolean showIngredients
  );

  /**
   * Search Menu Items
   * Search over 115,000 menu items from over 800 fast food and chain restaurants. For example, McDonald&#x27;s Big Mac or Starbucks Mocha.
   * @param query The (natural language) search query. (optional)
   * @param minCalories The minimum amount of calories the menu item must have. (optional)
   * @param maxCalories The maximum amount of calories the menu item can have. (optional)
   * @param minCarbs The minimum amount of carbohydrates in grams the menu item must have. (optional)
   * @param maxCarbs The maximum amount of carbohydrates in grams the menu item can have. (optional)
   * @param minProtein The minimum amount of protein in grams the menu item must have. (optional)
   * @param maxProtein The maximum amount of protein in grams the menu item can have. (optional)
   * @param minFat The minimum amount of fat in grams the menu item must have. (optional)
   * @param maxFat The maximum amount of fat in grams the menu item can have. (optional)
   * @param addMenuItemInformation If set to true, you get more information about the menu items returned. (optional)
   * @param offset The number of results to skip (between 0 and 900). (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @return Call&lt;InlineResponse20038&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-Menu-Items">Search Menu Items Documentation</a>
   */
  @GET("food/menuItems/search")
  Call<InlineResponse20038> searchMenuItems(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("minCalories") BigDecimal minCalories, @retrofit2.http.Query("maxCalories") BigDecimal maxCalories, @retrofit2.http.Query("minCarbs") BigDecimal minCarbs, @retrofit2.http.Query("maxCarbs") BigDecimal maxCarbs, @retrofit2.http.Query("minProtein") BigDecimal minProtein, @retrofit2.http.Query("maxProtein") BigDecimal maxProtein, @retrofit2.http.Query("minFat") BigDecimal minFat, @retrofit2.http.Query("maxFat") BigDecimal maxFat, @retrofit2.http.Query("addMenuItemInformation") Boolean addMenuItemInformation, @retrofit2.http.Query("offset") Integer offset, @retrofit2.http.Query("number") Integer number
  );

  /**
   * Menu Item Nutrition by ID Widget
   * Visualize a menu item&#x27;s nutritional information as HTML including CSS.
   * @param id The item&#x27;s id. (required)
   * @param defaultCss Whether the default CSS should be added to the response. (optional, default to true)
   * @param accept Accept header. (optional)
   * @return Call&lt;String&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Menu-Item-Nutrition-by-ID-Widget">Menu Item Nutrition by ID Widget Documentation</a>
   */
  @GET("food/menuItems/{id}/nutritionWidget")
  Call<String> visualizeMenuItemNutritionByID(
    @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("defaultCss") Boolean defaultCss, @retrofit2.http.Header("Accept") String accept
  );

}
