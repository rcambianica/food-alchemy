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
    public HomeRecipesRecylerViewAdapter(Context context, ArrayList<HomeRecipeModel> homeRecipeModels){
        this.context = context;
        this.homeRecipeModels = homeRecipeModels;
    }


    @NonNull
    @Override
    public HomeRecipesRecylerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipes_home, parent, false);
        return new HomeRecipesRecylerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecipesRecylerViewAdapter.MyViewHolder holder, int position) {

        holder.title.setText(homeRecipeModels.get(position).getRecipeTitle());
        holder.description.setText(homeRecipeModels.get(position).getRecipeDescription());
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


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recipe_image);
            title = itemView.findViewById(R.id.recipe_name);
            description = itemView.findViewById(R.id.recipe_description);
        }
    }
}
