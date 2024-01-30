package it.unimib.food_alchemy.ui.main;

import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.ID_TOKEN;
import static it.unimib.food_alchemy.util.Constants.SHARED_FRIDGE_ITEMS_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_FRIDGE_ITEMS_FIRST_LOADING;
import static it.unimib.food_alchemy.util.Constants.SHARED_FRIDGE_ITEMS_FRIDGE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.data.repository.user.IUserRepository;
import it.unimib.food_alchemy.data.repository.user.UserRepository;
import it.unimib.food_alchemy.databinding.FragmentAddIngredientBinding;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.ui.auth.UserViewModel;
import it.unimib.food_alchemy.ui.auth.UserViewModelFactory;
import it.unimib.food_alchemy.util.DataEncryptionUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddIngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIngredientFragment extends Fragment {

    private static final String TAG = AddIngredientFragment.class.getSimpleName();
    private FragmentAddIngredientBinding fragmentAddIngredientBinding;
    private DatePickerDialog datePickerDialog;
    private RecipeViewModel recipeViewModel;
    private SharedPreferencesUtil sharedPreferencesUtil;

    ArrayList<String>arrayList;
    ArrayAdapter<String>adapter;
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

        sharedPreferencesUtil = new SharedPreferencesUtil(getActivity().getApplication());

        initDatePicker();

        fragmentAddIngredientBinding.spinnerButton.setText(getTodaysDate());
        fragmentAddIngredientBinding.spinnerButton.setOnClickListener(v -> datePickerDialog.show());
        fragmentAddIngredientBinding.returnButton.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigateUp();
        });
        fragmentAddIngredientBinding.saveButton.setOnClickListener(v -> {
            String ingredientName = fragmentAddIngredientBinding.autoCompleteTextView.getText().toString().trim();
            String expiryDate = formatDate(fragmentAddIngredientBinding.spinnerButton.getText().toString());
            if(isIngredientOk(ingredientName))  {
                long ran = (long)Math.floor(Math.random()*(30));;
                Ingredient fridgeItem = new Ingredient(ran,ingredientName,expiryDate,false);
                recipeViewModel.insertFridgeItem(fridgeItem);
                Navigation.findNavController(requireView()).navigateUp();
            }
        });
        NavBackStackEntry navBackStackEntry = Navigation.
                findNavController(view).getPreviousBackStackEntry();

        // Logic to highlight the correct tab in BottomNavigationView when the user
        // goes to NewsDetailFragment from a tab which is neither CountryNewsFragment
        // nor FavoriteNewsFragment
        if (navBackStackEntry != null &&
                navBackStackEntry.getDestination().getId() == R.id.fridgeFragment) {
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottom_navigation)).
                    getMenu().findItem(R.id.fridgeFragment).setChecked(true);
        }


        arrayList=new ArrayList<>();
        arrayList.add("Sappo");
        arrayList.add("Santo");
        arrayList.add("Sasso");
        arrayList.add("Salmone");
        arrayList.add("Salame");

        adapter=new ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,arrayList);
        fragmentAddIngredientBinding.autoCompleteTextView.setAdapter(adapter);

    }

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
    private boolean isIngredientOk(String ingredient) {
        // Check if the ingredient is null
        if (ingredient.isEmpty()) {
            fragmentAddIngredientBinding.autoCompleteTextView.setError(getString(R.string.error_ingredient_message));
            return false;
        } else {
            fragmentAddIngredientBinding.autoCompleteTextView.setError(null);
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAddIngredientBinding = null;
    }

}
