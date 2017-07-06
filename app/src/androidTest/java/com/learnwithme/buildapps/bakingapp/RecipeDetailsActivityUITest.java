package com.learnwithme.buildapps.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.learnwithme.buildapps.bakingapp.ui.recipedetail.activity.RecipeDetailsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by Nithin on 03/07/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeDetailsActivityUITest {
    private static final int STEP_WITH_VIDEO = 0;
    private static final int STEP_WITHOUT_VIDEO = 1;

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> activityTestRule =
            new ActivityTestRule<>(RecipeDetailsActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registeringIdlingResource() {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void clickOnRecyclerViewItem_opensRecipeStepActivity() {

        onView(withId(R.id.recipe_details_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.recipe_step_viewpager))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnStepWithVideo_showsVideoPlayerView() {

        onView(withId(R.id.recipe_details_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(STEP_WITH_VIDEO, click()));

        onView(
                allOf(
                        withId(R.id.recipe_step_video),
                        withParent(withParent(withId(R.id.recipe_step_viewpager))),
                        isDisplayed()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnStepWithoutVideo_hidesVideoPlayerView() {

        onView(withId(R.id.recipe_details_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(STEP_WITHOUT_VIDEO, click()));

        onView(
                allOf(
                        withId(R.id.recipe_step_video),
                        withParent(withParent(withId(R.id.recipe_step_viewpager))),
                        isDisplayed()))
                .check(doesNotExist());
    }

    @After
    public void unregisteringIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}