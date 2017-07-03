package com.learnwithme.buildapps.bakingapp.ui.recipestep.contract;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.data.model.Step;
import com.learnwithme.buildapps.bakingapp.ui.recipestep.fragment.RecipeStepPageFragment;

import java.util.List;
import java.util.Locale;

/**
 * Created by Nithin on 29/06/2017.
 */

public class RecipeStepPagerAdapter extends FragmentPagerAdapter {
    private final String tabTitle;
    private List<Step> steps;

    public RecipeStepPagerAdapter(FragmentManager fm, List<Step> steps, Context context) {
        super(fm);
        setSteps(steps);
        tabTitle = context.getResources().getString(R.string.recipe_step_tab_label);
    }

    public void setSteps(@NonNull List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return RecipeStepPageFragment.newInstance(
                steps.get(position).description(),
                steps.get(position).videoURL(),
                steps.get(position).thumbnailURL()
        );
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.format(Locale.US, tabTitle, position);
    }
}