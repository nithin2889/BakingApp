package com.learnwithme.buildapps.bakingapp.data.idlingresource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Nithin on 02/07/2017.
 */

/**
 * Idling in Espresso is built around the concept: Espresso waits until the app
 * is "idle" before performing the next action and checking the next assertion.
 */
public class RecipeIdlingResource implements IdlingResource {
    @Nullable
    private volatile ResourceCallback callback;

    private AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return RecipeIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean idleState) {
        isIdleNow.set(idleState);

        if(idleState && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}