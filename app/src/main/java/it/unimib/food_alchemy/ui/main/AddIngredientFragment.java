package it.unimib.food_alchemy.ui.main;

import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.IngredientsRecyclerViewAdapter;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.data.service.SpoonacularApiService;
import it.unimib.food_alchemy.databinding.FragmentAddIngredientBinding;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.util.Constants;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class AddIngredientFragment extends Fragment {

    private static final String TAG = AddIngredientFragment.class.getSimpleName();
    private FragmentAddIngredientBinding fragmentAddIngredientBinding;
    private DatePickerDialog datePickerDialog;
    private RecipeViewModel recipeViewModel;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private List<Ingredient> autocompleteIngredients;
    private Ingredient ingredientToAdd;
    private IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter;
    private boolean isFirstLoading;
    public AddIngredientFragment()  {
        // Required empty public constructor
    }

    public static AddIngredientFragment newInstance() {
        return new AddIngredientFragment();
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
        autocompleteIngredients = new ArrayList<>();
        ingredientToAdd = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAddIngredientBinding = FragmentAddIngredientBinding.inflate(inflater,container,false);
        return fragmentAddIngredientBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isFirstLoading = sharedPreferencesUtil.readBooleanData(Constants.SHARED_PREFERENCES_FILE_NAME,
                Constants.SHARED_PREFERENCES_FRIDGE_ITEMS_FIRST_LOADING);
        // Initialize the RecyclerView
        RecyclerView recyclerViewIngredients = fragmentAddIngredientBinding.recyclerviewIngredients;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);

        // Set up the adapter for the autocomplete
        ingredientsRecyclerViewAdapter =
                new IngredientsRecyclerViewAdapter(autocompleteIngredients, ingredient -> {
                    fragmentAddIngredientBinding.textInputEditTextIngredient.setText(ingredient.getTitle());
                    fragmentAddIngredientBinding.recyclerviewIngredients.setVisibility(View.GONE);
                    ingredientToAdd = ingredient;
                });
        recyclerViewIngredients.setLayoutManager(layoutManager);
        recyclerViewIngredients.setAdapter(ingredientsRecyclerViewAdapter);

        // Initialize the date picker
        initDatePicker();

        // Setting up the date picker with  today's date the showing the date chooser
        fragmentAddIngredientBinding.spinnerButton.setText(getTodaysDate());
        fragmentAddIngredientBinding.spinnerButton.setOnClickListener(v -> datePickerDialog.show());
        fragmentAddIngredientBinding.returnButton.setOnClickListener(v -> Navigation.findNavController(requireView()).navigateUp());

        Set<String> intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_INTOLERANCES);
        fragmentAddIngredientBinding.textInputEditTextIngredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fragmentAddIngredientBinding.textInputLayoutIngredient.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Making the fetch after typing in the search bar with the text typed
                fetchIngredients(String.valueOf(editable),intolerances);
                fragmentAddIngredientBinding.recyclerviewIngredients.setVisibility(View.VISIBLE);
            }
        });

        // Save the selected ingredient to the fridge
        fragmentAddIngredientBinding.saveButton.setOnClickListener(v -> {
            String expiryDate = formatDate(fragmentAddIngredientBinding.spinnerButton.getText().toString());
            if(isIngredientOk(ingredientToAdd))  {
                ingredientToAdd.setExpiryDate(expiryDate);
                recipeViewModel.insertFridgeItem(ingredientToAdd);
                Navigation.findNavController(requireView()).navigateUp();
            }
        });


        NavBackStackEntry navBackStackEntry = Navigation.
                findNavController(view).getPreviousBackStackEntry();

        // Logic to highlight the correct tab in BottomNavigationView
        if (navBackStackEntry != null &&
                navBackStackEntry.getDestination().getId() == R.id.fridgeFragment) {
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.fridgeFragment).setChecked(true);
        }



    }

    // Method to get today's date
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day,month,year);
            fragmentAddIngredientBinding.spinnerButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int style;
        // Verifica il tema corrente
        if (currentTheme == Configuration.UI_MODE_NIGHT_YES) {
            style = AlertDialog.THEME_HOLO_DARK;
        } else {
            style = AlertDialog.THEME_HOLO_LIGHT;
        }
        datePickerDialog = new DatePickerDialog(requireContext(), style, dateSetListener, day, month, year);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
    }

    private String makeDateString(int day, int month, int year) {
        String d;
        String m;
        if(day > 0 && day < 10)
            d = "0" + day;
        else
            d = String.valueOf(day);
        if(month > 0 && month < 10)
            m = "0" + month;
        else
            m = String.valueOf(month);
        return d + "/" +  m + "/" + year;
    }
    private String formatDate(String date) {
        String formattedString = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter initialFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(date, initialFormatter);
            DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            formattedString = localDate.format(targetFormatter);
        }
        return formattedString;
    }

    //error shown when the ingredient text box is empty
    private boolean isIngredientOk(Ingredient ingredientToAdd) {
        if (ingredientToAdd == null) {
            fragmentAddIngredientBinding.textInputLayoutIngredient.setError(getString(R.string.error_ingredient_null));
            return false;
        }

        // Check if the ingredient is null
        List<Ingredient> fridgeItems = new ArrayList<>(((Result.IngredientsResponseSuccess)recipeViewModel.getFridgeItems(isFirstLoading).getValue()).getData().getIngredientsList());
        if(ingredientToAdd.getTitle().isEmpty()){
            fragmentAddIngredientBinding.textInputLayoutIngredient.setError(getString(R.string.error_ingredient_message));
            return false;
        }else{
            for(Ingredient ingredient : fridgeItems){
                if(ingredient.getId() == ingredientToAdd.getId()) {
                    fragmentAddIngredientBinding.textInputLayoutIngredient.setError(getString(R.string.error_ingredient_present));
                    return false;
                }
            }
        }
        return true;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAddIngredientBinding = null;
    }

}
