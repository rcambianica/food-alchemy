package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("analyzedInstructions")
    private List<AnalyzedInstruction> instructions;

    @SerializedName("diets")
    private List<String> diets = new ArrayList<String>();

    @SerializedName("dishTypes")
    private List<String> type = new ArrayList<String>();

    @SerializedName("extendedIngredients")
    @NonNull
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    private boolean isFavorite;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;

    public Recipe(){}
    protected Recipe(Parcel in) {
        id = in.readLong();
        imageUrl = in.readString();
        imageType = in.readString();
        title = in.readString();
        instructions = in.createTypedArrayList(AnalyzedInstruction.CREATOR);
        diets = in.createStringArrayList();
        type = in.createStringArrayList();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        isFavorite = in.readByte() != 0;
        isSynchronized = in.readByte() != 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public List<AnalyzedInstruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<AnalyzedInstruction> instructions) {
        this.instructions = instructions;
    }

    public List<String> getDiets() {
        return diets;
    }

    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    @NonNull
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(@NonNull List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Exclude
    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(imageUrl);
        parcel.writeString(imageType);
        parcel.writeString(title);
        parcel.writeTypedList(instructions);
        parcel.writeStringList(diets);
        parcel.writeStringList(type);
        parcel.writeTypedList(ingredients);
        parcel.writeByte((byte) (isFavorite ? 1 : 0));
        parcel.writeByte((byte) (isSynchronized ? 1 : 0));
    }
}
