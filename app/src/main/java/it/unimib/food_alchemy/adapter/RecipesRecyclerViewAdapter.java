package it.unimib.food_alchemy.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.model.Recipe;

public class RecipesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPES_VIEW_TYPE = 0;
    private static final int LOADING_VIEW_TYPE = 1;



    /**
     * Interface to associate a click listener with
     * a RecyclerView item.
     */
    public interface OnItemClickListener {
        void onRecipesItemClick(Recipe recipe);
    }

    private final List<Recipe> recipeList;
    private final OnItemClickListener onItemClickListener;
    private final Application application;

    public RecipesRecyclerViewAdapter(Application application, List<Recipe> recipeList, OnItemClickListener onItemClickListener) {
        this.application = application;
        this.recipeList = recipeList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position)    {
        if(recipeList.get(position) == null)    {
            return LOADING_VIEW_TYPE;
        } else {
            return RECIPES_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        if(viewType == RECIPES_VIEW_TYPE)   {
            view = LayoutInflater.from(parent.getContext()).
                     inflate(R.layout.recipes_home, parent, false);
            return new RecipesViewHolder(view);
        }   else {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recipes_loading_item, parent, false);
            return new LoadingRecipesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  RecipesViewHolder)    {
            ((RecipesViewHolder) holder).bind(recipeList.get(position));
        } else if (holder instanceof LoadingRecipesViewHolder)  {
            ((LoadingRecipesViewHolder) holder).activate();
        }
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
                    .centerInside()
                    .placeholder(R.drawable.progress_activity)
                    .centerCrop()
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
    public static class LoadingRecipesViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar progressBar;

        LoadingRecipesViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressbar_loading_recipes);
        }

        public void activate() {
            progressBar.setIndeterminate(true);
        }
    }
}
