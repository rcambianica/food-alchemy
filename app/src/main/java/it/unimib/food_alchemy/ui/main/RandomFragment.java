package it.unimib.food_alchemy.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.adapter.HomeRecipesRecylerViewAdapter;
import it.unimib.food_alchemy.databinding.FragmentRandomBinding;
import it.unimib.food_alchemy.model.HomeRecipeModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RandomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RandomFragment extends Fragment {

    ArrayList<HomeRecipeModel> homeRecipeModels = new ArrayList<>();

    int[] homeRecipeImages = {R.drawable.arrosto};

    private FragmentRandomBinding fragmentRandomBinding;

    public RandomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RandomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RandomFragment newInstance() {
        return new RandomFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentRandomBinding = FragmentRandomBinding.inflate(inflater, container, false);
        return fragmentRandomBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        RecyclerView randomRecipesRecyclerView = fragmentRandomBinding.randomRecyclerview;

        setRandomRecipeModels();

        HomeRecipesRecylerViewAdapter adapter = new HomeRecipesRecylerViewAdapter(requireContext(), homeRecipeModels, new HomeRecipesRecylerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemCLick(int position) {
                Navigation.findNavController(requireView()).navigate(R.id.action_randomFragment_to_recipeFragment);
            }
        });
        randomRecipesRecyclerView.setAdapter(adapter);
        randomRecipesRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    }

    private void setRandomRecipeModels(){
        for(int i = 0; i < 10; i++){
            homeRecipeModels.add(new HomeRecipeModel("Arrosto al forno " + i, homeRecipeImages[0]));
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        fragmentRandomBinding = null;
    }
}