package com.sqisland.tutorial.recipes.ui.recipe;

public interface RecipeContract {

    //Allows the presenter to talk to the view
    interface View {
        void showRecipeNotFoundError ();
        void setTitle(String title);
        void setDescription(String description);
        void setFavorites(boolean favorite);
    }

    //Allows the view to talk to the presenter
    interface Listener {

    }
}
