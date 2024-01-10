package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ingredients")
public class Ingredient implements Parcelable {

    @SerializedName("name")
    @PrimaryKey
    @NonNull
    private String title;

    private float amount;

    private String unit;

    private String image;

    @NonNull
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
        return getAmount() + " " + getUnit() + " " + getTitle() + '\n';
    }

    public Ingredient() {}
    @Ignore
    public Ingredient (String title, String expiryDate)   {
        this.title = title;
        this.expiryDate = expiryDate;
    }

    public Ingredient (String title, String image, String expiryDate) {
        this.title = title;
        this.image = image;
        this.expiryDate = expiryDate;
    }

    protected Ingredient(Parcel in) {
        title = in.readString();
        amount = in.readFloat();
        unit = in.readString();
        image = in.readString();
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
        parcel.writeFloat(amount);
        parcel.writeString(unit);
        parcel.writeString(image);
        parcel.writeString(expiryDate);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
