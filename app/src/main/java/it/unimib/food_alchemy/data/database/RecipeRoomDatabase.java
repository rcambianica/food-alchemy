package it.unimib.food_alchemy.data.database;

import static it.unimib.food_alchemy.util.Constants.DATABASE_VERSION;
import static it.unimib.food_alchemy.util.Constants.RECIPES_DATABASE_NAME;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.Recipe;
import it.unimib.food_alchemy.util.Converters;

@Database(entities = {Recipe.class, Ingredient.class}, version = DATABASE_VERSION)
@TypeConverters({Converters.class})
public abstract class RecipeRoomDatabase extends RoomDatabase{

    public abstract RecipeDao recipeDao();

    private static volatile RecipeRoomDatabase INSTANCE;
    private static final int NUMBERS_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBERS_OF_THREADS);

    public static RecipeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null)   {
            synchronized (RecipeRoomDatabase.class) {
                if(INSTANCE == null)    {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeRoomDatabase.class,RECIPES_DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
