package com.learnwithme.buildapps.bakingapp.utils.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.learnwithme.buildapps.bakingapp.data.model.Ingredients;
import com.learnwithme.buildapps.bakingapp.data.model.Recipe;
import com.learnwithme.buildapps.bakingapp.data.model.Step;
import com.learnwithme.buildapps.bakingapp.data.source.local.db.contract.IngredientsContract.IngredientsEntry;
import com.learnwithme.buildapps.bakingapp.data.source.local.db.contract.RecipeContract.RecipeEntry;
import com.learnwithme.buildapps.bakingapp.data.source.local.db.contract.StepsContract.StepsEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithin on 26/06/2017.
 */

public class DbUtils {
    public static String querySelectAll(String tableName) {
        return "SELECT * FROM " + tableName;
    }

    public static String querySelectById(String tableName, String column) {
        return "SELECT * FROM " + tableName + " WHERE " + column + " = ?";
    }

    public static ContentValues ingredientsToContentValues(
            @NonNull Ingredients ingredients,
            int recipeId) {
        ContentValues cv = new ContentValues();

        cv.put(IngredientsEntry.COLUMN_RECIPE_ID, recipeId);
        cv.put(IngredientsEntry.COLUMN_QUANTITY, ingredients.quantity());
        cv.put(IngredientsEntry.COLUMN_MEASURE, ingredients.measure());
        cv.put(IngredientsEntry.COLUMN_INGREDIENT, ingredients.ingredient());

        return cv;
    }

    public static List<Ingredients> ingredientsFromCursor(@NonNull Cursor cursor) {
        List<Ingredients> ingredientsList = new ArrayList<>();

        // Firstly, cursor will be moved to -1 and then starts traversing from first element
        if(cursor.getCount() > 0) {
            cursor.moveToPosition(-1);

            while(cursor.moveToNext()) {
                float quantity = cursor.getFloat(cursor.getColumnIndexOrThrow
                        (IngredientsEntry.COLUMN_QUANTITY));
                String measure = cursor.getString(cursor.getColumnIndexOrThrow
                        (IngredientsEntry.COLUMN_MEASURE));
                String ingredient = cursor.getString(cursor.getColumnIndexOrThrow
                        (IngredientsEntry.COLUMN_INGREDIENT));

                Ingredients result = Ingredients.builder()
                        .quantity(quantity)
                        .measure(measure)
                        .ingredient(ingredient)
                        .build();

                ingredientsList.add(result);
            }
        }
        return ingredientsList;
    }

    public static ContentValues stepsToContentValues(
            @NonNull Step step,
            int recipeId) {
        ContentValues cv = new ContentValues();

        cv.put(StepsEntry.COLUMN_RECIPE_ID, recipeId);
        cv.put(StepsEntry.COLUMN_STEP_ID, step.id());
        cv.put(StepsEntry.COLUMN_SHORT_DESC, step.shortDescription());
        cv.put(StepsEntry.COLUMN_DESC, step.description());
        cv.put(StepsEntry.COLUMN_VIDEO_URL, step.videoURL());
        cv.put(StepsEntry.COLUMN_THUMB_URL, step.thumbnailURL());

        return cv;
    }

    public static List<Step> stepsFromCursor(@NonNull Cursor cursor) {
        List<Step> stepList = new ArrayList<>();

        // Firstly, cursor will be moved to -1 and then starts traversing from first element
        if(cursor.getCount() > 0) {
            cursor.moveToPosition(-1);

            while(cursor.moveToNext()) {
                int stepId = cursor.getInt(cursor.getColumnIndexOrThrow
                        (StepsEntry.COLUMN_STEP_ID));
                String shortDesc = cursor.getString(cursor.getColumnIndexOrThrow
                        (StepsEntry.COLUMN_SHORT_DESC));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow
                        (StepsEntry.COLUMN_DESC));
                String video = cursor.getString(cursor.getColumnIndexOrThrow
                        (StepsEntry.COLUMN_VIDEO_URL));
                String thumbnail = cursor.getString(cursor.getColumnIndexOrThrow
                        (StepsEntry.COLUMN_THUMB_URL));

                Step step = Step.builder()
                        .id(stepId)
                        .shortDescription(shortDesc)
                        .description(desc)
                        .videoURL(video)
                        .thumbnailURL(thumbnail)
                        .build();

                stepList.add(step);
            }
        }
        return stepList;
    }

    public static ContentValues recipeToContentValues(@NonNull Recipe recipe) {
        ContentValues cv = new ContentValues();

        cv.put(RecipeEntry.COLUMN_RECIPE_ID, recipe.id());
        cv.put(RecipeEntry.COLUMN_NAME, recipe.name());
        cv.put(RecipeEntry.COLUMN_SERVINGS, recipe.servings());
        cv.put(RecipeEntry.COLUMN_IMAGE, recipe.image());

        return cv;
    }

    public static List<Recipe> recipesFromCursor(@NonNull Cursor cursor) {
        List<Recipe> recipesList = new ArrayList<>();

        // Firstly, cursor will be moved to -1 and then starts traversing from first element
        if(cursor.getCount() > 0) {
            cursor.moveToPosition(-1);

            while(cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow
                        (RecipeEntry.COLUMN_RECIPE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow
                        (RecipeEntry.COLUMN_NAME));
                int servings = cursor.getInt(cursor.getColumnIndexOrThrow
                        (RecipeEntry.COLUMN_SERVINGS));
                String image = cursor.getString(cursor.getColumnIndexOrThrow
                        (RecipeEntry.COLUMN_IMAGE));

                Recipe recipe = Recipe.builder()
                        .id(id)
                        .name(name)
                        .ingredients(new ArrayList<>())
                        .steps(new ArrayList<>())
                        .servings(servings)
                        .image(image)
                        .build();

                recipesList.add(recipe);
            }
        }
        return recipesList;
    }
}
