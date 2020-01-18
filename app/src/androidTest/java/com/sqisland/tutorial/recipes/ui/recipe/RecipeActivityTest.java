package com.sqisland.tutorial.recipes.ui.recipe;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import com.sqisland.tutorial.recipes.R;
import com.sqisland.tutorial.recipes.test.RecipeRobot;

import org.junit.Rule;
import org.junit.Test;


public class RecipeActivityTest {

    private static final String CARROTS_ID = "creamed_carrots" ;
    @Rule
    public ActivityTestRule<RecipeActivity> activityTestRule
            = new ActivityTestRule<>(
                    RecipeActivity.class, true, false);

    @Test
    public void recipeNotFound() {

        //Replace activityTestRule.launchActivity(null);  //launch activity without an intent with
        //the Robot, which takes care of the launch function
        new RecipeRobot()
                .launch(activityTestRule)
                .noTitle()
                .description(R.string.recipe_not_found);

        //The RecipeRobot contains all of the logic for how the test is done, and this lets us abstract
        //that logic away from 'what' the test is doing, so that we can write functions that are more readable.
    }

    @Test
    public void clickToFavorite() {
        new RecipeRobot()
                .launch(activityTestRule, CARROTS_ID)
                .title(R.string.title_creamed_carrots)
                .notIsFavorite()
                .performClickOnView(R.id.title_textview)
                .isFavorite();

        //Will need to use **dependency injection with test doubles** to restore app to known state
        //before running this test. Store selection value in an in-memory HashMap instead of
        //shared preferences. The hash map will always be empty when we restart the test regardless
        //of the previous test. For this we use dependency injection. So during testing, we need to
        //swap out SharedPreferences for the in-memory HashMap.
    }

    @Test
    public void alreadyFavorite() {
        //Set the state of the app
        new RecipeRobot()
                .setFavorite(CARROTS_ID)
                .launch(activityTestRule, CARROTS_ID)
                .isFavorite();
    }

    private void launchRecipe(String id) {
        Intent intent = new Intent();
        intent.putExtra(RecipeActivity.KEY_ID, id);
        activityTestRule.launchActivity(intent);
    }


}