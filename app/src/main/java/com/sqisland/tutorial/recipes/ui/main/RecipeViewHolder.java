package com.sqisland.tutorial.recipes.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sqisland.tutorial.recipes.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    public final TextView title;

    public RecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.list_item_title);
    }
}
