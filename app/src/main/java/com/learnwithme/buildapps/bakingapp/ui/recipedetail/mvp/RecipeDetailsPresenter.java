package com.learnwithme.buildapps.bakingapp.ui.recipedetail.mvp;

import com.learnwithme.buildapps.bakingapp.data.DataRepository;
import com.learnwithme.buildapps.bakingapp.ui.recipedetail.contract.RecipeDetailsContract;
import com.learnwithme.buildapps.bakingapp.ui.recipedetail.contract.RecipeDetailsContract.View;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Nithin on 29/06/2017.
 */

public class RecipeDetailsPresenter implements RecipeDetailsContract.Presenter {
    private final DataRepository mRecipeRepository;
    private final RecipeDetailsContract.View mDetailsView;
    private final int mRecipeId;
    private final CompositeDisposable mDisposableList;

    @Inject
    public RecipeDetailsPresenter(DataRepository recipeRepository,
                                  View detailsView,
                                  int recipeId) {
        mRecipeRepository = recipeRepository;
        mDetailsView = detailsView;
        mRecipeId = recipeId;

        mDisposableList = new CompositeDisposable();
    }

    @Inject
    public void setUpListeners() {
        mDetailsView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getRecipeNameFromRepo();
        getIngredientsFromRepo();
        getStepsFromRepo();
    }

    @Override
    public void unsubscribe() {
        mDisposableList.clear();
    }

    @Override
    public void getRecipeNameFromRepo() {
        Disposable subscription = mRecipeRepository
                .getRecipes()
                .flatMap(Observable::fromIterable)
                .filter(recipe -> recipe.id() == mRecipeId)
                .subscribe(
                    // onNext
                    recipe -> mDetailsView.displayRecipeInActivityTitle(recipe.name()),
                    // onError
                    throwable -> mDetailsView.displayErrorMessage());

        mDisposableList.add(subscription);
    }

    @Override
    public void getIngredientsFromRepo() {
        Disposable subscription = mRecipeRepository
            .getIngredients(mRecipeId)
            .subscribe(
                // onNext
                mDetailsView::displayIngredientsList,
                // onError
                throwable -> mDetailsView.displayErrorMessage());

        mDisposableList.add(subscription);
    }

    @Override
    public void getStepsFromRepo() {
        Disposable subscription = mRecipeRepository
            .getRecipeSteps(mRecipeId)
            .subscribe(
                // onNext
                mDetailsView::displayStepsList,
                // onError
                throwable -> mDetailsView.displayErrorMessage());

        mDisposableList.add(subscription);
    }

    @Override
    public void loadStepDetails(int stepId) {
        mDetailsView.displayStepDetails(stepId);
    }

    @Override
    public void loadStepData(int stepId) {
        Disposable subscription = mRecipeRepository
            .getRecipeSteps(mRecipeId)
            .flatMap(Observable::fromIterable)
            .subscribe(
                // onNext
                step -> mDetailsView.refreshStepContainerFragment(
                        step.description(),
                        step.videoURL(),
                        step.thumbnailURL()),
                // onError
                throwable -> mDetailsView.displayErrorMessage());

        mDisposableList.add(subscription);
    }
}