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
 *  Class to represent the API response of Spoonacular (https://api.spoonacular.com/)
 *  associated with the endpoint "recipes" - recipes/complexSearch.
 */
public class RecipeApiResponse extends RecipesResponse{

    private int totalResults;
    public RecipeApiResponse() {
        super();
    }

    public RecipeApiResponse(int totalResults, List<Recipe> recipes)    {
        super(recipes);
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "RecipeApiResponse{" +
                "totalResults=" + totalResults +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.totalResults);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.totalResults = source.readInt();
    }

    protected RecipeApiResponse(Parcel in) {
        super(in);
        this.totalResults = in.readInt();
    }

    public static final Creator<RecipeApiResponse> CREATOR = new Creator<RecipeApiResponse>() {
        @Override
        public RecipeApiResponse createFromParcel(Parcel source) {
            return new RecipeApiResponse(source);
        }

        @Override
        public RecipeApiResponse[] newArray(int size) {
            return new RecipeApiResponse[size];
        }
    };
}
