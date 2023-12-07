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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.HomeRecipesRecylerViewAdapter;
import it.unimib.food_alchemy.databinding.FragmentHomeBinding;
import it.unimib.food_alchemy.model.HomeRecipeModel;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ArrayList<HomeRecipeModel> homeRecipeModels = new ArrayList<>();

    int[] homeRecipeImages = {R.drawable.appetizer};
    private boolean[] buttonState = {false, false, false, false};
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

    private void setHomeRecipeModels(){
        for(int i = 0; i < 10; i++){
            homeRecipeModels.add(new HomeRecipeModel("titolo " + i, "descrizione", homeRecipeImages[0]));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container, false);
        return fragmentHomeBinding.getRoot();
    }

    private void toggleButtonChecked(final int button) {
        switch (button) {
            case 0:
                if (buttonState[button]) {
                fragmentHomeBinding.bottoneMainCourse.setBackgroundResource(R.drawable.custom_button);
                } else {
                fragmentHomeBinding.bottoneMainCourse.setBackgroundResource(R.drawable.custom_button_checked);
                }
                buttonState[button] = !buttonState[button];
                break;
            case 1:
                if (buttonState[button]) {
                    fragmentHomeBinding.bottoneSideDish.setBackgroundResource(R.drawable.custom_button);
                } else {
                    fragmentHomeBinding.bottoneSideDish.setBackgroundResource(R.drawable.custom_button_checked);
                }
                buttonState[button] = !buttonState[button];
                break;
            case 2:
                if (buttonState[button]) {
                    fragmentHomeBinding.bottoneDessert.setBackgroundResource(R.drawable.custom_button);
                } else {
                    fragmentHomeBinding.bottoneDessert.setBackgroundResource(R.drawable.custom_button_checked);
                }
                buttonState[button] = !buttonState[button];
                break;
            case 3:
                if (buttonState[button]) {
                    fragmentHomeBinding.bottoneAppetizer.setBackgroundResource(R.drawable.custom_button);
                } else {
                    fragmentHomeBinding.bottoneAppetizer.setBackgroundResource(R.drawable.custom_button_checked);
                }
                buttonState[button] = !buttonState[button];
                break;
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recipesRecyclerView = fragmentHomeBinding.recipesRecyclerView;

        setHomeRecipeModels();

        HomeRecipesRecylerViewAdapter adapter = new HomeRecipesRecylerViewAdapter(requireContext(), homeRecipeModels);
        recipesRecyclerView.setAdapter(adapter);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        fragmentHomeBinding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_searchFragment);
            }
        });
        fragmentHomeBinding.bottoneMainCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "bottone main course cliccato");
                toggleButtonChecked(0);
            }
        });
        fragmentHomeBinding.bottoneSideDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "bottone side dish cliccato");
                toggleButtonChecked(1);
            }
        });
        fragmentHomeBinding.bottoneDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "bottone dessert cliccato");
                toggleButtonChecked(2);
            }
        });
        fragmentHomeBinding.bottoneAppetizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "bottone appetizer cliccato");
                toggleButtonChecked(3);
            }
        });
    }




    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentHomeBinding = null;
    }
}