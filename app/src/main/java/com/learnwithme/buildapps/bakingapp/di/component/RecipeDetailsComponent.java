package com.learnwithme.buildapps.bakingapp.di.component;

import com.learnwithme.buildapps.bakingapp.di.FragmentScoped;
import com.learnwithme.buildapps.bakingapp.di.module.RecipeDetailsPresenterModule;
import com.learnwithme.buildapps.bakingapp.ui.recipedetail.activity.RecipeDetailsActivity;

import dagger.Component;

/**
 * Created by Nithin on 29/06/2017.
 */
@FragmentScoped
@Component(dependencies = RecipeRepositoryComponent.class,
           modules = RecipeDetailsPresenterModule.class)
public interface RecipeDetailsComponent {
    void inject(RecipeDetailsActivity recipeDetailsActivity);
}