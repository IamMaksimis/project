package com.iammaksimus.recipes;

import java.util.ArrayList;

/**
 * Created by 111 on 30.06.2016.
 */
public class Thematic {
    ArrayList<String> thematicItem;
    Thematic(String... params){
        thematicItem = new ArrayList<>();
        for(String in : params){
            this.thematicItem.add(in);
        }
    }

    public ArrayList<String> getThematicItem() {
        return thematicItem;
    }
}
