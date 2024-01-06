package it.unimib.food_alchemy.model;

public class HomeRecipeModel {
    String recipeTitle;
    int recipeImage;

    public HomeRecipeModel(String recipeTitle, int recipeImage) {
        this.recipeTitle = recipeTitle;
        this.recipeImage = recipeImage;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public int getRecipeImage() {
        return recipeImage;
    }
}
