package com.iammaksimus.recipes;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class RecipeActivity extends AppCompatActivity implements IRecipe{

    private RecyclerView learningList;
    private ListView ingredient;
    private FloatingActionButton fab;
    private LinearLayout linearLayout;
    private RecipePresenter recipePresenter;
    private View header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        linearLayout = (LinearLayout)findViewById(R.id.content);
        header = createHeader();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
       // learningList = (RecyclerView)findViewById(R.id.learning);
        //learningList.addHeaderView(header);
        recipePresenter = new RecipePresenter(this);
    }

    // создание Header
    View createHeader() {
        View v = getLayoutInflater().inflate(R.layout.header, null);
        ingredient = (ListView)v.findViewById(R.id.ingredientList);
        return v;
    }


    @Override
    public ListView getList() {
        return null;
    }

    @Override
    public ListView getIngredient() {
        return null;
    }

    @Override
    public FloatingActionButton getFab() {
        return fab;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public LinearLayout getLayout() {
        return linearLayout;
    }
}
