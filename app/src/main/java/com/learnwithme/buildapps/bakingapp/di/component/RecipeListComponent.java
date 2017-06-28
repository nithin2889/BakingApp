package com.learnwithme.buildapps.bakingapp.di.component;

import com.learnwithme.buildapps.bakingapp.di.FragmentScoped;
import com.learnwithme.buildapps.bakingapp.ui.recipelist.activity.RecipeListActivity;
import com.learnwithme.buildapps.bakingapp.di.module.RecipeListPresenterModule;

import dagger.Component;

/**
 * Created by Nithin on 28/06/2017.
 */
@FragmentScoped
@Component(dependencies = RecipeRepositoryComponent.class,
        modules = RecipeListPresenterModule.class)
public interface RecipeListComponent {
    void inject(RecipeListActivity recipeListActivity);
}