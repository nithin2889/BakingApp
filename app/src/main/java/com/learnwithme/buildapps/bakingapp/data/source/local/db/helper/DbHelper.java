package com.learnwithme.buildapps.bakingapp.data.source.local.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.learnwithme.buildapps.bakingapp.data.source.local.db.contract.IngredientsContract;
import com.learnwithme.buildapps.bakingapp.data.source.local.db.contract.RecipeContract;
import com.learnwithme.buildapps.bakingapp.data.source.local.db.contract.StepsContract;

/**
 * Created by Nithin on 23/06/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 2;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(IngredientsContract.INGREDIENT_CREATE_QUERY);
        db.execSQL(StepsContract.STEP_CREATE_QUERY);
        db.execSQL(RecipeContract.RECIPE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.RecipeEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + StepsContract.StepsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + IngredientsContract.IngredientsEntry.TABLE_NAME);
        onCreate(db);
    }
}