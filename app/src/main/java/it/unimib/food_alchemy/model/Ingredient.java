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

    @SerializedName("id")
    @PrimaryKey
    private long id;

    @SerializedName("name")
    @NonNull
    private String title;

    private float amount;

    private String unit;

    @NonNull
    private String expiryDate;

    @ColumnInfo(name = "is_fridge_item", defaultValue = "0")
    private boolean isFridgeItem;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;

    public Ingredient() {}

    public Ingredient (@NonNull String title, @NonNull String expiryDate)   {
        this.title = title;
        this.expiryDate = expiryDate;
    }
    protected Ingredient(Parcel in) {
        id = in.readLong();
        title = in.readString();
        amount = in.readFloat();
        unit = in.readString();
        expiryDate = in.readString();
        isFridgeItem = in.readByte() != 0;
        isSynchronized = in.readByte() != 0;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
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

    @NonNull
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(@NonNull String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isFridgeItem() {
        return isFridgeItem;
    }

    public void setFridgeItem(boolean fridgeItem) {
        isFridgeItem = fridgeItem;
    }

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
        parcel.writeString(title);
        parcel.writeFloat(amount);
        parcel.writeString(unit);
        parcel.writeString(expiryDate);
        parcel.writeByte((byte) (isFridgeItem ? 1 : 0));
        parcel.writeByte((byte) (isSynchronized ? 1 : 0));
    }
}
