package com.learnwithme.buildapps.bakingapp.di.module;

import com.learnwithme.buildapps.bakingapp.ui.recipelist.contract.RecipeListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nithin on 28/06/2017.
 */
@Module
public class RecipeListPresenterModule {
    private final RecipeListContract.View mView;

    public RecipeListPresenterModule(RecipeListContract.View view) {
        mView = view;
    }

    @Provides
    public RecipeListContract.View provideRecipeListContractView() {
        return mView;
    }
}