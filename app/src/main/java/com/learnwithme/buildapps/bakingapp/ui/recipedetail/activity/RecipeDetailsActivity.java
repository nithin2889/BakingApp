package com.learnwithme.buildapps.bakingapp.ui.recipedetail.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learnwithme.buildapps.bakingapp.BakingApp;
import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.data.idlingresource.RecipeIdlingResource;
import com.learnwithme.buildapps.bakingapp.di.component.DaggerRecipeDetailsComponent;
import com.learnwithme.buildapps.bakingapp.di.module.RecipeDetailsPresenterModule;
import com.learnwithme.buildapps.bakingapp.ui.recipedetail.fragment.RecipeDetailsFragment;
import com.learnwithme.buildapps.bakingapp.ui.recipedetail.mvp.RecipeDetailsPresenter;
import com.learnwithme.buildapps.bakingapp.utils.fragment.FragmentUtils;

import javax.inject.Inject;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "RECIPE_ID";
    private static final int DEFAULT_RECIPE_ID = 1;

    @Inject
    RecipeDetailsPresenter mRecipeDetailsPresenter;

    @Nullable
    private RecipeIdlingResource idlingResource;

    public static Intent prepareIntent(Context context, int recipeId) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_details);

        setupActionBar();

        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, DEFAULT_RECIPE_ID);
        RecipeDetailsFragment recipeDetailsFragment =
                (RecipeDetailsFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.detailsFragmentContainer);

        if(recipeDetailsFragment == null) {
            recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipeId);
            FragmentUtils.addFragment(getSupportFragmentManager(), recipeDetailsFragment,
                    R.id.detailsFragmentContainer);
        }

        DaggerRecipeDetailsComponent.builder()
                .recipeRepositoryComponent(((BakingApp) getApplication())
                        .getRecipeRepositoryComponent())
                .recipeDetailsPresenterModule(new RecipeDetailsPresenterModule(recipeDetailsFragment, recipeId))
                .build()
                .inject(this);
    }

    private void setupActionBar() {
        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new RecipeIdlingResource();
        }
        return idlingResource;
    }
}