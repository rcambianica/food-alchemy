package it.unimib.food_alchemy.adapter;

import static it.unimib.food_alchemy.R.color.md_theme_light_error;

import android.app.Application;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        void onSelectedButtonPressed(int position);
    }

    private final List<Ingredient> ingredientsList;
    private final OnItemClickListener onItemClickListener;
    //private final Application application;

    public IngredientsRecyclerViewAdapter(List<Ingredient>  ingredientsList/*, Application application,*/, OnItemClickListener onItemClickListener)    {
        this.ingredientsList = ingredientsList;
        //this.application = application;
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.ingredient_list_item, parent,false);

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
        private final TextView textViewDate;
        private final ImageView check;
        private final View flag_view;

        public IngredientsViewHolder(@NonNull View itemView)    {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title_textview);
            textViewDate = itemView.findViewById(R.id.date_textview);
            check = itemView.findViewById(R.id.check_imageview);
            flag_view = itemView.findViewById(R.id.flag_view);

            itemView.setOnClickListener(this);
        }

        public void bind (Ingredient ingredient)    {
            textViewTitle.setText(ingredient.getTitle());
            textViewDate.setText(formatDate(ingredient.getExpiryDate()));
            /**
             * colora il background dell'elemento se è scaduto o in scadenza
             * **/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int expiryDays = Integer.parseInt(ingredient.getExpiryDate()) - Integer.parseInt(getCurrentDate());
                if(expiryDays <= 0)  {
                    flag_view.setVisibility(View.VISIBLE);
                    flag_view.setBackgroundColor(Color.parseColor("#93000A"));
                }
                else if(expiryDays>=1 && expiryDays < 4)    {
                    flag_view.setVisibility(View.VISIBLE);
                    flag_view.setBackgroundColor(Color.parseColor("#FFD700"));
                }
            }
            //setImageViewSelectedFridgeItem(ingredientsList.get(getAdapterPosition()).isSelected());
        }
        @Override
        public void onClick(View v) {
            /*
            if(v.getId() == R.id.check_imageview)   {
                setImageViewSelectedFridgeItem(ingredientsList.get(getAdapterPosition()).isSelected());
                onItemClickListener.onSelectedButtonPressed(getAdapterPosition());
            } else {
                onItemClickListener.onIngredientsItemClick(ingredientsList.get(getAdapterPosition()));
            }*/
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        private String getCurrentDate() {
            // Ottieni la data corrente
            LocalDate currentDate = LocalDate.now();
            // Restituisci la data formattata
            return currentDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        }

        private String formatDate(String date) {
            String formattedString = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter initialFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDate localDate = LocalDate.parse(date, initialFormatter);
                DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                formattedString = localDate.format(targetFormatter);
            }
            return formattedString;
        }

        /*private void setImageViewSelectedFridgeItem(boolean isSelected) {
            if (isSelected) {
                Log.d("TAG", "ISSELECTED");
                check.setImageDrawable(
                        AppCompatResources.getDrawable(application,
                                R.drawable.baseline_check_circle_24));
            } else {
                Log.d("TAG", "ISNOTSELECTED");
                check.setImageDrawable(
                        AppCompatResources.getDrawable(application,
                                R.drawable.baseline_circle_24));
            }
        }*/
    }
}
