package com.learnwithme.buildapps.bakingapp.data;

import com.learnwithme.buildapps.bakingapp.data.model.Ingredients;
import com.learnwithme.buildapps.bakingapp.data.model.Recipe;
import com.learnwithme.buildapps.bakingapp.data.model.Step;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Nithin on 23/06/2017.
 */

public interface RecipeDataManager {
    Observable<List<Recipe>> getRecipes();

    Observable<List<Ingredients>> getIngredients(int recipeId);

    Observable<List<Ingredients>> getIngredients(String recipeName);

    Observable<List<Step>> getRecipeSteps(int recipeId);

    void saveRecipe(List<Recipe> recipe);
}