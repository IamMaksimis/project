package com.iammaksimus.recipes;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by 111 on 30.06.2016.
 */
public class ThematicFragmentReturnPresenter {
    IMain iMain;
    Context context;
    ListView recipesList;
    MainPresenter mainPresenter;
    ArrayList<Recipe> recipes;
    String[]
            columns = new String[] { "id", "name", "category", "ingredients" , "national" , "cover" , "url" };
    ArrayList<String> where;
    String groupBy;
    String having;
    ThematicFragmentReturnPresenter(IMain iMain, String groupBy, ArrayList<String> where){
        this.iMain = iMain;
        this.context = iMain.getContext();
        this.recipesList = iMain.getRecipesList();
        this.mainPresenter = MainFragment.mainPresenter;
        this.groupBy = groupBy;
        this.having = having;
        this.where = where;
        this.recipes = select(columns, groupBy, where);
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
        }else{
            for (int i = 0; i < recipe.size(); i++) {
                recipe.get(i).loadCover(recipesList);
            }
        }
    }

    ArrayList<Recipe> select(String[] columns, String groupBy, ArrayList<String> where){
        ArrayList<Recipe>
                one = new ArrayList<>(),
                two = new ArrayList<>(),
                three = new ArrayList<>();
        ArrayList<Recipe> r = new ArrayList<>();
        String
                w = "category LIKE '%"+where.get(0)+"%'";
        for(int i = 1; i < where.size(); i++){
            w += " " + "or" + " ingredients LIKE '%"+where.get(i)+"%'";
        }
        Cursor c = mainPresenter.mainModel.db.query("recipes", columns,"category LIKE '%"+where.get(0)+"%'", null, groupBy, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    one.add(new Recipe(c.getString(1).replaceAll("&quot;", ""), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
                   //  Log.d("MainPresenter", c.getString(2));
                } while (c.moveToNext());
            }
            c.close();
        } else {
            Log.d("MainPresenter", "Cursor is null");
        }
        c = mainPresenter.mainModel.db.query("recipes", columns, "category LIKE '%"+where.get(1)+"%'", null, groupBy, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    two.add(new Recipe(c.getString(1).replaceAll("&quot;", ""), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
                    //  Log.d("MainPresenter", c.getString(2));
                } while (c.moveToNext());
            }
            c.close();
        } else {
            Log.d("MainPresenter", "Cursor is null");
        }
        c = mainPresenter.mainModel.db.query("recipes", columns,"category LIKE '%"+where.get(2)+"%'", null, groupBy, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    three.add(new Recipe(c.getString(1).replaceAll("&quot;", ""), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
                    //  Log.d("MainPresenter", c.getString(2));
                } while (c.moveToNext());
            }
            c.close();
        } else {
            Log.d("MainPresenter", "Cursor is null");
        }


        int n = Math.min(one.size(), two.size());
        n = Math.min(n, three.size());

        r = new ArrayList<>();
        for(int i = 0; i < n; i++){

                r.add(one.get(i));

                r.add(two.get(i));

                r.add(three.get(i));

        }

        return r;
    }
}
