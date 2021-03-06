package com.learnwithme.buildapps.bakingapp.ui.recipelist.contract;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.data.model.Recipe;
import com.learnwithme.buildapps.bakingapp.ui.recipelist.activity.RecipeListActivity;

import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nithin on 27/06/2017.
 */

public class RecipeListAdapter extends
        RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    final OnRecipeListClickListener recipeListClickListener;
    private List<Recipe> recipeList;
    private Context context;

    public RecipeListAdapter(Context context, List<Recipe> recipes, OnRecipeListClickListener listener) {
        setRecipes(recipes);
        this.context = context;
        recipeListClickListener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe_list_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bindTo(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public long getItemId(int position) {
        return recipeList.get(position).id();
    }

    public void refreshRecipes(List<Recipe> recipes) {
        setRecipes(recipes);
        notifyDataSetChanged();
    }

    private void setRecipes(@NonNull List<Recipe> recipes) {
        recipeList = recipes;
    }

    public interface OnRecipeListClickListener {
        public void recipeClicked(int recipeId);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.list_recipe_name)
        TextView listRecipeName;
        @BindView(R.id.list_recipe_servings)
        TextView listRecipeServings;
        // Accessing image view through ButterKnife
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindString(R.string.recipe_list_servings_text)
        String recipeListServingsText;

        private int currentId;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bindTo(@NonNull Recipe recipe) {
            currentId = recipe.id();
            String name = recipe.name();
            int servings = recipe.servings();
            String image = recipe.image();

            // Setting data to the text views here
            listRecipeName.setText(name);
            // Giving a custom format to the servings
            listRecipeServings
                    .setText(String.format(Locale.US, recipeListServingsText, servings));
            // Setting the image from API else setting default image using Glide
            Glide.with(context)
                .load(image)
                .placeholder(R.drawable.brownies)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        }

        @Override
        public void onClick(View v) {
            recipeListClickListener.recipeClicked(currentId);
        }
    }
}