package it.unimib.food_alchemy.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.food_alchemy.repository.IRecipesRepository;

public class RecipeViewModelFactory implements ViewModelProvider.Factory {

    private final IRecipesRepository recipeRepository;

    public RecipeViewModelFactory(IRecipesRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeViewModel(recipeRepository);
    }
}
