package com.sqisland.tutorial.recipes.data.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Recipe {

    private static final String ID_PREFIX = "id=";
    private static final String TITLE_PREFIX = "title=";
    public final String id;
    public final String title;
    public final String description;

    /**
     * Constructor is declared private because we are using a static factory method to create
     * the Recipe object instance
     * @param id
     * @param title
     * @param description
     */
    private Recipe(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    //Factory method uses InputStream to read from the assets folder
    public static Recipe readFromStream (InputStream stream) {
        String id = null;
        String title = null;
        StringBuilder descBuilder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        //Read the text line by line
        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.startsWith(ID_PREFIX)) {
                    id = line.substring(ID_PREFIX.length());
                    continue;
                }
                if (line.startsWith(TITLE_PREFIX)) {
                    title = line.substring(TITLE_PREFIX.length());
                    continue;
                }
                //Since readLine() strips the trailing new line character, we need to add it back in
                //before reading the description. So first make sure we are reading the description line.
                if (descBuilder.length() > 0) {
                    descBuilder.append("\n");
                }

                //This gets updated for each iteration in the loop until it reaches the last line
                //in the text, which becomes the final and last update.
                descBuilder.append(line);
            }

        } catch (IOException e) {
            return null;
        }
        return new Recipe(id, title, descBuilder.toString());
    }
}
