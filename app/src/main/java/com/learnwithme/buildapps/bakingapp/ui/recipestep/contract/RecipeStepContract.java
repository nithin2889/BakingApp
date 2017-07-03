package com.learnwithme.buildapps.bakingapp.ui.recipestep.contract;

import com.learnwithme.buildapps.bakingapp.data.model.Step;
import com.learnwithme.buildapps.bakingapp.ui.base.BasePresenter;
import com.learnwithme.buildapps.bakingapp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nithin on 29/06/2017.
 */

public interface RecipeStepContract {
    interface View extends BaseView<Presenter> {
        void showStepsInViewPager(List<Step> steps);

        void showErrorMessage();

        void moveToCurrentStep();
    }

    interface Presenter extends BasePresenter {
        void loadStepsToAdapter();
    }
}