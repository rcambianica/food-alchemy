package it.unimib.food_alchemy.ui.preferences;

import static it.unimib.food_alchemy.util.Constants.DAIRY;
import static it.unimib.food_alchemy.util.Constants.EGG;
import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.GLUTEN_FREE;
import static it.unimib.food_alchemy.util.Constants.GRAIN;
import static it.unimib.food_alchemy.util.Constants.ID_TOKEN;
import static it.unimib.food_alchemy.util.Constants.NO_DIET;
import static it.unimib.food_alchemy.util.Constants.PEANUT;
import static it.unimib.food_alchemy.util.Constants.SEA_FOOD;
import static it.unimib.food_alchemy.util.Constants.SESAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_DIET;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FIRST_LOADING;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;
import static it.unimib.food_alchemy.util.Constants.SHELL_FISH;
import static it.unimib.food_alchemy.util.Constants.SOY;
import static it.unimib.food_alchemy.util.Constants.SULFITE;
import static it.unimib.food_alchemy.util.Constants.TREE_NUT;
import static it.unimib.food_alchemy.util.Constants.VEGAN;
import static it.unimib.food_alchemy.util.Constants.VEGETARIAN;
import static it.unimib.food_alchemy.util.Constants.WHEAT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.data.repository.user.IUserRepository;
import it.unimib.food_alchemy.ui.auth.UserViewModelFactory;
import it.unimib.food_alchemy.util.DataEncryptionUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;
import it.unimib.food_alchemy.ui.auth.UserViewModel;
import it.unimib.food_alchemy.ui.main.MainActivity;

/**
 * Activity to allow user to choose his diet, intolerances and ingredients preferences
 */
public class PreferencesActivity extends AppCompatActivity {

    private static final String TAG = PreferencesActivity.class.getSimpleName();

    private Spinner spinnerDiets;
    private CheckBox checkBoxDairy;
    private CheckBox checkBoxGrain;
    private CheckBox checkBoxEgg;
    private CheckBox checkBoxPeanut;
    private CheckBox checkBoxSeaFood;
    private CheckBox checkBoxSesame;
    private CheckBox checkBoxShellFish;
    private CheckBox checkBoxSoy;
    private CheckBox checkBoxSulfite;
    private CheckBox checkBoxTreeNut;
    private CheckBox checkBoxWheat;
    private ConstraintLayout layout;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        layout = findViewById(R.id.preferences_activity_layout);
        spinnerDiets = findViewById(R.id.spinner_diet);

        checkBoxDairy = findViewById(R.id.checkbox_dairy);
        checkBoxGrain = findViewById(R.id.checkbox_grain);
        checkBoxEgg = findViewById(R.id.checkbox_egg);
        checkBoxPeanut = findViewById(R.id.checkbox_peanut);
        checkBoxSeaFood = findViewById(R.id.checkbox_sea_food);
        checkBoxSesame = findViewById(R.id.checkbox_sesame);
        checkBoxShellFish = findViewById(R.id.checkbox_shell_fish);
        checkBoxSoy = findViewById(R.id.checkbox_soy);
        checkBoxSulfite = findViewById(R.id.checkbox_sulfite);
        checkBoxTreeNut = findViewById(R.id.checkbox_tree_nut);
        checkBoxWheat = findViewById(R.id.checkbox_wheat);

