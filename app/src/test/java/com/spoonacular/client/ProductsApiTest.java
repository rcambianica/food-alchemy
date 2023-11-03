package com.spoonacular.client;

import com.spoonacular.ApiClient;
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
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * API tests for ProductsApi
 */
public class ProductsApiTest {

    private ProductsApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(ProductsApi.class);
    }


    /**
     * Autocomplete Product Search
     *
     * Generate suggestions for grocery products based on a (partial) query. The matches will be found by looking in the title only.
     */
    @Test
    public void autocompleteProductSearchTest() {
        String query = null;
        Integer number = null;
        // InlineResponse20033 response = api.autocompleteProductSearch(query, number);

        // TODO: test validations
    }

    /**
     * Classify Grocery Product
     *
     * This endpoint allows you to match a packaged food to a basic category, e.g. a specific brand of milk to the category milk.
     */
    @Test
    public void classifyGroceryProductTest() {
        ProductsClassifyBody body = null;
        String locale = null;
        // InlineResponse20034 response = api.classifyGroceryProduct(body, locale);

        // TODO: test validations
    }

    /**
     * Classify Grocery Product Bulk
     *
     * Provide a set of product jsons, get back classified products.
     */
    @Test
    public void classifyGroceryProductBulkTest() {
        List<ProductsClassifyBatchBody> body = null;
        String locale = null;
        // List<InlineResponse20035> response = api.classifyGroceryProductBulk(body, locale);

        // TODO: test validations
    }

    /**
     * Get Comparable Products
     *
     * Find comparable products to the given one.
     */
    @Test
    public void getComparableProductsTest() {
        BigDecimal upc = null;
        // InlineResponse20032 response = api.getComparableProducts(upc);

        // TODO: test validations
    }

    /**
     * Get Product Information
     *
     * Use a product id to get full information about a product, such as ingredients, nutrition, etc. The nutritional information is per serving.
     */
    @Test
    public void getProductInformationTest() {
        Integer id = null;
        // InlineResponse20031 response = api.getProductInformation(id);

        // TODO: test validations
    }

    /**
     * Product Nutrition by ID Image
     *
     * Visualize a product&#x27;s nutritional information as an image.
     */
    @Test
    public void productNutritionByIDImageTest() {
        BigDecimal id = null;
        // Object response = api.productNutritionByIDImage(id);

        // TODO: test validations
    }

    /**
     * Product Nutrition Label Image
     *
     * Get a product&#x27;s nutrition label as an image.
     */
    @Test
    public void productNutritionLabelImageTest() {
        BigDecimal id = null;
        Boolean showOptionalNutrients = null;
        Boolean showZeroValues = null;
        Boolean showIngredients = null;
        // Object response = api.productNutritionLabelImage(id, showOptionalNutrients, showZeroValues, showIngredients);

        // TODO: test validations
    }

    /**
     * Product Nutrition Label Widget
     *
     * Get a product&#x27;s nutrition label as an HTML widget.
     */
    @Test
    public void productNutritionLabelWidgetTest() {
        BigDecimal id = null;
        Boolean defaultCss = null;
        Boolean showOptionalNutrients = null;
        Boolean showZeroValues = null;
        Boolean showIngredients = null;
        // String response = api.productNutritionLabelWidget(id, defaultCss, showOptionalNutrients, showZeroValues, showIngredients);

        // TODO: test validations
    }

    /**
     * Search Grocery Products
     *
     * Search packaged food products, such as frozen pizza or Greek yogurt.
     */
    @Test
    public void searchGroceryProductsTest() {
        String query = null;
        BigDecimal minCalories = null;
        BigDecimal maxCalories = null;
        BigDecimal minCarbs = null;
        BigDecimal maxCarbs = null;
        BigDecimal minProtein = null;
        BigDecimal maxProtein = null;
        BigDecimal minFat = null;
        BigDecimal maxFat = null;
        Boolean addProductInformation = null;
        Integer offset = null;
        Integer number = null;
        // InlineResponse20028 response = api.searchGroceryProducts(query, minCalories, maxCalories, minCarbs, maxCarbs, minProtein, maxProtein, minFat, maxFat, addProductInformation, offset, number);

        // TODO: test validations
    }

    /**
     * Search Grocery Products by UPC
     *
     * Get information about a packaged food using its UPC.
     */
    @Test
    public void searchGroceryProductsByUPCTest() {
        BigDecimal upc = null;
        // InlineResponse20029 response = api.searchGroceryProductsByUPC(upc);

        // TODO: test validations
    }

    /**
     * Product Nutrition by ID Widget
     *
     * Visualize a product&#x27;s nutritional information as HTML including CSS.
     */
    @Test
    public void visualizeProductNutritionByIDTest() {
        Integer id = null;
        Boolean defaultCss = null;
        String accept = null;
        // String response = api.visualizeProductNutritionByID(id, defaultCss, accept);

        // TODO: test validations
    }
}
