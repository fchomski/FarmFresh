package com.example.farmfresh.model.data.enums;

import android.widget.FrameLayout;

import java.util.stream.Stream;

public enum Category {
    Fruit,
    Veggies,
    Protein,
    Dairy,
    Sweets,
    Grains,
    Plants,
    Jewelry,
    Others
    ;

    public static Category parseString(String str) {
        for (Category c : Category.values()) {
            if (str.equals(c.name())) return c;
        }
        return Others;
    }

    public static String[] names() {
        Category[] cate = values();
        String[] names = new String[cate.length];

        for (int i = 0; i < cate.length; ++i) {
            System.out.println(cate[i].name());
            names[i] = cate[i].name();
        }

        return names;
    }
}
