package com.spoonacular.client;

import com.spoonacular.ApiClient;
import java.math.BigDecimal;
import com.spoonacular.models.InlineResponse20048;
import com.spoonacular.models.RecipesAnalyzeBody;
import com.spoonacular.models.RecipesAnalyzeBody1;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * API tests for DefaultApi
 */
public class DefaultApiTest {

    private DefaultApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(DefaultApi.class);
    }


    /**
     * Analyze Recipe
     *
     * This endpoint allows you to send raw recipe information, such as title, servings, and ingredients, to then see what we compute (badges, diets, nutrition, and more). This is useful if you have your own recipe data and want to enrich it with our semantic analysis.
     */
    @Test
    public void analyzeRecipeTest() {
        RecipesAnalyzeBody body = null;
        String language = null;
        Boolean includeNutrition = null;
        Boolean includeTaste = null;
        // Object response = api.analyzeRecipe(body, language, includeNutrition, includeTaste);

        // TODO: test validations
    }

    /**
     * Create Recipe Card
     *
     * Generate a recipe card for a recipe.
     */
    @Test
    public void createRecipeCardGetTest() {
        BigDecimal id = null;
        String mask = null;
        String backgroundImage = null;
        String backgroundColor = null;
        String fontColor = null;
        // Object response = api.createRecipeCardGet(id, mask, backgroundImage, backgroundColor, fontColor);

        // TODO: test validations
    }

    /**
     * Search Restaurants
     *
     * Search through thousands of restaurants (in North America) by location, cuisine, budget, and more.
     */
    @Test
    public void searchRestaurantsTest() {
        String query = null;
        BigDecimal lat = null;
        BigDecimal lng = null;
        BigDecimal distance = null;
        BigDecimal budget = null;
        String cuisine = null;
        BigDecimal minRating = null;
        Boolean isOpen = null;
        String sort = null;
        BigDecimal page = null;
        // InlineResponse20048 response = api.searchRestaurants(query, lat, lng, distance, budget, cuisine, minRating, isOpen, sort, page);

        // TODO: test validations
    }
}
