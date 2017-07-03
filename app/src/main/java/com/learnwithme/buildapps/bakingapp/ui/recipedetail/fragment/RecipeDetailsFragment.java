package com.learnwithme.buildapps.bakingapp.ui.recipedetail.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.data.model.Ingredients;
import com.learnwithme.buildapps.bakingapp.data.model.Step;
import com.learnwithme.buildapps.bakingapp.ui.recipedetail.activity.RecipeDetailsActivity;
import com.learnwithme.buildapps.bakingapp.ui.recipedetail.contract.RecipeDetailsAdapter;
import com.learnwithme.buildapps.bakingapp.ui.recipedetail.contract.RecipeDetailsContract;
import com.learnwithme.buildapps.bakingapp.ui.recipestep.activity.RecipeStepActivity;
import com.learnwithme.buildapps.bakingapp.utils.string.StringUtils;
import com.learnwithme.buildapps.bakingapp.utils.textviewutils.TextViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Nithin on 29/06/2017.
 */

public class RecipeDetailsFragment extends Fragment
        implements RecipeDetailsContract.View {

    @BindView(R.id.recipe_details_ingredients)
    TextView mRecipeDetailsIngredients;
    @BindView(R.id.recipe_details_steps)
    RecyclerView mRecipeDetailsStepsRecyclerView;

    @BindBool(R.bool.two_pane_screen)
    boolean twoPaneScreenMode;
    @BindString(R.string.loading_data_error)
    String mErrorMessage;
    @BindString(R.string.recipe_details_ingredients_header)
    String mIngredientsListHeader;

    Unbinder unbinder;

    private RecipeDetailsContract.Presenter mRecipeDetailsPresenter;
    private RecipeDetailsAdapter mRecipeDetailsAdapter;
    private int mRecipeId;

    public RecipeDetailsFragment() {
    }

    public static RecipeDetailsFragment newInstance(int recipeId) {
        Bundle arguments = new Bundle();
        arguments.putInt(RecipeDetailsActivity.EXTRA_RECIPE_ID, recipeId);
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipeId = getArguments().getInt(RecipeDetailsActivity.EXTRA_RECIPE_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        mRecipeDetailsAdapter = new RecipeDetailsAdapter(new ArrayList<>(0),
                stepId -> mRecipeDetailsPresenter.loadStepDetails(stepId));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecipeDetailsStepsRecyclerView.setLayoutManager(layoutManager);
        mRecipeDetailsStepsRecyclerView.setHasFixedSize(true);
        mRecipeDetailsStepsRecyclerView.setAdapter(mRecipeDetailsAdapter);

        mRecipeDetailsStepsRecyclerView
                .addItemDecoration(new DividerItemDecoration(getContext(),
                        DividerItemDecoration.VERTICAL));

        if (twoPaneScreenMode) {
            mRecipeDetailsPresenter.loadStepData(0);
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mRecipeDetailsPresenter.unsubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecipeDetailsPresenter.subscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(RecipeDetailsContract.Presenter presenter) {
        this.mRecipeDetailsPresenter = presenter;
    }

    @Override
    public void displayIngredientsList(List<Ingredients> ingredients) {
        StringBuilder sb = new StringBuilder();
        sb.append(mIngredientsListHeader);

        for(Ingredients ingredient : ingredients) {
            String name = ingredient.ingredient();
            float quantity = ingredient.quantity();
            String measure = ingredient.measure();

            sb.append("\n");
            sb.append(StringUtils.formatIngredientString(getContext(), name, quantity, measure));
        }
        TextViewUtils.setTextWithSpan(mRecipeDetailsIngredients, sb.toString(),
                mIngredientsListHeader, new StyleSpan(Typeface.BOLD));
    }

    @Override
    public void displayStepsList(List<Step> steps) {
        mRecipeDetailsAdapter.refreshStepsList(steps);
    }

    @Override
    public void displayErrorMessage() {
        // User must not see this
        Toast.makeText(getContext(), mErrorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayRecipeInActivityTitle(String recipeName) {
        getActivity().setTitle(recipeName);
    }

    @Override
    public void displayStepDetails(int stepId) {
        if(twoPaneScreenMode) {
            mRecipeDetailsPresenter.loadStepData(stepId);
        } else {
            startActivity(RecipeStepActivity.prepareIntent(getContext(), mRecipeId, stepId));
        }
    }

    @Override
    public void refreshStepContainerFragment(String desc, String videoUrl, String imageUrl) {

    }
}