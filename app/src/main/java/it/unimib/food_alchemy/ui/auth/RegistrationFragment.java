package it.unimib.food_alchemy.ui.auth;

import static it.unimib.food_alchemy.util.Constants.EMAIL_ADDRESS;
import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.ID_TOKEN;
import static it.unimib.food_alchemy.util.Constants.MINIMUM_PASSWORD_LENGTH;
import static it.unimib.food_alchemy.util.Constants.NAME;
import static it.unimib.food_alchemy.util.Constants.PASSWORD;
import static it.unimib.food_alchemy.util.Constants.USER_COLLISION_ERROR;
import static it.unimib.food_alchemy.util.Constants.WEAK_PASSWORD_ERROR;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.databinding.FragmentRegistrationBinding;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.model.User;
import it.unimib.food_alchemy.ui.preferences.PreferencesActivity;
import it.unimib.food_alchemy.util.DataEncryptionUtil;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    private static final String TAG = RegistrationFragment.class.getSimpleName();

    private FragmentRegistrationBinding fragmentRegistrationBinding;
    private UserViewModel userViewModel;
    private DataEncryptionUtil dataEncryptionUtil;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance() {return new RegistrationFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.setAuthenticationError(false);
        dataEncryptionUtil = new DataEncryptionUtil(requireActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRegistrationBinding = FragmentRegistrationBinding.inflate(inflater,container,false);
        return fragmentRegistrationBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        super.onViewCreated(view, savedInstanceState);

        fragmentRegistrationBinding.buttonRegister.setOnClickListener(v -> {
            String name = fragmentRegistrationBinding.textInputEditTextName.getText().toString().trim();
            String email = fragmentRegistrationBinding.textInputEditTextEmail.getText().toString().trim();
            String password = fragmentRegistrationBinding.textInputEditTextPassword.getText().toString().trim();

            //start registration if name, email and password are ok
            if(isNameOk(name) & isEmailOk(email) & isPasswordOk(password))   {
                if (!userViewModel.isAuthenticationError()) {
                    userViewModel.getUserMutableLiveData(email, password, false).observe(
                            getViewLifecycleOwner(), result -> {
                                if (result.isSuccess()) {
                                    User user = ((Result.UserResponseSuccess) result).getData();
                                    saveLoginData(name, email, password, user.getIdToken());
                                    userViewModel.setAuthenticationError(false);
                                    Navigation.findNavController(requireView()).navigate(R.id.action_registrationFragment_to_PreferencesActivity);
                                } else {
                                    userViewModel.setAuthenticationError(true);
                                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    userViewModel.getUser(email, password, false);
                }
            } else {
                userViewModel.setAuthenticationError(true);
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });

        fragmentRegistrationBinding.buttonSignin.setOnClickListener(v-> {
            Navigation.findNavController(view).navigate(
                    R.id.action_registrationFragment_to_loginFragment);
        });
    }

    private String getErrorMessage(String message) {
        switch(message) {
            case WEAK_PASSWORD_ERROR:
                return requireActivity().getString(R.string.error_password);
            case USER_COLLISION_ERROR:
                return requireActivity().getString(R.string.error_user_collision_message);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }
    /**
     * Checks if the name is not empty.
     * @param name The name to be checked
     * @return True if the name is not empty, false otherwise
     */
    private boolean isNameOk(String name) {
        // Check if the name length is correct
        if (name.isEmpty()) {
            fragmentRegistrationBinding.textInputEditTextName.setError(getString(R.string.error_name));
            return false;
        } else {
            fragmentRegistrationBinding.textInputEditTextName.setError(null);
            return true;
        }
    }

    /**
     * Checks if the email address has a correct format.
     * @param email The email address to be validated
     * @return true if the email address is valid, false otherwise
     */
    private boolean isEmailOk(String email) {
        // Check if the email is valid through the use of this library:
        // https://commons.apache.org/proper/commons-validator/
        if (!EmailValidator.getInstance().isValid((email))) {
            fragmentRegistrationBinding.textInputLayoutEmail.setError(getString(R.string.error_email));
            return false;
        } else {
            fragmentRegistrationBinding.textInputLayoutEmail.setError(null);
            return true;
        }
    }

    /**
     * Checks if the password is not empty.
     * @param password The password to be checked
     * @return True if the password is not empty, false otherwise
     */
    private boolean isPasswordOk(String password) {
        // Check if the password length is correct
        if (password.isEmpty() || password.length() < MINIMUM_PASSWORD_LENGTH) {
            fragmentRegistrationBinding.textInputLayoutPassword.setError(getString(R.string.error_password));
            return false;
        } else {
            fragmentRegistrationBinding.textInputLayoutPassword.setError(null);
            return true;
        }
    }

    /**
     * Encrypts login data using DataEncryptionUtil class.
     * @param email The email address to be encrypted and saved
     * @param password The password to be encrypted and saved
     * @param idToken The token associated with the account
     */
    private void saveLoginData(String name, String email, String password, String idToken) {
        try {
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME,NAME,name);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, EMAIL_ADDRESS, email);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD, password);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ID_TOKEN, idToken);
            dataEncryptionUtil.writeSecreteDataOnFile(ENCRYPTED_DATA_FILE_NAME,
                    name.concat(":").concat(email).concat(":").concat(password));
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentRegistrationBinding = null;
    }
}