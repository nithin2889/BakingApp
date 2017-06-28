package com.learnwithme.buildapps.bakingapp.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Nithin on 28/06/2017.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScoped {
}