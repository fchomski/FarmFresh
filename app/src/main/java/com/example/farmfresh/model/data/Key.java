package com.example.farmfresh.model.data;

/* Some enum Keys to access HashMap record
   Only access record from Key here since it provides type checking rather than
   arbitrary strings.
 */
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
