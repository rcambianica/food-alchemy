package it.unimib.food_alchemy.ui.main;

import static it.unimib.food_alchemy.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.ID_TOKEN;
import static it.unimib.food_alchemy.util.Constants.SHARED_FRIDGE_ITEMS_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_FRIDGE_ITEMS_FIRST_LOADING;
import static it.unimib.food_alchemy.util.Constants.SHARED_FRIDGE_ITEMS_FRIDGE;

import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.IngredientsRecyclerViewAdapter;
import it.unimib.food_alchemy.data.repository.user.IUserRepository;
import it.unimib.food_alchemy.databinding.FragmentFridgeBinding;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.ui.auth.UserViewModel;
import it.unimib.food_alchemy.ui.auth.UserViewModelFactory;
import it.unimib.food_alchemy.util.DataEncryptionUtil;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FridgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FridgeFragment extends Fragment {

    private static final String TAG = FridgeFragment.class.getSimpleName();
    private FragmentFridgeBinding fragmentFridgeBinding;
    private List<Ingredient> ingredientList;

    //serve per recuperare un ingrediente eliminato per sbaglio con lo swipe a sinistra
    private Ingredient deletedIngredient = null;
    private IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter;

    private UserViewModel userViewModel;
    public FridgeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FridgeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FridgeFragment newInstance() {
        return new FridgeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(getActivity().getApplication());
        userViewModel = new ViewModelProvider(
                this, new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        /*userViewModel.getUserFridgeItems(userViewModel.getLoggedUser().getIdToken()).observe(this, result -> {

        });*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFridgeBinding = FragmentFridgeBinding.inflate(inflater,container,false);
        return fragmentFridgeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerViewIngredients = fragmentFridgeBinding.recyclerviewIngredients;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);

        ingredientList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            if(i%2 == 0)
                ingredientList.add(new Ingredient("cheese "+i,"20231128"));
            else
                ingredientList.add(new Ingredient("potato "+i,"20231213"));
        }

        ingredientList.add(new Ingredient("chicken", "20231215"));

        ingredientsRecyclerViewAdapter =
                new IngredientsRecyclerViewAdapter(ingredientList, new IngredientsRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onIngredientsItemClick(Ingredient ingredient) {
                        Snackbar.make(view, ingredient.getTitle(), Snackbar.LENGTH_SHORT).show();
                    }
                });
        recyclerViewIngredients.setLayoutManager(layoutManager);
        recyclerViewIngredients.setAdapter(ingredientsRecyclerViewAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewIngredients);

        fragmentFridgeBinding.addIngredientButton.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_fridgeFragment_to_addIngredientFragment);
        });
        saveInformation();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getBindingAdapterPosition();
            deletedIngredient = new Ingredient(ingredientList.get(pos).getTitle(), ingredientList.get(pos).getExpiryDate());
            ingredientList.remove(pos);
            ingredientsRecyclerViewAdapter.notifyItemRemoved(pos);
            Snackbar.make(requireView(),deletedIngredient.getTitle(),Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingredientList.add(pos,deletedIngredient);
                    ingredientsRecyclerViewAdapter.notifyItemInserted(pos);
                }
            }).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(requireContext(), R.color.md_theme_light_error))
                    .addActionIcon(R.drawable.baseline_delete_24)
                    .addCornerRadius(1,5)
                    .addPadding(1,6,6,6)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    private void saveInformation()  {
        Set<String> fridgeItems = new HashSet<>();
        for(int i = 0; i < ingredientList.size(); i++)  {
            fridgeItems.add(ingredientList.get(i).getTitle()+" expiry_date: "+ingredientList.get(i).getExpiryDate());
        }

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity().getApplication());
        sharedPreferencesUtil.writeStringSetData(
                SHARED_FRIDGE_ITEMS_FILE_NAME, SHARED_FRIDGE_ITEMS_FRIDGE, fridgeItems);
        sharedPreferencesUtil.writeBooleanData(SHARED_FRIDGE_ITEMS_FILE_NAME,
                SHARED_FRIDGE_ITEMS_FIRST_LOADING, true);

        DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(getActivity());
        String idToken = null;
        try {
            idToken = dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ID_TOKEN);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        //userViewModel.saveUserFridgeItems(fridgeItems,idToken);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentFridgeBinding = null;
    }
}