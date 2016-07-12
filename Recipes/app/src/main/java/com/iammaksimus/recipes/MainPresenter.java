package com.iammaksimus.recipes;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 111 on 01.06.2016.
 */
public class MainPresenter extends Activity implements IPresenter{
    IMain iMain;
    ListView recipesList;
    Context context;
    ArrayList<Recipe> recipes;
    MainModel mainModel;
    String[] columns;
    String groupBy;
    String having;
    EditText search;
    MainPresenter(IMain iMain, EditText search){
        this.iMain = iMain;
        this.recipesList = iMain.getRecipesList();
        this.context = iMain.getContext();
        this.mainModel = new MainModel(this);
        this.search = search;
        //c();
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


    public void c(){
        new AsyncTask<Void, Void, ArrayList<Recipe>>() {
            ArrayList<Recipe> r;
            @Override
            protected ArrayList<Recipe> doInBackground(Void... params) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           createList(startSelect());
                        }
                    });
                } catch (Exception exception) {
                    // createAndShowDialog(exception, "Error");
                }
                return null;
            }
        }.execute();
    }




    ArrayList<Recipe> startSelect(){
        Cursor c = mainModel.db.query("recipes", null, null, null, null, null, null);
        int n = c.getCount();
        columns = new String[] { "id", "name", "category", "ingredients" , "national" , "cover" , "url" };
        groupBy = "id";
        ArrayList<Recipe> r = new ArrayList<>();
        final Random random = new Random();
        int id = random.nextInt(n) - 40;
        having = "id > " + id + " AND id <" + (id + 30);
       // for(int i= 0; i < 30; i++){
            c = mainModel.db.query("recipes", columns, null, null, groupBy, having, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        r.add(new Recipe(c.getString(1), c.getString(2),c.getString(3) ,c.getString(4),c.getString(5),c.getString(6)));
                       // Log.d("MainPresenter", c.getString(1));
                    } while (c.moveToNext());
                }
                c.close();
            } else {
                Log.d("MainPresenter", "Cursor is null");
            }
      //  }
        return r;
    }

    ArrayList<Recipe> select(String[] columns, String groupBy, String having, String[] where){
        this.columns = columns; //new String[] { "id", "name", "category", "ingredients" , "national" , "cover" , "url" };
        this.groupBy = groupBy; //"id";
        this.having = having; //"id < " + 20;
        ArrayList<Recipe> r = new ArrayList<>();
        Cursor c = mainModel.db.query("recipes", columns, having, where, groupBy, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    r.add(new Recipe(c.getString(1), c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
                   // Log.d("MainPresenter", c.getString(1));
                } while (c.moveToNext());
            }
            c.close();
        } else {
            Log.d("MainPresenter", "Cursor is null");
        }
        return r;
    }

    void search(){
        String[]
                columns = new String[] { "id", "name", "category", "ingredients" , "national" , "cover" , "url" };
        String groupBy = "id";
        String having = "name = ?";
        createList(select(columns, groupBy, "name LIKE '%"+search.getText().toString()+"%'", null));
    }

    @Override
    public Context getContext() {
        return context;
    }
}
