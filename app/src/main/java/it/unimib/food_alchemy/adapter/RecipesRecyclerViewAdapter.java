package it.unimib.food_alchemy.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Recipe;

public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecipesRecyclerViewAdapter.RecipesViewHolder> {

    /**
     * Interface to associate a click listener with
     * a RecyclerView item.
     */
    public interface OnItemClickListener {
        void onRecipesItemClick(Recipe recipe);

        void onStarButtonPressed(int position);
    }

    private final List<Recipe> recipeList;
    private final OnItemClickListener onItemClickListener;
    private final Application application;

    public RecipesRecyclerViewAdapter(Application application, List<Recipe> recipeList, OnItemClickListener onItemClickListener) {
        this.application = application;
        this.recipeList = recipeList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recipes_home, parent, false);

        return new RecipesRecyclerViewAdapter.RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesRecyclerViewAdapter.RecipesViewHolder holder, int position) {
        holder.bind(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        if (recipeList != null) {
            return recipeList.size();
        }
        return 0;
    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textViewTitle;
        private final ImageView imageView;

        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.recipe_name);
            imageView = itemView.findViewById(R.id.recipe_image);

            itemView.setOnClickListener(this);
        }

        public void bind(Recipe recipe) {
            textViewTitle.setText(recipe.getTitle());
            Glide.with(application)
                    .load(recipe.getImageUrl())
                    .placeholder(R.drawable.baseline_access_time_24)
                    .into(imageView);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onRecipesItemClick(recipeList.get(position));
                }
            }
        }
    }
}
