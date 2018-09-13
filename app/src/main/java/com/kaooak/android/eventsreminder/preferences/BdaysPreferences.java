package com.kaooak.android.eventsreminder.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by 1 on 03.12.2017.
 */

public class BdaysPreferences {

    private static final String KEY_IS_NOTIFICATIONS = "isNotification";
    private static final String KEY_NOTIFICATION_TIME = "notificationTime";
    private static final String KEY_IS_EXP_NOTIFICATIONS = "isExpNotification";
    private static final String KEY_EXP_NOTIFICATION_DAYS = "expNotificationDays";
    private static final String KEY_EXP_NOTIFICATION_TIME = "expNotificationTime";

    public static boolean isNotification(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(KEY_IS_NOTIFICATIONS, true);
    }
    public static void setIsNotification(Context context, boolean isNotifications)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(KEY_IS_NOTIFICATIONS, isNotifications).apply();
    }

    public static int getNotificationTime(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(KEY_NOTIFICATION_TIME, 1200);
    }

    public static boolean isExpNotification(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(KEY_IS_EXP_NOTIFICATIONS, true);
    }

    public static int getExpNotificationDays(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(KEY_EXP_NOTIFICATION_DAYS, 2);
    }

    public static int getExpNotificationTime(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(KEY_EXP_NOTIFICATION_TIME, 1200);
    }
}
