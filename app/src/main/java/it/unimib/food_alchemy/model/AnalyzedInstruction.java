package it.unimib.food_alchemy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AnalyzedInstruction implements Parcelable {
    private List<InstructionStep> steps;

    public AnalyzedInstruction(){}
    protected AnalyzedInstruction(Parcel in) {
        steps = in.createTypedArrayList(InstructionStep.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnalyzedInstruction> CREATOR = new Creator<AnalyzedInstruction>() {
        @Override
        public AnalyzedInstruction createFromParcel(Parcel in) {
            return new AnalyzedInstruction(in);
        }

        @Override
        public AnalyzedInstruction[] newArray(int size) {
            return new AnalyzedInstruction[size];
        }
    };

    public List<InstructionStep> getSteps() {
        return steps;
    }

    public void setSteps(List<InstructionStep> steps) {
        this.steps = steps;
    }
}
