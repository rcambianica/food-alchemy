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


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;


import com.google.android.material.chip.Chip;
//import com.google.android.material.search.SearchView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.databinding.FragmentSearchBinding;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.ui.main.MainActivity;
import it.unimib.food_alchemy.ui.main.RecipeViewModel;
import it.unimib.food_alchemy.ui.main.RecipeViewModelFactory;
import it.unimib.food_alchemy.util.ErrorMessagesUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding fragmentSearchBinding;
    private RecipeViewModel recipeViewModel;

    private List<Ingredient> selectedFridgeItems;

    ArrayList<String> arrayList;
    ArrayAdapter<String>adapter;

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
        selectedFridgeItems = new ArrayList<>();

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

        //fragmentSearchBinding.searchView.setIconified(false);

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
                                Log.d("TAG", selectedFridgeItems.toString());
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

        setViewsChecked();


        fragmentSearchBinding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();

            }
        });


        fragmentSearchBinding.searchList.setVisibility(View.GONE);

        arrayList=new ArrayList<>();
        arrayList.add("Pizza");
        arrayList.add("Pasta");
        arrayList.add("Pomodoro");
        arrayList.add("Salmone");
        arrayList.add("Cavolfiore");
        arrayList.add("Zoccolo");
        arrayList.add("Maiale");
        arrayList.add("Suino");
        arrayList.add("Cacca");
        arrayList.add("Pupù");

        adapter=new ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,arrayList);
        fragmentSearchBinding.searchList.setAdapter(adapter);

        fragmentSearchBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            public boolean onQueryTextSubmit(String s){
                fragmentSearchBinding.searchList.setVisibility(View.GONE);
                return false;
            }

            public boolean onQueryTextChange(String s){
                fragmentSearchBinding.searchList.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(s);
                return false;
            }
        });

        fragmentSearchBinding.searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentSearchBinding.searchList.setVisibility(View.GONE);


                Chip chip = new Chip(requireContext());
                chip.setText((String) parent.getItemAtPosition(position));
                chip.setCloseIconVisible(true);
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragmentSearchBinding.chipIngredients.removeView(chip);
                        Log.d("TAG", selectedFridgeItems.toString());
                    }
                });
                fragmentSearchBinding.chipIngredients.addView(chip);
            }
        });

    }

    private void setViewsChecked()  {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        String diet = sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_DIET);
        Set<String> intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME,
                SHARED_PREFERENCES_INTOLERANCES);

        if (diet != null) {
            if(diet.equals(NO_DIET))
                fragmentSearchBinding.noDiet.setChecked(true);
            if(diet.equals(GLUTEN_FREE))
                fragmentSearchBinding.glutenFree.setChecked(true);
            if(diet.equals(VEGETARIAN))
                fragmentSearchBinding.vegetarian.setChecked(true);
            if(diet.equals(VEGAN))
                fragmentSearchBinding.vegan.setChecked(true);
        }

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

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentSearchBinding = null;
    }
}