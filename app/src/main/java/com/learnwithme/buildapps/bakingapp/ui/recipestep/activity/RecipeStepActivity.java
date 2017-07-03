package com.learnwithme.buildapps.bakingapp.ui.recipestep.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learnwithme.buildapps.bakingapp.BakingApp;
import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.di.component.DaggerRecipeStepComponent;
import com.learnwithme.buildapps.bakingapp.di.module.RecipeStepPresenterModule;
import com.learnwithme.buildapps.bakingapp.ui.recipestep.fragment.RecipeStepFragment;
import com.learnwithme.buildapps.bakingapp.ui.recipestep.mvp.RecipeStepPresenter;
import com.learnwithme.buildapps.bakingapp.utils.fragment.FragmentUtils;

import javax.inject.Inject;

public class RecipeStepActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPE_ID = "RECIPE_ID";
    private static final int DEFAULT_RECIPE_ID = 1;

    public static final String EXTRA_STEP_ID = "STEP_ID";
    private static final int DEFAULT_STEP_ID = 0;

    @Inject
    RecipeStepPresenter mRecipeStepPresenter;

    public static Intent prepareIntent(Context context, int recipeId, int stepId) {
        Intent intent = new Intent(context, RecipeStepActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        intent.putExtra(EXTRA_STEP_ID, stepId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, DEFAULT_RECIPE_ID);
        int stepId = getIntent().getIntExtra(EXTRA_STEP_ID, DEFAULT_STEP_ID);

        RecipeStepFragment mRecipeStepFragment =
                (RecipeStepFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.stepFragmentContainer);

        if(mRecipeStepFragment == null) {
            mRecipeStepFragment = RecipeStepFragment.newInstance(stepId);
            FragmentUtils.addFragment(getSupportFragmentManager(), mRecipeStepFragment,
                    R.id.stepFragmentContainer);
        }

        DaggerRecipeStepComponent.builder()
            .recipeRepositoryComponent(((BakingApp) getApplication())
                    .getRecipeRepositoryComponent())
            .recipeStepPresenterModule(
                    new RecipeStepPresenterModule(mRecipeStepFragment, recipeId))
            .build()
            .inject(this);
    }
}