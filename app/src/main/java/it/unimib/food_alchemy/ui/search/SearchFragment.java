package it.unimib.food_alchemy.ui.search;

import static it.unimib.food_alchemy.util.Constants.DAIRY;
import static it.unimib.food_alchemy.util.Constants.EGG;
import static it.unimib.food_alchemy.util.Constants.GLUTEN_FREE;
import static it.unimib.food_alchemy.util.Constants.GRAIN;
import static it.unimib.food_alchemy.util.Constants.NO_DIET;
import static it.unimib.food_alchemy.util.Constants.PEANUT;
import static it.unimib.food_alchemy.util.Constants.SEA_FOOD;
import static it.unimib.food_alchemy.util.Constants.SESAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_DIET;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;
import static it.unimib.food_alchemy.util.Constants.SHELL_FISH;
import static it.unimib.food_alchemy.util.Constants.SOY;
import static it.unimib.food_alchemy.util.Constants.SULFITE;
import static it.unimib.food_alchemy.util.Constants.TREE_NUT;
import static it.unimib.food_alchemy.util.Constants.VEGAN;
import static it.unimib.food_alchemy.util.Constants.VEGETARIAN;
import static it.unimib.food_alchemy.util.Constants.WHEAT;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.IngredientsRecyclerViewAdapter;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.data.service.SpoonacularApiService;
import it.unimib.food_alchemy.databinding.FragmentSearchBinding;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.model.SearchParameters;
import it.unimib.food_alchemy.ui.main.MainActivity;
import it.unimib.food_alchemy.ui.main.RecipeViewModel;
import it.unimib.food_alchemy.ui.main.RecipeViewModelFactory;
import it.unimib.food_alchemy.util.ErrorMessagesUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private final static String TAG = SearchFragment.class.getSimpleName().toString();
    private FragmentSearchBinding fragmentSearchBinding;
    private RecipeViewModel recipeViewModel;
    private List<Ingredient> selectedFridgeItems;
    private List<Ingredient> autocompleteIngredients;
    private List<Ingredient> ingredientList;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IRecipesRepository recipesRepository =
                ServiceLocator.getInstance().getRecipesRepository(requireActivity().getApplication());

        if(recipesRepository != null)   {
            recipeViewModel = new ViewModelProvider(requireActivity(),
                    new RecipeViewModelFactory(recipesRepository)).get(RecipeViewModel.class);
        }   else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }
        sharedPreferencesUtil = new SharedPreferencesUtil(getActivity().getApplication());

        selectedFridgeItems = new ArrayList<>();
        autocompleteIngredients = new ArrayList<>();
        ingredientList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        return fragmentSearchBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);
        Set<String> intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_INTOLERANCES);

        // Initialize the RecyclerView
        RecyclerView recyclerViewIngredients = fragmentSearchBinding.recyclerviewIngredientsSearch;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);

        // Set up the adapter for the autocomplete
        ingredientsRecyclerViewAdapter =
                new IngredientsRecyclerViewAdapter(autocompleteIngredients, new IngredientsRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onIngredientsItemClick(Ingredient ingredient) {
                        //TODO da vedere se si vuole con o senza
                        //fragmentSearchBinding.searchInputEditTextIngredient.setText(ingredient.getTitle());
                        fragmentSearchBinding.searchInputEditTextIngredient.setText("");
                        fragmentSearchBinding.recyclerviewIngredientsSearch.setVisibility(View.GONE);
                        ingredientList.add(ingredient);
                        Log.d(TAG, ingredientList.toString());
                        // Adding a chip accordingly with the item clicked on the RecyclerView
                        Chip chip = new Chip(requireContext());
                        chip.setText(ingredient.getTitle());
                        chip.setCloseIconVisible(true);
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ingredient.setSelected(false);

                                fragmentSearchBinding.chipIngredients.removeView(view);
                                // Remove the specific chip from the ChipGroup
                                Chip chipToRemove = findChipToRemove(ingredient); // Implement findChipToRemove method
                                if (chipToRemove != null) {
                                    fragmentSearchBinding.chipIngredients.removeView(chipToRemove);
                                }

                                ingredientList.remove(ingredient);
                            }
                        });
                        fragmentSearchBinding.chipIngredients.addView(chip);
                    }
                });
        recyclerViewIngredients.setLayoutManager(layoutManager);
        recyclerViewIngredients.setAdapter(ingredientsRecyclerViewAdapter);

        // Adding a listener for the changing text int the search bar
        fragmentSearchBinding.searchInputEditTextIngredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Making the fetch after typing in the search bar with the text typed
                fetchIngredients(String.valueOf(editable),intolerances);
                fragmentSearchBinding.recyclerviewIngredientsSearch.setVisibility(View.VISIBLE);
            }
        });

        // Setting up the chips according to the selected items in the FridgeFragment
        recipeViewModel.getFridgeItemsSelectedLiveData().observe(getViewLifecycleOwner(),result -> {
            if(result != null)  {
                if(result.isSuccess())  {
                    selectedFridgeItems.clear();
                    selectedFridgeItems.addAll(((Result.IngredientsResponseSuccess)result).getData().getIngredientsList());
                    for(Ingredient ingredient: selectedFridgeItems) {
                        Chip chip = new Chip(requireContext());
                        chip.setText(ingredient.getTitle());
                        chip.setCloseIconVisible(true);
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ingredient.setSelected(false);
                                recipeViewModel.updateSelectedFridgeItem(ingredient);
                                fragmentSearchBinding.chipIngredients.removeAllViews();
                                selectedFridgeItems.remove(ingredient);
                            }
                        });
                        fragmentSearchBinding.chipIngredients.addView(chip);
                    }
                } else {
                    ErrorMessagesUtil errorMessagesUtil =
                            new ErrorMessagesUtil(requireActivity().getApplication());
                    Snackbar.make(view, errorMessagesUtil.
                                    getErrorMessage(((Result.Error)result).getMessage()),
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        // Calling the methode to pre-check the chips
        setViewsChecked();

        // Going back to the main activity with the return button
        fragmentSearchBinding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();

            }
        });
        fragmentSearchBinding.searchBtn.setOnClickListener(v -> {
            selectedFridgeItems.addAll(ingredientList);
            List<String> ingredients = new ArrayList<>();
            for(Ingredient ingredient : selectedFridgeItems)    {
                ingredients.add(ingredient.getTitle());
            }


            //recipeViewModel.fetchRecipesScroll(null, getDiet(), getIntolerances(),ingredients,getType());
            SearchParameters parameters = new SearchParameters(getDiet(),getIntolerances(),ingredients,getType());
            SearchFragmentDirections.ActionSearchFragmentToResultsSearchFragment action =
                    SearchFragmentDirections.actionSearchFragmentToResultsSearchFragment(parameters);
            Navigation.findNavController(requireView()).navigate(action);
        });

    }

    private String getType() {
        String type = null;
        int id = fragmentSearchBinding.chipGroupCategories.getCheckedChipId();
        for (int i = 0; i < fragmentSearchBinding.chipGroupCategories.getChildCount(); i++) {
            if (fragmentSearchBinding.chipGroupCategories.getChildAt(i).getId() == id) {
                Chip chip = ((Chip) fragmentSearchBinding.chipGroupCategories.getChildAt(i));
                type = String.valueOf(chip.getText());
            }
        }
        return type;
    }

    private String getDiet()    {
        String diet = null;
        int id = fragmentSearchBinding.chipGroupDiet.getCheckedChipId();
        for(int i = 0; i < fragmentSearchBinding.chipGroupDiet.getChildCount(); i++)    {
            if(fragmentSearchBinding.chipGroupDiet.getChildAt(i).getId() == id){
                Chip chip =((Chip) fragmentSearchBinding.chipGroupDiet.getChildAt(i));
                diet = String.valueOf(chip.getText());
            }
        }
        return diet;
    }

    private Set<String> getIntolerances()  {
        Set<String> intolerances = new HashSet<>();
        List<Integer> ids = new ArrayList<>(fragmentSearchBinding.chipGroupIntolerances.getCheckedChipIds());
        for(int i = 0; i < fragmentSearchBinding.chipGroupIntolerances.getChildCount(); i++) {
            if(ids.contains(fragmentSearchBinding.chipGroupIntolerances.getChildAt(i).getId())) {
                Chip chip = ((Chip) fragmentSearchBinding.chipGroupIntolerances.getChildAt(i));
                intolerances.add(String.valueOf(chip.getText()));
            }
        }
        return intolerances;
    }

    // Method that makes the call to the API for the autocomplete
    private void fetchIngredients(String query,Set<String> intolerances)  {
        SpoonacularApiService service = ServiceLocator.getInstance().getSpoonacularApiService();
        Call<List<Ingredient>> ingredientsResponseCall = service
                .autocompleteIngredientSearch(query,
                        5,
                        true,
                        intolerances == null ? null : String.join(",",intolerances),
                        getActivity().getApplication().getString(R.string.spoonacular_api_key));
        ingredientsResponseCall.enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(@NonNull Call<List<Ingredient>> call, @NonNull Response<List<Ingredient>> response) {
                if(response.body() != null && response.isSuccessful())  {
                    onApiResult(response.body());
                } else {
                    onApiResult(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Ingredient>> call, @NonNull Throwable t) {
                onApiResult(null);
            }
        });
    }

    // Getting the result from the fetch, from the API
    private void onApiResult(List<Ingredient> results)  {
        if(results != null) {
            autocompleteIngredients.clear();
            autocompleteIngredients.addAll(results);
        } else {
            Log.d(TAG, "Lista nulla");
        }
        ingredientsRecyclerViewAdapter.notifyDataSetChanged();
    }

    // Set up the chips (UI) using the account preferences
    private void setViewsChecked()  {
        // Getting the information about diet and intolerances
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());
        String diet = sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_DIET);
        Set<String> intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME,
                SHARED_PREFERENCES_INTOLERANCES);

        // Checking the diet's chips
        if (diet != null) {
            if(diet.equals(GLUTEN_FREE))
                fragmentSearchBinding.glutenFree.setChecked(true);
            if(diet.equals(VEGETARIAN))
                fragmentSearchBinding.vegetarian.setChecked(true);
            if(diet.equals(VEGAN))
                fragmentSearchBinding.vegan.setChecked(true);
        }

        // Checking the intolerances' chips
        if (intolerances != null) {
            if (intolerances.contains(DAIRY)) {
                fragmentSearchBinding.dairy.setChecked(true);
            }
            if (intolerances.contains(GRAIN)) {
                fragmentSearchBinding.grain.setChecked(true);
            }
            if (intolerances.contains(EGG)) {
                fragmentSearchBinding.egg.setChecked(true);
            }
            if (intolerances.contains(PEANUT)) {
                fragmentSearchBinding.peanut.setChecked(true);
            }
            if (intolerances.contains(SEA_FOOD)) {
                fragmentSearchBinding.seaFood.setChecked(true);
            }
            if (intolerances.contains(SESAME)) {
                fragmentSearchBinding.sesame.setChecked(true);
            }
            if (intolerances.contains(SHELL_FISH)) {
                fragmentSearchBinding.shellFish.setChecked(true);
            }
            if (intolerances.contains(SOY)) {
                fragmentSearchBinding.soy.setChecked(true);
            }
            if (intolerances.contains(SULFITE)) {
                fragmentSearchBinding.sulfite.setChecked(true);
            }
            if (intolerances.contains(TREE_NUT)) {
                fragmentSearchBinding.treeNut.setChecked(true);
            }
            if (intolerances.contains(WHEAT)) {
                fragmentSearchBinding.wheat.setChecked(true);
            }
        }
    }

    private Chip findChipToRemove(Ingredient ingredient) {
        // Iterate over child views of the ChipGroup to find the matching chip
        for (int i = 0; i < fragmentSearchBinding.chipIngredients.getChildCount(); i++) {
            View child = fragmentSearchBinding.chipIngredients.getChildAt(i);
            if (child instanceof Chip) {
                Chip chip = (Chip) child;
                // Implement your logic to match the chip with the ingredient
                // For example, you might compare the text or tag of the chip
                if (TextUtils.equals(chip.getText(), ingredient.getTitle())) {
                    return chip;
                }
            }
        }
        return null; // Chip not found
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentSearchBinding = null;
    }
}