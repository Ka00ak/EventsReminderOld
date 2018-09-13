package com.kaooak.android.eventsreminder.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kaooak.android.eventsreminder.data.Event;
import com.kaooak.android.eventsreminder.fragments.EventFragment;
import com.kaooak.android.eventsreminder.R;
import com.kaooak.android.eventsreminder.database.EventsDbSingleton;

import java.util.List;

public class EventActivity extends AppCompatActivity {

    private static final String EXTRA_BDAY_UUID = "com.kaooak.android.birthdays.uuid";

//    private ViewPager mViewPager;
//    private List<Event> mEventList;
    private Event mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

//        mEventList = EventsDbSingleton.get(getApplicationContext()).selectEvents();

//        mViewPager = findViewById(R.id.viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String uuid = getIntent().getStringExtra(EXTRA_BDAY_UUID);
        mEvent = EventsDbSingleton.get(getApplicationContext()).selectBday(uuid);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.containerFragment);
        if (fragment == null)
        {
            fragment = EventFragment.getInstance(mEvent.getID());
            fragmentManager.beginTransaction()
                    .add(R.id.containerFragment, fragment)
                    .commit();
        }
//
//        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
//            @Override
//            public Fragment getItem(int position) {
//                String uuid = mEventList.get(position).getID().toString();
//                return EventFragment.getInstance(uuid);
//            }
//
//            @Override
//            public int getCount() {
//                return mEventList.size();
//            }
//        });
//
//        //
//        String uuid = getIntent().getStringExtra(EXTRA_BDAY_UUID);
//        for (int i = 0; i < mEventList.size(); i++)
//            if (mEventList.get(i).getID().toString().equals(uuid))
//                mViewPager.setCurrentItem(i);
    }

    public static Intent getIntent(Context context, String uuid) {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(EXTRA_BDAY_UUID, uuid);
        return intent;
    }
}
