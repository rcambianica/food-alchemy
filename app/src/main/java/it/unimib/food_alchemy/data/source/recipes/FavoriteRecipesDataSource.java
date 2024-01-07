package it.unimib.food_alchemy.data.source.recipes;

import static it.unimib.food_alchemy.util.Constants.FIREBASE_FAVORITE_RECIPES_COLLECTION;
import static it.unimib.food_alchemy.util.Constants.FIREBASE_USERS_COLLECTION;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import it.unimib.food_alchemy.model.Recipe;

public class FavoriteRecipesDataSource extends BaseFavoriteRecipesDataSource    {

    private static final String TAG = FavoriteRecipesDataSource.class.getSimpleName();

    private final DatabaseReference databaseReference;
    private final String idToken;

    public FavoriteRecipesDataSource(String idToken)    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().getRef();
        this.idToken = idToken;
    }

    @Override
    public void getFavoriteRecipes() {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_RECIPES_COLLECTION).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d(TAG, "Error getting data", task.getException());
                    }
                    else {
                        Log.d(TAG, "Successful read: " + task.getResult().getValue());

                        List<Recipe> recipesList = new ArrayList<>();
                        for(DataSnapshot ds : task.getResult().getChildren()) {
                            Recipe recipe = ds.getValue(Recipe.class);
                            recipe.setSynchronized(true);
                            recipesList.add(recipe);
                        }

                        recipesCallback.onSuccessFromCloudReading(recipesList);
                    }
                });
    }

    @Override
    public void addFavoriteRecipe(Recipe recipe) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_RECIPES_COLLECTION).child(String.valueOf(recipe.hashCode())).setValue(recipe)
                .addOnSuccessListener(unused -> {
                    recipe.setSynchronized(true);
                    recipesCallback.onSuccessFromCloudWriting(recipe);
                })
                .addOnFailureListener(e -> recipesCallback.onFailureFromCloud(e));
    }

    @Override
    public void synchronizeFavoriteRecipes(List<Recipe> notSynchronizedRecipesList) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_RECIPES_COLLECTION).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Recipe> recipesList = new ArrayList<>();
                        for (DataSnapshot ds : task.getResult().getChildren()) {
                            Recipe recipe = ds.getValue(Recipe.class);
                            recipe.setSynchronized(true);
                            recipesList.add(recipe);
                        }

                        recipesList.addAll(notSynchronizedRecipesList);

                        for (Recipe recipe : recipesList) {
                            databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                                    child(FIREBASE_FAVORITE_RECIPES_COLLECTION).
                                    child(String.valueOf(recipe.hashCode())).setValue(recipe).addOnSuccessListener(
                                            unused -> recipe.setSynchronized(true)
                                    );
                        }
                    }
                });
    }

    @Override
    public void deleteFavoriteRecipe(Recipe recipe) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_RECIPES_COLLECTION).child(String.valueOf(recipe.hashCode())).
                removeValue().addOnSuccessListener(unused -> {
                    recipe.setSynchronized(false);
                    recipesCallback.onSuccessFromCloudWriting(recipe);
                }).addOnFailureListener(e -> {
                    recipesCallback.onFailureFromCloud(e);
                });
    }

    @Override
    public void deleteAllFavoriteRecipes() {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_RECIPES_COLLECTION).removeValue().addOnSuccessListener(unused -> {
                    recipesCallback.onSuccessFromCloudWriting(null);
                }).addOnFailureListener(e -> {
                    recipesCallback.onFailureFromCloud(e);
                });
    }
}
