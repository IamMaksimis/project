package com.iammaksimus.recipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by 111 on 11.06.2016.
 */
public class RecipePresenter{

    private IRecipe iRecipe;
    private ListView
            listView,
            ingredientList;
    private Context context;
    private ArrayList<Step> step;
    LinearLayout linearLayout;
    private DBHelper dbHelper;
    private ImageButton fab;
    private RecipeModel model;
    ArrayList<Ingredient> ingredients;
    Calories calories;
    RecipePresenter(IRecipe iRecipe){
        this.context = iRecipe.getContext();
        this.iRecipe = iRecipe;
        this.listView = iRecipe.getList();
        this.step = Values.step;
        this.linearLayout = iRecipe.getLayout();
        this.ingredientList = iRecipe.getIngredient();
        createList(step);
        createIngredient(split());
        this.dbHelper = new DBHelper(context);
        this.fab = iRecipe.getFab();
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRecipe(Values.recipe);
                Log.d("insertRecipe", "ok");
                fab.animate().rotation(360).setDuration(300);
                Snackbar.make(v, R.string.save_recipe, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        calories = new Calories();
        model = new RecipeModel(iRecipe);
        calories();
    }

    public void createList(ArrayList<Step> r){
        AdapterStep adapter;
        final ArrayList<Step> recipe;
        recipe = r;
        adapter = new AdapterStep(context, recipe);
        //adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        for(int i = 0; i < recipe.size(); i++){
            recipe.get(i).loadCover(listView);
        }
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (int i = firstVisibleItem; i < (firstVisibleItem + visibleItemCount) && i < totalItemCount; i++) {
                    if(i < recipe.size()) {
                        recipe.get(i).loadCover(listView);
                    }
                }

            }
        });
    }

    public ArrayList<Ingredient> split(){
        String[] tmp = Values.ingredients.split(",");
        ingredients = new ArrayList<>();
        for(int i = 0; i < tmp.length - 1; i++){
            if((tmp[i].indexOf("[0-9]") == tmp[i].toString().length()) && tmp[i + 1].indexOf("[0-9]") == tmp[i + 1].toString().length()){
                ingredients.add(new Ingredient(tmp[i] + tmp[i + 1], ""));
                i++;
            }else {
                ingredients.add(new Ingredient(tmp[i], ""));
            }
          /*  for(int j = 1; i < 10; i++){
                if(tmp[i].indexOf(j + "") > 1){
                    int index = tmp[i].indexOf(j + "");
                    name = tmp[i].substring(0, index - 1);
                    mera = tmp[i].substring(index);
                    ingredients.add(new Ingredient(name, mera));

                }
            }
            */
        }
        return ingredients;
    }

    public void createIngredient(ArrayList<Ingredient> r){
        AdapterHeader adapter;
        final ArrayList<Ingredient> recipe;
        recipe = r;
        adapter = new AdapterHeader(context, recipe);
        //adapter.notifyDataSetChanged();
        ingredientList.setAdapter(adapter);
    }

    public void insertRecipe(Recipe recipe){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("name", recipe.getName());
        cv.put("category", recipe.getCategory());
        cv.put("ingredients", recipe.getIngredients());
        cv.put("national", recipe.getNational());
        cv.put("cover", recipe.getCover());
        cv.put("url", recipe.getUrl());
        db.insert("my_recipes", null, cv);
        Log.d("DataBase", recipe.getName() + "");
        db.close();
    }

    public void calories(){
        String[]
                columns = new String[] { "name", "b", "g", "u", "k"},
                where;
        String groupBy = "name";
        String having;
        for(int i = 0; i < ingredients.size(); i++){
            select(columns, groupBy, ingredients.get(i).getName());
        }

    }


    void select(String[] columns, String groupBy, String where){
        String[] w = where.split(" - ");
//        int count = w[1].toCharArray()[0];
        String[] t = w[0].split(" ");
        boolean f = false;
        for(int i = 0; i < t.length; i++){
            if(!f) {
                Cursor c = model.db.query("calories", columns, "name LIKE '%" + t[i] + "%'", null, groupBy, null, null);
                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            calories.add(c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                            f = true;
                            break;
                            //  Log.d("MainPresenter", c.getString(2));
                        } while (c.moveToNext());
                    }
                    c.close();
                } else {
                    Log.d("MainPresenter", "Cursor is null");
                }
            }
        }

    }

    public String getCalories() {
        String
                b = context.getResources().getString(R.string.b),
                g = context.getResources().getString(R.string.g),
                u = context.getResources().getString(R.string.u),
                k = context.getResources().getString(R.string.k);

        String c =
                    b + calories.getB() + "\n" +
                            g + calories.getG() + "\n" + u + calories.getU() + "\n" + k + calories.getK();
        return c;
    }
}
