package com.learnwithme.buildapps.bakingapp.di.module;

import com.learnwithme.buildapps.bakingapp.data.RecipeDataManager;
import com.learnwithme.buildapps.bakingapp.data.source.local.Local;
import com.learnwithme.buildapps.bakingapp.data.source.local.RecipeLocalDataSource;
import com.learnwithme.buildapps.bakingapp.data.source.remote.RecipeRemoteDataSource;
import com.learnwithme.buildapps.bakingapp.data.source.remote.RecipeService;
import com.learnwithme.buildapps.bakingapp.data.source.remote.Remote;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nithin on 26/06/2017.
 */
@Module
public class RecipeRepositoryModule {
    @Singleton
    @Provides
    @Local
    RecipeDataManager provideRecipeLocalDataSource(BriteDatabase briteDatabase) {
        return new RecipeLocalDataSource(briteDatabase);
    }

    @Singleton
    @Provides
    @Remote
    RecipeDataManager provideRecipeRemoteDataSource(RecipeService recipeService) {
        return new RecipeRemoteDataSource(recipeService);
    }
}