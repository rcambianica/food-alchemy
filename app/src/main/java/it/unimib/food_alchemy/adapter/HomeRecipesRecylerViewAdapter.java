package it.unimib.food_alchemy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.model.HomeRecipeModel;
import it.unimib.food_alchemy.ui.main.HomeFragment;

public class HomeRecipesRecylerViewAdapter extends RecyclerView.Adapter<HomeRecipesRecylerViewAdapter.MyViewHolder> {
    Context context;

    ArrayList<HomeRecipeModel> homeRecipeModels;

    public interface OnItemClickListener{
        void onItemCLick(int position);
    }

    private final OnItemClickListener onItemClickListener;

    public HomeRecipesRecylerViewAdapter(Context context, ArrayList<HomeRecipeModel> homeRecipeModels, OnItemClickListener onItemClickListener){
        this.context = context;
        this.homeRecipeModels = homeRecipeModels;
        this.onItemClickListener = onItemClickListener;
    }



    @NonNull
    @Override
    public HomeRecipesRecylerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipes_home, parent, false);
        return new HomeRecipesRecylerViewAdapter.MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecipesRecylerViewAdapter.MyViewHolder holder, int position) {

        holder.title.setText(homeRecipeModels.get(position).getRecipeTitle());
        holder.imageView.setImageResource(homeRecipeModels.get(position).getRecipeImage());
    }

    @Override
    public int getItemCount() {
        if(homeRecipeModels != null) {
            return homeRecipeModels.size();
        }
        return 0;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, description;


        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recipe_image);
            title = itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            onItemClickListener.onItemCLick(position);
                        }
                    }
                }
            });
        }
    }
}
