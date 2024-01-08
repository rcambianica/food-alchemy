package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class InstructionStep implements Parcelable {
    private String step;

    protected InstructionStep(Parcel in) {
        step = in.readString();
    }

    public static final Creator<InstructionStep> CREATOR = new Creator<InstructionStep>() {
        @Override
        public InstructionStep createFromParcel(Parcel in) {
            return new InstructionStep(in);
        }

        @Override
        public InstructionStep[] newArray(int size) {
            return new InstructionStep[size];
        }
    };

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(step);
    }
}
