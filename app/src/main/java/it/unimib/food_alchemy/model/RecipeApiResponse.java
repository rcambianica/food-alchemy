package it.unimib.food_alchemy.model;

import java.util.List;

/**
 *  Class to represent the API response of Spoonacular (https://api.spoonacular.com/)
 *  associated with the endpoint "recipes" - recipes/complexSearch.
 */
public class RecipeApiResponse extends RecipesResponse {
    private int totalResults;
    private boolean fromRandom;
    public RecipeApiResponse() {
        super();
    }

    public RecipeApiResponse(int totalResults, List<Recipe> recipes)    {
        super(recipes);
        this.totalResults = totalResults;
        this.fromRandom = false;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
    public boolean isFromRandom() {
        return fromRandom;
    }
    public void setFromRandom(boolean fromRandom) {
        this.fromRandom = fromRandom;
    }

    @Override
    public String toString() {
        return "RecipeApiResponse{" +
                "totalResults=" + totalResults +
                ", fromRandom=" + fromRandom +
                '}';
    }
}
