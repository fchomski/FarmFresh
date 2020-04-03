package com.example.farmfresh.model.data;

// Singleton class to hold session state.
public class State {
    private static State instance;
    private UserType userType;
    private User user;

    private State() {}

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static synchronized State getInstance() {
        if (instance == null) instance = new State();
        return instance;
    }
}