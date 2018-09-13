package com.kaooak.android.eventsreminder.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.kaooak.android.eventsreminder.R;

/**
 * Created by 1 on 20.02.2018.
 */

public class NumberPreference extends DialogPreference {

//    private static final String TAG = "NumberPreference";

    public static final int DEFAULT_VALUE = 2;

    private NumberPicker mNumberPicker;
    private int mCurrentValue;
    private int mNewValue;

    public NumberPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateDialogView() {
//        Log.i(TAG, "onCreateDialogView");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_period, null);

        mCurrentValue = getPersistedInt(DEFAULT_VALUE);

        mNumberPicker = (NumberPicker) view;
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(30);
        mNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mNewValue = newVal;
            }
        });
        mNumberPicker.setValue(mCurrentValue);

        return view;
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        int value = a.getInt(index, DEFAULT_VALUE);

//        Log.i(TAG, "onGetDefaultValue" + "\t" +
//                "a[index] = " + value + "\t" +
//                "index = " + index);

        return value;
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);

        if (restorePersistedValue)
            mCurrentValue = getPersistedInt(DEFAULT_VALUE);
        else
            mCurrentValue = (Integer) defaultValue;

//        Log.i(TAG, "onSetInitialValue" + "\t" +
//                "isRestoreValue = " + restorePersistedValue+ "\t" +
//                "defaultValue = " + defaultValue + "\t" +
//                "value = " + mCurrentValue);

        persistInt(mCurrentValue);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            mCurrentValue = mNewValue;
            persistInt(mCurrentValue);
        }

//        Log.i(TAG, "onDialogClosed" + "\t" +
//                "idPositiveResult = " + positiveResult + "\t" +
//                "value = " + mCurrentValue);
    }

//
//    @Override
//    protected Parcelable onSaveInstanceState() {
//        Parcelable superState = super.onSaveInstanceState();
//
//        if (isPersistent()) {
//            return superState;
//        } else {
//            SavedState myState = new SavedState(superState);
//            myState.value = mCurrentValue;
//            return myState;
//        }
//    }
//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        super.onRestoreInstanceState(state);
//
//        if (state == null || !state.getClass().equals(SavedState.class)) {
//            super.onRestoreInstanceState(state);
//        } else {
//            SavedState myState = (SavedState) state;
//            super.onRestoreInstanceState(myState.getSuperState());
//            mNumberPicker.setValue(myState.value);
//        }
//    }
//
//    private static class SavedState extends BaseSavedState {
//        int value;
//
//        public SavedState(Parcelable superState) {
//            super(superState);
//        }
//        public SavedState(Parcel source) {
//            super(source);
//
//            value = source.readInt();
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            super.writeToParcel(dest, flags);
//
//            dest.writeInt(value);
//        }
//
//        // Standard creator object using an instance of this class
//        public static final Parcelable.Creator<SavedState> CREATOR =
//                new Parcelable.Creator<SavedState>() {
//
//                    public SavedState createFromParcel(Parcel in) {
//                        return new SavedState(in);
//                    }
//
//                    public SavedState[] newArray(int size) {
//                        return new SavedState[size];
//                    }
//                };
//    }
}

