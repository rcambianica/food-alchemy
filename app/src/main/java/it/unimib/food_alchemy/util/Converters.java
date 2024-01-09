package it.unimib.food_alchemy.util;

import androidx.room.TypeConverter;

import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import it.unimib.food_alchemy.model.AnalyzedInstruction;
import it.unimib.food_alchemy.model.Ingredient;
import it.unimib.food_alchemy.model.InstructionStep;

public class Converters {
    @TypeConverter
    public static List<String> stringsFromString(String value) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static String fromIngredients(List<Ingredient> ingredients) {
        Gson gson = new Gson();
        String json = gson.toJson(ingredients);
        return json;
    }

    @TypeConverter
    public static List<Ingredient> ingredientsFromString(String value) {
        Type listType = new TypeToken<List<Ingredient>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static List<AnalyzedInstruction> instructionFromString(String value) {
        Type listType = new TypeToken<List<InstructionStep>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromInstruction(List<AnalyzedInstruction> obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }
}
