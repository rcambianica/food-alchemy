package com.spoonacular.client;

import com.spoonacular.ApiClient;
import java.math.BigDecimal;
import com.spoonacular.models.InlineResponse20030;
import com.spoonacular.models.InlineResponse20053;
import com.spoonacular.models.InlineResponse20054;
import com.spoonacular.models.InlineResponse20056;
import com.spoonacular.models.InlineResponse20057;
import com.spoonacular.models.InlineResponse20058;
import com.spoonacular.models.InlineResponse20059;
import com.spoonacular.models.InlineResponse20060;
import com.spoonacular.models.InlineResponse20061;
import com.spoonacular.models.InlineResponse20062;
import com.spoonacular.models.InlineResponse20063;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * API tests for MiscApi
 */
public class MiscApiTest {

    private MiscApi api;

    @Before
    public void setup() {
        api = new ApiClient().createService(MiscApi.class);
    }


    /**
     * Detect Food in Text
     *
     * Take any text and find all mentions of food contained within it. This task is also called Named Entity Recognition (NER). In this case, the entities are foods. Either dishes, such as pizza or cheeseburger, or ingredients, such as cucumber or almonds.
     */
    @Test
    public void detectFoodInTextTest() {
        String contentType = null;
        // InlineResponse20056 response = api.detectFoodInText(contentType);

        // TODO: test validations
    }

    /**
     * Random Food Joke
     *
     * Get a random joke that is related to food. Caution: this is an endpoint for adults!
     */
    @Test
    public void getARandomFoodJokeTest() {
        // InlineResponse20060 response = api.getARandomFoodJoke();

        // TODO: test validations
    }

    /**
     * Conversation Suggests
     *
     * This endpoint returns suggestions for things the user can say or ask the chatbot.
     */
    @Test
    public void getConversationSuggestsTest() {
        String query = null;
        BigDecimal number = null;
        // InlineResponse20063 response = api.getConversationSuggests(query, number);

        // TODO: test validations
    }

    /**
     * Random Food Trivia
     *
     * Returns random food trivia.
     */
    @Test
    public void getRandomFoodTriviaTest() {
        // InlineResponse20061 response = api.getRandomFoodTrivia();

        // TODO: test validations
    }

    /**
     * Image Analysis by URL
     *
     * Analyze a food image. The API tries to classify the image, guess the nutrition, and find a matching recipes.
     */
    @Test
    public void imageAnalysisByURLTest() {
        String imageUrl = null;
        // InlineResponse20054 response = api.imageAnalysisByURL(imageUrl);

        // TODO: test validations
    }

    /**
     * Image Classification by URL
     *
     * Classify a food image.
     */
    @Test
    public void imageClassificationByURLTest() {
        String imageUrl = null;
        // InlineResponse20053 response = api.imageClassificationByURL(imageUrl);

        // TODO: test validations
    }

    /**
     * Search All Food
     *
     * Search all food content with one call. That includes recipes, grocery products, menu items, simple foods (ingredients), and food videos.
     */
    @Test
    public void searchAllFoodTest() {
        String query = null;
        Integer offset = null;
        Integer number = null;
        // InlineResponse20058 response = api.searchAllFood(query, offset, number);

        // TODO: test validations
    }

    /**
     * Search Custom Foods
     *
     * Search custom foods in a user&#x27;s account.
     */
    @Test
    public void searchCustomFoodsTest() {
        String username = null;
        String hash = null;
        String query = null;
        Integer offset = null;
        Integer number = null;
        // InlineResponse20030 response = api.searchCustomFoods(username, hash, query, offset, number);

        // TODO: test validations
    }

    /**
     * Search Food Videos
     *
     * Find recipe and other food related videos.
     */
    @Test
    public void searchFoodVideosTest() {
        String query = null;
        String type = null;
        String cuisine = null;
        String diet = null;
        String includeIngredients = null;
        String excludeIngredients = null;
        BigDecimal minLength = null;
        BigDecimal maxLength = null;
        Integer offset = null;
        Integer number = null;
        // InlineResponse20059 response = api.searchFoodVideos(query, type, cuisine, diet, includeIngredients, excludeIngredients, minLength, maxLength, offset, number);

        // TODO: test validations
    }

    /**
     * Search Site Content
     *
     * Search spoonacular&#x27;s site content. You&#x27;ll be able to find everything that you could also find using the search suggestions on spoonacular.com. This is a suggest API so you can send partial strings as queries.
     */
    @Test
    public void searchSiteContentTest() {
        String query = null;
        // InlineResponse20057 response = api.searchSiteContent(query);

        // TODO: test validations
    }

    /**
     * Talk to Chatbot
     *
     * This endpoint can be used to have a conversation about food with the spoonacular chatbot. Use the \&quot;Get Conversation Suggests\&quot; endpoint to show your user what he or she can say.
     */
    @Test
    public void talkToChatbotTest() {
        String text = null;
        String contextId = null;
        // InlineResponse20062 response = api.talkToChatbot(text, contextId);

        // TODO: test validations
    }
}
