package com.learnwithme.buildapps.bakingapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

import com.learnwithme.buildapps.bakingapp.di.component.DaggerRecipeRepositoryComponent;
import com.learnwithme.buildapps.bakingapp.di.component.RecipeRepositoryComponent;

import timber.log.Timber;

/**
 * Created by Nithin on 22/06/2017.
 */

public class BakingApp extends Application {
    private RecipeRepositoryComponent mRecipeRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());

            Stetho.initializeWithDefaults(this);
        }
        mRecipeRepositoryComponent =
            DaggerRecipeRepositoryComponent.builder()
                .bakingAppModule(new BakingAppModule(getApplicationContext()))
                .build();
    }

    public RecipeRepositoryComponent getRecipeRepositoryComponent() {
        return mRecipeRepositoryComponent;
    }
}