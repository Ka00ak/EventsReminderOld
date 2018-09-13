package com.kaooak.android.eventsreminder.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kaooak.android.eventsreminder.R;
import com.kaooak.android.eventsreminder.fragments.PreferencesFragment;

public class PreferencesActivity extends AppCompatActivity implements OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction()
                .replace(R.id.containerSettingsFragment, PreferencesFragment.newInstance())
                .commit();
    }


    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, PreferencesActivity.class);
        return intent;
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        if (key.equals(KEY_PREF))
//        findPreference
    }
}
