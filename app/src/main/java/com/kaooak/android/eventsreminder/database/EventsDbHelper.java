package com.kaooak.android.eventsreminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.constraint.ConstraintLayout;

import com.kaooak.android.eventsreminder.data.Categories;
import com.kaooak.android.eventsreminder.data.Event;
import com.kaooak.android.eventsreminder.database.EventsDbScheme.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 1 on 05.11.2017.
 */

public class EventsDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bdays.db";
    private static final int VERSION = 1;

    public EventsDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + EventsTable.NAME + " " +
                "(_id integer primary key autoincrement, " +
                EventsTable.Columns.UUID + ", " +
                EventsTable.Columns.DAY + ", " +
                EventsTable.Columns.MONTH + ", " +
                EventsTable.Columns.YEAR + ", " +
                EventsTable.Columns.CATEGORY_ID + ", " +
                EventsTable.Columns.BDAY_NAME + ", " +
                EventsTable.Columns.BDAY_GIFT + ", " +
                EventsTable.Columns.WEDDING_NAME_ONE + ", " +
                EventsTable.Columns.WEDDING_NAME_TWO + ", " +
                EventsTable.Columns.WEDDING_GIFT + ")");

        //
//        Event event = new Event(1,1, 1996, Categories.CATEGORY_BDAY,
//                "Имя 1", "Подарок 1",
//                "Имя1 ", "Имя2 ", "Подарок ");
//        ContentValues contentValues = lol(event);
//        sqLiteDatabase.insert(EventsTable.NAME, null, contentValues);
//
//        event = new Event(12,6, 1996, Categories.CATEGORY_BDAY,
//                "Имя 1", "Подарок 1",
//                "Имя1 ", "Имя2 ", "Подарок ");
//        contentValues = lol(event);
//        sqLiteDatabase.insert(EventsTable.NAME, null, contentValues);
//
//        event = new Event(13,6, 1996, Categories.CATEGORY_BDAY,
//                "Имя 1", "Подарок 1",
//                "Имя1 ", "Имя2 ", "Подарок ");
//        contentValues = lol(event);
//        sqLiteDatabase.insert(EventsTable.NAME, null, contentValues);
//
//        event = new Event(14,6, 1996, Categories.CATEGORY_BDAY,
//                "Имя 1", "Подарок 1",
//                "Имя1 ", "Имя2 ", "Подарок ");
//        contentValues = lol(event);
//        sqLiteDatabase.insert(EventsTable.NAME, null, contentValues);
//
//        event = new Event(5,5, 1996, Categories.CATEGORY_BDAY,
//                "Имя 1", "Подарок 1",
//                "Имя1 ", "Имя2 ", "Подарок ");
//        contentValues = lol(event);
//        sqLiteDatabase.insert(EventsTable.NAME, null, contentValues);
//
//        event = new Event(8,7, 1996, Categories.CATEGORY_BDAY,
//                "Имя 1", "Подарок 1",
//                "Имя1 ", "Имя2 ", "Подарок ");
//        contentValues = lol(event);
//        sqLiteDatabase.insert(EventsTable.NAME, null, contentValues);
//
//        event = new Event(28,11, 1996, Categories.CATEGORY_BDAY,
//                "Имя 1", "Подарок 1",
//                "Имя1 ", "Имя2 ", "Подарок ");
//        contentValues = lol(event);
//        sqLiteDatabase.insert(EventsTable.NAME, null, contentValues);
//
//        event = new Event(1,0, 1996, Categories.CATEGORY_BDAY,
//                "Имя 1", "Подарок 1",
//                "Имя1 ", "Имя2 ", "Подарок ");
//        contentValues = lol(event);
//        sqLiteDatabase.insert(EventsTable.NAME, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private ContentValues lol(Event event) {
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
