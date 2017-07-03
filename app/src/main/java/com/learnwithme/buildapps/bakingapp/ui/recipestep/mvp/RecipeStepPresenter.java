package com.learnwithme.buildapps.bakingapp.ui.recipestep.mvp;

import com.learnwithme.buildapps.bakingapp.data.DataRepository;
import com.learnwithme.buildapps.bakingapp.ui.recipestep.contract.RecipeStepContract;
import com.learnwithme.buildapps.bakingapp.ui.recipestep.contract.RecipeStepContract.View;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Nithin on 29/06/2017.
 */

public class RecipeStepPresenter implements RecipeStepContract.Presenter {
    private final DataRepository mDataRepository;
    private final RecipeStepContract.View mStepView;
    private final int mRecipeId;
    private final CompositeDisposable disposableList;

    @Inject
    public RecipeStepPresenter(DataRepository dataRepository,
                               View view,
                               int recipeId) {
        mDataRepository = dataRepository;
        mStepView = view;
        mRecipeId = recipeId;

        disposableList = new CompositeDisposable();
    }

    @Inject
    public void setupListeners() {
        mStepView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadStepsToAdapter();
    }

    @Override
    public void unsubscribe() {
        disposableList.clear();
    }

    @Override
    public void loadStepsToAdapter() {
        Disposable subscription = mDataRepository
            .getRecipeSteps(mRecipeId)
            .subscribe(
                // onNext
                steps -> {
                    mStepView.showStepsInViewPager(steps);
                    mStepView.moveToCurrentStep();
                },
                // onError
                throwable -> mStepView.showErrorMessage()
            );
        disposableList.add(subscription);
    }
}