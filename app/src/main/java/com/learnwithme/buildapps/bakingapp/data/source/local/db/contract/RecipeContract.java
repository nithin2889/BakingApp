package com.learnwithme.buildapps.bakingapp.data.source.local.db.contract;

import android.provider.BaseColumns;

/**
 * Created by Nithin on 26/06/2017.
 */

public class RecipeContract {
    private RecipeContract() { }

    public static abstract class RecipeEntry {
        public static final String TABLE_NAME = "recipes";

        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERVINGS = "servings";
        public static final String COLUMN_IMAGE = "image";
    }

    public static final String RECIPE_CREATE_QUERY =
        "CREATE TABLE " + RecipeEntry.TABLE_NAME + " ("
        + RecipeContract.RecipeEntry.COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY, "
        + RecipeContract.RecipeEntry.COLUMN_NAME + " TEXT NOT NULL, "
        + RecipeContract.RecipeEntry.COLUMN_SERVINGS + " INTEGER NOT NULL, "
        + RecipeContract.RecipeEntry.COLUMN_IMAGE + " TEXT NOT NULL"
        + ");";
}