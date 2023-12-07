package it.unimib.food_alchemy.model;

public class HomeRecipeModel {
    String recipeTitle;
    String recipeDescription;
    int recipeImage;

    public HomeRecipeModel(String recipeTitle, String recipeDescription, int recipeImage) {
        this.recipeTitle = recipeTitle;
        this.recipeDescription = recipeDescription;
        this.recipeImage = recipeImage;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public int getRecipeImage() {
        return recipeImage;
    }
}
