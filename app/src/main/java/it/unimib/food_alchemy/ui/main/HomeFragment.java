package it.unimib.food_alchemy.ui.main;

import static it.unimib.food_alchemy.util.Constants.DAIRY;
import static it.unimib.food_alchemy.util.Constants.EGG;
import static it.unimib.food_alchemy.util.Constants.GLUTEN_FREE;
import static it.unimib.food_alchemy.util.Constants.GRAIN;
import static it.unimib.food_alchemy.util.Constants.NO_DIET;
import static it.unimib.food_alchemy.util.Constants.PEANUT;
import static it.unimib.food_alchemy.util.Constants.SEA_FOOD;
import static it.unimib.food_alchemy.util.Constants.SESAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_DIET;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;
import static it.unimib.food_alchemy.util.Constants.SHELL_FISH;
import static it.unimib.food_alchemy.util.Constants.SOY;
import static it.unimib.food_alchemy.util.Constants.SULFITE;
import static it.unimib.food_alchemy.util.Constants.TREE_NUT;
import static it.unimib.food_alchemy.util.Constants.VEGAN;
import static it.unimib.food_alchemy.util.Constants.VEGETARIAN;
import static it.unimib.food_alchemy.util.Constants.WHEAT;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Set;

import it.unimib.food_alchemy.databinding.FragmentHomeBinding;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

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
    public static HomeFragment newInstance() {return new HomeFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container, false);
        return fragmentHomeBinding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        setViewsChecked();

        fragmentHomeBinding.bottoneMainCourse.setOnClickListener(v -> Log.d(TAG, "bottone main course cliccato"));

        fragmentHomeBinding.bottoneSideDish.setOnClickListener(v -> Log.d(TAG, "bottone side dish cliccato"));

        fragmentHomeBinding.bottoneDessert.setOnClickListener(v -> Log.d(TAG, "bottone dessert cliccato"));

        fragmentHomeBinding.bottoneAppetizer.setOnClickListener(v -> Log.d(TAG, "bottone appetizer cliccato"));
    }

    private void setViewsChecked()  {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        String diet = sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_DIET);
        Set<String> intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME,
                SHARED_PREFERENCES_INTOLERANCES);

        if (diet != null) {
            if(diet.equals(NO_DIET))
                fragmentHomeBinding.noDiet.setChecked(true);
            if(diet.equals(GLUTEN_FREE))
                fragmentHomeBinding.glutenFree.setChecked(true);
            if(diet.equals(VEGETARIAN))
                fragmentHomeBinding.vegetarian.setChecked(true);
            if(diet.equals(VEGAN))
                fragmentHomeBinding.vegan.setChecked(true);
        }

        if (intolerances != null) {
            if (intolerances.contains(DAIRY)) {
                fragmentHomeBinding.dairy.setChecked(true);
            }
            if (intolerances.contains(GRAIN)) {
                fragmentHomeBinding.grain.setChecked(true);
            }
            if (intolerances.contains(EGG)) {
                fragmentHomeBinding.egg.setChecked(true);
            }
            if (intolerances.contains(PEANUT)) {
                fragmentHomeBinding.peanut.setChecked(true);
            }
            if (intolerances.contains(SEA_FOOD)) {
                fragmentHomeBinding.seaFood.setChecked(true);
            }
            if (intolerances.contains(SESAME)) {
                fragmentHomeBinding.sesame.setChecked(true);
            }
            if (intolerances.contains(SHELL_FISH)) {
                fragmentHomeBinding.shellFish.setChecked(true);
            }
            if (intolerances.contains(SOY)) {
                fragmentHomeBinding.soy.setChecked(true);
            }
            if (intolerances.contains(SULFITE)) {
                fragmentHomeBinding.sulfite.setChecked(true);
            }
            if (intolerances.contains(TREE_NUT)) {
                fragmentHomeBinding.treeNut.setChecked(true);
            }
            if (intolerances.contains(WHEAT)) {
                fragmentHomeBinding.wheat.setChecked(true);
            }
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentHomeBinding = null;
    }
}