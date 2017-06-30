package com.learnwithme.buildapps.bakingapp.di.module;

import com.learnwithme.buildapps.bakingapp.ui.recipedetail.contract.RecipeDetailsContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nithin on 29/06/2017.
 */
@Module
public class RecipeDetailsPresenterModule {
    private final RecipeDetailsContract.View mView;
    private final int mRecipeId;

    public RecipeDetailsPresenterModule(RecipeDetailsContract.View view, int recipeId) {
        mView = view;
        mRecipeId = recipeId;
    }

    @Provides
    public RecipeDetailsContract.View provideRecipeDetailsContractView() {
        return mView;
    }

    @Provides
    public int provideRecipeId() {
        return mRecipeId;
    }
}