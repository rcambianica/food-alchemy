package it.unimib.food_alchemy.ui.preferences;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.unimib.food_alchemy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntolerancesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntolerancesFragment extends Fragment {
    public IntolerancesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment IntolerancesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntolerancesFragment newInstance() {return new IntolerancesFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intolerances, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button buttonNext = view.findViewById(R.id.button_next);
        final Button buttonBack = view.findViewById(R.id.button_back);
        buttonNext.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_intolerancesFragment_to_excludeIngredientsFragment);
        });

        buttonBack.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_intolerancesFragment_to_dietFragment);
        });
    }
}