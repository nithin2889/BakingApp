package com.learnwithme.buildapps.bakingapp.utils.gson;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by Nithin on 26/06/2017.
 */
@GsonTypeAdapterFactory
public abstract class MyGsonAdapterFactory implements TypeAdapterFactory {
    public static TypeAdapterFactory create() {
        return new AutoValueGson_MyGsonAdapterFactory();
    }
}