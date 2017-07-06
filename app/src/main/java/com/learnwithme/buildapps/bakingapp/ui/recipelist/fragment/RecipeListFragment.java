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
    private static final String BUNDLE_RECYCLER_LAYOUT = "BUNDLE_RECYCLER_LAYOUT";
    private static final String BUNDLE_RECYCLER_RECIPE_DATA = "BUNDLE_RECYCLER_RECIPE_DATA";
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
    ArrayList<Recipe> mRecipeList;
    Unbinder unbinder;
    private RecipeListContract.Presenter mRecipeListPresenter;
    private RecipeListAdapter mRecipeListAdapter;

    public RecipeListFragment() { }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecipeListRecyclerView.getLayoutManager().onSaveInstanceState());
        // bundle.putParcelableArrayList(BUNDLE_RECYCLER_RECIPE_DATA, mRecipeList);
    }

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Changes regarding the saveInstanceState on device rotation starts
        /*if(savedInstanceState != null) {
            Parcelable savedRecyclerViewState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecipeListRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);
            mRecipeList = savedInstanceState.getParcelableArrayList(BUNDLE_RECYCLER_RECIPE_DATA);
            mRecipeListAdapter.refreshRecipes(mRecipeList);
        } else {
            mRecipeList = getArguments().getParcelableArrayList(BUNDLE_RECYCLER_RECIPE_DATA);
        }*/

        // List<Ingredients> ingredients = mRecipeList.get(0).ingredients();
        // Changes regarding the saveInstanceState on device rotation ends

        mRecipeListAdapter = new RecipeListAdapter(getContext(), mRecipeList, recipeId
                -> mRecipeListPresenter.loadRecipeDetails(recipeId));
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

    /*
    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if(bundle != null) {
            bundle.putParcelable(SAVED_LAYOUT_MANAGER,
                    mRecipeListRecyclerView
                        .getLayoutManager()
                        .onSaveInstanceState());
            Timber.d("instance state=>", mRecipeListRecyclerView.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if(bundle != null) {
            savedRecyclerLayoutState = bundle.getParcelable(SAVED_LAYOUT_MANAGER);
            Timber.d("onViewStateRestored savedRecyclerLayoutState=>",
                    savedRecyclerLayoutState);
            mRecipeListRecyclerView
                    .getLayoutManager()
                    .onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        mRecipeListPresenter.subscribe();
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