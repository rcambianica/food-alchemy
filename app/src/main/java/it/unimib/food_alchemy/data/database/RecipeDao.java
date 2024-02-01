package it.unimib.food_alchemy.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Recipe;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE type LIKE '%' || :type || '%' LIMIT 10 OFFSET :offset")
    List<Recipe> getRecipesByType(String type, int offset);

    @Query("SELECT * FROM recipes WHERE id = :id")
    Recipe getRecipe(long id);

    @Query("SELECT * FROM recipes WHERE is_favorite = 1")
    List<Recipe> getFavoriteRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertRecipesList(List<Recipe> recipes);

    @Insert
    void insertAllRecipes(Recipe... recipes);

    @Update
    int updateSingleFavoriteRecipe(Recipe recipe);

    @Update
    int updateListFavoritesRecipes(List<Recipe> recipes);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Delete
    void deleteAllRecipesWithoutQuery(Recipe... recipes);

    @Query("DELETE FROM recipes")
    int deleteAllRecipes();

    @Query("DELETE FROM recipes WHERE is_favorite = 0")
    void deleteNotFavoriteRecipes();

    /**
     * Query per la tabella degli ingredienti
     */

    @Query("SELECT * FROM fridge_items")
    List<Ingredient> getAllFridgeItems();

    @Query("SELECT * FROM fridge_items WHERE id = :id")
    Ingredient getFridgeItem(long id);

    @Query("SELECT * FROM fridge_items WHERE is_selected = 1")
    List<Ingredient> getFridgeItemsSelected();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertIngredientsList(List<Ingredient> ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertIngredient(Ingredient ingredient);

    @Insert
    void insertAllIngredients(Ingredient... ingredients);

    @Update
    int updateSingleFridgeItem(Ingredient ingredient);

    @Update
    int updateListFridgeItems(List<Ingredient> ingredients);

    @Delete
    void deleteIngredient(Ingredient ingredient);

    @Delete
    void deleteAllIngredientsWithoutQuery(Ingredient... ingredients);

    @Query("DELETE FROM fridge_items")
    int deleteAllFridgeItems();
}