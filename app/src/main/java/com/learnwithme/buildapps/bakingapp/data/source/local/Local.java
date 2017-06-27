package com.learnwithme.buildapps.bakingapp.data.source.local;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Nithin on 23/06/2017.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Local {
}