package com.sqisland.tutorial.recipes.injection;

import android.app.Application;

import com.sqisland.tutorial.recipes.data.local.Favorites;
import com.sqisland.tutorial.recipes.data.local.SharedPreferencesFavorites;

/**
 * This class provides a means for latently initializing the SharedPreferencesFavorites class. That way
 * it is only initialized if someone uses it.
 */

public class RecipeApplication extends Application {

    //Initialize the interface
    private Favorites favorites = null;

    public Favorites getFavorites() {
        if (favorites == null) {
            favorites = new SharedPreferencesFavorites(this);
        }
        return favorites;
    }
}
