package com.learnwithme.buildapps.bakingapp.ui.recipelist.fragment;

import android.os.Bundle;
import android.os.Parcelable;
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

public class RecipeListFragment extends Fragment
        implements RecipeListContract.View {

    private static final String KEY_LAYOUT = "KEY_LAYOUT";
    private static final String POSITION = "POSITION";

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

    GridLayoutManager gridLayoutManager;
    Unbinder unbinder;

    private RecipeListContract.Presenter mRecipeListPresenter;
    private RecipeListAdapter mRecipeListAdapter;

    private Parcelable mRecipeListParcelable;
    private int mScrollPosition = -1;

    public RecipeListFragment() { }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        int scrollPosition = ((GridLayoutManager) mRecipeListRecyclerView.getLayoutManager())
                .findFirstCompletelyVisibleItemPosition();
        mRecipeListParcelable = gridLayoutManager.onSaveInstanceState();
        bundle.putParcelable(KEY_LAYOUT, mRecipeListParcelable);
        bundle.putInt(POSITION, scrollPosition);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle bundle) {
        super.onViewStateRestored(bundle);
        if(bundle != null) {
            mRecipeListParcelable = bundle.getParcelable(KEY_LAYOUT);
            mScrollPosition = bundle.getInt(POSITION);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecipeListPresenter.subscribe();
        // Not needed, the presenter callback works and the view is reset anyway.
        /*if(mRecipeListParcelable != null) {
            gridLayoutManager.onRestoreInstanceState(mRecipeListParcelable);
        }*/
    }

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecipeListAdapter
                = new RecipeListAdapter(getContext(), new ArrayList<>(0),
                recipeId -> mRecipeListPresenter.loadRecipeDetails(recipeId));

        mRecipeListAdapter.setHasStableIds(true);

        gridLayoutManager = new GridLayoutManager(getContext(), mGridColumnCount);
        mRecipeListRecyclerView.setLayoutManager(gridLayoutManager);
        mRecipeListRecyclerView.setHasFixedSize(true);
        mRecipeListRecyclerView.setAdapter(mRecipeListAdapter);

        return view;
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
        mRecipeListRecyclerView.scrollToPosition(mScrollPosition);
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