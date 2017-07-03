package com.learnwithme.buildapps.bakingapp.di.module;

import com.learnwithme.buildapps.bakingapp.ui.recipestep.contract.RecipeStepContract;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nithin on 30/06/2017.
 */
@Module
public class RecipeStepPresenterModule {
    private final RecipeStepContract.View mView;
    private final int mRecipeId;

    public RecipeStepPresenterModule(RecipeStepContract.View view,
                                     int recipeId) {
        mView = view;
        mRecipeId = recipeId;
    }

    @Provides
    public RecipeStepContract.View providesRecipeStepContractView() {
        return mView;
    }

    @Provides
    public int provideRecipeId() {
        return mRecipeId;
    }
}