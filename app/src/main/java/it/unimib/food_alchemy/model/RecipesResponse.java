package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipesResponse implements Parcelable {

    private boolean isLoading;

    @SerializedName("results")
    private List<Recipe> recipesList;

    public RecipesResponse() {}

    public RecipesResponse(List<Recipe> recipesList) {
        this.recipesList = recipesList;
    }

    public List<Recipe> getRecipesList() {
        return recipesList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipesList = recipeList;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public String toString() {
        return "RecipesResponse{" +
                "recipesList=" + recipesList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isLoading ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.recipesList);
    }

    public void readFromParcel(Parcel source) {
        this.isLoading = source.readByte() != 0;
        this.recipesList = source.createTypedArrayList(Recipe.CREATOR);
    }

    protected RecipesResponse(Parcel in) {
        this.isLoading = in.readByte() != 0;
        this.recipesList = in.createTypedArrayList(Recipe.CREATOR);
    }

    public static final Parcelable.Creator<RecipesResponse> CREATOR = new Parcelable.Creator<RecipesResponse>() {
        @Override
        public RecipesResponse createFromParcel(Parcel source) {
            return new RecipesResponse(source);
        }

        @Override
        public RecipesResponse[] newArray(int size) {
            return new RecipesResponse[size];
        }
    };
}
