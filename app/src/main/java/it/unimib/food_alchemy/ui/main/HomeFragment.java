package it.unimib.food_alchemy.ui.main;

import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_DIET;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.data.repository.recipes.IRecipesRepository;
import it.unimib.food_alchemy.data.service.SpoonacularApiService;
import it.unimib.food_alchemy.databinding.FragmentHomeBinding;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.RecipesResponse;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.util.Constants;
import it.unimib.food_alchemy.util.ErrorMessagesUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;


public class HomeFragment extends Fragment {
    private final boolean[] buttonState = {false, false, false, false};
    private static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding fragmentHomeBinding;
    private RecipeViewModel recipeViewModel;
    private int ran;
    private List<Recipe> randomRecipes;
    private SharedPreferencesUtil sharedPreferencesUtil;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());
        IRecipesRepository recipesRepository =
                ServiceLocator.getInstance().getRecipesRepository(requireActivity().getApplication());

        ran = (int) Math.floor(Math.random() * (30));

        if (recipesRepository != null) {
            recipeViewModel = new ViewModelProvider(requireActivity(),
                    new RecipeViewModelFactory(recipesRepository)).get(RecipeViewModel.class);
        } else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.unexpected_error), Snackbar.LENGTH_SHORT).show();
        }
        recipeViewModel.setOffset(ran);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return fragmentHomeBinding.getRoot();
    }

    // Method to toggle the state of buttons based on user interaction
    private void toggleButtonChecked(final int clickedButton) {
        Button[] buttons = {
                fragmentHomeBinding.bottoneMainCourse,
                fragmentHomeBinding.bottoneSideDish,
                fragmentHomeBinding.bottoneDessert,
                fragmentHomeBinding.bottoneAppetizer
        };

        for (int i = 0; i < buttons.length; i++) {
            if (i == clickedButton) {
                // Toggle the state for the clicked button
                buttonState[i] = !buttonState[i];
                if (buttonState[i]) {
                    buttons[i].setBackgroundResource(R.drawable.custom_button_checked);
                } else {
                    buttons[i].setBackgroundResource(R.drawable.custom_button);
                }
            } else {
                // Turn off other buttons
                buttonState[i] = false;
                buttons[i].setBackgroundResource(R.drawable.custom_button);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve diet and intolerances from shared preferences
        String diet;
        Set<String> intolerances;
        diet = sharedPreferencesUtil.readStringData(SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_DIET);
        intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_INTOLERANCES);

        // Setting the random dish suggested on the initial home page
        recipeViewModel.getRandomRecipe(diet, intolerances).observe(getViewLifecycleOwner(),
                result -> {
                    if (result != null) {
                        if (result.isSuccess()) {
                            RecipesResponse recipesResponse = ((Result.RecipesResponseSuccess) result).getData();
                            randomRecipes = recipesResponse.getRecipesList();
                            if (randomRecipes.size() != 0) {
                                Glide.with(fragmentHomeBinding.dayImage.getContext())
                                        .load(randomRecipes.get(0).getImageUrl())
                                        .placeholder(R.drawable.baseline_access_time_24)
                                        .into(fragmentHomeBinding.dayImage);
                                fragmentHomeBinding.dayTitle.setText(randomRecipes.get(0).getTitle());
                                return;
                            }
                        }
                    }
                    ErrorMessagesUtil errorMessagesUtil =
                            new ErrorMessagesUtil(requireActivity().getApplication());
                    Snackbar.make(view, errorMessagesUtil.
                                    getErrorMessage(((Result.Error) result).getMessage()),
                            Snackbar.LENGTH_SHORT).show();
                });

        // Opening the RecipeFragment by clicking the suggested dish
        fragmentHomeBinding.dayImage.setOnClickListener(v -> {
            HomeFragmentDirections.ActionHomeFragmentToRecipeFragment action =
                    HomeFragmentDirections.actionHomeFragmentToRecipeFragment(randomRecipes.get(0));
            Navigation.findNavController(view).navigate(action);
        });

        // Going to the SearchFragment by clicking the searchButton
        fragmentHomeBinding.searchButton.setOnClickListener(v ->
                Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_searchActivity));

        // Adding the onClick to every button in the discovery with the right "type" for the incoming call for the API
        fragmentHomeBinding.bottoneMainCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "bottone main course cliccato");
                toggleButtonChecked(0);
                HomeFragmentDirections.ActionHomeFragmentToRandomFragment action =
                        HomeFragmentDirections.actionHomeFragmentToRandomFragment("main course");
                Navigation.findNavController(view).navigate(action);
            }
        });
        fragmentHomeBinding.bottoneSideDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "bottone side dish cliccato");
                toggleButtonChecked(1);
                HomeFragmentDirections.ActionHomeFragmentToRandomFragment action =
                        HomeFragmentDirections.actionHomeFragmentToRandomFragment("side dish");
                Navigation.findNavController(view).navigate(action);
            }
        });
        fragmentHomeBinding.bottoneDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "bottone dessert cliccato");
                toggleButtonChecked(2);
                HomeFragmentDirections.ActionHomeFragmentToRandomFragment action =
                        HomeFragmentDirections.actionHomeFragmentToRandomFragment("dessert");
                Navigation.findNavController(view).navigate(action);
            }
        });
        fragmentHomeBinding.bottoneAppetizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "bottone appetizer cliccato");
                toggleButtonChecked(3);
                HomeFragmentDirections.ActionHomeFragmentToRandomFragment action =
                        HomeFragmentDirections.actionHomeFragmentToRandomFragment("appetizer");
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentHomeBinding = null;
    }
}