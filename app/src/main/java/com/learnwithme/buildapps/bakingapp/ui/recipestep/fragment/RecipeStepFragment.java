package com.learnwithme.buildapps.bakingapp.ui.recipestep.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.data.model.Step;
import com.learnwithme.buildapps.bakingapp.ui.recipestep.activity.RecipeStepActivity;
import com.learnwithme.buildapps.bakingapp.ui.recipestep.contract.RecipeStepContract;
import com.learnwithme.buildapps.bakingapp.ui.recipestep.contract.RecipeStepPagerAdapter;

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

public class RecipeStepFragment extends Fragment implements RecipeStepContract.View {
    @BindView(R.id.recipe_step_viewpager)
    ViewPager mRecipeStepViewPager;
    @BindView(R.id.recipe_step_tablayout)
    TabLayout mRecipeStepTabLayout;

    @BindString(R.string.loading_data_error)
    String mErrorMessage;
    @BindBool(R.bool.two_pane_screen)
    boolean isTwoPane;

    Unbinder unbinder;
    int mStepId;
    private RecipeStepContract.Presenter mRecipeStepPresenter;
    private RecipeStepPagerAdapter mViewPagerAdapter;

    public RecipeStepFragment() { }

    public static RecipeStepFragment newInstance(int stepId) {
        Bundle arguments = new Bundle();
        arguments.putInt(RecipeStepActivity.EXTRA_STEP_ID, stepId);
        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            mStepId = getArguments().getInt(RecipeStepActivity.EXTRA_STEP_ID);
        } else {
            mStepId = savedInstanceState.getInt(RecipeStepActivity.EXTRA_STEP_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        unbinder = ButterKnife.bind(this, view);

        mViewPagerAdapter = new RecipeStepPagerAdapter(getFragmentManager(), new ArrayList<>(0), getContext());
        mRecipeStepViewPager.setAdapter(mViewPagerAdapter);
        setUpViewPagerListener();
        mRecipeStepTabLayout.setupWithViewPager(mRecipeStepViewPager);

        // Hiding tabs on landscape and not on two-pane mode
        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            mRecipeStepTabLayout.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(RecipeStepActivity.EXTRA_STEP_ID, mStepId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecipeStepPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRecipeStepPresenter.unsubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(RecipeStepContract.Presenter presenter) {
        mRecipeStepPresenter = presenter;
    }

    @Override
    public void showStepsInViewPager(List<Step> steps) {
        mViewPagerAdapter.setSteps(steps);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getContext(), mErrorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToCurrentStep() {
        mRecipeStepViewPager.setCurrentItem(mStepId);
    }

    private void setUpViewPagerListener() {
        mRecipeStepViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                mStepId = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }
}