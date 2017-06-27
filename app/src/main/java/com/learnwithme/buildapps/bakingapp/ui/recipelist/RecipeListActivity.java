package com.learnwithme.buildapps.bakingapp.ui.recipelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learnwithme.buildapps.bakingapp.R;

import javax.inject.Inject;

public class RecipeListActivity extends AppCompatActivity {
    /**
     *  The way I understood Dagger2:
     *  A dependency consumer (@Inject) asks for the
     *  dependency (object) from a dependency provider (@Provides)
     *  through a connector (@Component).
     *  @param savedInstanceState
     */

    @Inject
    RecipeListPresenter recipeListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}