package com.iammaksimus.recipes;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by 111 on 02.06.2016.
 */
public class ReturnSearchPresenter {
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
    ReturnSearchPresenter(IMain iMain, String groupBy, String having, String[] where){
        this.iMain = iMain;
        this.context = iMain.getContext();
        this.recipesList = iMain.getRecipesList();
        this.mainPresenter = MainFragment.mainPresenter;
        this.groupBy = groupBy;
        this.having = having;
        this.where = where;
        this.recipes = mainPresenter.select(columns, groupBy, having, where);
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

}
