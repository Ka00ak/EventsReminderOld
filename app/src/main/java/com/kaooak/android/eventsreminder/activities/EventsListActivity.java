package com.kaooak.android.eventsreminder.activities;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.kaooak.android.eventsreminder.fragments.EventsListFragment;
import com.kaooak.android.eventsreminder.R;

public class EventsListActivity extends AppCompatActivity {

//    private static final String TAG = "EventsListActivity";

    private DrawerLayout mDrawerLayout;

    public static Intent newIntent(Context context) {
        return new Intent(context, EventsListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
//        Log.d(TAG, "onCreate()");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (item.getItemId())
                {
                    case R.id.all:
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        Fragment fragment = EventsListFragment.getInstance(0);

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerFragment, fragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.bdays:
                        FragmentManager fragmentManager2 = getSupportFragmentManager();

                        Fragment fragment2 = EventsListFragment.getInstance(1);

                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.replace(R.id.containerFragment, fragment2);
                        fragmentTransaction2.commit();
                        break;
                    case R.id.weddings:
                        FragmentManager fragmentManager3 = getSupportFragmentManager();

                        Fragment fragment3 = EventsListFragment.getInstance(2);

                        FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                        fragmentTransaction3.replace(R.id.containerFragment, fragment3);
                        fragmentTransaction3.commit();
                        break;
                    case R.id.preferences:
                        item.setChecked(false);

                        Intent intentSettings = PreferencesActivity.getIntent(getApplicationContext());
                        startActivity(intentSettings);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
//        mDrawerLayout.
        //
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.containerFragment);
        if (fragment == null) {
            fragment = EventsListFragment.getInstance(0);
            fragmentManager.beginTransaction()
                    .add(R.id.containerFragment, fragment)
                    .commit();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
//        Log.d(TAG, "onStart()");
    }
    @Override
    protected void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume()");
    }
    @Override
    protected void onPause() {
        super.onPause();
//        Log.d(TAG, "onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
//        Log.d(TAG, "onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.d(TAG, "onDestroy()");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
