package com.learnwithme.buildapps.bakingapp.ui.widget;

import com.learnwithme.buildapps.bakingapp.data.DataRepository;
import com.learnwithme.buildapps.bakingapp.data.model.Ingredients;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Nithin on 03/07/2017.
 */

public class WidgetDataHelper {
    private final DataRepository recipeRepository;

    @Inject
    public WidgetDataHelper(DataRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Set<String> getRecipeNamesFromPrefs() {
        return recipeRepository.getPreferencesHelper().getRecipeNamesList();
    }

    public void deleteRecipeFromPrefs(int widgetId) {
        recipeRepository.getPreferencesHelper().deleteRecipeName(widgetId);
    }

    public void saveRecipeNameToPrefs(int appWidgetId, String name) {
        recipeRepository.getPreferencesHelper().saveRecipeName(appWidgetId, name);
    }

    public String getRecipeNameFromPrefs(int appWidgetId) {
        return recipeRepository.getPreferencesHelper().getRecipeName(appWidgetId);
    }

    public Observable<List<Ingredients>> getIngredientsList(String recipeName) {
        return recipeRepository.getIngredients(recipeName);
    }
}