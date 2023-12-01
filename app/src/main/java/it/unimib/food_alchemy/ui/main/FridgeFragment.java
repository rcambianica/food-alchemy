package it.unimib.food_alchemy.ui.main;

import static android.app.ProgressDialog.show;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.IngredientsRecyclerViewAdapter;
import it.unimib.food_alchemy.databinding.FragmentFavoritesBinding;
import it.unimib.food_alchemy.databinding.FragmentFridgeBinding;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Result;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FridgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FridgeFragment extends Fragment {

    private FragmentFridgeBinding fragmentFridgeBinding;
    private List<Ingredient> ingredientList;

    //serve per recuperare un ingrediente eliminato per sbaglio con lo swipe a sinistra
    private Ingredient deletedIngredient = null;
    private IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter;
    public FridgeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FridgeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FridgeFragment newInstance() {
        return new FridgeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFridgeBinding = FragmentFridgeBinding.inflate(inflater,container,false);
        return fragmentFridgeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewIngredients = fragmentFridgeBinding.recyclerviewIngredients;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);




        ingredientList = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            ingredientList.add(new Ingredient("cheese "+i,"28/11/2023"));
        }


        ingredientsRecyclerViewAdapter =
                new IngredientsRecyclerViewAdapter(ingredientList, new IngredientsRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onIngredientsItemClick(Ingredient ingredient) {
                        Snackbar.make(view, ingredient.getTitle(), Snackbar.LENGTH_SHORT).show();
                    }
                });
        recyclerViewIngredients.setLayoutManager(layoutManager);
        recyclerViewIngredients.setAdapter(ingredientsRecyclerViewAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewIngredients);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            deletedIngredient = new Ingredient(ingredientList.get(pos).getTitle(), ingredientList.get(pos).getExpiryDate());
            ingredientList.remove(pos);
            ingredientsRecyclerViewAdapter.notifyItemRemoved(pos);
            Snackbar.make(requireView(),deletedIngredient.getTitle(),Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingredientList.add(pos,deletedIngredient);
                    ingredientsRecyclerViewAdapter.notifyItemInserted(pos);
                }
            }).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                    .addActionIcon(R.drawable.baseline_delete_24)
                    .addCornerRadius(1,5)
                    .addPadding(1,6,6,6)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void setExpiryColor()   {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentFridgeBinding = null;
    }
}