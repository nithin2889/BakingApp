package com.learnwithme.buildapps.bakingapp.data.source.local;


import com.learnwithme.buildapps.bakingapp.data.RecipeDataManager;
import com.learnwithme.buildapps.bakingapp.data.model.Ingredients;
import com.learnwithme.buildapps.bakingapp.data.model.Recipe;
import com.learnwithme.buildapps.bakingapp.data.model.Step;
import com.learnwithme.buildapps.bakingapp.data.source.local.db.contract.IngredientsContract.IngredientsEntry;
import com.learnwithme.buildapps.bakingapp.data.source.local.db.contract.RecipeContract.RecipeEntry;
import com.learnwithme.buildapps.bakingapp.data.source.local.db.contract.StepsContract.StepsEntry;
import com.learnwithme.buildapps.bakingapp.utils.db.DbUtils;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;

/**
 * Created by Nithin on 26/06/2017.
 */
@Singleton
public class RecipeLocalDataSource implements RecipeDataManager {
    private final BriteDatabase mBriteDatabase;

    @Inject
    public RecipeLocalDataSource(BriteDatabase briteDatabase) {
        mBriteDatabase = briteDatabase;
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        rx.Observable<List<Recipe>> listObservable = mBriteDatabase
                .createQuery(RecipeEntry.TABLE_NAME,
                        DbUtils.querySelectAll(RecipeEntry.TABLE_NAME))
                .mapToOne(DbUtils::recipesFromCursor);

        return RxJavaInterop.toV2Observable(listObservable);
    }

    @Override
    public Observable<List<Ingredients>> getIngredients(int recipeId) {
        rx.Observable<List<Ingredients>> listObservable = mBriteDatabase
                .createQuery(IngredientsEntry.TABLE_NAME,
                        DbUtils.querySelectById(IngredientsEntry.TABLE_NAME, IngredientsEntry.COLUMN_RECIPE_ID),
                        String.valueOf(recipeId))
                .mapToOne(DbUtils::ingredientsFromCursor);

        return RxJavaInterop.toV2Observable(listObservable);
    }

    @Override
    public Observable<List<Ingredients>> getIngredients(String recipeName) {
        return getRecipes()
                .flatMap(Observable::fromIterable)
                .filter(recipe -> Objects.equals(recipe.name(), recipeName))
                .map(Recipe::id)
                .flatMap(this::getIngredients);
    }

    @Override
    public Observable<List<Step>> getRecipeSteps(int recipeId) {
        rx.Observable<List<Step>> listObservable = mBriteDatabase
                .createQuery(StepsEntry.TABLE_NAME,
                        DbUtils.querySelectById(StepsEntry.TABLE_NAME,
                                StepsEntry.COLUMN_RECIPE_ID),
                        String.valueOf(recipeId))
                .mapToOne(DbUtils::stepsFromCursor);

        return RxJavaInterop.toV2Observable(listObservable);
    }

    @Override
    public void saveRecipe(List<Recipe> recipe) {
        BriteDatabase.Transaction transaction = mBriteDatabase.newTransaction();
        try {
            deleteAllRecipes();

            for(Recipe recipes : recipe) {
                int id = recipes.id();

                for(Ingredients ingredients : recipes.ingredients()) {
                    mBriteDatabase.insert(IngredientsEntry.TABLE_NAME,
                                    DbUtils.ingredientsToContentValues(ingredients, id));
                }

                for(Step step : recipes.steps()) {
                    mBriteDatabase.insert(StepsEntry.TABLE_NAME,
                            DbUtils.stepsToContentValues(step, id));
                }
                mBriteDatabase.insert(RecipeEntry.TABLE_NAME,
                        DbUtils.recipeToContentValues(recipes));
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    private void deleteAllRecipes() {
        mBriteDatabase.delete(RecipeEntry.TABLE_NAME, null);
        mBriteDatabase.delete(StepsEntry.TABLE_NAME, null);
        mBriteDatabase.delete(IngredientsEntry.TABLE_NAME, null);
    }
}