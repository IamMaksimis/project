package com.iammaksimus.recipes;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by 111 on 13.06.2016.
 */
public class MyResipesFragmentPresenter {
    private IMain iMain;
    private Context context;
    private ListView recipesList;
    private MyResipesFragmentModel myResipesFragmentModel;
    MyResipesFragmentPresenter(IMain iMain){
        this.iMain = iMain;
        this.context = iMain.getContext();
        this.recipesList = iMain.getRecipesList();
        createList(startSelect());
    }

    public void createList(ArrayList<Recipe> r){
        AdapterRecipe adapter;
        final ArrayList<Recipe> recipe;
        recipe = r;
        adapter = new AdapterRecipe(context, recipe);
        recipesList.setAdapter(adapter);
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

    ArrayList<Recipe> startSelect(){
        Cursor c;
        myResipesFragmentModel = new MyResipesFragmentModel(context);
        String[] columns = new String[] { "name", "category", "ingredients" , "national" , "cover" , "url" };
        ArrayList<Recipe> r = new ArrayList<>();
        c = myResipesFragmentModel.db.query("my_recipes", columns, null, null, null, null, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        r.add(new Recipe(c.getString(0).replaceAll("&quot;", ""), c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5)));
                        Log.d("MainPresenter", c.getString(0));
                    } while (c.moveToNext());
                }
                c.close();
            } else {
                Log.d("MainPresenter", "Cursor is null");
            }
        return r;
    }
}
