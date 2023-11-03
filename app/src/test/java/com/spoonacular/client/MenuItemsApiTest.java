package com.spoonacular.client;

import com.spoonacular.ApiClient;
import java.math.BigDecimal;
import com.spoonacular.models.InlineResponse20037;
import com.spoonacular.models.InlineResponse20038;
import com.spoonacular.models.InlineResponse20039;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * API tests for MenuItemsApi
 */
public class MenuItemsApiTest {

    private MenuItemsApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(MenuItemsApi.class);
    }


    /**
     * Autocomplete Menu Item Search
     *
     * Generate suggestions for menu items based on a (partial) query. The matches will be found by looking in the title only.
     */
    @Test
    public void autocompleteMenuItemSearchTest() {
        String query = null;
        BigDecimal number = null;
        // InlineResponse20037 response = api.autocompleteMenuItemSearch(query, number);

        // TODO: test validations
    }

    /**
     * Get Menu Item Information
     *
     * Use a menu item id to get all available information about a menu item, such as nutrition.
     */
    @Test
    public void getMenuItemInformationTest() {
        Integer id = null;
        // InlineResponse20039 response = api.getMenuItemInformation(id);

        // TODO: test validations
    }

    /**
     * Menu Item Nutrition by ID Image
     *
     * Visualize a menu item&#x27;s nutritional information as HTML including CSS.
     */
    @Test
    public void menuItemNutritionByIDImageTest() {
        BigDecimal id = null;
        // Object response = api.menuItemNutritionByIDImage(id);

        // TODO: test validations
    }

    /**
     * Menu Item Nutrition Label Image
     *
     * Visualize a menu item&#x27;s nutritional label information as an image.
     */
    @Test
    public void menuItemNutritionLabelImageTest() {
        BigDecimal id = null;
        Boolean showOptionalNutrients = null;
        Boolean showZeroValues = null;
        Boolean showIngredients = null;
        // Object response = api.menuItemNutritionLabelImage(id, showOptionalNutrients, showZeroValues, showIngredients);

        // TODO: test validations
    }

    /**
     * Menu Item Nutrition Label Widget
     *
     * Visualize a menu item&#x27;s nutritional label information as HTML including CSS.
     */
    @Test
    public void menuItemNutritionLabelWidgetTest() {
        BigDecimal id = null;
        Boolean defaultCss = null;
        Boolean showOptionalNutrients = null;
        Boolean showZeroValues = null;
        Boolean showIngredients = null;
        // String response = api.menuItemNutritionLabelWidget(id, defaultCss, showOptionalNutrients, showZeroValues, showIngredients);

        // TODO: test validations
    }

    /**
     * Search Menu Items
     *
     * Search over 115,000 menu items from over 800 fast food and chain restaurants. For example, McDonald&#x27;s Big Mac or Starbucks Mocha.
     */
    @Test
    public void searchMenuItemsTest() {
        String query = null;
        BigDecimal minCalories = null;
        BigDecimal maxCalories = null;
        BigDecimal minCarbs = null;
        BigDecimal maxCarbs = null;
        BigDecimal minProtein = null;
        BigDecimal maxProtein = null;
        BigDecimal minFat = null;
        BigDecimal maxFat = null;
        Boolean addMenuItemInformation = null;
        Integer offset = null;
        Integer number = null;
        // InlineResponse20038 response = api.searchMenuItems(query, minCalories, maxCalories, minCarbs, maxCarbs, minProtein, maxProtein, minFat, maxFat, addMenuItemInformation, offset, number);

        // TODO: test validations
    }

    /**
     * Menu Item Nutrition by ID Widget
     *
     * Visualize a menu item&#x27;s nutritional information as HTML including CSS.
     */
    @Test
    public void visualizeMenuItemNutritionByIDTest() {
        Integer id = null;
        Boolean defaultCss = null;
        String accept = null;
        // String response = api.visualizeMenuItemNutritionByID(id, defaultCss, accept);

        // TODO: test validations
    }
}
