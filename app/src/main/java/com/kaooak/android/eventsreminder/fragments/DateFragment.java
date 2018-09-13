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

import com.kaooak.android.eventsreminder.R;

import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 1 on 15.11.2017.
 */

public class DateFragment extends DialogFragment {

    private static final String ARG_DATE = "date";

    private static final String EXTRA_DATE = "com.kaooak.android.birthdays.date";

    DatePicker mDatePicker;

    public static final DateFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DateFragment dateFragment = new DateFragment();
        dateFragment.setArguments(args);
        return dateFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        mDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Выберите Дату")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.YEAR, year);

                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_DATE, calendar.getTime());
                        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                    }
                })
                .create();
    }

    public static final Date getAnswerDate(Intent intent) {
        return (Date) intent.getSerializableExtra(EXTRA_DATE);
    }
}
