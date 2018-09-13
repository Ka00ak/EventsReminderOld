package com.kaooak.android.eventsreminder.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.kaooak.android.eventsreminder.data.Categories;
import com.kaooak.android.eventsreminder.data.Event;
import com.kaooak.android.eventsreminder.database.EventsDbScheme.*;

import java.util.UUID;


/**
 * Created by 1 on 05.11.2017.
 */

public class EventsDbCursorWrapper extends CursorWrapper {

    public EventsDbCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Event getEvent() {
        String uuid = getString(getColumnIndex(EventsTable.Columns.UUID));
        int day = getInt(getColumnIndex(EventsTable.Columns.DAY));
        int month = getInt(getColumnIndex(EventsTable.Columns.MONTH));
        int year = getInt(getColumnIndex(EventsTable.Columns.YEAR));
        int categoryID = getInt(getColumnIndex(EventsTable.Columns.CATEGORY_ID));

        Event event = new Event(categoryID, uuid, day, month, year);
        event.setBdayName(getString(getColumnIndex(EventsTable.Columns.BDAY_NAME)));
        event.setBdayGift(getString(getColumnIndex(EventsTable.Columns.BDAY_GIFT)));

        event.setWeddingNameOne(getString(getColumnIndex(EventsTable.Columns.WEDDING_NAME_ONE)));
        event.setWeddingNameTwo(getString(getColumnIndex(EventsTable.Columns.WEDDING_NAME_TWO)));
        event.setWeddingGift(getString(getColumnIndex(EventsTable.Columns.WEDDING_GIFT)));

        return event;
    }
}
