package it.unimib.food_alchemy.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.food_alchemy.adapter.FavoritesRecyclerViewAdapter;
import it.unimib.food_alchemy.databinding.FragmentFavoritesBinding;
import it.unimib.food_alchemy.model.Favorites;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding fragmentFavoritesBinding;
    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
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
        fragmentFavoritesBinding = FragmentFavoritesBinding.inflate(inflater,container,false);
        return fragmentFavoritesBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewFavorites = fragmentFavoritesBinding.favoritesRecyclerView;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerViewFavorites.setLayoutManager(layoutManager);

        List<Favorites> favArray = new ArrayList<>();
        for(int i=0; i<100; i++){
            favArray.add(new Favorites("Titolo"+ i, "decription"+i, null));
        }

        FavoritesRecyclerViewAdapter adapter= new FavoritesRecyclerViewAdapter(favArray, new FavoritesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onFavClick(Favorites favorites) {
                Snackbar.make(view,favorites.getTitle(),Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteButtonPressed(int position) {
                Snackbar.make(view,"Size"+favArray.size(),Snackbar.LENGTH_SHORT).show();
            }
        });
        recyclerViewFavorites.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentFavoritesBinding = null;
    }
}