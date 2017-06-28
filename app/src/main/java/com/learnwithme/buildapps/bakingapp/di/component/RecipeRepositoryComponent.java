package com.learnwithme.buildapps.bakingapp.di.component;

import com.learnwithme.buildapps.bakingapp.BakingAppModule;
import com.learnwithme.buildapps.bakingapp.data.DataRepository;
import com.learnwithme.buildapps.bakingapp.data.source.local.db.DbModule;
import com.learnwithme.buildapps.bakingapp.data.source.local.preferences.PreferenceModule;
import com.learnwithme.buildapps.bakingapp.di.module.RecipeRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nithin on 26/06/2017.
 */
@Singleton
@Component(modules = {RecipeRepositoryModule.class, DbModule.class,
        PreferenceModule.class, BakingAppModule.class})
public interface RecipeRepositoryComponent {
    DataRepository getDataRecipeRepository();
}