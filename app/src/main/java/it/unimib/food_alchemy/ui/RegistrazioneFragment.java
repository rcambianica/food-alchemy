package it.unimib.food_alchemy.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import it.unimib.food_alchemy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrazioneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrazioneFragment extends Fragment {

    private static final String TAG = RegistrazioneFragment.class.getSimpleName();

    private TextInputLayout nameInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    public RegistrazioneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrazioneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrazioneFragment newInstance(String param1, String param2) {
        RegistrazioneFragment fragment = new RegistrazioneFragment();
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
        return inflater.inflate(R.layout.fragment_registrazione, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        super.onViewCreated(view, savedInstanceState);
        nameInputLayout = view.findViewById(R.id.nameInputLayout);
        emailInputLayout = view.findViewById(R.id.email_input_layout);
        passwordInputLayout = view.findViewById(R.id.password_input_layout);
        final Button buttonRegister = view.findViewById(R.id.button_register);
        final Button buttonSignin = view.findViewById(R.id.button_signin);
        buttonSignin.setOnClickListener(view1 -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_registrazioneFragment_to_loginFragment);
        });
    }
}