package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "fridge_items")
public class Ingredient implements Parcelable {

    @SerializedName("id")
    @PrimaryKey
    private long id;

    @SerializedName("name")
    @NonNull
    private String title;

    @Ignore
    private float amount;

    @Ignore
    private String unit;

    private String expiryDate;

    @ColumnInfo(name = "is_selected", defaultValue = "0")
    private boolean isSelected;
    public Ingredient() {}

    public Ingredient(long id, String title, String expiryDate, boolean isSelected) {
        this.id = id;
        this.title = title;
        this.expiryDate = expiryDate;
        this.isSelected = isSelected;
    }

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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", isSelected=" + isSelected +
                '}';
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
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }

    protected Ingredient(Parcel in) {
        id = in.readLong();
        title = in.readString();
        amount = in.readFloat();
        unit = in.readString();
        expiryDate = in.readString();
        isSelected = in.readByte() != 0;
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
}
