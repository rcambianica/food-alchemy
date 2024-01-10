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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<String> diets = new HashSet<>();

    @SerializedName("intolerances")
    private Set<String> intolerances;

    @SerializedName("dishTypes")
    private Set<String> type = new HashSet<>();

    @SerializedName("extendedIngredients")
    @NonNull
    private Set<Ingredient> ingredients = new HashSet<>();

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
        dest.writeArray(new List[]{this.instructions});
        dest.writeList((List) this.diets);
        dest.writeList((List) this.intolerances);
        dest.writeList((List) this.type);
        dest.writeList((List) this.ingredients);
        dest.writeByte((byte) (this.isFavorite ? 1 : 0));
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.imageUrl = source.readString();
        this.imageType = source.readString();
        this.instructions = source.readParcelable(AnalyzedInstruction.class.getClassLoader());
        source.readStringList((List<String>) this.diets);
        source.readStringList((List<String>) this.intolerances);
        source.readStringList((List<String>) this.type);
        source.readList((List) this.ingredients, Ingredient.class.getClassLoader());
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

    public List<AnalyzedInstruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<AnalyzedInstruction> instructions) {
        this.instructions = instructions;
    }

    public Set<String> getDiets() {
        return diets;
    }

    public void setDiets(Set<String> diets) {
        this.diets = diets;
    }

    public Set<String> getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(Set<String> intolerances) {
        this.intolerances = intolerances;
    }

    public Set<String> getType() {
        return type;
    }

    public void setType(Set<String> type) {
        this.type = type;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isSynchronized() {
        return isSynchronized;
    }

    public void setSynchronized(boolean aSynchronized) {
        isSynchronized = aSynchronized;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageType='" + imageType + '\'' +
                ", title='" + title + '\'' +
                ", instructions=" + instructions +
                ", diets=" + diets +
                ", intolerances=" + intolerances +
                ", type=" + type +
                ", ingredients=" + ingredients +
                ", isFavorite=" + isFavorite +
                ", isSynchronized=" + isSynchronized +
                '}';
    }
}
