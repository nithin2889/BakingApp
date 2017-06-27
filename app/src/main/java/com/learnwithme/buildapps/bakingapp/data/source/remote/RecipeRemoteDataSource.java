package com.learnwithme.buildapps.bakingapp.data.source.remote;

import com.learnwithme.buildapps.bakingapp.data.RecipeDataManager;
import com.learnwithme.buildapps.bakingapp.data.model.Ingredients;
import com.learnwithme.buildapps.bakingapp.data.model.Recipe;
import com.learnwithme.buildapps.bakingapp.data.model.Step;
import com.learnwithme.buildapps.bakingapp.utils.rx.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Nithin on 26/06/2017.
 */

public class RecipeRemoteDataSource implements RecipeDataManager {
    private final RecipeService mRecipeService;

    @Inject
    public RecipeRemoteDataSource(RecipeService recipeService) {
        mRecipeService = recipeService;
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        return mRecipeService
                .loadRecipesFromServer()
                .compose(RxUtils.applySchedulers())
                .doOnSubscribe(disposable -> Timber.d("Syncing started"))
                .doOnError(Throwable -> Timber.d("Syncing failed!"))
                .doOnComplete(() -> Timber.d("Syncing completed!"));
    }

    @Override
    public Observable<List<Ingredients>> getIngredients(int recipeId) {
        throw new UnsupportedOperationException("getIngredients in RemoteDataSource is not implemented!");
    }

    @Override
    public Observable<List<Ingredients>> getIngredients(String recipeName) {
        throw new UnsupportedOperationException("getIngredients in RemoteDataSource is not implemented!");
    }

    @Override
    public Observable<List<Step>> getRecipeSteps(int recipeId) {
        throw new UnsupportedOperationException("getRecipeSteps in RemoteDataSource is not implemented!");
    }

    @Override
    public void saveRecipe(List<Recipe> recipe) {
        throw new UnsupportedOperationException("saveRecipe in RemoteDataSource is not implemented!");
    }
}