package com.iammaksimus.recipes;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashSet;

/**
 * Created by 111 on 04.06.2016.
 */
public class IngredientPresenter extends Activity{
    IMain iMain;
    Context context;
    MainPresenter mainPresenter;
    ListView recipesList;
    HashSet<String> ingredients;
    EditText ingredientEdit;
    TextView help;
    IngredientPresenter(IMain iMain, TextView help){
        this.iMain = iMain;
        this.context = iMain.getContext();
        this.recipesList = iMain.getRecipesList();
        ingredients = new HashSet<>();
        this.help = help;

    }


    public void addIngredient(String i){
        ingredients.add(i);
        createList(ingredients);
        Values.where = ingredients;
        help.setText("");
    }

    public void createList(HashSet<String> r){
        AdapterIngredient adapter;
        final HashSet<String> recipe;
        recipe = r;
        adapter = new AdapterIngredient(context, recipe);
        //adapter.notifyDataSetChanged();
        recipesList.setAdapter(adapter);
        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               ingredients.remove(ingredients.toArray()[(int)id]);
               createList(ingredients);
                Values.where = ingredients;
            }
        });
    }


    public void switchIngredient(){
            Values.switchIngredient = !Values.switchIngredient;
    }

}
