package com.learnwithme.buildapps.bakingapp.ui.recipelist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.data.model.Recipe;
import com.learnwithme.buildapps.bakingapp.ui.recipedetail.activity.RecipeDetailsActivity;
import com.learnwithme.buildapps.bakingapp.ui.recipelist.contract.RecipeListAdapter;
import com.learnwithme.buildapps.bakingapp.ui.recipelist.contract.RecipeListContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Nithin on 27/06/2017.
 */

public class RecipeListFragment extends Fragment
        implements RecipeListContract.View {

    @BindView(R.id.recipe_list_recycler_view)
    RecyclerView mRecipeListRecyclerView;
    @BindView(R.id.recipe_list_progress_bar)
    ProgressBar mRecipeListProgressBar;

    @BindInt(R.integer.grid_column_count)
    int mGridColumnCount;
    @BindString(R.string.recipe_list_sync_completed)
    String mRecipeListSyncCompleted;
    @BindString(R.string.recipe_list_connection_error)
    String mRecipeListConnectionError;

    Unbinder unbinder;

    private RecipeListContract.Presenter mRecipeListPresenter;
    private RecipeListAdapter mRecipeListAdapter;

    public RecipeListFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecipeListAdapter = new RecipeListAdapter(
                new ArrayList<>(0),
                recipeId -> mRecipeListPresenter.loadRecipeDetails(recipeId)
        );
        mRecipeListAdapter.setHasStableIds(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), mGridColumnCount);
        mRecipeListRecyclerView.setLayoutManager(layoutManager);
        mRecipeListRecyclerView.setHasFixedSize(true);
        mRecipeListRecyclerView.setAdapter(mRecipeListAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecipeListPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRecipeListPresenter.unsubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    @Override
    public void setPresenter(RecipeListContract.Presenter recipeListPresenter) {
        this.mRecipeListPresenter = recipeListPresenter;
    }

    @Override
    public void showRecipeList(List<Recipe> recipeList) {
        mRecipeListAdapter.refreshRecipes(recipeList);
    }

    @Override
    public void loadProgressBar(boolean show) {
        setViewVisibility(mRecipeListRecyclerView, !show);
        setViewVisibility(mRecipeListProgressBar, show);
    }

    @Override
    public void displayCompletedMessage() {
        Toast.makeText(getContext(), mRecipeListSyncCompleted, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayErrorMessage() {
        Toast.makeText(getContext(), mRecipeListConnectionError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayRecipeDetails(int recipeId) {
        startActivity(RecipeDetailsActivity.prepareIntent(getContext(), recipeId));
    }

    private void setViewVisibility(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }
}