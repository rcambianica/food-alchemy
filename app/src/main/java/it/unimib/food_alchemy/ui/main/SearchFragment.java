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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.ChipGroup;

import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.databinding.FragmentSearchBinding;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private FragmentSearchBinding fragmentSearchBinding;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);
        return fragmentSearchBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        fragmentSearchBinding.searchView.setIconified(false);

        setViewsChecked();

        fragmentSearchBinding.returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigateUp();
            }
        });

    }

    private void setViewsChecked()  {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        String diet = sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_DIET);
        Set<String> intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME,
                SHARED_PREFERENCES_INTOLERANCES);

        if (diet != null) {
            if(diet.equals(NO_DIET))
                fragmentSearchBinding.noDiet.setChecked(true);
            if(diet.equals(GLUTEN_FREE))
                fragmentSearchBinding.glutenFree.setChecked(true);
            if(diet.equals(VEGETARIAN))
                fragmentSearchBinding.vegetarian.setChecked(true);
            if(diet.equals(VEGAN))
                fragmentSearchBinding.vegan.setChecked(true);
        }

        if (intolerances != null) {
            if (intolerances.contains(DAIRY)) {
                fragmentSearchBinding.dairy.setChecked(true);
            }
            if (intolerances.contains(GRAIN)) {
                fragmentSearchBinding.grain.setChecked(true);
            }
            if (intolerances.contains(EGG)) {
                fragmentSearchBinding.egg.setChecked(true);
            }
            if (intolerances.contains(PEANUT)) {
                fragmentSearchBinding.peanut.setChecked(true);
            }
            if (intolerances.contains(SEA_FOOD)) {
                fragmentSearchBinding.seaFood.setChecked(true);
            }
            if (intolerances.contains(SESAME)) {
                fragmentSearchBinding.sesame.setChecked(true);
            }
            if (intolerances.contains(SHELL_FISH)) {
                fragmentSearchBinding.shellFish.setChecked(true);
            }
            if (intolerances.contains(SOY)) {
                fragmentSearchBinding.soy.setChecked(true);
            }
            if (intolerances.contains(SULFITE)) {
                fragmentSearchBinding.sulfite.setChecked(true);
            }
            if (intolerances.contains(TREE_NUT)) {
                fragmentSearchBinding.treeNut.setChecked(true);
            }
            if (intolerances.contains(WHEAT)) {
                fragmentSearchBinding.wheat.setChecked(true);
            }
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentSearchBinding = null;
    }
}