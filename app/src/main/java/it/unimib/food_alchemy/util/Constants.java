package it.unimib.food_alchemy.util;

public class Constants {
    public static final String APP_DATA_FILE = "app_data.txt";

    //Constants for Spooncular.org
    public static final String NONE = "None";
    public static final String GLUTEN_FREE = "Gluten Free";
    public static final String VEGETARIAN = "Vegetarian";
    public static final String VEGAN = "Vegan";

    public static final String DAIRY = "dairy";
    public static final String GRAIN = "grain";
    public static final String EGG = "egg";
    public static final String PEANUT = "peanut";
    public static final String SEA_FOOD = "sea_food";
    public static final String SESAME = "sesame";
    public static final String SHELL_FISH = "shell_fish";
    public static final String SOY = "soy";
    public static final String SULFITE = "sulfite";
    public static final String TREE_NUT = "tree_nut";
    public static final String WHEAT = "wheat";

    //Constants for SharedPreferences
    public static final String SHARED_PREFERENCES_FILE_NAME = "it.unimib.food_alchemy.preferences";
    public static final String SHARED_PREFERENCES_DIET = "diet";
    public static final String SHARED_PREFERENCES_INTOLERANCES = "intolerances";

    // Constants for EncryptedSharedPreferences
    public static final String ENCRYPTED_SHARED_PREFERENCES_FILE_NAME = "it.unimib.food_alchemy.encrypted_preferences";
    public static final String EMAIL_ADDRESS = "email_address";
    public static final String PASSWORD = "password";
    // Constants for encrypted files
    public static final String ENCRYPTED_DATA_FILE_NAME = "it.unimib.food_alchemy.encrypted_file.txt";

    //Constants for Spoonacular (https://api.spoonacular.com)
    public static final String SPOONACULAR_API_BASE_URL = "https://api.spoonacular.com/";
    public static final String RECIPES_ENDPOINT = "recipes/complexSearch";
    public static final String RECIPES_QUERY_PARAMETER = "query";
    public static final int RECIPES_NUMBER_PARAMETER = 100;

    // Constants for Room database
    public static final String RECIPES_DATABASE_NAME = "recipes_db";
    public static final int DATABASE_VERSION = 1;

    // Constants for managing errors
    public static final String RETROFIT_ERROR = "retrofit_error";
    public static final String API_KEY_ERROR = "api_key_error";
    public static final String UNEXPECTED_ERROR = "unexpected_error";
    public static final String INVALID_USER_ERROR = "invalidUserError";
    public static final String INVALID_CREDENTIALS_ERROR = "invalidCredentials";
    public static final String USER_COLLISION_ERROR = "userCollisionError";
    public static final String WEAK_PASSWORD_ERROR = "passwordIsWeak";

    public static final int MINIMUM_PASSWORD_LENGTH = 6;

    // Constants for Firebase Realtime Database
    public static final String FIREBASE_REALTIME_DATABASE = "";
    public static final String FIREBASE_USERS_COLLECTION = "users";
    public static final String FIREBASE_FAVORITE_RECIPES_COLLECTION = "favorite_recipes";
}
