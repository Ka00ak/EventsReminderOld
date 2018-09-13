package com.kaooak.android.eventsreminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.kaooak.android.eventsreminder.data.Categories;
import com.kaooak.android.eventsreminder.data.Event;
import com.kaooak.android.eventsreminder.database.EventsDbScheme.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * Created by 1 on 05.11.2017.
 */

public class EventsDbSingleton {
    private static EventsDbSingleton sEventsDbSingleton;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private EventsDbSingleton(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new EventsDbHelper(mContext).getWritableDatabase();
    }

    public static EventsDbSingleton get(Context context) {
        if (sEventsDbSingleton == null)
            sEventsDbSingleton = new EventsDbSingleton(context);
        return sEventsDbSingleton;
    }


    private ArrayList<Event> unionQueries(EventsDbCursorWrapper eventsOne, EventsDbCursorWrapper eventsTwo) {

        ArrayList<Event> list = new ArrayList<>();

        //
        eventsOne.moveToFirst();
        while (!eventsOne.isAfterLast()) {
            Event event = eventsOne.getEvent();
            list.add(event);
            eventsOne.moveToNext();
        }
        eventsOne.close();

        //
        eventsTwo.moveToFirst();
        while (!eventsTwo.isAfterLast()) {
            Event event = eventsTwo.getEvent();
            list.add(event);
            eventsTwo.moveToNext();
        }
        eventsTwo.close();

        return list;
    }

    private ArrayList<Event> queryEvents(String inSelection, String[] inSelectionArgs) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        String selection;
        String selection2;
        String[] selectionArgs = inSelectionArgs;
        String orderBy = EventsTable.Columns.MONTH + ", " + EventsTable.Columns.DAY;

        if (inSelection != null) {
            selection =
                    "(" + EventsTable.Columns.MONTH + " = " + month + " AND " + EventsTable.Columns.DAY + " >= " + day + " OR " + EventsTable.Columns.MONTH + " > " + month + ") " +
                            "AND (" + inSelection + ")";

            selection2 =
                    "(" + EventsTable.Columns.MONTH + " = " + month + " AND " + EventsTable.Columns.DAY + " < " + day + " OR " + EventsTable.Columns.MONTH + " < " + month + ") " +
                            "AND (" + inSelection + ")";
        } else {
            selection =
                    "(" + EventsTable.Columns.MONTH + " = " + month + " AND " + EventsTable.Columns.DAY + " >= " + day + " OR " + EventsTable.Columns.MONTH + " > " + month + ") ";

            selection2 =
                    "(" + EventsTable.Columns.MONTH + " = " + month + " AND " + EventsTable.Columns.DAY + " < " + day + " OR " + EventsTable.Columns.MONTH + " < " + month + ") ";
        }

        Cursor cursor = mDatabase.query(EventsTable.NAME, null, selection, selectionArgs, null, null, orderBy);
        EventsDbCursorWrapper eventsDbCursorWrapper = new EventsDbCursorWrapper(cursor);

        Cursor cursor2 = mDatabase.query(EventsTable.NAME, null, selection2, selectionArgs, null, null, orderBy);
        EventsDbCursorWrapper eventsDbCursorWrapper2 = new EventsDbCursorWrapper(cursor2);

        //
        ArrayList<Event> events = unionQueries(eventsDbCursorWrapper, eventsDbCursorWrapper2);
        return events;
    }

    public List<Event> selectEvents() {
        String selection = null;
        String[] selectionArgs = null;

        ArrayList<Event> list = queryEvents(selection, selectionArgs);

        return list;
    }
    public List<Event> selectEvents(int categoryID) {
        String selection = EventsTable.Columns.CATEGORY_ID + " = " + categoryID;
        String[] selectionArgs = null;

        ArrayList<Event> list = queryEvents(selection, selectionArgs);

        return list;
    }
    public List<Event> selectEvents(String search) {
        String selection =
                EventsTable.Columns.BDAY_NAME + " like ? OR " +
                EventsTable.Columns.WEDDING_NAME_ONE + " like ? OR " +
                EventsTable.Columns.WEDDING_NAME_TWO + " like ? ";
        String[] selectionArgs = new String[]{"%" + search + "%", "%" + search + "%", "%" + search + "%"};

        ArrayList<Event> list = queryEvents(selection, selectionArgs);

        return list;
    }
    public List<Event> selectEvents(int categoryID, String search) {
        String selection =
                EventsTable.Columns.CATEGORY_ID + " = " + categoryID + " AND (" +
                EventsTable.Columns.BDAY_NAME + " like ? OR " +
                EventsTable.Columns.WEDDING_NAME_ONE + " like ? OR " +
                EventsTable.Columns.WEDDING_NAME_TWO + " like ?)";
        String[] selectionArgs = new String[]{ "%" + search + "%", "%" + search + "%", "%" + search + "%"};

        ArrayList<Event> list = queryEvents(selection, selectionArgs);

        return list;
    }
    public List<Event> selectEvents(int day, int month) {
        String selection = EventsTable.Columns.DAY + " = " + day + " AND " + EventsTable.Columns.MONTH + " = " + month;
        String[] selectionArgs = null;

        ArrayList<Event> list = queryEvents(selection, selectionArgs);

        return list;
    }
    public Event selectBday(String uuid) {
        String selection = EventsTable.Columns.UUID + " = ?";
        String[] selectionArgs = new String[]{uuid};

        ArrayList<Event> list = queryEvents(selection, selectionArgs);
        Event event = list.get(0);

        return event;
    }

    public void insertBday(Event event) {
        ContentValues contentValues = getContentValues(event);
        mDatabase.insert(EventsTable.NAME, null, contentValues);
    }
    public void updateBday(Event event, String where, String[] whereArgs) {
        ContentValues contentValues = getContentValues(event);
        mDatabase.update(EventsTable.NAME, contentValues, where, whereArgs);
    }
    public void deleteBday(Event event) {
        mDatabase.delete(EventsTable.NAME, "uuid = ?", new String[]{event.getID().toString()});
    }
    private ContentValues getContentValues(Event event) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventsTable.Columns.UUID, event.getID().toString());
        contentValues.put(EventsTable.Columns.DAY, event.getDay());
        contentValues.put(EventsTable.Columns.MONTH, event.getMonth());
        contentValues.put(EventsTable.Columns.YEAR, event.getYear());
        contentValues.put(EventsTable.Columns.CATEGORY_ID, event.getCategoryID());

        contentValues.put(EventsTable.Columns.BDAY_NAME, event.getBdayName());
        contentValues.put(EventsTable.Columns.BDAY_GIFT, event.getBdayGift());

        contentValues.put(EventsTable.Columns.WEDDING_NAME_ONE, event.getWeddingNameOne());
        contentValues.put(EventsTable.Columns.WEDDING_NAME_TWO, event.getWeddingNameTwo());
        contentValues.put(EventsTable.Columns.WEDDING_GIFT, event.getWeddingGift());

        return contentValues;
    }

}
