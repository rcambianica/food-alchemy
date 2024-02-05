package it.unimib.food_alchemy.ui.main;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.FridgeItemsRecyclerViewAdapter;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.databinding.FragmentFridgeBinding;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.util.Constants;
import it.unimib.food_alchemy.util.ErrorMessagesUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FridgeFragment extends Fragment {
    private static final String TAG = FridgeFragment.class.getSimpleName();
    private FragmentFridgeBinding fragmentFridgeBinding;
    private List<Ingredient> ingredientList;

    // Used to undo a deleted ingredient
    private Ingredient deletedIngredient = null;
    private FridgeItemsRecyclerViewAdapter fridgeItemsRecyclerViewAdapter;
    private RecipeViewModel recipeViewModel;
    private SharedPreferencesUtil sharedPreferencesUtil;
    public FridgeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FridgeFragment.
     */
    public static FridgeFragment newInstance() {
        return new FridgeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredientList = new ArrayList<>();
        IRecipesRepository recipesRepository =
                ServiceLocator.getInstance().getRecipesRepository(requireActivity().getApplication());
        if(recipesRepository != null)   {
            recipeViewModel = new ViewModelProvider(requireActivity(),
                    new RecipeViewModelFactory(recipesRepository)).get(RecipeViewModel.class);
        }   else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());
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

        // Initialise the RecyclerView for the fridge items
        RecyclerView recyclerViewIngredients = fragmentFridgeBinding.recyclerviewFridgeItems;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);

        // Set up the adapter
        fridgeItemsRecyclerViewAdapter =
                new FridgeItemsRecyclerViewAdapter(ingredientList, new FridgeItemsRecyclerViewAdapter.OnItemClickListener() {

                    // Click listener to select the fridge item for a future search
                    @Override
                    public void onIngredientsItemClick(Ingredient ingredient) {
                        ingredient.setSelected(!ingredient.isSelected());
                        recipeViewModel.updateFridgeItem(ingredient);
                    }
                });
        recyclerViewIngredients.setLayoutManager(layoutManager);
        recyclerViewIngredients.setAdapter(fridgeItemsRecyclerViewAdapter);

        // Initialize the ItemTouchHelper for handling swipe gestures
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewIngredients);

        // Used to check if it's the first loading of fridge items
        boolean isFirstLoading = sharedPreferencesUtil.readBooleanData(Constants.SHARED_PREFERENCES_FILE_NAME,
                Constants.SHARED_PREFERENCES_FRIDGE_ITEMS_FIRST_LOADING);


        // Observe the LiveData for fridge items
        recipeViewModel.getFridgeItems(isFirstLoading).observe(getViewLifecycleOwner(),
                result -> {
                        if(result != null){
                                if(result.isSuccess())  {
                                    ingredientList.clear();
                                    ingredientList.addAll(((Result.IngredientsResponseSuccess)result).getData().getIngredientsList());
                                    Collections.sort(ingredientList, new Comparator<Ingredient>() {
                                        @Override
                                        public int compare(Ingredient ingrediente1, Ingredient ingrediente2) {
                                            return ingrediente1.getExpiryDate().compareTo(ingrediente2.getExpiryDate());
                                        }
                                    });
                                    fridgeItemsRecyclerViewAdapter.notifyDataSetChanged();
                                    if (isFirstLoading) {
                                        sharedPreferencesUtil.writeBooleanData(Constants.SHARED_PREFERENCES_FILE_NAME,
                                                Constants.SHARED_PREFERENCES_FRIDGE_ITEMS_FIRST_LOADING, false);
                                    }
                                } else {
                                    ErrorMessagesUtil errorMessagesUtil =
                                            new ErrorMessagesUtil(requireActivity().getApplication());
                                    Snackbar.make(view, errorMessagesUtil.
                                                    getErrorMessage(((Result.Error)result).getMessage()),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                        }else{
                            fragmentFridgeBinding.noFridgeItem.setVisibility(View.VISIBLE);
                        }
                });

        // Navigation to the AddIngredientFragment by clicking the addIngredientButton
        fragmentFridgeBinding.addIngredientButton.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_fridgeFragment_to_addIngredientFragment));
    }

    // Callback for handling swipe gestures on RecyclerView items
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        // Deleting the item from the fridge by swiping left, and show a Snackbar to undo the elimination
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getBindingAdapterPosition();
            recipeViewModel.deleteFridgeItem(ingredientList.get(pos));
            deletedIngredient = new Ingredient(ingredientList.get(pos).getId(),ingredientList.get(pos).getTitle(), ingredientList.get(pos).getExpiryDate(),false);
            Snackbar.make(requireView(),deletedIngredient.getTitle(),Snackbar.LENGTH_LONG).setAction("Undo", view -> {
                ingredientList.add(pos,deletedIngredient);
                recipeViewModel.insertFridgeItem(deletedIngredient);
            }).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(requireContext(), R.color.md_theme_light_error))
                    .addActionIcon(R.drawable.baseline_delete_24)
                    .addCornerRadius(1,5)
                    .addPadding(1,6,6,6)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onDestroy(){
        super.onDestroy();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentFridgeBinding.noFridgeItem.setVisibility(View.GONE);
        fragmentFridgeBinding = null;
    }
}