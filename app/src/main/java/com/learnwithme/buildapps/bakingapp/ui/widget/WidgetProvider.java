package com.learnwithme.buildapps.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.learnwithme.buildapps.bakingapp.BakingApp;
import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.data.model.Ingredients;
import com.learnwithme.buildapps.bakingapp.di.component.DaggerWidgetDataComponent;
import com.learnwithme.buildapps.bakingapp.utils.string.StringUtils;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Nithin on 03/07/2017.
 */

public class WidgetProvider extends AppWidgetProvider {
    @Inject
    WidgetDataHelper widgetDataHelper;

    public static void updateAppWidgetContent(Context context, AppWidgetManager appWidgetManager,
          int appWidgetId, String recipeName, List<Ingredients> ingredients) {

        Timber.d("updateAppWidgetContent call...");
        Timber.d("id: " + appWidgetId + ", name: " + recipeName + "ingredients: "
                + ingredients.size());

        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_ingredients_list);
        views.setTextViewText(R.id.widget_recipe_name, recipeName);
        views.removeAllViews(R.id.widget_ingredients_container);

        for (Ingredients ingredient : ingredients) {
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                    R.layout.widget_ingredients);

            String line = StringUtils.formatIngredientString(
                    context, ingredient.ingredient(), ingredient.quantity(),
                    ingredient.measure());

            ingredientView.setTextViewText(R.id.widget_ingredient_name, line);
            views.addView(R.id.widget_ingredients_container, ingredientView);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Timber.d("onUpdate");

        DaggerWidgetDataComponent.builder()
                .recipeRepositoryComponent(
                        ((BakingApp) context.getApplicationContext())
                                .getRecipeRepositoryComponent())
                .build()
                .inject(this);

        for (int appWidgetId : appWidgetIds) {
            String recipeName = widgetDataHelper.getRecipeNameFromPrefs(appWidgetId);

            widgetDataHelper
                    .getIngredientsList(recipeName)
                    .take(1)
                    .subscribe(
                            // OnNext
                            ingredients ->
                                    WidgetProvider
                                            .updateAppWidgetContent(context, appWidgetManager,
                                                    appWidgetId, recipeName, ingredients),
                            // OnError
                            throwable ->
                                    Timber.d("Error: unable to populate widget data."));
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);

        DaggerWidgetDataComponent.builder()
                .recipeRepositoryComponent(
                        ((BakingApp) context.getApplicationContext())
                                .getRecipeRepositoryComponent())
                .build()
                .inject(this);

        for (int appWidgetId : appWidgetIds) {
            widgetDataHelper.deleteRecipeFromPrefs(appWidgetId);
        }
    }
}