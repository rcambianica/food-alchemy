package it.unimib.food_alchemy.ui.auth;

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
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends Fragment {

    public static final String TAG = ForgotPasswordFragment.class.getSimpleName();

    private TextInputLayout emailInputLayout;
    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ForgotPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        super.onViewCreated(view, savedInstanceState);

        emailInputLayout = view.findViewById(R.id.text_input_layout_email);
        final Button buttonReset = view.findViewById(R.id.button_reset);
        final Button buttonReturnLogin = view.findViewById(R.id.button_return_login);

        buttonReset.setOnClickListener(view12 -> {
            String email = emailInputLayout.getEditText().getText().toString();

            //start login if email and password are ok
            if(isEmailOk(email))   {
                Log.d(TAG, "Email is ok");
            } else {
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });

        buttonReturnLogin.setOnClickListener(view1 -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
        });
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
}