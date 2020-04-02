package com.example.farmfresh.model.data;

public class Key {
    public enum Coordinate { LNG, LAT }

    public enum User {
        USER_NAME,
        USER_PASSWORD,
        USER_TYPE,
        USER_LOCATION
    }

    public enum Item {
        ITEM_NAME,
        PRICE,
        QUANTITY,
        IMAGE_PATH
    }
}
