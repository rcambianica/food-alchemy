package it.unimib.food_alchemy.data.source.user;

import static it.unimib.food_alchemy.util.Constants.FIREBASE_FAVORITE_RECIPES_COLLECTION;
import static it.unimib.food_alchemy.util.Constants.FIREBASE_USERS_COLLECTION;
import static it.unimib.food_alchemy.util.Constants.SHARED_FRIDGE_ITEMS_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_FRIDGE_ITEMS_FRIDGE;
import static it.unimib.food_alchemy.util.Constants.SHARED_FRIDGE_ITEMS_TITLE;
import static it.unimib.food_alchemy.util.Constants.SHARED_FRIGDE_ITEMS_EXPIRY_DATE;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_DIET;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.food_alchemy.util.Constants.SHARED_PREFERENCES_INTOLERANCES;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.common.reflection.qual.NewInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.food_alchemy.model.Ingredient;
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
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    userResponseCallback.onFailureFromRemoteDatabase(e.getLocalizedMessage());
                                }
                            });
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
                        userResponseCallback.onFailureFromRemoteDatabase(task.getException().getLocalizedMessage());
                    }
                    else {
                        Log.d(TAG, "Successful read: " + task.getResult().getValue());

                        List<Recipe> recipesList = new ArrayList<>();
                        for(DataSnapshot ds : task.getResult().getChildren()) {
                            Recipe recipe = ds.getValue(Recipe.class);
                            recipesList.add(recipe);
                        }

                        userResponseCallback.onSuccessFromRemoteDatabase(recipesList);
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
                                child(SHARED_PREFERENCES_INTOLERANCES).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<String> intolerances = new ArrayList<>();
                                            for(DataSnapshot ds : task.getResult().getChildren()) {
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

    @Override
    public void getUserFridgeItems(String idToken)  {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(SHARED_FRIDGE_ITEMS_FRIDGE).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                                child(SHARED_FRIDGE_ITEMS_FRIDGE).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<Ingredient> ingredientList = new ArrayList<>();
                                            Ingredient ingredient = new Ingredient(null,null);
                                            for(DataSnapshot ds: task.getResult().getChildren())    {
                                                ingredient.setTitle(ds.child(SHARED_FRIDGE_ITEMS_TITLE).getValue(String.class));
                                                ingredient.setExpiryDate(ds.child(SHARED_FRIGDE_ITEMS_EXPIRY_DATE).getValue(String.class));
                                                ingredientList.add(ingredient);
                                            }
                                            if (ingredientList.size() > 0) {
                                                Set<String> fridgeItemsSet = new HashSet<>();
                                                for(int i = 0; i < ingredientList.size(); i++)  {
                                                    fridgeItemsSet.add(ingredientList.get(i).getTitle()+" - "+ingredientList.get(i).getExpiryDate());
                                                }
                                                sharedPreferencesUtil.writeStringSetData(
                                                        SHARED_FRIDGE_ITEMS_FILE_NAME,
                                                        SHARED_FRIDGE_ITEMS_FRIDGE,
                                                        fridgeItemsSet);
                                            }
                                            userResponseCallback.onSuccessFromGettingUserFridgeItems();
                                        }
                                    }
                                });
                    }
                });
    }

    @Override
    public void saveUserFridgeItems(List<Ingredient> ingredientList, String idToken)   {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(SHARED_FRIDGE_ITEMS_FRIDGE).setValue(new ArrayList<>(ingredientList));
    }
}
