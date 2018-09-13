package com.kaooak.android.eventsreminder.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.kaooak.android.eventsreminder.R;
import com.kaooak.android.eventsreminder.data.Categories;
import com.kaooak.android.eventsreminder.data.Event;
import com.kaooak.android.eventsreminder.database.EventsDbSingleton;

import java.text.DateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 1 on 05.11.2017.
 */

public class EventFragment extends Fragment {

    private static final String ARG_BDAY_UUID = "uuid";

    private static final String DIALOG_DATE = "dialogDate";

    private static final int REQUEST_DIALOG_DATE = 0;

    private Event mEvent;

    private ImageView mImageView;
    private Spinner mSpinnerCategory;
    private Button mButtonDate;

    private EditText mEditTextBdayName;
    private EditText mEditTextBdayGift;

    private EditText mEditTextWeddingNameOne;
    private EditText mEditTextWeddingNameTwo;
    private EditText mEditTextWeddingGift;

    public static EventFragment getInstance(String uuid) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_BDAY_UUID, uuid);

        EventFragment fragment = new EventFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uuid = getArguments().getString(ARG_BDAY_UUID);

        mEvent = EventsDbSingleton.get(getActivity()).selectBday(uuid);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        mImageView = view.findViewById(R.id.imageView);

        mSpinnerCategory = view.findViewById(R.id.spinnerCategory);
        switch (mEvent.getCategoryID()) {
            case Categories.CATEGORY_BDAY:
                mSpinnerCategory.setSelection(0);
                break;
            case Categories.CATEGORY_WEDDING:
                mSpinnerCategory.setSelection(1);
                break;
        }
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup rootView = (ViewGroup) getView();
                rootView.removeViewAt(1);
                View viewTmp = null;
                switch (position) {
                    case 0:
                        mEvent.setCategoryID(Categories.CATEGORY_BDAY);
                        mImageView.setImageResource(R.drawable.ic_cake_black_110dp);
                        viewTmp = loadBday();
                        break;
                    case 1:
                        mEvent.setCategoryID(Categories.CATEGORY_WEDDING);
                        mImageView.setImageResource(R.drawable.ic_wc_black_110dp);
                        viewTmp = loadWedding();
                        break;
                }
                updateEvent();
                rootView.addView(viewTmp);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mButtonDate = view.findViewById(R.id.buttonDate);
        mButtonDate.setText(android.text.format.DateFormat.getDateFormat(getActivity()).format(mEvent.getDate()));
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFragment dateFragment = DateFragment.newInstance(mEvent.getDate());
                dateFragment.setTargetFragment(EventFragment.this, REQUEST_DIALOG_DATE);
                dateFragment.show(getActivity().getSupportFragmentManager(), DIALOG_DATE);
            }
        });


        View viewTmp = null;
        switch (mEvent.getCategoryID()) {
            case Categories.CATEGORY_BDAY:
                mImageView.setImageResource(R.drawable.ic_cake_black_110dp);
                viewTmp = loadBday();
                break;
            case Categories.CATEGORY_WEDDING:
                mImageView.setImageResource(R.drawable.ic_wc_black_110dp);
                viewTmp = loadWedding();
                break;
        }
        ((ViewGroup) view).addView(viewTmp);

        return view;
    }

    private View loadBday() {
        View viewTmp = getLayoutInflater().inflate(R.layout.fragment_bday, null);

        mEditTextBdayName = viewTmp.findViewById(R.id.editTextBdayName);
        mEditTextBdayName.setText(mEvent.getBdayName());
        mEditTextBdayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setBdayName(s.toString());
                updateEvent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        mEditTextBdayGift = viewTmp.findViewById(R.id.editTextBdayGift);
        mEditTextBdayGift.setText(mEvent.getBdayGift());
        mEditTextBdayGift.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setBdayGift(s.toString());
                updateEvent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        return viewTmp;
    }

    private View loadWedding() {
        View viewTmp = getLayoutInflater().inflate(R.layout.fragment_wedding, null);

        mEditTextWeddingNameOne = viewTmp.findViewById(R.id.editTextWeddingNameOne);
        mEditTextWeddingNameOne.setText(mEvent.getWeddingNameOne());
        mEditTextWeddingNameOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setWeddingNameOne(s.toString());
                updateEvent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        mEditTextWeddingNameTwo = viewTmp.findViewById(R.id.editTextWeddingNameTwo);
        mEditTextWeddingNameTwo.setText(mEvent.getWeddingNameTwo());
        mEditTextWeddingNameTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setWeddingNameTwo(s.toString());
                updateEvent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        mEditTextWeddingGift = viewTmp.findViewById(R.id.editTextWeddingGift);
        mEditTextWeddingGift.setText(mEvent.getWeddingGift());
        mEditTextWeddingGift.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setWeddingGift(s.toString());
                updateEvent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        return viewTmp;
    }

    private void updateEvent()
    {
        String oldID = mEvent.getID().toString();
        EventsDbSingleton.get(getActivity()).updateBday(mEvent, "uuid = ?", new String[]{oldID});

        getActivity().setResult(RESULT_OK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        //
        switch (requestCode) {
            case REQUEST_DIALOG_DATE:
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(DateFragment.getAnswerDate(data));

                mEvent.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                mEvent.setMonth(calendar.get(Calendar.MONTH));
                mEvent.setYear(calendar.get(Calendar.YEAR));

                mButtonDate.setText(DateFormat.getDateInstance().format(mEvent.getDate()));
                updateEvent();
                break;
            default:
                break;
        }
    }

}
