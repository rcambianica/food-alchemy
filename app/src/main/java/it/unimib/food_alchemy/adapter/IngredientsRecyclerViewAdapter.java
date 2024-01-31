package it.unimib.food_alchemy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.model.Ingredient;

/**
 * Custom adapter that extends RecyclerView.Adapter to show an ArrayList of Ingredients
 * with a RecyclerView.
 */
public class IngredientsRecyclerViewAdapter extends
        RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsViewHolder> {

    /**
     * Interface to associate a click listener with
     * a RecyclerView item.
     */
    public interface OnItemClickListener {
        void onIngredientsItemClick(Ingredient ingredient);
    }

    private final List<Ingredient> ingredientsList;
    private final OnItemClickListener onItemClickListener;

    public IngredientsRecyclerViewAdapter(List<Ingredient>  ingredientsList, OnItemClickListener onItemClickListener)    {
        this.ingredientsList = ingredientsList;
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ingredient_item, parent,false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.bind(ingredientsList.get(position));
    }

    @Override
    public int getItemCount() {
        if (ingredientsList != null)    {
            return ingredientsList.size();
        }
        return 0;
    }

    /**
     * Custom ViewHolder to bind data to the RecyclerView items.
     */
    public class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textViewTitle;

        public IngredientsViewHolder(@NonNull View itemView)    {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.ingredient_title);

            itemView.setOnClickListener(this);
        }

        public void bind (Ingredient ingredient)    {
            textViewTitle.setText(ingredient.getTitle());
        }
        @Override
        public void onClick(View v) {
            onItemClickListener.onIngredientsItemClick(ingredientsList.get(getAdapterPosition()));

        }
    }
}
