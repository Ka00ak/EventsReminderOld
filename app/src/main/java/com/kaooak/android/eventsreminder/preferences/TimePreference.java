package com.kaooak.android.eventsreminder.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.kaooak.android.eventsreminder.R;

/**
 * Created by 1 on 20.02.2018.
 */

public class TimePreference extends DialogPreference {

//    private static final String TAG = "TimePreference";

    public static final int DEFAULT_VALUE = 2;

    private TimePicker mTimePicker;
    private int mCurrentTime;
    private int mCurrentHours;
    private int mCurrentMinutes;
    private int mNewTime;
    private int mNewHours;
    private int mNewMinutes;

    public TimePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateDialogView() {
//        Log.i(TAG, "onCreateDialogView");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_time, null);

        mCurrentTime = getPersistedInt(DEFAULT_VALUE);

        mTimePicker = (TimePicker) view;
        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mNewTime = hourOfDay * 100 + minute;
            }
        });
        mTimePicker.setCurrentHour(mCurrentTime / 100);
        mTimePicker.setCurrentMinute(mCurrentTime % 100);

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
            mCurrentTime = getPersistedInt(DEFAULT_VALUE);
        else
            mCurrentTime= (Integer) defaultValue;

//        Log.i(TAG, "onSetInitialValue" + "\t" +
//                "isRestoreValue = " + restorePersistedValue+ "\t" +
//                "defaultValue = " + defaultValue + "\t" +
//                "value = " + mCurrentTime);

        persistInt(mCurrentTime);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            mCurrentTime = mNewTime;
            persistInt(mCurrentTime);
        }

//        Log.i(TAG, "onDialogClosed" + "\t" +
//                "idPositiveResult = " + positiveResult + "\t" +
//                "value = " + mCurrentTime);
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
//            mTimePicker.setValue(myState.value);
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

