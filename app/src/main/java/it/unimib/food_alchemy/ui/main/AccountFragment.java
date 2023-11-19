package it.unimib.food_alchemy.ui.main;

import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_DIET;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Set;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.data.repository.user.IUserRepository;
import it.unimib.food_alchemy.data.repository.user.UserRepository;
import it.unimib.food_alchemy.databinding.FragmentAccountBinding;
import it.unimib.food_alchemy.model.Result;
import it.unimib.food_alchemy.model.User;
import it.unimib.food_alchemy.ui.auth.UserViewModel;
import it.unimib.food_alchemy.ui.auth.UserViewModelFactory;
import it.unimib.food_alchemy.util.ServiceLocator;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private static final String TAG = AccountFragment.class.getSimpleName();
    private FragmentAccountBinding fragmentAccountBinding;


    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AccountFragment.
     */
    public static AccountFragment newInstance() { return new AccountFragment();}

    private UserViewModel userViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false);
        return  fragmentAccountBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentAccountBinding.userEmail.setText(userViewModel.getLoggedUser().getEmail());
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity().getApplication());

        String diet = sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_DIET);
        String[] intolerances = sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME,
                SHARED_PREFERENCES_INTOLERANCES).toArray(new String[0]);

        fragmentAccountBinding.textviewUserDiet.setText(diet);
        Log.d(TAG, intolerancesToString(intolerances));
        fragmentAccountBinding.textviewUserIntolerances.setText(intolerancesToString(intolerances));

        fragmentAccountBinding.buttonEdit.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate((R.id.action_accountFragment_to_preferencesActivity));
        });

        fragmentAccountBinding.buttonLogout.setOnClickListener(v -> {
            userViewModel.logout();
            Navigation.findNavController(requireView()).navigate(R.id.action_accountFragment_to_authActivity);
            /*userViewModel.logout().observe(getViewLifecycleOwner(), result -> {
                Log.d(TAG,"PROVA");
                if (result.isSuccess()) {

                    Navigation.findNavController(requireView()).navigate(
                            R.id.action_accountFragment_to_authActivity);
                } else {
                    Snackbar.make(view,
                            requireActivity().getString(R.string.unexpected_error),
                            Snackbar.LENGTH_SHORT).show();
                }
            });*/
        });
    }

    private String intolerancesToString(String[] array)   {
        String intolerances = "- ";
        for(int i = 0; i < array.length; i++) {
            intolerances = intolerances.concat(array[i]);
            if(i<array.length-1)
                intolerances = intolerances.concat("\n").concat("- ");
            Log.d(TAG,intolerances);
        }
        return intolerances;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAccountBinding = null;
    }
}