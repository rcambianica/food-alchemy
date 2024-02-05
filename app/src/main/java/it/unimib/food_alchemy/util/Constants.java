package it.unimib.food_alchemy.util;

public class Constants {

    //Constants for Spooncular.org
    public static final String NO_DIET = "No Diet";
    public static final String GLUTEN_FREE = "Gluten Free";
    public static final String VEGETARIAN = "Vegetarian";
    public static final String VEGAN = "Vegan";

    public static final String DAIRY = "Dairy";
    public static final String GRAIN = "Grain";
    public static final String EGG = "Egg";
    public static final String PEANUT = "Peanut";
    public static final String SEA_FOOD = "Sea Food";
    public static final String SESAME = "Sesame";
    public static final String SHELL_FISH = "Shell Fish";
    public static final String SOY = "Soy";
    public static final String SULFITE = "Sulfite";
    public static final String TREE_NUT = "Tree Nut";
    public static final String WHEAT = "Wheat";

    //Constants type dishes
    public static final String MAIN_COURSE = "main course";
    public static final String SIDE_DISH = "side dish";
    public static final String DESSERT = "dessert";
    public static final String APPETIZER = "appetizer";



    //Constants for SharedPreferences
    public static final String SHARED_PREFERENCES_FILE_NAME = "it.unimib.food_alchemy.preferences";
    public static final String SHARED_PREFERENCES_DIET = "diet";
    public static final String SHARED_PREFERENCES_INTOLERANCES = "intolerances";
    public static final String SHARED_PREFERENCES_RECIPES_FIRST_LOADING = "recipes_first_loading";
    public static final String SHARED_PREFERENCES_FRIDGE_ITEMS_FIRST_LOADING = "fridge_items_first_loading";

    // Constants for EncryptedSharedPreferences
    public static final String ENCRYPTED_SHARED_PREFERENCES_FILE_NAME = "it.unimib.food_alchemy.encrypted_preferences";
    public static final String NAME = "name";
    public static final String EMAIL_ADDRESS = "email_address";
    public static final String PASSWORD = "password";
    public static final String ID_TOKEN = "google_token";

    // Constants for encrypted files
    public static final String ENCRYPTED_DATA_FILE_NAME = "it.unimib.food_alchemy.encrypted_file.txt";

    //Constants for Spoonacular (https://api.spoonacular.com)
    public static final String SPOONACULAR_API_BASE_URL = "https://api.spoonacular.com/";
    public static final int RECIPES_NUMBER_PARAMETER = 10;

    public static final String LAST_UPDATE = "last_update";

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
    public static final String FIREBASE_USERS_COLLECTION = "users";
    public static final String FIREBASE_FAVORITE_RECIPES_COLLECTION = "favorite_recipes";
    public static final String FIREBASE_FRIDGE_ITEMS_COLLECTION = "fridge_items";
}
