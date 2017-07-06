package com.learnwithme.buildapps.bakingapp.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * Created by Nithin on 22/06/2017.
 */
@AutoValue
public abstract class Recipe {
    public abstract int id();
    public abstract String name();
    public abstract List<Ingredients> ingredients();
    public abstract List<Step> steps();
    public abstract int servings();
    public abstract String image();

    public static Builder builder() {
        return new AutoValue_Recipe.Builder();
    }

    public static TypeAdapter<Recipe> typeAdapter(Gson gson) {
        return new AutoValue_Recipe.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(int id);
        public abstract Builder name(String name);
        public abstract Builder ingredients(List<Ingredients> ingredients);
        public abstract Builder steps(List<Step> steps);
        public abstract Builder servings(int servings);
        public abstract Builder image(String image);

        public abstract Recipe build();
    }

    /*
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String INGREDIENTS = "ingredients";
    public static final String STEPS = "steps";
    public static final String SERVINGS = "servings";
    public static final String IMAGE = "image";

    private int id;
    private String name;
    private List<Ingredients> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;

    public abstract int id();
    public abstract String name();
    public abstract List<Ingredients> ingredients();
    public abstract List<Step> steps();
    public abstract int servings();
    public abstract String image();

    public static Recipe.Builder builder() {
        return new AutoValue_Recipe.Builder();
    }

    public static TypeAdapter<Recipe> typeAdapter(Gson gson) {
        return new AutoValue_Recipe.GsonTypeAdapter(gson);
    }

    public static final class Builder {
        private final ContentValues values = new ContentValues();
        List<Ingredients> ingredient;
        List<Step> step;

        public Recipe.Builder id(int id) {
            values.put(ID, id);
            return this;
        }

        public Builder name(String name) {
            values.put(NAME, name);
            return this;
        }

        public Builder ingredients(List<Ingredients> ingredients) {
            *//*for(int i=0; i<ingredients.size(); i++) {
                values.put(INGREDIENTS, ingredients.get(i).ingredient());
            }*//*
            this.ingredient = ingredients;
            return this;
        }

        public Builder steps(List<Step> steps) {
            *//*for(int i=0; i<steps.size(); i++) {
                values.put(STEPS, steps.get(i));
            }*//*
            this.step = steps;
            return this;
        }

        public Builder servings(int servings) {
            values.put(SERVINGS, servings);
            return this;
        }

        public Builder image(String image) {
            values.put(IMAGE, image);
            return this;
        }

        public Recipe build() {
            Recipe recipe = null;
            recipe.id = (int) values.get(ID);
            recipe.name = (String) values.get(NAME);
            recipe.ingredients = ingredient;
            recipe.steps = step;
            recipe.servings = (int) values.get(SERVINGS);
            recipe.image = (String) values.get(IMAGE);

            return recipe;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }*/
}