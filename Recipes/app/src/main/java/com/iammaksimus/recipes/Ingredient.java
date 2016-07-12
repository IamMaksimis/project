package com.iammaksimus.recipes;

/**
 * Created by 111 on 11.06.2016.
 */
public class Ingredient {
    private String
            name,
            count;

    Ingredient(String name, String count){
        this.count = count;
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public String getName() {
        return name;
    }
}
