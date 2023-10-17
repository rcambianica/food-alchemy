package it.unimib.food_alchemy.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import it.unimib.food_alchemy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    private static final String TAG = RegistrationFragment.class.getSimpleName();

    private TextInputLayout nameInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        super.onViewCreated(view, savedInstanceState);
        nameInputLayout = view.findViewById(R.id.text_input_layout_name);
        emailInputLayout = view.findViewById(R.id.text_input_layout_email);
        passwordInputLayout = view.findViewById(R.id.text_input_layout_password);
        final Button buttonRegister = view.findViewById(R.id.button_register);
        final Button buttonSignin = view.findViewById(R.id.button_signin);
        buttonRegister.setOnClickListener(view12 -> {
            String name = nameInputLayout.getEditText().getText().toString();
            String email = emailInputLayout.getEditText().getText().toString();
            String password = passwordInputLayout.getEditText().getText().toString();

            //start registration if name, email and password are ok
            if(isNameOk(name) & isEmailOk(email) & isPasswordOk(password))   {
                Log.d(TAG, "Email and password are ok");

            } else {
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });
        buttonSignin.setOnClickListener(view1 -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_registrationFragment_to_loginFragment);
        });
    }

    /**
     * Checks if the name is not empty.
     * @param name The name to be checked
     * @return True if the name is not empty, false otherwise
     */
    private boolean isNameOk(String name) {
        // Check if the name length is correct
        if (name.isEmpty()) {
            nameInputLayout.setError(getString(R.string.error_name));
            return false;
        } else {
            nameInputLayout.setError(null);
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
            emailInputLayout.setError(getString(R.string.error_email));
            return false;
        } else {
            emailInputLayout.setError(null);
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
        if (password.isEmpty()) {
            passwordInputLayout.setError(getString(R.string.error_password));
            return false;
        } else {
            passwordInputLayout.setError(null);
            return true;
        }
    }
}