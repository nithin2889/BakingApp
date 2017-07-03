package com.learnwithme.buildapps.bakingapp.di.component;

import com.learnwithme.buildapps.bakingapp.di.FragmentScoped;
import com.learnwithme.buildapps.bakingapp.di.module.RecipeStepPresenterModule;
import com.learnwithme.buildapps.bakingapp.ui.recipestep.activity.RecipeStepActivity;

import dagger.Component;

/**
 * Created by Nithin on 30/06/2017.
 */
@FragmentScoped
@Component(dependencies = RecipeRepositoryComponent.class,
    modules = RecipeStepPresenterModule.class)
public interface RecipeStepComponent {
    void inject(RecipeStepActivity recipeStepActivity);
}