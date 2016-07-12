package com.iammaksimus.recipes;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 111 on 09.06.2016.
 */
public class ReturnIngredientPresenter{
    IMain iMain;
    Context context;
    ListView recipesList;
    MainPresenter mainPresenter;
    ArrayList<Recipe> recipes;
    String[]
            columns = new String[] { "id", "name", "category", "ingredients" , "national" , "cover" , "url" },
            where;
    String groupBy;
    String having;
    int wieght = 0;
    ReturnIngredientPresenter(IMain iMain, String groupBy, String having, String[] where){
        this.iMain = iMain;
        this.context = iMain.getContext();
        this.recipesList = iMain.getRecipesList();
        this.mainPresenter = MainFragment.mainPresenter;
        this.groupBy = groupBy;
        this.having = having;
        this.where = where;
        this.recipes = weghtList(select(columns, groupBy, where), where);
        createList(recipes);
    }

    public void createList(ArrayList<Recipe> r){
        AdapterRecipe adapter;
        final ArrayList<Recipe> recipe;
        recipe = r;
        adapter = new AdapterRecipe(context, recipe);
        //adapter.notifyDataSetChanged();
        recipesList.setAdapter(adapter);
        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Ingredients", recipes.get((int)id).getIngredients());
            }
        });

        recipesList.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (int i = firstVisibleItem; i < (firstVisibleItem + visibleItemCount) && i < totalItemCount; i++) {
                    recipe.get(i).loadCover(recipesList);
                }

            }
        });
        if(recipe.size() > 4) {
            for (int i = 0; i < 4; i++) {
                recipe.get(i).loadCover(recipesList);
            }
        }
    }

    ArrayList<Recipe> select(String[] columns, String groupBy, String[] where){
        ArrayList<Recipe> r = new ArrayList<>();
        String
                w = "ingredients LIKE '%"+where[0]+"%'",
                or_and;
        if(Values.switchIngredient){
            or_and = "or";
        }else{
            or_and = "AND";
        }
        for(int i = 1; i < where.length; i++){
            w += " " + or_and + " ingredients LIKE '%"+where[i]+"%'";
        }
        Cursor c = mainPresenter.mainModel.db.query("recipes", columns,w, null, groupBy, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                        r.add(new Recipe(c.getString(1).replaceAll("&quot;", ""), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
                   // Log.d("MainPresenter", c.getString(1));
                } while (c.moveToNext());
            }
            c.close();
        } else {
            Log.d("MainPresenter", "Cursor is null");
        }
        return r;
    }

    ArrayList<Recipe> weghtList(ArrayList<Recipe> r, String[] where){
        for(int i = 0; i < r.size(); i++){
            for(int j = 0; j < where.length; j++){
                if(r.get(i).getIngredients().indexOf(where[j]) > -1){
                    int w = r.get(i).getWeght();
                    w++;
                    r.get(i).setWeght(w);
                }
            }
        }
        Recipe[] recipes = new Recipe[r.size()];
        for(int i = 0; i < r.size(); i++){
            recipes[i] = r.get(i);
        }
        Arrays.sort(recipes);
        ArrayList<Recipe> tmp = new ArrayList<>();
        for(int i = r.size() - 1; i > 0; i--){
            if(Values.switchIngredient) {
                String[] t = recipes[i].getIngredients().split(", ");
                if ((where.length == recipes[i].getWeght()) && (where.length + Integer.parseInt(IngredientFragment.countEdit.getText().toString()) == t.length)) {
                    tmp.add(recipes[i]);
                }
            }else{
                tmp.add(recipes[i]);
            }
        }
        return tmp;
    }
}
