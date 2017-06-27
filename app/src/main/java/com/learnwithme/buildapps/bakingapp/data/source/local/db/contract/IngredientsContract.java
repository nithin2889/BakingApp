package com.learnwithme.buildapps.bakingapp.data.source.local.db.contract;

import android.provider.BaseColumns;

/**
 * Created by Nithin on 26/06/2017.
 */

public class IngredientsContract {
    private IngredientsContract() { }

    public static abstract class IngredientsEntry implements BaseColumns {
        public static final String TABLE_NAME = "ingredients";

        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MEASURE = "measure";
        public static final String COLUMN_INGREDIENT = "ingredient";
    }

    public static final String INGREDIENT_CREATE_QUERY =
        "CREATE TABLE " + IngredientsEntry.TABLE_NAME + " ("
        + IngredientsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + IngredientsEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, "
        + IngredientsEntry.COLUMN_QUANTITY + " REAL NOT NULL, "
        + IngredientsEntry.COLUMN_MEASURE + " TEXT NOT NULL, "
        + IngredientsEntry.COLUMN_INGREDIENT + " TEXT NOT NULL"
        + ");";
}