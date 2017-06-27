package com.learnwithme.buildapps.bakingapp.data.source.local.preferences;

import android.content.Context;
import android.support.annotation.NonNull;

import com.learnwithme.buildapps.bakingapp.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nithin on 26/06/2017.
 */
@Module
public class PreferenceModule {
    @Singleton
    @Provides
    @NonNull
    PreferenceHelper providePreferenceHelper(@ApplicationContext Context context) {
        return new AppPreferenceHelper(context);
    }
}