package com.kaooak.android.eventsreminder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.kaooak.android.eventsreminder.preferences.BdaysPreferences;
import com.kaooak.android.eventsreminder.activities.EventsListActivity;
import com.kaooak.android.eventsreminder.data.Event;
import com.kaooak.android.eventsreminder.database.EventsDbSingleton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by 1 on 03.12.2017.
 */

public class NotificationService extends IntentService {

    private static final String TAG = "NotificationService";

    private static final String EXTRA_TYPE_NOTIFICATION = "com.kaooak.android.birthdays.extra_type_notification";

    private static final int MAIN_TYPE_NOTIFICATION = 0;
    private static final int EXP_TYPE_NOTIFICATION = 1;


    private static final long INTERVAL_FIVE_MINUTES = TimeUnit.MINUTES.toMillis(5);

    private static Intent newIntent(Context context, int typeNotification) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.putExtra(EXTRA_TYPE_NOTIFICATION, typeNotification);
        return intent;
    }

    public NotificationService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
//        Log.i(TAG, "onCreate()");
        super.onCreate();
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent intent = NotificationService.newIntent(context, MAIN_TYPE_NOTIFICATION);
        Intent intent2 = NotificationService.newIntent(context, EXP_TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        PendingIntent pendingIntent2 = PendingIntent.getService(context, 1, intent2, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
//            alarmManager.cancel(pendingIntent);
//            alarmManager.cancel(pendingIntent2);

            //
            int time = BdaysPreferences.getNotificationTime(context);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            long millisCurrent = calendar.getTimeInMillis();

            calendar.set(Calendar.HOUR_OF_DAY, time / 100);
            calendar.set(Calendar.MINUTE, time % 100);
            long millisNeed = calendar.getTimeInMillis();

            if (millisCurrent > millisNeed) {
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
                millisNeed = calendar.getTimeInMillis();
            }

            //
            int time2 = BdaysPreferences.getExpNotificationTime(context);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(System.currentTimeMillis());
            long millisCurrent2 = calendar2.getTimeInMillis();

            calendar2.set(Calendar.HOUR_OF_DAY, time2 / 100);
            calendar2.set(Calendar.MINUTE, time2 % 100);
            long millisNeed2 = calendar2.getTimeInMillis();

            if (millisCurrent2 > millisNeed2) {
                calendar2.set(Calendar.DAY_OF_YEAR, calendar2.get(Calendar.DAY_OF_YEAR) + 1);
                millisNeed2 = calendar2.getTimeInMillis();
            }

            //
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, millisNeed, alarmManager.INTERVAL_DAY, pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, millisNeed2, alarmManager.INTERVAL_DAY, pendingIntent2);
            } else {
            alarmManager.cancel(pendingIntent);
            alarmManager.cancel(pendingIntent2);
            pendingIntent.cancel();
            pendingIntent2.cancel();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        Log.i(TAG, "onHandleIntent : " + intent);

        Calendar calendar = Calendar.getInstance();

        switch (intent.getIntExtra(EXTRA_TYPE_NOTIFICATION, 0)) {
            case MAIN_TYPE_NOTIFICATION:
                if (BdaysPreferences.isNotification(this)) {
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    List<Event> events = EventsDbSingleton.get(getApplicationContext()).selectEvents(day, month);

                    if (events.size() > 0) {
                        String contentText = getResources().getString(R.string.notification_today_text, events.get(0).getBdayName());
                        createNotification(contentText, 0);
                    }
                }
                break;
            case EXP_TYPE_NOTIFICATION:
                if (BdaysPreferences.isExpNotification(this)) {
                    int days = BdaysPreferences.getExpNotificationDays(this);
                    calendar.add(Calendar.DAY_OF_MONTH, days);
                    int day2 = calendar.get(Calendar.DAY_OF_MONTH);
                    int month2 = calendar.get(Calendar.MONTH);
                    List<Event> events2 = EventsDbSingleton.get(getApplicationContext()).selectEvents(day2, month2);

                    if (events2.size() > 0) {
                        String contentText = getResources().getString(R.string.notification_text, days, events2.get(0).getBdayName());
                        createNotification(contentText, 1);
                    }
                }
                break;
            default:
                break;
        }

        setServiceAlarm(getApplicationContext(), true);
    }

    private void createNotification(String contentText, int notifyID) {
        Intent intent = EventsListActivity.newIntent(getApplicationContext());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setTicker(getResources().getString(R.string.ticker))
                .setSmallIcon(R.drawable.ic_bday)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(contentText)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(contentText))
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notifyID, notification);
    }

    @Override
    public void onDestroy() {
//        Log.i(TAG, "onDestroy()");
        super.onDestroy();
    }
}
