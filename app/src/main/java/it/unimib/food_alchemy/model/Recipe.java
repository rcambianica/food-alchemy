package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 *  Class to represent the recipes of SpoonacularAPI
 *  dobbiamo capire quali attributi inserire, guardare esempio prof
 */
@Entity
public class Recipe implements Parcelable {

    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageType;

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Recipe()   {}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.imageType);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.title = source.readString();
        this.imageUrl = source.readString();
        this.imageType = source.readString();
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.imageType = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
