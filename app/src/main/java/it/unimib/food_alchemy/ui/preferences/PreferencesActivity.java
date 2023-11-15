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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.data.repository.user.IUserRepository;
import it.unimib.food_alchemy.databinding.ActivityPreferencesBinding;
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
    private ActivityPreferencesBinding activityPreferencesBinding;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(getApplication());
        userViewModel = new ViewModelProvider(
                this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        userViewModel.getUserPreferences(userViewModel.getLoggedUser().getIdToken()).
                observe(this, result -> {
                    setViewsChecked();
                });

        activityPreferencesBinding.buttonNext.setOnClickListener(view -> {
            if(isDietSelected())   {
                Log.d(TAG,"One diet and at least one intolerance has been chosen");
                saveInformation();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Checks if a diet has been selected.
     * @return true if a diet has been selected, false otherwise.
     */
    private boolean isDietSelected() {

        if (activityPreferencesBinding.spinnerDiet.getSelectedItem() != null) {
            return true;
        }

        Snackbar.make(findViewById(android.R.id.content), R.string.alert_text_diet,
                Snackbar.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Stores the diet and the intolerances chosen by the user
     * in the file system through the use of SharedPreferences API
     */
    private void saveInformation() {

        String diet = activityPreferencesBinding.spinnerDiet.getSelectedItem().toString();
        Set<String> intolerances = new HashSet<>();

        if (activityPreferencesBinding.checkboxDairy.isChecked()) {
            intolerances.add(DAIRY);
        }
        if (activityPreferencesBinding.checkboxGrain.isChecked()) {
            intolerances.add(GRAIN);
        }
        if (activityPreferencesBinding.checkboxEgg.isChecked()) {
            intolerances.add(EGG);
        }
        if (activityPreferencesBinding.checkboxPeanut.isChecked()) {
            intolerances.add(PEANUT);
        }
        if (activityPreferencesBinding.checkboxSeaFood.isChecked()) {
            intolerances.add(SEA_FOOD);
        }
        if (activityPreferencesBinding.checkboxSesame.isChecked()) {
            intolerances.add(SESAME);
        }
        if (activityPreferencesBinding.checkboxShellFish.isChecked()) {
            intolerances.add(SHELL_FISH);
        }
        if (activityPreferencesBinding.checkboxSoy.isChecked()) {
            intolerances.add(SOY);
        }
        if (activityPreferencesBinding.checkboxSulfite.isChecked()) {
            intolerances.add(SULFITE);
        }
        if (activityPreferencesBinding.checkboxTreeNut.isChecked()) {
            intolerances.add(TREE_NUT);
        }
        if (activityPreferencesBinding.checkboxWheat.isChecked()) {
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
            activityPreferencesBinding.spinnerDiet.setSelection(
                    getSpinnerPositionBasedOnValue(getDiet(diet)));
        }

        if (intolerances != null) {
            if (intolerances.contains(DAIRY)) {
                activityPreferencesBinding.checkboxDairy.setChecked(true);
            }
            if (intolerances.contains(GRAIN)) {
                activityPreferencesBinding.checkboxGrain.setChecked(true);
            }
            if (intolerances.contains(EGG)) {
                activityPreferencesBinding.checkboxEgg.setChecked(true);
            }
            if (intolerances.contains(PEANUT)) {
                activityPreferencesBinding.checkboxPeanut.setChecked(true);
            }
            if (intolerances.contains(SEA_FOOD)) {
                activityPreferencesBinding.checkboxSeaFood.setChecked(true);
            }
            if (intolerances.contains(SESAME)) {
                activityPreferencesBinding.checkboxSesame.setChecked(true);
            }
            if (intolerances.contains(SHELL_FISH)) {
                activityPreferencesBinding.checkboxShellFish.setChecked(true);
            }
            if (intolerances.contains(SOY)) {
                activityPreferencesBinding.checkboxSoy.setChecked(true);
            }
            if (intolerances.contains(SULFITE)) {
                activityPreferencesBinding.checkboxSulfite.setChecked(true);
            }
            if (intolerances.contains(TREE_NUT)) {
                activityPreferencesBinding.checkboxTreeNut.setChecked(true);
            }
            if (intolerances.contains(WHEAT)) {
                activityPreferencesBinding.checkboxWheat.setChecked(true);
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        activityPreferencesBinding = null;
    }
}