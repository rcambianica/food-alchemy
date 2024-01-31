package it.unimib.food_alchemy.data.service;

import java.util.List;

import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.IngredientsResponse;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipeApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SpoonacularApiService {
    /**
     * Search Recipes
     * Search through hundreds of thousands of recipes using advanced filtering and ranking. NOTE: This method combines searching by query, by ingredients, and by nutrients into one endpoint.
     * @param query The (natural language) search query. (optional)
     * @param diet The diet for which the recipes must be suitable. See a full list of supported diets. (optional)
     * @param intolerances A comma-separated list of intolerances. All recipes returned must not contain ingredients that are not suitable for people with the intolerances entered. See a full list of supported intolerances. (optional)
     * @param includeIngredients A comma-separated list of ingredients that should/must be used in the recipes. (optional)
     * @param type The type of recipe. See a full list of supported meal types. (optional)
     * @param sort The strategy to sort recipes by. See a full list of supported sorting options. (optional)
     * @param offset The number of results to skip (between 0 and 900). (optional)
     * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
     * @param instructionsRequired Whether the recipes must have instructions.
     * @param fillIngredients Add information about the ingredients and whether they are used or missing in relation to the query.
     * @param addRecipeInformation If set to true, you get more information about the recipes returned.
     * @param apiKey API Key to authenticate requests.
     * @return Call<RecipeApiResponse>;
     * Read entire docs
     * @see <a href="https://spoonacular.com/food-api/docs#Search-Recipes">Search Recipes Documentation</a>
     */
    @GET("recipes/complexSearch")
    Call<RecipeApiResponse> searchRecipes(
            @retrofit2.http.Query("query") String query,
            @retrofit2.http.Query("diet") String diet,
            @retrofit2.http.Query("intolerances") String intolerances,
            @retrofit2.http.Query("includeIngredients") String includeIngredients,
            @retrofit2.http.Query("type") String type,
            @retrofit2.http.Query("sort") String sort,
            @retrofit2.http.Query("offset") Integer offset,
            @retrofit2.http.Query("number") Integer number,
            @retrofit2.http.Query("instructionsRequired") boolean instructionsRequired,
            @retrofit2.http.Query("fillIngredients") boolean fillIngredients,
            @retrofit2.http.Query("addRecipeInformation") boolean addRecipeInformation,
            @retrofit2.http.Query("apiKey") String apiKey
    );

    /**
     * Get Recipe Information
     * Use a recipe id to get full information about a recipe, such as ingredients, nutrition, diet and allergen information, etc.
     * @param id The item&#x27;s id. (required)
     * @param includeNutrition Include nutrition data in the recipe information. Nutrition data is per serving. If you want the nutrition data for the entire recipe, just multiply by the number of servings. (optional, default to false)
     * @param apiKey API Key to authenticate requests.
     * @return Call<RecipeResponse>;
     * Read entire docs
     * @see <a href="https://spoonacular.com/food-api/docs#Get-Recipe-Information">Get Recipe Information Documentation</a>
     */
    @GET("recipes/{id}/information")
    Call<Recipe> getRecipeInformation(
            @retrofit2.http.Path("id") long id,
            @retrofit2.http.Query("includeNutrition") Boolean includeNutrition,
            @retrofit2.http.Query("apiKey") String apiKey
    );

    /**
     * Autocomplete Ingredient Search
     * Autocomplete the entry of an ingredient.
     * @param query The (natural language) search query. (optional)
     * @param number The maximum number of items to return (between 1 and 100). Defaults to 10. (optional, default to 10)
     * @param apiKey API Key to authenticate requests.
     * @return Call<List<IngredientResponse>>;
     * Read entire docs
     * @see <a href="https://spoonacular.com/food-api/docs#Autocomplete-Ingredient-Search">Autocomplete Ingredient Search Documentation</a>
     */
    @GET("food/ingredients/autocomplete")
    Call<List<Ingredient>> autocompleteIngredientSearch(
            @retrofit2.http.Query("query") String query,
            @retrofit2.http.Query("number") Integer number,
            @retrofit2.http.Query("metaInformation") boolean metaInformation,
            @retrofit2.http.Query("intolerances") String intolerances,
            @retrofit2.http.Query("apiKey") String apiKey
    );
}
