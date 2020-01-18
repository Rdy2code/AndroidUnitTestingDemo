package com.sqisland.tutorial.recipes.test;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.test.espresso.ViewAction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

public abstract class ScreenRobot<T extends ScreenRobot> {

    public T checkIsHidden (@IdRes int... viewIds){
        for (int viewId : viewIds) {
            onView(withId(viewId))
                    .check(matches(not(isDisplayed())));
        }

        //This lets us return any specific type of robot, such as RecipeRobot.
        return (T) this;
    }

    public T checkViewHasText (@IdRes int viewId, @StringRes int stringId) {
        onView(withId(viewId)).check(matches(withText(stringId)));
        return (T) this;
    }

    public T checkIsSelected (@IdRes int... viewIds) {
        for (int viewId : viewIds) {
            onView(withId(viewId))
                    .check(matches(isSelected()));
        }
        return (T) this;
    }

    public T checkNotIsSelected (@IdRes int... viewIds) {
        for (int viewId : viewIds) {
            onView(withId(viewId))
                    .check(matches(not(isSelected())));
        }
        return (T) this;
    }

    public T clickOnView (@IdRes int... viewIds) {
        for (int viewId : viewIds) {
            onView(withId(viewId)).perform(click());
        }
        return (T) this;
    }
}
