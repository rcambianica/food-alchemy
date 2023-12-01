package it.unimib.food_alchemy.ui.main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import it.unimib.food_alchemy.R;
import it.unimib.food_alchemy.databinding.FragmentAddIngredientBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddIngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIngredientFragment extends Fragment {

    private FragmentAddIngredientBinding fragmentAddIngredientBinding;
    private DatePickerDialog datePickerDialog;

    public AddIngredientFragment()  {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AddIngredientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddIngredientFragment newInstance() {
        return new AddIngredientFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAddIngredientBinding = FragmentAddIngredientBinding.inflate(inflater,container,false);
        return fragmentAddIngredientBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDatePicker();
        Log.d("TAG", getTodaysDate());
        fragmentAddIngredientBinding.spinnerButton.setText(getTodaysDate());
        fragmentAddIngredientBinding.spinnerButton.setOnClickListener(v -> datePickerDialog.show());
        fragmentAddIngredientBinding.returnButton.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_addIngredientFragment_to_fridgeFragment));
        fragmentAddIngredientBinding.saveButton.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_addIngredientFragment_to_fridgeFragment);
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            fragmentAddIngredientBinding.spinnerButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        SimpleDateFormat dayOfMonthFormat = new SimpleDateFormat("dd", Locale.getDefault());
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int style;
        // Verifica il tema corrente
        if (currentTheme == Configuration.UI_MODE_NIGHT_YES) {
            style = AlertDialog.THEME_HOLO_DARK;
        } else {
            style = AlertDialog.THEME_HOLO_LIGHT;
        }
        datePickerDialog = new DatePickerDialog(requireContext(), style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
    }

    private String makeDateString(int day, int month, int year) {
        if(day > 0 && day < 10)
            return "0" + day + "/" + month + "/" + year;
        return day + "/" + month + "/" + year;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentAddIngredientBinding = null;
    }

}
