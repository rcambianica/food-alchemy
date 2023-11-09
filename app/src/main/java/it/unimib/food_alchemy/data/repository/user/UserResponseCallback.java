package it.unimib.food_alchemy.data.repository.user;

import it.unimib.food_alchemy.model.User;

public interface UserResponseCallback {
    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);
    //void onSuccessFromRemoteDatabase(List<News> newsList);
    void onSuccessFromGettingUserPreferences();
    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout();
}
