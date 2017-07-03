package com.learnwithme.buildapps.bakingapp.ui.recipelist.contract;

import com.learnwithme.buildapps.bakingapp.data.idlingresource.RecipeIdlingResource;
import com.learnwithme.buildapps.bakingapp.data.model.Recipe;
import com.learnwithme.buildapps.bakingapp.ui.base.BasePresenter;
import com.learnwithme.buildapps.bakingapp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nithin on 27/06/2017.
 */

public interface RecipeListContract {
    interface View extends BaseView<Presenter> {
        void showRecipeList(List<Recipe> recipeList);

        void loadProgressBar(boolean show);

        void displayCompletedMessage();

        void displayErrorMessage();

        void displayRecipeDetails(int recipeId);
    }

    interface Presenter extends BasePresenter {
        void loadRecipesFromRepo(boolean isSyncForced, RecipeIdlingResource resource);

        void loadRecipeDetails(int recipeId);
    }
}