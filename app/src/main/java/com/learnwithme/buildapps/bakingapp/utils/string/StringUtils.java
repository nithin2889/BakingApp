package com.learnwithme.buildapps.bakingapp.utils.string;

import android.content.Context;

import com.learnwithme.buildapps.bakingapp.R;

import java.util.Locale;

/**
 * Created by Nithin on 26/06/2017.
 */

public class StringUtils {
    public static String formatIngredientString(Context context, String name,
                                                float quantity, String measure) {
        String line = context.getResources().getString(R.string.recipe_details_ingredient_line);
        String quantityStr = String.format(Locale.US, "%s", quantity);
        if(quantity == (long) quantity) {
            quantityStr = String.format(Locale.US, "%d", (long) quantity);
        }
        return String.format(Locale.US, line, name, quantityStr, measure);
    }
}