        final Button buttonNext = findViewById(R.id.button_next);

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(getApplication());
        userViewModel = new ViewModelProvider(
                this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        userViewModel.getUserPreferences(userViewModel.getLoggedUser().getIdToken()).
                observe(this, result -> {
                    if(result.isSuccess())  {
                        setViewsChecked();
                    } else {
                        Snackbar.make(layout,
                                getString(R.string.unexpected_error),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });


       buttonNext.setOnClickListener(v -> {
           saveInformation();
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
           finish();
       });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)    {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState");
    }

    /**
     * Stores the diet and the intolerances chosen by the user
     * in the file system through the use of SharedPreferences API
     */
    private void saveInformation() {

        String diet = spinnerDiets.getSelectedItem().toString();
        Set<String> intolerances = new HashSet<>();


        if (checkBoxDairy.isChecked()) {
            intolerances.add(DAIRY);
        }
        if (checkBoxGrain.isChecked()) {
            intolerances.add(GRAIN);
        }
        if (checkBoxEgg.isChecked()) {
            intolerances.add(EGG);
        }
        if (checkBoxPeanut.isChecked()) {
            intolerances.add(PEANUT);
        }
        if (checkBoxSeaFood.isChecked()) {
            intolerances.add(SEA_FOOD);
        }
        if (checkBoxSesame.isChecked()) {
            intolerances.add(SESAME);
        }
        if (checkBoxShellFish.isChecked()) {
            intolerances.add(SHELL_FISH);
        }
        if (checkBoxSoy.isChecked()) {
            intolerances.add(SOY);
        }
        if (checkBoxSulfite.isChecked()) {
            intolerances.add(SULFITE);
        }
        if (checkBoxTreeNut.isChecked()) {
            intolerances.add(TREE_NUT);
        }
        if (checkBoxWheat.isChecked()) {
            intolerances.add(WHEAT);
        }

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getApplication());
        sharedPreferencesUtil.writeStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_DIET,diet);

        sharedPreferencesUtil.writeStringSetData(SHARED_PREFERENCES_FILE_NAME,
                SHARED_PREFERENCES_INTOLERANCES, intolerances);

        sharedPreferencesUtil.writeBooleanData(SHARED_PREFERENCES_FILE_NAME,
                SHARED_PREFERENCES_FIRST_LOADING, true);

        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(getApplication());

        String idToken = null;
        try {
            idToken = dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ID_TOKEN);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        userViewModel.saveUserPreferences(diet, intolerances, idToken);
    }

    /**
     * Sets the spinner and the checkbox values based on what it has been saved in the
     * SharedPreferences file.
     */
    private void setViewsChecked() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getApplication());

        String diet = sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_DIET);
        Set<String> intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME,
                SHARED_PREFERENCES_INTOLERANCES);

        if (diet != null) {
            spinnerDiets.setSelection(
                    getSpinnerPositionBasedOnValue(getDiet(diet)));
        }

        if (intolerances != null) {
            if (intolerances.contains(DAIRY)) {
                checkBoxDairy.setChecked(true);
            }
            if (intolerances.contains(GRAIN)) {
                checkBoxGrain.setChecked(true);
            }
            if (intolerances.contains(EGG)) {
                checkBoxEgg.setChecked(true);
            }
            if (intolerances.contains(PEANUT)) {
                checkBoxPeanut.setChecked(true);
            }
            if (intolerances.contains(SEA_FOOD)) {
                checkBoxSeaFood.setChecked(true);
            }
            if (intolerances.contains(SESAME)) {
                checkBoxSesame.setChecked(true);
            }
            if (intolerances.contains(SHELL_FISH)) {
                checkBoxShellFish.setChecked(true);
            }
            if (intolerances.contains(SOY)) {
                checkBoxSoy.setChecked(true);
            }
            if (intolerances.contains(SULFITE)) {
                checkBoxSulfite.setChecked(true);
            }
            if (intolerances.contains(TREE_NUT)) {
                checkBoxTreeNut.setChecked(true);
            }
            if (intolerances.contains(WHEAT)) {
                checkBoxWheat.setChecked(true);
            }
        }
    }
    /**
     * Allows to get the position of a given diet in the string array
     * used to fill the spinner.
     * @param value the diet to be checked in the string array
     * @return the position of the diet in the string array
     */
    private int getSpinnerPositionBasedOnValue(String value) {

        String[] diet = getResources().getStringArray(R.array.diet_array);

        for (int i = 0; i < diet.length; i++) {
            if (diet[i].equals(value)) {
                return i;
            }
        }

        return 0;
    }

    /**
     * Retrieves the diet id to be used with Spoonacular API
     * @param diet the diet chosen by the user
     * @return the country id
     */
    private String getDiet(String diet) {
        switch (diet) {
            case NO_DIET:
                return getString(R.string.no_diet);
            case GLUTEN_FREE:
                return getString(R.string.gluten_free);
            case VEGAN:
                return getString(R.string.vegan);
            case VEGETARIAN:
                return getString(R.string.vegetarian);
            default:
                return null;
        }
    }
}