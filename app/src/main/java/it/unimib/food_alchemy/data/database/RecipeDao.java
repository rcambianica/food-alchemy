package it.unimib.food_alchemy.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.food_alchemy.model.Recipe;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY id")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipes WHERE id = :id")
    Recipe getRecipeById(long id);

    @Query("SELECT * FROM recipes WHERE is_favorite = true ORDER BY id")
    List<Recipe> getFavoriteRecipes();

    @Query("UPDATE recipes SET is_favorite = :is_favorite WHERE id = :id")
    int updateRecipeIsFavorite(long id, boolean is_favorite);
}
