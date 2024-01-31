package it.unimib.food_alchemy.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.validator.routines.EmailValidator;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.data.repository.user.IUserRepository;
import it.unimib.food_alchemy.databinding.FragmentForgotPasswordBinding;
import it.unimib.food_alchemy.util.ServiceLocator;

public class ForgotPasswordFragment extends Fragment {

    public static final String TAG = ForgotPasswordFragment.class.getSimpleName();
    private FragmentForgotPasswordBinding fragmentForgotPasswordBinding;

    private UserViewModel userViewModel;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgotPasswordFragment newInstance() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentForgotPasswordBinding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return fragmentForgotPasswordBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentForgotPasswordBinding.buttonReset.setOnClickListener(v -> {
            String email = fragmentForgotPasswordBinding.textInputEditTextEmail.getText().toString();

            if (isEmailOk(email)) {
                userViewModel.forgotPassword(email).observe(getViewLifecycleOwner(), result -> {
                    if(result != null)  {
                        if(result.isSuccess())  {
                            Snackbar.make(requireActivity().findViewById(android.R.id.content), "Email sent successfully, check your inbox!", Snackbar.LENGTH_SHORT).show();
                        }else {
                            Snackbar.make(view,
                                    requireActivity().getString(R.string.unexpected_error),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Snackbar.make(requireActivity().findViewById(android.R.id.content), R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });

        fragmentForgotPasswordBinding.buttonReturnLogin.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigateUp();
        });
    }

    /**
     * Checks if the email address has a correct format.
     *
     * @param email The email address to be validated
     * @return true if the email address is valid, false otherwise
     */
    private boolean isEmailOk(String email) {
        // Check if the email is valid through the use of this library:
        // https://commons.apache.org/proper/commons-validator/
        if (!EmailValidator.getInstance().isValid((email))) {
            fragmentForgotPasswordBinding.textInputLayoutEmail.setError(getString(R.string.error_email));
            return false;
        } else {
            fragmentForgotPasswordBinding.textInputLayoutEmail.setError(null);
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentForgotPasswordBinding = null;
    }
}