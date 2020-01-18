package com.sqisland.tutorial.recipes.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sqisland.tutorial.recipes.R;
import com.sqisland.tutorial.recipes.data.local.RecipeStore;
import com.sqisland.tutorial.recipes.data.model.Recipe;
import com.sqisland.tutorial.recipes.ui.recipe.RecipeActivity;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    private final RecipeStore store;

    public RecipeAdapter (RecipeStore store) {
        this.store = store;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {
      final Recipe recipe = store.recipes.get(position);
      holder.title.setText(recipe.title);
      holder.title.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Context context = holder.title.getContext();
              Intent intent = new Intent (context, RecipeActivity.class);
              intent.putExtra(RecipeActivity.KEY_ID, recipe.id);
              context.startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return store.recipes.size();
    }
}
