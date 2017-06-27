package com.learnwithme.buildapps.bakingapp;

import android.content.Context;

import com.learnwithme.buildapps.bakingapp.data.source.remote.RecipeService;
import com.learnwithme.buildapps.bakingapp.data.source.remote.ServiceClientFactory;
import com.learnwithme.buildapps.bakingapp.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nithin on 26/06/2017.
 */
@Module
public class BakingAppModule {
    private Context context;

    public BakingAppModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    RecipeService provideRecipeService() {
        return ServiceClientFactory.createFrom(RecipeService.class, BuildConfig.ENDPOINT);
    }
}