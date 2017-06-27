package com.learnwithme.buildapps.bakingapp.data.source.local.db.contract;

import android.provider.BaseColumns;

/**
 * Created by Nithin on 26/06/2017.
 */

public class StepsContract {
    private StepsContract() { }

    public abstract class StepsEntry implements BaseColumns {
        public static final String TABLE_NAME = "steps";

        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_STEP_ID = "step_id";
        public static final String COLUMN_SHORT_DESC = "short_desc";
        public static final String COLUMN_DESC = "desc";
        public static final String COLUMN_VIDEO_URL = "video";
        public static final String COLUMN_THUMB_URL = "thumb";
    }

    public static final String STEP_CREATE_QUERY =
        "CREATE TABLE " + StepsEntry.TABLE_NAME + " ("
        + StepsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + StepsEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL,"
        + StepsEntry.COLUMN_STEP_ID + " INTEGER NOT NULL,"
        + StepsEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL,"
        + StepsEntry.COLUMN_DESC + " TEXT NOT NULL,"
        + StepsEntry.COLUMN_VIDEO_URL + " TEXT NOT NULL,"
        + StepsEntry.COLUMN_THUMB_URL + " TEXT NOT NULL"
        + ");";
}