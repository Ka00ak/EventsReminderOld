package com.kaooak.android.eventsreminder.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import com.kaooak.android.eventsreminder.R;

import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 1 on 15.11.2017.
 */

public class PeriodFragment extends DialogFragment {

    private static final String ARG_PERIOD = "date";

    private static final String EXTRA_PERIOD = "com.kaooak.android.birthdays.period";

    NumberPicker mNumberPicker;

    public static final PeriodFragment newInstance(int period) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERIOD, period);

        PeriodFragment periodFragment = new PeriodFragment();
        periodFragment.setArguments(args);
        return periodFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int period = getArguments().getInt(ARG_PERIOD);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_period, null);
        mNumberPicker = view.findViewById(R.id.numberPicker);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(90);
        mNumberPicker.setValue(period);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Выберите кол-во дней")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_PERIOD, mNumberPicker.getValue());
                        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                    }
                })
                .create();
    }

    public static final int getAnswerPeriod(Intent intent) {
        return intent.getIntExtra(EXTRA_PERIOD, 3);
    }
}
