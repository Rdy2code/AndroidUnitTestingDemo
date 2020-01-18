package com.sqisland.tutorial.recipes.data.local;

import android.content.Context;
import android.content.res.AssetManager;

import com.sqisland.tutorial.recipes.data.model.Recipe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Derive a list of recipes by reading from files in an asset directory
 */
public class RecipeStore {

    public final List<Recipe> recipes = new ArrayList<>();

    //Look up table
    private final Map<String, Recipe> map = new HashMap<>();

    //Because the constructor requires a context, use an Instrumentation Unit Test--Espresso
    public RecipeStore (Context context, String directory) {
        List<InputStream> streams = getAssetStreams(context.getAssets(), directory);

        for (InputStream stream : streams) {
            Recipe recipe = Recipe.readFromStream(stream);
            if (recipe != null) {
                recipes.add(recipe);
                map.put(recipe.id, recipe);
            }
        }
    }

    //Find all files that contain recipes
    private static List<InputStream> getAssetStreams(AssetManager manager, String directory) {
        String[] filenames = getfilenames(manager, directory);
        List<InputStream> streams = new ArrayList<>();
        for (String filename : filenames) {
            File file = new File(directory, filename);
            try {
                InputStream stream = manager.open(file.getPath());
                if (stream != null) {
                    streams.add(stream);
                }
            } catch (IOException e) {
            }

        }
        return streams;
    }

    private static String[] getfilenames (AssetManager manager, String directory) {
        if (directory == null) {
            return new String[0];   //return an empty array
        }
        try {
            return manager.list(directory);     //return list of strings that contains all files
        } catch (IOException e) {
            return new String[0];
        }
    }

    public Recipe getRecipe(String id) {
        return map.get(id);
    }
}
