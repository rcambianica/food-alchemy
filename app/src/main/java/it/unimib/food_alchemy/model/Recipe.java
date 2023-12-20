package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.List;

/**
 *  Class to represent the recipes of SpoonacularAPI
 */
@Entity(tableName = "recipes")
public class Recipe implements Parcelable {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "image_type")
    private String imageType;
    private String title;
    private String instructions;
    private List<String> cuisines;
    private List<String> diets;
    private BigDecimal servings;
    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    private boolean isFavorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {this.title = title;}


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}


    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Recipe() {}

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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public List<String> getDiets() {
        return diets;
    }

    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    public BigDecimal getServings() {
        return servings;
    }

    public void setServings(BigDecimal servings) {
        this.servings = servings;
    }
}
