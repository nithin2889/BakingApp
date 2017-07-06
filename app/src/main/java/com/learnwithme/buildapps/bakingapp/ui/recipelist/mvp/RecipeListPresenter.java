package com.learnwithme.buildapps.bakingapp.ui.recipelist.mvp;

import com.learnwithme.buildapps.bakingapp.data.DataRepository;
import com.learnwithme.buildapps.bakingapp.data.idlingresource.RecipeIdlingResource;
import com.learnwithme.buildapps.bakingapp.ui.recipelist.contract.RecipeListContract;
import com.learnwithme.buildapps.bakingapp.ui.recipelist.contract.RecipeListContract.View;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Nithin on 26/06/2017.
 */

public class RecipeListPresenter implements RecipeListContract.Presenter {

    private final DataRepository mDataRepository;
    private final RecipeListContract.View mRecipesView;
    private final CompositeDisposable mDisposableList;

    @Inject
    public RecipeListPresenter(DataRepository dataRepository,
                        View recipesView) {
        mDataRepository = dataRepository;
        mRecipesView = recipesView;

        mDisposableList = new CompositeDisposable();
    }

    @Inject
    void setupListeners() {
        mRecipesView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadRecipesFromRepo(false, null);
    }

    @Override
    public void unsubscribe() {
        mDisposableList.clear();
    }

    @Override
    public void loadRecipesFromRepo(boolean isSyncForced, RecipeIdlingResource resource) {
        if (isSyncForced) {
            mDataRepository.markRepoAsSynced(false);
        }
        mDisposableList.clear();

        Disposable subscription = mDataRepository
            .getRecipes()
            .doOnSubscribe(disposable -> mRecipesView.loadProgressBar(true))
            .subscribe(
                recipesList -> {
                    mRecipesView.showRecipeList(recipesList);
                    mDataRepository.markRepoAsSynced(true);
                    mRecipesView.loadProgressBar(false);
                    if (isSyncForced) mRecipesView.displayCompletedMessage();
                },
                throwable -> {
                    mRecipesView.loadProgressBar(false);
                    mRecipesView.displayErrorMessage();
                    mDataRepository.markRepoAsSynced(false);
                }
            );
        mDisposableList.add(subscription);
    }

    @Override
    public void loadRecipeDetails(int recipeId) {
        mRecipesView.displayRecipeDetails(recipeId);
    }
}