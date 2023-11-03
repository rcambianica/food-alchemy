package com.spoonacular.client;

import com.spoonacular.ApiClient;
import java.math.BigDecimal;
import com.spoonacular.models.InlineResponse20049;
import com.spoonacular.models.InlineResponse20050;
import com.spoonacular.models.InlineResponse20051;
import com.spoonacular.models.InlineResponse20052;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * API tests for WineApi
 */
public class WineApiTest {

    private WineApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(WineApi.class);
    }


    /**
     * Dish Pairing for Wine
     *
     * Find a dish that goes well with a given wine.
     */
    @Test
    public void getDishPairingForWineTest() {
        String wine = null;
        // InlineResponse20049 response = api.getDishPairingForWine(wine);

        // TODO: test validations
    }

    /**
     * Wine Description
     *
     * Get a simple description of a certain wine, e.g. \&quot;malbec\&quot;, \&quot;riesling\&quot;, or \&quot;merlot\&quot;.
     */
    @Test
    public void getWineDescriptionTest() {
        String wine = null;
        // InlineResponse20051 response = api.getWineDescription(wine);

        // TODO: test validations
    }

    /**
     * Wine Pairing
     *
     * Find a wine that goes well with a food. Food can be a dish name (\&quot;steak\&quot;), an ingredient name (\&quot;salmon\&quot;), or a cuisine (\&quot;italian\&quot;).
     */
    @Test
    public void getWinePairingTest() {
        String food = null;
        BigDecimal maxPrice = null;
        // InlineResponse20050 response = api.getWinePairing(food, maxPrice);

        // TODO: test validations
    }

    /**
     * Wine Recommendation
     *
     * Get a specific wine recommendation (concrete product) for a given wine type, e.g. \&quot;merlot\&quot;.
     */
    @Test
    public void getWineRecommendationTest() {
        String wine = null;
        BigDecimal maxPrice = null;
        BigDecimal minRating = null;
        BigDecimal number = null;
        // InlineResponse20052 response = api.getWineRecommendation(wine, maxPrice, minRating, number);

        // TODO: test validations
    }
}
