package com.sqisland.tutorial.recipes.data.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Implements Favorites so that we can create an in-memory version of the SharedPreferences for
 * instrumentation testing the selection of the favorites icon in RecipeActivity
 */
public class SharedPreferencesFavorites implements Favorites {

    private final SharedPreferences pref;

    public SharedPreferencesFavorites(Context context) {
       pref = context.getSharedPreferences("favorites.xml", Context.MODE_PRIVATE);
    }

    public boolean get(String id) {
        return pref.getBoolean(id, false);
    }

    public void put(String id, boolean favorite) {
        SharedPreferences.Editor editor = pref.edit();

        if (favorite) {
            editor.putBoolean(id, true);
        } else {
            editor.remove(id);
        }
        editor.apply();
    }

    @Override
    public boolean toggle(String id) {
        boolean favorite = get(id);
        put (id, !favorite);
        return !favorite;
    }
}
