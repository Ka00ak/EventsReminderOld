package com.kaooak.android.eventsreminder.database;

/**
 * Created by 1 on 05.11.2017.
 */

public class EventsDbScheme {

    public static final class EventsTable {
        public static final String NAME = "events";

        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String DAY = "day";
            public static final String MONTH = "month";
            public static final String YEAR = "year";
            public static final String CATEGORY_ID = "categoryID";

            public static final String BDAY_NAME = "bdayName";
            public static final String BDAY_GIFT = "bdayGift";

            public static final String WEDDING_NAME_ONE = "weddingNameOne";
            public static final String WEDDING_NAME_TWO = "weddingNameTwo";
            public static final String WEDDING_GIFT = "weddingGift";
        }
    }

//    public static final class CategoriesTable {
//        public static final String NAME = "categories";
//
//        public static final class Columns {
//            public static final String NAME = "name";
//        }
//    }

}
