package com.sqisland.tutorial.recipes.ui.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sqisland.tutorial.recipes.R;
import com.sqisland.tutorial.recipes.data.local.Favorites;
import com.sqisland.tutorial.recipes.data.local.RecipeStore;
import com.sqisland.tutorial.recipes.data.local.SharedPreferencesFavorites;
import com.sqisland.tutorial.recipes.data.model.Recipe;
import com.sqisland.tutorial.recipes.injection.RecipeApplication;

public class RecipeActivity extends AppCompatActivity implements RecipeContract.View {

    public static final String KEY_ID = "id";
    private TextView titleView;
    private TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Step 1: Set up the UI
            //***No logic here so this code stays in activity***
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        titleView = (TextView) findViewById(R.id.title_textview);
        descriptionView = (TextView) findViewById(R.id.description);

        //Step 2: Load recipe from store
            //***Getting the intent is part of the activity and stays here***
        Intent intent = getIntent();
        String id = null;
        if (intent != null) {
            if (intent.hasExtra(KEY_ID)) {
                id = intent.getStringExtra(KEY_ID);
            }
        }
            //***Loading the recipe is java that can go inside the presenter, since Recipe is a pure
            //Java object
        RecipeStore store = new RecipeStore(this, "recipes");
        final RecipeApplication app = (RecipeApplication) getApplication();
        final Favorites favorites = app.getFavorites();
        final RecipePresenter presenter = new RecipePresenter(store, this, favorites);
        presenter.loadRecipe(id);

        //Step 3: If recipe is null, show error. This is done in the Presenter


        //Will pull SharedPreferences initialization out of the activity so that it is provided by the application instead
        //That way we can swap in an in-memory HashMap for the androidTest of favorites
        //We will subclass the Application class to override the provide method. We will also
        //use a custom test runner. Basically, we are going to share the selection methods between
        //two classes, one for testing the other for app operation.

        //  1. Create an interface for the get() and toggle() methods from SharedPreferences so that
        //we can use these methods in an in-memory version during tests and in SharedPreferences
        //during normal operation of the app. Call the interface **Favorites**.
        //  2. Have SharedPreferencesFavorites implement the Favorites interface.
        //  3. In the androidTest folder, create a new Java class called InMemoryFavorites and have it
        //implement the Favorites interface. Override the methods.
        //  4. Declare and initialize a HashMap. Implement the methods, get, put, and toggle to save
        //and get boolean values in and from the HashMap.
        //  5. Create a new Java class inside main directory called RecipeApplication that extends
        //Application. Give it the package name .injection.
        //  6. Declare and initialize the Favorites interface using a getter method. Register the
        //application class in the androidmanifest.
        //  7. Here, get an instance of the custom application class.
        //  8. Then call getFavorites to get an instance of the SharedPreferencesFavorites class
        //  9. Create a new Java class inside androidTest/java called TestRecipeApplication that extends
        //RecipeApplication. Override the getFavorites function and replace the super with
        //code the returns an instance of InMemoryFavorites
        //  10. Create a custom test runner so that we can use it during testing. To do this:
        //      a. Create a new Java class inside androidTest/java called CustomTestRunner
        //      b. Extend from AndroidJUnitRunner and override newApplication()
        //      c. Update the build.gradle file inside the defaultConfig section

        //Replace final SharedPreferencesFavorites favorites = new SharedPreferencesFavorites(this);
        //with the following:

        //Step 4: If recipe is not null, show recipe. This is done in the Presenter

        //Step 5: When the title is clicked, toggle favorites
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.toggleFavorite();
            }
        });
    }

    @Override
    public void showRecipeNotFoundError() {
        titleView.setVisibility(View.GONE);
        descriptionView.setText(R.string.recipe_not_found);
    }

    @Override
    public void setTitle(String title) {
        titleView.setText(title);
    }

    @Override
    public void setDescription(String description) {
        descriptionView.setText(description);
    }

    @Override
    public void setFavorites(boolean favorite) {
        titleView.setSelected(favorite);
    }
}
