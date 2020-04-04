package com.example.farmfresh.model.data;

import com.example.farmfresh.model.data.data.Item;
import com.example.farmfresh.model.data.data.User;
import com.example.farmfresh.model.data.enums.UserType;

import java.util.ArrayList;

// Singleton class to hold session state.
public class State {
    private static State instance;
    private UserType userType;
    private User user;
    private ArrayList<Item> cart = new ArrayList<>();

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

    public ArrayList<Item> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Item> cart) {
        this.cart = cart;
    }
}
