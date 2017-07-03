package com.learnwithme.buildapps.bakingapp.di.component;

import com.learnwithme.buildapps.bakingapp.BakingAppModule;
import com.learnwithme.buildapps.bakingapp.di.ProviderScoped;
import com.learnwithme.buildapps.bakingapp.ui.widget.WidgetConfigActivity;
import com.learnwithme.buildapps.bakingapp.ui.widget.WidgetProvider;

import dagger.Component;

/**
 * Created by Nithin on 03/07/2017.
 */
@ProviderScoped
@Component(dependencies = RecipeRepositoryComponent.class,
modules = BakingAppModule.class)
public interface WidgetDataComponent {
    void inject(WidgetProvider provider);
    void inject(WidgetConfigActivity activity);
}