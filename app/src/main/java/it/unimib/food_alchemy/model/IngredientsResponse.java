package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponse implements Parcelable {

    private boolean isLoading;

    @SerializedName("results")
    private List<Ingredient> ingredientsList;

    public IngredientsResponse() {}

    public IngredientsResponse(List<Ingredient> ingredientsList)    {
        this.ingredientsList = ingredientsList;
    }

    public List<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public String toString() {
        return "IngredientsResponse{" +
                "ingredientsList=" + ingredientsList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeByte((byte) (isLoading ? 1 : 0));
        parcel.writeTypedList(ingredientsList);
    }

    protected IngredientsResponse(Parcel in) {
        isLoading = in.readByte() != 0;
        ingredientsList = in.createTypedArrayList(Ingredient.CREATOR);
    }

    public static final Creator<IngredientsResponse> CREATOR = new Creator<IngredientsResponse>() {
        @Override
        public IngredientsResponse createFromParcel(Parcel in) {
            return new IngredientsResponse(in);
        }

        @Override
        public IngredientsResponse[] newArray(int size) {
            return new IngredientsResponse[size];
        }
    };
}
