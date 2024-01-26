package it.unimib.food_alchemy.data.source.recipes;

import static it.unimib.food_alchemy.util.Constants.FIREBASE_FAVORITE_RECIPES_COLLECTION;
import static it.unimib.food_alchemy.util.Constants.FIREBASE_FRIDGE_ITEMS_COLLECTION;
import static it.unimib.food_alchemy.util.Constants.FIREBASE_USERS_COLLECTION;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import it.unimib.food_alchemy.model.Ingredient;
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
                        Log.d(TAG, "Successful recipes read: " + task.getResult().getValue());

                        List<Recipe> recipesList = new ArrayList<>();
                        for(DataSnapshot ds : task.getResult().getChildren()) {
                            Recipe recipe = ds.getValue(Recipe.class);
                            recipe.setSynchronized(true);
                            recipesList.add(recipe);
                        }

                        recipesCallback.onSuccessFromCloudReadingFavoriteRecipes(recipesList);
                    }
                });
    }

    @Override
    public void addFavoriteRecipe(Recipe recipe) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_RECIPES_COLLECTION).child(String.valueOf(recipe.getId())).setValue(recipe)
                .addOnSuccessListener(unused -> {
                    recipe.setSynchronized(true);
                    recipesCallback.onSuccessFromCloudWritingFavoriteRecipes(recipe);
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
                                    child(String.valueOf(recipe.getId())).setValue(recipe).addOnSuccessListener(
                                            unused -> recipe.setSynchronized(true)
                                    );
                        }
                    }
                });
    }

    @Override
    public void deleteFavoriteRecipe(Recipe recipe) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_RECIPES_COLLECTION).child(String.valueOf(recipe.getId())).
                removeValue().addOnSuccessListener(unused -> {
                    recipe.setSynchronized(false);
                    recipesCallback.onSuccessFromCloudWritingFavoriteRecipes(recipe);
                }).addOnFailureListener(e -> {
                    recipesCallback.onFailureFromCloud(e);
                });
    }

    @Override
    public void deleteAllFavoriteRecipes() {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FAVORITE_RECIPES_COLLECTION).removeValue().addOnSuccessListener(unused -> {
                    recipesCallback.onSuccessFromCloudWritingFavoriteRecipes(null);
                }).addOnFailureListener(e -> {
                    recipesCallback.onFailureFromCloud(e);
                });
    }

    @Override
    public void getFridgeItems() {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FRIDGE_ITEMS_COLLECTION).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d(TAG, "Error getting data", task.getException());
                    }
                    else {
                        Log.d(TAG, "Successful read: " + task.getResult().getValue());

                        List<Ingredient> ingredientList = new ArrayList<>();
                        for(DataSnapshot ds : task.getResult().getChildren()) {
                            Ingredient ingredient = ds.getValue(Ingredient.class);
                            ingredient.setSynchronized(true);
                            ingredientList.add(ingredient);
                        }

                        recipesCallback.onSuccessFromCloudReadingFridgeItems(ingredientList);
                    }
                });
    }

    @Override
    public void addFridgeItem(Ingredient ingredient) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FRIDGE_ITEMS_COLLECTION).child(String.valueOf(ingredient.getId())).setValue(ingredient)
                .addOnSuccessListener(unused -> {
                    ingredient.setSynchronized(true);
                    recipesCallback.onSuccessFromCloudWritingFridgeItems(ingredient);
                })
                .addOnFailureListener(e -> recipesCallback.onFailureFromCloud(e));
    }

    @Override
    public void deleteFridgeItem(Ingredient ingredient) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FRIDGE_ITEMS_COLLECTION).child(String.valueOf(ingredient.getId())).
                removeValue().addOnSuccessListener(unused -> {
                    ingredient.setSynchronized(false);
                    recipesCallback.onSuccessFromCloudWritingFridgeItems(ingredient);
                }).addOnFailureListener(e -> {
                    recipesCallback.onFailureFromCloud(e);
                });
    }

    @Override
    public void deleteAllFridgeItems() {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FRIDGE_ITEMS_COLLECTION).removeValue().addOnSuccessListener(unused -> {
                    recipesCallback.onSuccessFromCloudWritingFridgeItems(null);
                }).addOnFailureListener(e -> {
                    recipesCallback.onFailureFromCloud(e);
                });
    }
}
