package it.unimib.food_alchemy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.model.Recipe;

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.Holder>{

    private final List<Recipe> recipesList;
    private final OnItemClickListener onItemClickListener;


    public interface OnItemClickListener{
        void onFavClick(Recipe recipe);
        void onDeleteButtonPressed(int position);

    }


    public FavoritesRecyclerViewAdapter(List<Recipe> recipesList, OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
        this.recipesList = recipesList;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_list_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(recipesList.get(position));
    }

    @Override
    public int getItemCount() {
       if(recipesList !=null)
           return recipesList.size();
       else
           return 0;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView textTitle;
        private final TextView textDescription;
        private final Button buttonDelete;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textTitle=itemView.findViewById(R.id.text_title);
            textDescription=itemView.findViewById(R.id.text_description);
            buttonDelete=itemView.findViewById(R.id.delete);
            itemView.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
        }

        public void bind (Recipe recipe){
            textTitle.setText(recipe.getTitle());
        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.delete){
                recipesList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                onItemClickListener.onDeleteButtonPressed(getAdapterPosition());
            } else {
                onItemClickListener.onFavClick(recipesList.get(getAdapterPosition()));
                }

        }
    }
}
