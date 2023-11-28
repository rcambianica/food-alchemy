package it.unimib.food_alchemy.ui.main;

import static android.app.ProgressDialog.show;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FridgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FridgeFragment extends Fragment {

    private FragmentFridgeBinding fragmentFridgeBinding;
    private List<Ingredient> ingredientList;
    private RecyclerView recyclerViewIngredients;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFridgeBinding = FragmentFridgeBinding.inflate(inflater,container,false);
        return fragmentFridgeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewIngredients = fragmentFridgeBinding.recyclerviewIngredients;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerViewIngredients.setLayoutManager(layoutManager);



        ingredientList = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            ingredientList.add(new Ingredient("cheese","28/11/2023"));
        }


        ingredientsRecyclerViewAdapter =
                new IngredientsRecyclerViewAdapter(ingredientList, new IngredientsRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onIngredientsItemClick(Ingredient ingredient) {
                        Snackbar.make(view, ingredient.getTitle(), Snackbar.LENGTH_SHORT).show();
                    }
                });

        recyclerViewIngredients.setAdapter(ingredientsRecyclerViewAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentFridgeBinding = null;
    }
}