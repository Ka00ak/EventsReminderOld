package com.kaooak.android.eventsreminder.data;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Event {

    private UUID mID;
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mCategoryID;

    private String mBdayName;
    private String mBdayGift;

    private String mWeddingNameOne;
    private String mWeddingNameTwo;
    private String mWeddingGift;

    public Event(int categoryID, int day, int month, int year) {
        mID = UUID.randomUUID();
        mDay = day;
        mMonth = month;
        mYear = year;
        mCategoryID = categoryID;

        mBdayName = "";
        mBdayGift = "";

        mWeddingNameOne = "";
        mWeddingNameTwo = "";
        mWeddingGift = "";
    }
    public Event(int categoryID, String uuid, int day, int month, int year) {
        mID = UUID.fromString(uuid);
        mDay = day;
        mMonth = month;
        mYear = year;
        mCategoryID = categoryID;

        mBdayName = "";
        mBdayGift = "";

        mWeddingNameOne = "";
        mWeddingNameTwo = "";
        mWeddingGift = "";
    }
    public Event(int day, int month, int year, int categoryID, String bdayName, String bdayGift, String weddingNameOne, String weddingNameTwo, String weddingGift) {
        mID = UUID.randomUUID();
        mDay = day;
        mMonth = month;
        mYear = year;
        mCategoryID = categoryID;

        mBdayName = bdayName;
        mBdayGift = bdayGift;

        mWeddingNameOne = weddingNameOne;
        mWeddingNameTwo = weddingNameTwo;
        mWeddingGift = weddingGift;
    }

    //
    public String getID() {
        return mID.toString();
    }

    public int getDay() {
        return mDay;
    }
    public void setDay(int day) {
        mDay = day;
    }

    public int getMonth() {
        return mMonth;
    }
    public void setMonth(int month) {
        mMonth = month;
    }

    public int getYear() {
        return mYear;
    }
    public void setYear(int year) {
        mYear = year;
    }

    public int getCategoryID() {
        return mCategoryID;
    }
    public void setCategoryID(int categoryID) {
        mCategoryID = categoryID;
    }

    public Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);

        return calendar.getTime();
    }

    //bday
    public String getBdayName() {
        return mBdayName;
    }
    public void setBdayName(String bdayName) {
        mBdayName = bdayName;
    }

    public String getBdayGift() {
        return mBdayGift;
    }
    public void setBdayGift(String bdayGift) {
        mBdayGift = bdayGift;
    }

    //wedding
    public String getWeddingNameOne() {
        return mWeddingNameOne;
    }
    public void setWeddingNameOne(String weddingNameOne) {
        mWeddingNameOne = weddingNameOne;
    }

    public String getWeddingNameTwo() {
        return mWeddingNameTwo;
    }
    public void setWeddingNameTwo(String weddingNameTwo) {
        mWeddingNameTwo = weddingNameTwo;
    }

    public String getWeddingGift() {
        return mWeddingGift;
    }
    public void setWeddingGift(String weddingGift) {
        mWeddingGift = weddingGift;
    }
}
