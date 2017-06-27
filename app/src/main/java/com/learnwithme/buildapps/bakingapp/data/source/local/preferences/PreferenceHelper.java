package com.learnwithme.buildapps.bakingapp.data.source.local.preferences;

import com.learnwithme.buildapps.bakingapp.data.model.Recipe;

import java.util.List;
import java.util.Set;

/**
 * Created by Nithin on 23/06/2017.
 */

public interface PreferenceHelper {
    boolean isRecipeListSynced();

    void setRecipeListSynced(boolean flag);

    void saveRecipeNamesList(List<Recipe> recipes);

    Set<String> getRecipeNamesList();

    String getRecipeName(int keySuffix);

    void saveRecipeName(int keySuffix, String name);

    void deleteRecipeName(int keySuffix);
}