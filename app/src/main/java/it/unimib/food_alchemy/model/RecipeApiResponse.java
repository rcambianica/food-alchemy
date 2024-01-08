package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *  Class to represent the API response of NewsAPI.org (https://api.spoonacular.com/)
 *  associated with the endpoint "recipes" - recipes/complexSearch.
 */
public class RecipeApiResponse extends RecipesResponse implements Parcelable {

    private List<Recipe> results = new ArrayList<Recipe>();
    private Integer totalResults = null;

    public RecipeApiResponse() {
        super();
    }

    public RecipeApiResponse addResultsItem(Recipe resultsItem) {
        this.results.add(resultsItem);
        return this;
    }

    /**
     * Get results
     * @return results
     **/
    @Schema(required = true, description = "")
    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }

    public RecipeApiResponse totalResults(Integer totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    /**
     * Get totalResults
     * @return totalResults
     **/
    @Schema(required = true, description = "")
    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecipeApiResponse recipeApiResponse = (RecipeApiResponse) o;
        return Objects.equals(this.results, recipeApiResponse.results) &&
                Objects.equals(this.totalResults, recipeApiResponse.totalResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results, totalResults);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RecipeApiResponse {\n");
        sb.append("    results: ").append(toIndentedString(results)).append("\n");
        sb.append("    totalResults: ").append(toIndentedString(totalResults)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    protected RecipeApiResponse(Parcel in) {
        in.readList(results, Recipe.class.getClassLoader());
        totalResults = in.readInt();
    }

    public static final Creator<RecipeApiResponse> CREATOR = new Creator<RecipeApiResponse>() {
        @Override
        public RecipeApiResponse createFromParcel(Parcel in) {
            return new RecipeApiResponse(in);
        }

        @Override
        public RecipeApiResponse[] newArray(int size) {
            return new RecipeApiResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeList(results);
        dest.writeInt(totalResults);
    }
}
