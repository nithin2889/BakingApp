package com.learnwithme.buildapps.bakingapp.data;

import com.learnwithme.buildapps.bakingapp.data.model.Ingredients;
import com.learnwithme.buildapps.bakingapp.data.model.Recipe;
import com.learnwithme.buildapps.bakingapp.data.model.Step;
import com.learnwithme.buildapps.bakingapp.data.source.local.Local;
import com.learnwithme.buildapps.bakingapp.data.source.local.preferences.AppPreferenceHelper;
import com.learnwithme.buildapps.bakingapp.data.source.local.preferences.PreferenceHelper;
import com.learnwithme.buildapps.bakingapp.data.source.remote.Remote;
import com.learnwithme.buildapps.bakingapp.utils.rx.RxUtils;

import io.reactivex.Observable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Nithin on 23/06/2017.
 */

@Singleton
public class DataRepository implements RecipeDataManager {
    private final RecipeDataManager mLocalDataManager;
    private final RecipeDataManager mRemoteDataManager;
    private final AppPreferenceHelper mAppPreferenceHelper;

    @Inject
    public DataRepository(@Local RecipeDataManager mLocalDataManager,
                          @Remote RecipeDataManager mRemoteDataManager,
                          AppPreferenceHelper mAppPreferenceHelper) {
        this.mLocalDataManager = mLocalDataManager;
        this.mRemoteDataManager = mRemoteDataManager;
        this.mAppPreferenceHelper = mAppPreferenceHelper;
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        if (!mAppPreferenceHelper.isRecipeListSynced()) {
            return mRemoteDataManager
                    .getRecipes()
                    .compose(RxUtils.applySchedulers())
                    .doOnNext(recipeList -> {
                        mLocalDataManager.saveRecipe(recipeList);
                        mAppPreferenceHelper.saveRecipeNamesList(recipeList);
                    });
        } else {
            return mLocalDataManager
                    .getRecipes()
                    .compose(RxUtils.applySchedulers());
        }
    }

    @Override
    public Observable<List<Ingredients>> getIngredients(int recipeId) {
        return mLocalDataManager
                .getIngredients(recipeId)
                .compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<List<Ingredients>> getIngredients(String recipeName) {
        return mLocalDataManager
                .getIngredients(recipeName)
                .compose(RxUtils.applySchedulers());
    }

    @Override
    public Observable<List<Step>> getRecipeSteps(int recipeId) {
        return mLocalDataManager
                .getRecipeSteps(recipeId)
                .compose(RxUtils.applySchedulers());
    }

    @Override
    public void saveRecipe(List<Recipe> recipe) {
        mLocalDataManager.saveRecipe(recipe);
    }

    public void markRepoAsSynced(boolean synced) {
        mAppPreferenceHelper.setRecipeListSynced(synced);
    }

    public PreferenceHelper getPreferencesHelper() {
        return mAppPreferenceHelper;
    }
}