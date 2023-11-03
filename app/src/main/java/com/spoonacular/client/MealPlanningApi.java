package com.spoonacular.client;

import com.spoonacular.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.math.BigDecimal;
import com.spoonacular.models.DayDateBody;
import com.spoonacular.models.InlineResponse20040;
import com.spoonacular.models.InlineResponse20041;
import com.spoonacular.models.InlineResponse20042;
import com.spoonacular.models.InlineResponse20043;
import com.spoonacular.models.InlineResponse20044;
import com.spoonacular.models.InlineResponse20045;
import com.spoonacular.models.InlineResponse20046;
import com.spoonacular.models.InlineResponse20047;
import com.spoonacular.models.ItemsIdBody;
import com.spoonacular.models.ItemsIdBody1;
import com.spoonacular.models.ShoppinglistItemsBody;
import com.spoonacular.models.ShoppinglistItemsBody1;
import com.spoonacular.models.StartdateEnddateBody;
import com.spoonacular.models.TemplatesIdBody;
import com.spoonacular.models.UsernameItemsBody;
import com.spoonacular.models.UsernameItemsBody1;
import com.spoonacular.models.UsernameTemplatesBody;
import com.spoonacular.models.UsersConnectBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MealPlanningApi {
  /**
   * Add Meal Plan Template
   * Add a meal plan template for a user.
   * @param body  (required)
   * @param hash The private hash for the username. (required)
   * @param username The username. (required)
   * @return Call&lt;InlineResponse20043&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Add-Meal-Plan-Template">Add Meal Plan Template Documentation</a>
   */
  @Headers({
    "Content-Type:"
  })
  @POST("mealplanner/{username}/templates")
  Call<InlineResponse20043> addMealPlanTemplate(
    @retrofit2.http.Body UsernameTemplatesBody body, @retrofit2.http.Query("hash") String hash, @retrofit2.http.Path("username") String username
  );

  /**
   * Add to Meal Plan
   * Add an item to the user&#x27;s meal plan.
   * @param body  (required)
   * @param hash The private hash for the username. (required)
   * @param username The username. (required)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Add-to-Meal-Plan">Add to Meal Plan Documentation</a>
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("mealplanner/{username}/items")
  Call<Object> addToMealPlan(
    @retrofit2.http.Body UsernameItemsBody body, @retrofit2.http.Query("hash") String hash, @retrofit2.http.Path("username") String username
  );

  /**
   * Add to Shopping List
   * Add an item to the current shopping list of a user.
   * @param body  (required)
   * @param hash The private hash for the username. (required)
   * @param username The username. (required)
   * @return Call&lt;InlineResponse20046&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Add-to-Shopping-List">Add to Shopping List Documentation</a>
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("mealplanner/{username}/shopping-list/items")
  Call<InlineResponse20046> addToShoppingList(
    @retrofit2.http.Body ShoppinglistItemsBody body, @retrofit2.http.Query("hash") String hash, @retrofit2.http.Path("username") String username
  );

  /**
   * Clear Meal Plan Day
   * Delete all planned items from the user&#x27;s meal plan for a specific day.
   * @param body  (required)
   * @param hash The private hash for the username. (required)
   * @param username The username. (required)
   * @param date The date in the format yyyy-mm-dd. (required)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Clear-Meal-Plan-Day">Clear Meal Plan Day Documentation</a>
   */
  @Headers({
    "Content-Type:"
  })
  @DELETE("mealplanner/{username}/day/{date}")
  Call<Object> clearMealPlanDay(
    @retrofit2.http.Body DayDateBody body, @retrofit2.http.Query("hash") String hash, @retrofit2.http.Path("username") String username, @retrofit2.http.Path("date") String date
  );

  /**
   * Connect User
   * In order to call user-specific endpoints, you need to connect your app&#x27;s users to spoonacular users.
   * @param body  (required)
   * @return Call&lt;InlineResponse20047&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Connect-User">Connect User Documentation</a>
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("users/connect")
  Call<InlineResponse20047> connectUser(
    @retrofit2.http.Body Object body
  );

  /**
   * Delete from Meal Plan
   * Delete an item from the user&#x27;s meal plan.
   * @param body  (required)
   * @param hash The private hash for the username. (required)
   * @param username The username. (required)
   * @param id The shopping list item id. (required)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Delete-from-Meal-Plan">Delete from Meal Plan Documentation</a>
   */
  @Headers({
    "Content-Type:"
  })
  @DELETE("mealplanner/{username}/items/{id}")
  Call<Object> deleteFromMealPlan(
    @retrofit2.http.Body ItemsIdBody body, @retrofit2.http.Query("hash") String hash, @retrofit2.http.Path("username") String username, @retrofit2.http.Path("id") BigDecimal id
  );

  /**
   * Delete from Shopping List
   * Delete an item from the current shopping list of the user.
   * @param body  (required)
   * @param hash The private hash for the username. (required)
   * @param username The username. (required)
   * @param id The item&#x27;s id. (required)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Delete-from-Shopping-List">Delete from Shopping List Documentation</a>
   */
  @Headers({
    "Content-Type:"
  })
  @DELETE("mealplanner/{username}/shopping-list/items/{id}")
  Call<Object> deleteFromShoppingList(
    @retrofit2.http.Body ItemsIdBody1 body, @retrofit2.http.Query("hash") String hash, @retrofit2.http.Path("username") String username, @retrofit2.http.Path("id") Integer id
  );

  /**
   * Delete Meal Plan Template
   * Delete a meal plan template for a user.
   * @param body  (required)
   * @param hash The private hash for the username. (required)
   * @param username The username. (required)
   * @param id The item&#x27;s id. (required)
   * @return Call&lt;Object&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Delete-Meal-Plan-Template">Delete Meal Plan Template Documentation</a>
   */
  @Headers({
    "Content-Type:"
  })
  @DELETE("mealplanner/{username}/templates/{id}")
  Call<Object> deleteMealPlanTemplate(
    @retrofit2.http.Body TemplatesIdBody body, @retrofit2.http.Query("hash") String hash, @retrofit2.http.Path("username") String username, @retrofit2.http.Path("id") Integer id
  );

  /**
   * Generate Meal Plan
   * Generate a meal plan with three meals per day (breakfast, lunch, and dinner).
   * @param timeFrame Either for one \&quot;day\&quot; or an entire \&quot;week\&quot;. (optional)
   * @param targetCalories What is the caloric target for one day? The meal plan generator will try to get as close as possible to that goal. (optional)
   * @param diet Enter a diet that the meal plan has to adhere to. See a full list of supported diets. (optional)
   * @param exclude A comma-separated list of allergens or ingredients that must be excluded. (optional)
   * @return Call&lt;InlineResponse20040&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Generate-Meal-Plan">Generate Meal Plan Documentation</a>
   */
  @GET("mealplanner/generate")
  Call<InlineResponse20040> generateMealPlan(
    @retrofit2.http.Query("timeFrame") String timeFrame, @retrofit2.http.Query("targetCalories") BigDecimal targetCalories, @retrofit2.http.Query("diet") String diet, @retrofit2.http.Query("exclude") String exclude
  );

  /**
   * Generate Shopping List
   * Generate the shopping list for a user from the meal planner in a given time frame.
   * @param body  (required)
   * @param hash The private hash for the username. (required)
   * @param username The username. (required)
   * @param startDate The start date in the format yyyy-mm-dd. (required)
   * @param endDate The end date in the format yyyy-mm-dd. (required)
   * @return Call&lt;InlineResponse20046&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Generate-Shopping-List">Generate Shopping List Documentation</a>
   */
  @Headers({
    "Content-Type:"
  })
  @POST("mealplanner/{username}/shopping-list/{start-date}/{end-date}")
  Call<InlineResponse20046> generateShoppingList(
    @retrofit2.http.Body StartdateEnddateBody body, @retrofit2.http.Query("hash") String hash, @retrofit2.http.Path("username") String username, @retrofit2.http.Path("start-date") String startDate, @retrofit2.http.Path("end-date") String endDate
  );

  /**
   * Get Meal Plan Template
   * Get information about a meal plan template.
   * @param username The username. (required)
   * @param id The item&#x27;s id. (required)
   * @param hash The private hash for the username. (required)
   * @return Call&lt;InlineResponse20044&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Meal-Plan-Template">Get Meal Plan Template Documentation</a>
   */
  @GET("mealplanner/{username}/templates/{id}")
  Call<InlineResponse20044> getMealPlanTemplate(
    @retrofit2.http.Path("username") String username, @retrofit2.http.Path("id") Integer id, @retrofit2.http.Query("hash") String hash
  );

  /**
   * Get Meal Plan Templates
   * Get meal plan templates from user or public ones.
   * @param username The username. (required)
   * @param hash The private hash for the username. (required)
   * @return Call&lt;InlineResponse20042&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Meal-Plan-Templates">Get Meal Plan Templates Documentation</a>
   */
  @GET("mealplanner/{username}/templates")
  Call<InlineResponse20042> getMealPlanTemplates(
    @retrofit2.http.Path("username") String username, @retrofit2.http.Query("hash") String hash
  );

  /**
   * Get Meal Plan Week
   * Retrieve a meal planned week for the given user. The username must be a spoonacular user and the hash must the the user&#x27;s hash that can be found in his/her account.
   * @param username The username. (required)
   * @param startDate The start date of the meal planned week in the format yyyy-mm-dd. (required)
   * @param hash The private hash for the username. (required)
   * @return Call&lt;InlineResponse20041&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Meal-Plan-Week">Get Meal Plan Week Documentation</a>
   */
  @GET("mealplanner/{username}/week/{start-date}")
  Call<InlineResponse20041> getMealPlanWeek(
    @retrofit2.http.Path("username") String username, @retrofit2.http.Path("start-date") String startDate, @retrofit2.http.Query("hash") String hash
  );

  /**
   * Get Shopping List
   * Get the current shopping list for the given user.
   * @param username The username. (required)
   * @param hash The private hash for the username. (required)
   * @return Call&lt;InlineResponse20045&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Get-Shopping-List">Get Shopping List Documentation</a>
   */
  @GET("mealplanner/{username}/shopping-list")
  Call<InlineResponse20045> getShoppingList(
    @retrofit2.http.Path("username") String username, @retrofit2.http.Query("hash") String hash
  );

}
