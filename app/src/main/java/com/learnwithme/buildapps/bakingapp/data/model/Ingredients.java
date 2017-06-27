package com.learnwithme.buildapps.bakingapp.data.model;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by Nithin on 22/06/2017.
 */
@AutoValue
public abstract class Ingredients {
    public abstract float quantity();
    public abstract String measure();
    public abstract String ingredient();

    public static Builder builder() {
        return new AutoValue_Ingredients.Builder();
    }

    public static TypeAdapter<Ingredients> typeAdapter(Gson gson) {
        return new AutoValue_Ingredients.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder quantity(float quantity);
        public abstract Builder measure(String measure);
        public abstract Builder ingredient(String ingredient);

        public abstract Ingredients build();
    }
}