package com.spoonacular.client;

import com.spoonacular.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MiscApi {
  /**
   * Detect Food in Text
   * Take any text and find all mentions of food contained within it. This task is also called Named Entity Recognition (NER). In this case, the entities are foods. Either dishes, such as pizza or cheeseburger, or ingredients, such as cucumber or almonds.
   * @param contentType The content type. (optional)
   * @return Call&lt;InlineResponse20056&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Detect-Food-in-Text">Detect Food in Text Documentation</a>
   */
  @Headers({
    "Content-Type:application/x-www-form-urlencoded"
  })
  @POST("food/detect")
  Call<InlineResponse20056> detectFoodInText(
    @retrofit2.http.Header("Content-Type") String contentType
  );

  /**
   * Random Food Joke
   * Get a random joke that is related to food. Caution: this is an endpoint for adults!
   * @return Call&lt;InlineResponse20060&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Random-Food-Joke">Random Food Joke Documentation</a>
   */
  @GET("food/jokes/random")
  Call<InlineResponse20060> getARandomFoodJoke();
    

  /**
   * Conversation Suggests
   * This endpoint returns suggestions for things the user can say or ask the chatbot.
   * @param query A (partial) query from the user. The endpoint will return if it matches topics it can talk about. (required)
   * @param number The number of suggestions to return (between 1 and 25). (optional)
   * @return Call&lt;InlineResponse20063&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Conversation-Suggests">Conversation Suggests Documentation</a>
   */
  @GET("food/converse/suggest")
  Call<InlineResponse20063> getConversationSuggests(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("number") BigDecimal number
  );

  /**
   * Random Food Trivia
   * Returns random food trivia.
   * @return Call&lt;InlineResponse20061&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Random-Food-Trivia">Random Food Trivia Documentation</a>
   */
  @GET("food/trivia/random")
  Call<InlineResponse20061> getRandomFoodTrivia();
    

  /**
   * Image Analysis by URL
   * Analyze a food image. The API tries to classify the image, guess the nutrition, and find a matching recipes.
   * @param imageUrl The URL of the image to be analyzed. (required)
   * @return Call&lt;InlineResponse20054&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Image-Analysis-by-URL">Image Analysis by URL Documentation</a>
   */
  @GET("food/images/analyze")
  Call<InlineResponse20054> imageAnalysisByURL(
    @retrofit2.http.Query("imageUrl") String imageUrl
  );

  /**
   * Image Classification by URL
   * Classify a food image.
   * @param imageUrl The URL of the image to be classified. (required)
   * @return Call&lt;InlineResponse20053&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Image-Classification-by-URL">Image Classification by URL Documentation</a>
   */
  @GET("food/images/classify")
  Call<InlineResponse20053> imageClassificationByURL(
    @retrofit2.http.Query("imageUrl") String imageUrl
  );

  /**
   * Search All Food
   * Search all food content with one call. That includes recipes, grocery products, menu items, simple foods (ingredients), and food videos.
   * @param query The search query. (required)
   * @param offset The number of results to skip (between 0 and 900). (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @return Call&lt;InlineResponse20058&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-All-Food">Search All Food Documentation</a>
   */
  @GET("food/search")
  Call<InlineResponse20058> searchAllFood(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("offset") Integer offset, @retrofit2.http.Query("number") Integer number
  );

  /**
   * Search Custom Foods
   * Search custom foods in a user&#x27;s account.
   * @param username The username. (required)
   * @param hash The private hash for the username. (required)
   * @param query The (natural language) search query. (optional)
   * @param offset The number of results to skip (between 0 and 900). (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @return Call&lt;InlineResponse20030&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-Custom-Foods">Search Custom Foods Documentation</a>
   */
  @GET("food/customFoods/search")
  Call<InlineResponse20030> searchCustomFoods(
    @retrofit2.http.Query("username") String username, @retrofit2.http.Query("hash") String hash, @retrofit2.http.Query("query") String query, @retrofit2.http.Query("offset") Integer offset, @retrofit2.http.Query("number") Integer number
  );

  /**
   * Search Food Videos
   * Find recipe and other food related videos.
   * @param query The (natural language) search query. (optional)
   * @param type The type of the recipes. See a full list of supported meal types. (optional)
   * @param cuisine The cuisine(s) of the recipes. One or more, comma separated. See a full list of supported cuisines. (optional)
   * @param diet The diet for which the recipes must be suitable. See a full list of supported diets. (optional)
   * @param includeIngredients A comma-separated list of ingredients that the recipes should contain. (optional)
   * @param excludeIngredients A comma-separated list of ingredients or ingredient types that the recipes must not contain. (optional)
   * @param minLength Minimum video length in seconds. (optional)
   * @param maxLength Maximum video length in seconds. (optional)
   * @param offset The number of results to skip (between 0 and 900). (optional)
   * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
   * @return Call&lt;InlineResponse20059&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-Food-Videos">Search Food Videos Documentation</a>
   */
  @GET("food/videos/search")
  Call<InlineResponse20059> searchFoodVideos(
    @retrofit2.http.Query("query") String query, @retrofit2.http.Query("type") String type, @retrofit2.http.Query("cuisine") String cuisine, @retrofit2.http.Query("diet") String diet, @retrofit2.http.Query("includeIngredients") String includeIngredients, @retrofit2.http.Query("excludeIngredients") String excludeIngredients, @retrofit2.http.Query("minLength") BigDecimal minLength, @retrofit2.http.Query("maxLength") BigDecimal maxLength, @retrofit2.http.Query("offset") Integer offset, @retrofit2.http.Query("number") Integer number
  );

  /**
   * Search Site Content
   * Search spoonacular&#x27;s site content. You&#x27;ll be able to find everything that you could also find using the search suggestions on spoonacular.com. This is a suggest API so you can send partial strings as queries.
   * @param query The query to search for. You can also use partial queries such as \&quot;spagh\&quot; to already find spaghetti recipes, articles, grocery products, and other content. (required)
   * @return Call&lt;InlineResponse20057&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Search-Site-Content">Search Site Content Documentation</a>
   */
  @GET("food/site/search")
  Call<InlineResponse20057> searchSiteContent(
    @retrofit2.http.Query("query") String query
  );

  /**
   * Talk to Chatbot
   * This endpoint can be used to have a conversation about food with the spoonacular chatbot. Use the \&quot;Get Conversation Suggests\&quot; endpoint to show your user what he or she can say.
   * @param text The request / question / answer from the user to the chatbot. (required)
   * @param contextId An arbitrary globally unique id for your conversation. The conversation can contain states so you should pass your context id if you want the bot to be able to remember the conversation. (optional)
   * @return Call&lt;InlineResponse20062&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Talk-to-Chatbot">Talk to Chatbot Documentation</a>
   */
  @GET("food/converse")
  Call<InlineResponse20062> talkToChatbot(
    @retrofit2.http.Query("text") String text, @retrofit2.http.Query("contextId") String contextId
  );

}
