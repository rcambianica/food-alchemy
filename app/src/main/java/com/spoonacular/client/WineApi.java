package com.spoonacular.client;

import com.spoonacular.CollectionFormats.*;

import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.math.BigDecimal;
import com.spoonacular.models.InlineResponse20049;
import com.spoonacular.models.InlineResponse20050;
import com.spoonacular.models.InlineResponse20051;
import com.spoonacular.models.InlineResponse20052;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WineApi {
  /**
   * Dish Pairing for Wine
   * Find a dish that goes well with a given wine.
   * @param wine The type of wine that should be paired, e.g. \&quot;merlot\&quot;, \&quot;riesling\&quot;, or \&quot;malbec\&quot;. (required)
   * @return Call&lt;InlineResponse20049&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Dish-Pairing-for-Wine">Dish Pairing for Wine Documentation</a>
   */
  @GET("food/wine/dishes")
  Call<InlineResponse20049> getDishPairingForWine(
    @retrofit2.http.Query("wine") String wine
  );

  /**
   * Wine Description
   * Get a simple description of a certain wine, e.g. \&quot;malbec\&quot;, \&quot;riesling\&quot;, or \&quot;merlot\&quot;.
   * @param wine The name of the wine that should be paired, e.g. \&quot;merlot\&quot;, \&quot;riesling\&quot;, or \&quot;malbec\&quot;. (required)
   * @return Call&lt;InlineResponse20051&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Wine-Description">Wine Description Documentation</a>
   */
  @GET("food/wine/description")
  Call<InlineResponse20051> getWineDescription(
    @retrofit2.http.Query("wine") String wine
  );

  /**
   * Wine Pairing
   * Find a wine that goes well with a food. Food can be a dish name (\&quot;steak\&quot;), an ingredient name (\&quot;salmon\&quot;), or a cuisine (\&quot;italian\&quot;).
   * @param food The food to get a pairing for. This can be a dish (\&quot;steak\&quot;), an ingredient (\&quot;salmon\&quot;), or a cuisine (\&quot;italian\&quot;). (required)
   * @param maxPrice The maximum price for the specific wine recommendation in USD. (optional)
   * @return Call&lt;InlineResponse20050&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Wine-Pairing">Wine Pairing Documentation</a>
   */
  @GET("food/wine/pairing")
  Call<InlineResponse20050> getWinePairing(
    @retrofit2.http.Query("food") String food, @retrofit2.http.Query("maxPrice") BigDecimal maxPrice
  );

  /**
   * Wine Recommendation
   * Get a specific wine recommendation (concrete product) for a given wine type, e.g. \&quot;merlot\&quot;.
   * @param wine The type of wine to get a specific product recommendation for. (required)
   * @param maxPrice The maximum price for the specific wine recommendation in USD. (optional)
   * @param minRating The minimum rating of the recommended wine between 0 and 1. For example, 0.8 equals 4 out of 5 stars. (optional)
   * @param number The number of wine recommendations expected (between 1 and 100). (optional, default to 10)
   * @return Call&lt;InlineResponse20052&gt;
   * Read entire docs
   * @see <a href="https://spoonacular.com/food-api/docs#Wine-Recommendation">Wine Recommendation Documentation</a>
   */
  @GET("food/wine/recommendation")
  Call<InlineResponse20052> getWineRecommendation(
    @retrofit2.http.Query("wine") String wine, @retrofit2.http.Query("maxPrice") BigDecimal maxPrice, @retrofit2.http.Query("minRating") BigDecimal minRating, @retrofit2.http.Query("number") BigDecimal number
  );

}
