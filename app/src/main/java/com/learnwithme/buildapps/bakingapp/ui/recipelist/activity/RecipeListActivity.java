package com.learnwithme.buildapps.bakingapp.ui.recipelist.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.learnwithme.buildapps.bakingapp.BakingApp;
import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.data.idlingresource.RecipeIdlingResource;
import com.learnwithme.buildapps.bakingapp.di.component.DaggerRecipeListComponent;
import com.learnwithme.buildapps.bakingapp.ui.recipelist.fragment.RecipeListFragment;
import com.learnwithme.buildapps.bakingapp.di.module.RecipeListPresenterModule;
import com.learnwithme.buildapps.bakingapp.ui.recipelist.mvp.RecipeListPresenter;
import com.learnwithme.buildapps.bakingapp.utils.fragment.FragmentUtils;

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
    RecipeListPresenter mRecipeListPresenter;
    
    @Nullable
    private RecipeIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        RecipeListFragment mRecipeListFragment =
                (RecipeListFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragmentContainer);

        if(mRecipeListFragment == null) {
            mRecipeListFragment = RecipeListFragment.newInstance();
            FragmentUtils.addFragment(getSupportFragmentManager(), mRecipeListFragment,
                    R.id.fragmentContainer);
        }
        DaggerRecipeListComponent.builder()
            .recipeRepositoryComponent(
                    ((BakingApp) getApplication()).getRecipeRepositoryComponent())
            .recipeListPresenterModule(new RecipeListPresenterModule(mRecipeListFragment))
            .build()
            .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_refresh) {
            mRecipeListPresenter.loadRecipesFromRepo(true, idlingResource);
            return true;
        }
        return super.onOptionsItemSelected(item);
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