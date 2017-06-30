package com.learnwithme.buildapps.bakingapp.ui.recipedetail.contract;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.data.model.Step;

import java.util.List;
import java.util.Locale;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nithin on 29/06/2017.
 */

public class RecipeDetailsAdapter
        extends RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailsViewHolder> {
    public final OnRecipeClickListener mRecipeClickListener;
    private List<Step> mStepList;
    public int currentPos;

    public RecipeDetailsAdapter(List<Step> steps, OnRecipeClickListener listener) {
        setupStepsList(steps);
        mRecipeClickListener = listener;
    }

    private void setupStepsList(@NonNull List<Step> steps) {
        mStepList = steps;
    }

    @Override
    public RecipeDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe_instructions, parent, false);
        return new RecipeDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeDetailsViewHolder holder, int position) {
        holder.bindTo(mStepList.get(position), position);
    }

    public void refreshStepsList(List<Step> steps) {
        setupStepsList(steps);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    @Override
    public long getItemId(int position) {
        return mStepList.get(position).id();
    }

    public interface OnRecipeClickListener {
        void stepClicked(int stepId);
    }

    class RecipeDetailsViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        @BindView(R.id.list_step_layout)
        RelativeLayout mStepListLayout;
        @BindView(R.id.list_step_description)
        TextView mStepDescription;
        @BindView(R.id.list_step_video_icon)
        ImageView mStepVideoIcon;
        @BindColor(R.color.colorMaterialGrey500)
        int mMaterialItemBackground;
        @BindColor(R.color.colorPrimaryDark)
        int mCurrentItemBackground;

        private int currentId;

        @Override
        public void onClick(View v) {
            currentPos = currentId;
            mRecipeClickListener.stepClicked(currentId);
            notifyDataSetChanged();
        }

        public RecipeDetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindTo(@NonNull Step step, int bindPosition) {
            currentId = step.id();
            String description = step.shortDescription();
            String video = step.videoURL();

            mStepDescription.setText(String.format(Locale.US, "%d. %s", currentId, description));

            if(video.isEmpty()) {
                mStepVideoIcon.setVisibility(View.INVISIBLE);
            } else {
                mStepVideoIcon.setVisibility(View.VISIBLE);
            }

            if(currentPos == bindPosition) {
                mStepListLayout.setBackgroundColor(mCurrentItemBackground);
            } else {
                mStepListLayout.setBackgroundColor(mMaterialItemBackground);
            }
        }
    }
}