package com.sqisland.tutorial.recipes.ui.recipe;

import com.sqisland.tutorial.recipes.data.local.Favorites;
import com.sqisland.tutorial.recipes.data.local.RecipeStore;
import com.sqisland.tutorial.recipes.data.model.Recipe;
import com.sqisland.tutorial.recipes.data.model.RecipeTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.InputStream;

import static org.junit.Assert.*;

public class RecipePresenterTest {

    private RecipeStore store;
    private Favorites favorites;
    private RecipeContract.View view;
    private RecipePresenter presenter;

    @Before
    public void setup() {

        //Create an object with same function signatures as RecipeStore, plus hooks to specify its
        //behavior when the functions are called. These are the so-called class 'stubs' that can
        //be used for testing purposes only, so that we do not need to pass actual implementations
        store = Mockito.mock(RecipeStore.class);

        //In Mockito, the stub returns false by default whenever the function signature returns a boolean
        favorites = Mockito.mock(Favorites.class);

        view = Mockito.mock(RecipeContract.View.class);

        //Here we use stubs to initialize the Presenter class. That way if we find a bug, we know it
        //is from the Presenter and not one of the other classes. We can specify the behavor of the
        //stubs exactly.
        presenter = new RecipePresenter(store, view, favorites);
    }

    @Test
    public void recipeNotFound() {
        //Step 1: arrange
        //Step 2: act
        //Step 3: assert
        //When the getRecipe() method is called, whatever parameter is passed to the method, return null.
        Mockito.when(store.getRecipe(Mockito.anyString())).thenReturn(null);
        presenter.loadRecipe("no_such_recipe");
        Mockito.verify(view, Mockito.times(1)).showRecipeNotFoundError();
    }

    @Test(expected = IllegalStateException.class)
    public void toggleWithoutLoad() {
        presenter.toggleFavorite();
    }

    @Test
    public void loadWaterAndFavorite () {
        //Load a recipe out of the file
        InputStream stream = RecipePresenterTest.class.getResourceAsStream("/recipes/water.txt");
        Recipe recipe = Recipe.readFromStream(stream);
        Mockito.when(store.getRecipe(Mockito.anyString())).thenReturn(recipe);
        Mockito.when(favorites.toggle(Mockito.anyString())).thenReturn(true);
        presenter.loadRecipe("anystring");
        presenter.toggleFavorite();

        //To verify the UI changes when toggleFavorites is called, use an ArgumentCaptor
        ArgumentCaptor<Boolean> captor = ArgumentCaptor.forClass(Boolean.class);
        //Verify that the setFavorites() function is called twice, then save the boolean values inside
        //the captor
        Mockito.verify(view, Mockito.times(2)).setFavorites(captor.capture());
        //Verify the values inside the captor. Get the first value in the list of saved values
        assertFalse(captor.getAllValues().get(0));
        assertTrue(captor.getAllValues().get(1));



    }



}