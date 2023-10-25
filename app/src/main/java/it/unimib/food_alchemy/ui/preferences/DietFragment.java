package it.unimib.food_alchemy.ui.preferences;

import static it.unimib.food_alchemy.util.Constants.EMAIL_ADDRESS;
import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.PASSWORD;

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

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.ui.main.MainActivity;
import it.unimib.food_alchemy.util.DataEncryptionUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DietFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DietFragment extends Fragment {
    public DietFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DietFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DietFragment newInstance() {return new DietFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button buttonNext = view.findViewById(R.id.button_next);

        buttonNext.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_dietFragment_to_intolerancesFragment);
        });
    }
}