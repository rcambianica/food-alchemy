package it.unimib.food_alchemy.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 *  Class to represent the API response of NewsAPI.org (https://api.spoonacular.com/)
 *  associated with the endpoint "recipes" - recipes/complexSearch.
 */
public class RecipeApiResponse implements Parcelable {
    protected RecipeApiResponse(Parcel in) {
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
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }
}
