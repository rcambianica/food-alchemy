package it.unimib.food_alchemy.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding fragmentHomeBinding;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container, false);
        return fragmentHomeBinding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);
        fragmentHomeBinding.bottoneMainCourse.setOnClickListener(v -> {
            Log.d(TAG, "bottone main course cliccato");
        });

        fragmentHomeBinding.bottoneSideDish.setOnClickListener(v -> {
            Log.d(TAG, "bottone side dish cliccato");
        });

        fragmentHomeBinding.bottoneDessert.setOnClickListener(v -> {
            Log.d(TAG, "bottone dessert cliccato");
        });

        fragmentHomeBinding.bottoneAppetizer.setOnClickListener(v -> {
            Log.d(TAG, "bottone appetizer cliccato");
        });
    }



    @Override
    public void onDestroyView(){

        super.onDestroyView();
        fragmentHomeBinding = null;

    }



}