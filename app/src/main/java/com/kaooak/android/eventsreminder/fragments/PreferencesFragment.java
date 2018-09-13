package com.kaooak.android.eventsreminder.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kaooak.android.eventsreminder.NotificationService;
//import com.kaooak.android.birthdays.BdaysPreferences;
import com.kaooak.android.eventsreminder.R;

import java.util.Calendar;

/**
 * Created by 1 on 03.12.2017.
 */

public class PreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static android.app.Fragment newInstance() {
        Bundle args = new Bundle();

        PreferencesFragment fragment = new PreferencesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();

        onSharedPreferenceChanged(sharedPreferences, "isNotification");
        onSharedPreferenceChanged(sharedPreferences, "notificationTime");
        onSharedPreferenceChanged(sharedPreferences, "isExpNotification");
        onSharedPreferenceChanged(sharedPreferences, "expNotificationDays");
        onSharedPreferenceChanged(sharedPreferences, "expNotificationTime");
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        Log.d("SETTINGS FRAGMENT", key);

        Preference preference = findPreference(key);

        switch (key) {
            case "isNotification":
                if (sharedPreferences.getBoolean(key, true))
                    preference.setSummary(getResources().getString(R.string.pref_notifications_is_summary_true));
                else
                    preference.setSummary(getResources().getString(R.string.pref_notifications_is_summary_false));
                break;
            case "notificationTime":
                int time = sharedPreferences.getInt(key, 1200);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, time/ 100);
                calendar.set(Calendar.MINUTE, time% 100);
                String str = android.text.format.DateFormat.getTimeFormat(getActivity()).format(calendar.getTime());
                preference.setSummary(getResources().getString(R.string.pref_notifications_time_summary, str));

                NotificationService.setServiceAlarm(getActivity().getApplicationContext(), true);
                break;
            case "isExpNotification":
                if (sharedPreferences.getBoolean(key, true))
                    preference.setSummary(getResources().getString(R.string.pref_notifications_is_summary_true));
                else
                    preference.setSummary(getResources().getString(R.string.pref_notifications_is_summary_false));
                break;
            case "expNotificationDays":
                preference.setSummary(getResources().getString(R.string.pref_notifications_days_summary, sharedPreferences.getInt(key, 2)));
                break;
            case "expNotificationTime":
                int expTime = sharedPreferences.getInt(key, 1200);
                Calendar expCalendar = Calendar.getInstance();
                expCalendar.set(Calendar.HOUR_OF_DAY, expTime/ 100);
                expCalendar.set(Calendar.MINUTE, expTime % 100);
                String expStr = android.text.format.DateFormat.getTimeFormat(getActivity()).format(expCalendar.getTime());
                preference.setSummary(getResources().getString(R.string.pref_notifications_time_summary, expStr));

                NotificationService.setServiceAlarm(getActivity().getApplicationContext(), true);
                break;
            default:
                break;
        }
    }
}
