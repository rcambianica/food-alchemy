package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Set;

public class SearchParameters implements Parcelable {
    private String diet;
    private Set<String> intolerances;
    private List<String> ingredients;
    private String type;

    public SearchParameters(String diet, Set<String> intolerances, List<String> ingredients, String type) {
        this.diet = diet;
        this.intolerances = intolerances;
        this.ingredients = ingredients;
        this.type = type;
    }



    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public Set<String> getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(Set<String> intolerances) {
        this.intolerances = intolerances;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected SearchParameters(Parcel in) {
        diet = in.readString();
        ingredients = in.createStringArrayList();
        type = in.readString();
    }

    public static final Creator<SearchParameters> CREATOR = new Creator<SearchParameters>() {
        @Override
        public SearchParameters createFromParcel(Parcel in) {
            return new SearchParameters(in);
        }

        @Override
        public SearchParameters[] newArray(int size) {
            return new SearchParameters[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(diet);
        parcel.writeStringList(ingredients);
        parcel.writeString(type);
    }
}
