package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class to  represent the recipes of SpoonacularAPI
 */
@Entity(tableName = "recipes")
public class Recipe implements Parcelable {

    @SerializedName("id")
    @PrimaryKey
    private long id;

    @SerializedName("image")
    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @SerializedName("imageType")
    @ColumnInfo(name = "image_type")
    private String imageType;

    @SerializedName("title")
    @NonNull
    private String title;

    @SerializedName("instructions")
    @NonNull
    private String instructions = null;

    @SerializedName("diets")
    private List<String> diets = new ArrayList<String>();

    @SerializedName("intolerances")
    private List<String> intolerances;

    @SerializedName("dishTypes")
    private List<String> type = new ArrayList<String>();

    @SerializedName("ingredients")
    @NonNull
    private List<String> ingredients = new ArrayList<String>();

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    @NonNull
    private boolean isFavorite;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {this.title = title;}


    public String getImageUrl() {return imageUrl;}

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
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.imageType);
        dest.writeString(this.instructions);
        dest.writeList(this.diets);
        dest.writeList(this.intolerances);
        dest.writeList(this.type);
        dest.writeList(this.ingredients);
        dest.writeByte((byte) (this.isFavorite ? 1 : 0));
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.imageUrl = source.readString();
        this.imageType = source.readString();
        this.instructions = source.readString();
        source.readStringList(this.diets);
        source.readStringList(this.intolerances);
        source.readStringList(this.type);
        source.readStringList(this.ingredients);
        this.isFavorite = source.readByte() != 0;
    }

    protected Recipe(Parcel in) {
        super();
        readFromParcel(in);
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

    public List<String> getDiets() {
        return diets;
    }

    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    public List<String> getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(List<String> intolerances) {
        this.intolerances = intolerances;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }
}
