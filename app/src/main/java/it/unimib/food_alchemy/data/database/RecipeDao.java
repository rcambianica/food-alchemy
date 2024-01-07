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
    List<Recipe> getAll();

    @Query("SELECT * FROM recipes WHERE id = :id")
    Recipe getRecipeById(long id);

    @Query("SELECT * FROM recipes ORDER BY RANDOM() LIMIT 1")
    Recipe getRandomRecipe();

    @Query("SELECT * FROM recipes WHERE is_favorite = true ORDER BY title")
    List<Recipe> getFavoriteRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Recipe> recipes);

    @Update
    int updateRecipe(Recipe recipe);

    @Update
    int updateRecipes(List<Recipe> recipes);

    @Query("UPDATE recipes SET is_favorite = false WHERE is_favorite = true")
    void deleteFavoriteRecipes();

    @Query("DELETE FROM recipes")
    int deleteAll();
}