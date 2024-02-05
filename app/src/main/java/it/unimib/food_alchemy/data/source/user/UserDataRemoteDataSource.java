package it.unimib.food_alchemy.data.source.user;

import static it.unimib.food_alchemy.util.Constants.FIREBASE_FAVORITE_RECIPES_COLLECTION;
import static it.unimib.food_alchemy.util.Constants.FIREBASE_USERS_COLLECTION;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_DIET;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;

import android.util.Log;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.model.User;
import it.unimib.food_alchemy.util.SharedPreferencesUtil;

public class UserDataRemoteDataSource extends BaseUserDataRemoteDataSource{
    private static final String TAG = UserDataRemoteDataSource.class.getSimpleName();

    private final DatabaseReference databaseReference;
    private final SharedPreferencesUtil sharedPreferencesUtil;

    public UserDataRemoteDataSource(SharedPreferencesUtil sharedPreferencesUtil) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().getRef();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void saveUserData(User user) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getIdToken()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d(TAG, "User already present in Firebase Realtime Database");
                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                } else {
                    Log.d(TAG, "User not present in Firebase Realtime Database");
                    databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getIdToken()).setValue(user)
                            .addOnSuccessListener(aVoid -> userResponseCallback.onSuccessFromRemoteDatabase(user))
                            .addOnFailureListener(e -> userResponseCallback.onFailureFromRemoteDatabase(e.getLocalizedMessage()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                userResponseCallback.onFailureFromRemoteDatabase(error.getMessage());
            }
        });
    }

    @Override
    public void getUserFavoriteRecipes(String idToken) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_RECIPES_COLLECTION).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d(TAG, "Error getting data", task.getException());
                        userResponseCallback.onFailureFromRemoteDatabase(Objects.requireNonNull(task.getException()).getLocalizedMessage());
                    }
                    else {
                        Log.d(TAG, "Successful recipes read: " + task.getResult().getValue());

                        List<Recipe> recipesList = new ArrayList<>();
                        for(DataSnapshot ds : task.getResult().getChildren()) {
                            Recipe recipe = ds.getValue(Recipe.class);
                            recipesList.add(recipe);
                        }

                        userResponseCallback.onSuccessFromGettingUserFavoritesRecipes(recipesList);
                    }
                });
    }

    @Override
    public void getUserPreferences(String idToken) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(SHARED_PREFERENCES_DIET).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String diet = task.getResult().getValue(String.class);
                        sharedPreferencesUtil.writeStringData(
                                SHARED_PREFERENCES_FILE_NAME,
                                SHARED_PREFERENCES_DIET,
                                diet);

                        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                                child(SHARED_PREFERENCES_INTOLERANCES).get().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        List<String> intolerances = new ArrayList<>();
                                        for(DataSnapshot ds : task1.getResult().getChildren()) {
                                            String intolerance = ds.getValue(String.class);
                                            intolerances.add(intolerance);
                                        }

                                        if (intolerances.size() > 0) {
                                            Set<String> intolerancesSet = new HashSet<>(intolerances);
                                            intolerancesSet.addAll(intolerances);

                                            sharedPreferencesUtil.writeStringSetData(
                                                    SHARED_PREFERENCES_FILE_NAME,
                                                    SHARED_PREFERENCES_INTOLERANCES,
                                                    intolerancesSet);
                                        }
                                        userResponseCallback.onSuccessFromGettingUserPreferences();
                                    }
                                });
                    }
                });
    }

    @Override
    public void saveUserPreferences(String diet, Set<String> intolerances, String idToken) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(SHARED_PREFERENCES_DIET).setValue(diet);

        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(SHARED_PREFERENCES_INTOLERANCES).setValue(new ArrayList<>(intolerances));
    }
}
