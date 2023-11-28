package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Ingredient implements Parcelable {

    private String title;
    private String expiryDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "title='" + title + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }

    public Ingredient (String title, String expiryDate)   {
        this.title = title;
        this.expiryDate = expiryDate;
    }

    protected Ingredient(Parcel in) {
        title = in.readString();
        expiryDate = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(expiryDate);
    }
}
