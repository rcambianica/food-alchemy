package it.unimib.food_alchemy.model;

public abstract class Result {
    private Result() {}

    public boolean isSuccess()  {
        if(this instanceof IngredientsResponseSuccess || this instanceof RecipesResponseSuccess || this instanceof UserResponseSuccess) {
            return true;
        } else {
            return false;
        }
    }

    public static final class RecipesResponseSuccess extends Result {
        private final RecipesResponse recipesResponse;
        public RecipesResponseSuccess(RecipesResponse recipesResponse)  {
            this.recipesResponse = recipesResponse;
        }
        public RecipesResponse getData() {
            return recipesResponse;
        }
    }

    public static final class IngredientsResponseSuccess extends Result {
        private final IngredientsResponse ingredientsResponse;
        public IngredientsResponseSuccess(IngredientsResponse ingredientsResponse)  {
            this.ingredientsResponse = ingredientsResponse;
        }
        public IngredientsResponse getData()    {
            return ingredientsResponse;
        }
    }

    public static final class UserResponseSuccess extends Result {
        private final User user;
        public UserResponseSuccess(User user) {
            this.user = user;
        }
        public User getData() {
            return user;
        }
    }

    public static final class Error extends Result {
        private final String message;
        public Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }
}
