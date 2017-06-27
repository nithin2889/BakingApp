package com.learnwithme.buildapps.bakingapp.data.source.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.learnwithme.buildapps.bakingapp.data.model.Recipe;
import com.learnwithme.buildapps.bakingapp.di.ApplicationContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Nithin on 23/06/2017.
 */
@Singleton
public class AppPreferenceHelper implements PreferenceHelper {

    private static final String PREFS_FILE_NAME = "baking_app_prefs";
    private static final String PREFS_SYNCED = "baking_app_synced";
    private static final String PREFS_RECIPES = "baking_app_recipes";
    private static final String PREFS_CHOSEN_RECIPE = "baking_app_widget_chosen_recipe";

    private final SharedPreferences mSharedPreferences;

    @Inject
    public AppPreferenceHelper(@ApplicationContext Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isRecipeListSynced() {
        return mSharedPreferences.getBoolean(PREFS_SYNCED, false);
    }

    @Override
    public void setRecipeListSynced(boolean flag) {
        mSharedPreferences.edit().putBoolean(PREFS_SYNCED, flag).apply();
    }

    @SuppressWarnings("Convert2streamapi")
    @Override
    public void saveRecipeNamesList(List<Recipe> recipes) {
        Set<String> values = new HashSet<>();
        for(Recipe recipe : recipes) {
            values.add(recipe.name());
        }
        mSharedPreferences.edit().putStringSet(PREFS_RECIPES, values).apply();
    }

    @Override
    public Set<String> getRecipeNamesList() {
        return mSharedPreferences.getStringSet(PREFS_RECIPES, new HashSet<String>(0));
    }

    @Override
    public String getRecipeName(int keySuffix) {
        return mSharedPreferences.getString(PREFS_CHOSEN_RECIPE + keySuffix, "");
    }

    @Override
    public void saveRecipeName(int keySuffix, String name) {
        mSharedPreferences.edit().putString(PREFS_CHOSEN_RECIPE + keySuffix, name).apply();
    }

    @Override
    public void deleteRecipeName(int keySuffix) {
        mSharedPreferences.edit().remove(PREFS_CHOSEN_RECIPE + keySuffix).apply();
    }
}