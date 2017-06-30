package com.learnwithme.buildapps.bakingapp.ui.recipedetail.contract;

import com.learnwithme.buildapps.bakingapp.data.model.Ingredients;
import com.learnwithme.buildapps.bakingapp.data.model.Step;
import com.learnwithme.buildapps.bakingapp.ui.base.BasePresenter;
import com.learnwithme.buildapps.bakingapp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nithin on 29/06/2017.
 */

public interface RecipeDetailsContract {
    interface View extends BaseView<Presenter> {
        void displayIngredientsList(List<Ingredients> ingredients);

        void displayStepsList(List<Step> steps);

        void displayErrorMessage();

        void displayRecipeInActivityTitle(String recipeName);

        void displayStepDetails(int stepId);

        void refreshStepContainerFragment(String desc, String videoUrl, String imageUrl);
    }

    interface Presenter extends BasePresenter {
        void getRecipeNameFromRepo();

        void getIngredientsFromRepo();

        void getStepsFromRepo();

        void loadStepDetails(int stepId);

        void loadStepData(int stepId);
    }
}