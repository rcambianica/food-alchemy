package com.spoonacular.client;

import com.spoonacular.ApiClient;
import java.math.BigDecimal;
import com.spoonacular.models.IngredientsMapBody;
import com.spoonacular.models.InlineResponse20022;
import com.spoonacular.models.InlineResponse20023;
import com.spoonacular.models.InlineResponse20025;
import com.spoonacular.models.InlineResponse20026;
import com.spoonacular.models.InlineResponse20027;
import com.spoonacular.models.InlineResponse20036;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * API tests for IngredientsApi
 */
public class IngredientsApiTest {

    private IngredientsApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(IngredientsApi.class);
    }


    /**
     * Autocomplete Ingredient Search
     *
     * Autocomplete the entry of an ingredient.
     */
    @Test
    public void autocompleteIngredientSearchTest() {
        String query = null;
        Integer number = null;
        Boolean metaInformation = null;
        String intolerances = null;
        String language = null;
        // List<InlineResponse20025> response = api.autocompleteIngredientSearch(query, number, metaInformation, intolerances, language);

        // TODO: test validations
    }

    /**
     * Compute Ingredient Amount
     *
     * Compute the amount you need of a certain ingredient for a certain nutritional goal. For example, how much pineapple do you have to eat to get 10 grams of protein?
     */
    @Test
    public void computeIngredientAmountTest() {
        BigDecimal id = null;
        String nutrient = null;
        BigDecimal target = null;
        String unit = null;
        // InlineResponse20023 response = api.computeIngredientAmount(id, nutrient, target, unit);

        // TODO: test validations
    }

    /**
     * Get Ingredient Information
     *
     * Use an ingredient id to get all available information about an ingredient, such as its image and supermarket aisle.
     */
    @Test
    public void getIngredientInformationTest() {
        Integer id = null;
        BigDecimal amount = null;
        String unit = null;
        // InlineResponse20022 response = api.getIngredientInformation(id, amount, unit);

        // TODO: test validations
    }

    /**
     * Get Ingredient Substitutes
     *
     * Search for substitutes for a given ingredient.
     */
    @Test
    public void getIngredientSubstitutesTest() {
        String ingredientName = null;
        // InlineResponse20027 response = api.getIngredientSubstitutes(ingredientName);

        // TODO: test validations
    }

    /**
     * Get Ingredient Substitutes by ID
     *
     * Search for substitutes for a given ingredient.
     */
    @Test
    public void getIngredientSubstitutesByIDTest() {
        Integer id = null;
        // InlineResponse20027 response = api.getIngredientSubstitutesByID(id);

        // TODO: test validations
    }

    /**
     * Ingredient Search
     *
     * Search for simple whole foods (e.g. fruits, vegetables, nuts, grains, meat, fish, dairy etc.).
     */
    @Test
    public void ingredientSearchTest() {
        String query = null;
        Boolean addChildren = null;
        BigDecimal minProteinPercent = null;
        BigDecimal maxProteinPercent = null;
        BigDecimal minFatPercent = null;
        BigDecimal maxFatPercent = null;
        BigDecimal minCarbsPercent = null;
        BigDecimal maxCarbsPercent = null;
        Boolean metaInformation = null;
        String intolerances = null;
        String sort = null;
        String sortDirection = null;
        Integer offset = null;
        Integer number = null;
        String language = null;
        // InlineResponse20026 response = api.ingredientSearch(query, addChildren, minProteinPercent, maxProteinPercent, minFatPercent, maxFatPercent, minCarbsPercent, maxCarbsPercent, metaInformation, intolerances, sort, sortDirection, offset, number, language);

        // TODO: test validations
    }

    /**
     * Ingredients by ID Image
     *
     * Visualize a recipe&#x27;s ingredient list.
     */
    @Test
    public void ingredientsByIDImageTest() {
        BigDecimal id = null;
        String measure = null;
        // Object response = api.ingredientsByIDImage(id, measure);

        // TODO: test validations
    }

    /**
     * Map Ingredients to Grocery Products
     *
     * Map a set of ingredients to products you can buy in the grocery store.
     */
    @Test
    public void mapIngredientsToGroceryProductsTest() {
        IngredientsMapBody body = null;
        // List<InlineResponse20036> response = api.mapIngredientsToGroceryProducts(body);

        // TODO: test validations
    }

    /**
     * Ingredients Widget
     *
     * Visualize ingredients of a recipe. You can play around with that endpoint!
     */
    @Test
    public void visualizeIngredientsTest() {
        String contentType = null;
        String language = null;
        String accept = null;
        // String response = api.visualizeIngredients(contentType, language, accept);

        // TODO: test validations
    }
}
