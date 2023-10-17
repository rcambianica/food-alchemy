package it.unimib.food_alchemy.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import it.unimib.food_alchemy.R;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = AuthActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}