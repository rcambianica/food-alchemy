package com.spoonacular.client;

import com.spoonacular.ApiClient;
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
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * API tests for MealPlanningApi
 */
public class MealPlanningApiTest {

    private MealPlanningApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(MealPlanningApi.class);
    }


    /**
     * Add Meal Plan Template
     *
     * Add a meal plan template for a user.
     */
    @Test
    public void addMealPlanTemplateTest() {
        UsernameTemplatesBody body = null;
        String hash = null;
        String username = null;
        // InlineResponse20043 response = api.addMealPlanTemplate(body, hash, username);

        // TODO: test validations
    }

    /**
     * Add to Meal Plan
     *
     * Add an item to the user&#x27;s meal plan.
     */
    @Test
    public void addToMealPlanTest() {
        UsernameItemsBody body = null;
        String hash = null;
        String username = null;
        // Object response = api.addToMealPlan(body, hash, username);

        // TODO: test validations
    }

    /**
     * Add to Shopping List
     *
     * Add an item to the current shopping list of a user.
     */
    @Test
    public void addToShoppingListTest() {
        ShoppinglistItemsBody body = null;
        String hash = null;
        String username = null;
        // InlineResponse20046 response = api.addToShoppingList(body, hash, username);

        // TODO: test validations
    }

    /**
     * Clear Meal Plan Day
     *
     * Delete all planned items from the user&#x27;s meal plan for a specific day.
     */
    @Test
    public void clearMealPlanDayTest() {
        DayDateBody body = null;
        String hash = null;
        String username = null;
        String date = null;
        // Object response = api.clearMealPlanDay(body, hash, username, date);

        // TODO: test validations
    }

    /**
     * Connect User
     *
     * In order to call user-specific endpoints, you need to connect your app&#x27;s users to spoonacular users.
     */
    @Test
    public void connectUserTest() {
        Object body = null;
        // InlineResponse20047 response = api.connectUser(body);

        // TODO: test validations
    }

    /**
     * Delete from Meal Plan
     *
     * Delete an item from the user&#x27;s meal plan.
     */
    @Test
    public void deleteFromMealPlanTest() {
        ItemsIdBody body = null;
        String hash = null;
        String username = null;
        BigDecimal id = null;
        // Object response = api.deleteFromMealPlan(body, hash, username, id);

        // TODO: test validations
    }

    /**
     * Delete from Shopping List
     *
     * Delete an item from the current shopping list of the user.
     */
    @Test
    public void deleteFromShoppingListTest() {
        ItemsIdBody1 body = null;
        String hash = null;
        String username = null;
        Integer id = null;
        // Object response = api.deleteFromShoppingList(body, hash, username, id);

        // TODO: test validations
    }

    /**
     * Delete Meal Plan Template
     *
     * Delete a meal plan template for a user.
     */
    @Test
    public void deleteMealPlanTemplateTest() {
        TemplatesIdBody body = null;
        String hash = null;
        String username = null;
        Integer id = null;
        // Object response = api.deleteMealPlanTemplate(body, hash, username, id);

        // TODO: test validations
    }

    /**
     * Generate Meal Plan
     *
     * Generate a meal plan with three meals per day (breakfast, lunch, and dinner).
     */
    @Test
    public void generateMealPlanTest() {
        String timeFrame = null;
        BigDecimal targetCalories = null;
        String diet = null;
        String exclude = null;
        // InlineResponse20040 response = api.generateMealPlan(timeFrame, targetCalories, diet, exclude);

        // TODO: test validations
    }

    /**
     * Generate Shopping List
     *
     * Generate the shopping list for a user from the meal planner in a given time frame.
     */
    @Test
    public void generateShoppingListTest() {
        StartdateEnddateBody body = null;
        String hash = null;
        String username = null;
        String startDate = null;
        String endDate = null;
        // InlineResponse20046 response = api.generateShoppingList(body, hash, username, startDate, endDate);

        // TODO: test validations
    }

    /**
     * Get Meal Plan Template
     *
     * Get information about a meal plan template.
     */
    @Test
    public void getMealPlanTemplateTest() {
        String username = null;
        Integer id = null;
        String hash = null;
        // InlineResponse20044 response = api.getMealPlanTemplate(username, id, hash);

        // TODO: test validations
    }

    /**
     * Get Meal Plan Templates
     *
     * Get meal plan templates from user or public ones.
     */
    @Test
    public void getMealPlanTemplatesTest() {
        String username = null;
        String hash = null;
        // InlineResponse20042 response = api.getMealPlanTemplates(username, hash);

        // TODO: test validations
    }

    /**
     * Get Meal Plan Week
     *
     * Retrieve a meal planned week for the given user. The username must be a spoonacular user and the hash must the the user&#x27;s hash that can be found in his/her account.
     */
    @Test
    public void getMealPlanWeekTest() {
        String username = null;
        String startDate = null;
        String hash = null;
        // InlineResponse20041 response = api.getMealPlanWeek(username, startDate, hash);

        // TODO: test validations
    }

    /**
     * Get Shopping List
     *
     * Get the current shopping list for the given user.
     */
    @Test
    public void getShoppingListTest() {
        String username = null;
        String hash = null;
        // InlineResponse20045 response = api.getShoppingList(username, hash);

        // TODO: test validations
    }
}
