package com.iammaksimus.recipes;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private FloatingActionButton fab;
    private FragmentTransaction ft;
    private int fragment;
    MainFragment mainFragment;
    SearchFragment searchFragment;
    ReturnSearchFragment returnSearchFragment;
    ReturnIngredientFragment returnIngredientFragment;
    IngredientFragment ingredientFragment;
    MyRecipesFragment myRecipesFragment;
    ThematicFragment thematicFragment;
    ThematicFragmentReturn thematicFragmentReturn;
    Toolbar toolbar;
    static ProgressBar progressBar;
    private boolean f = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //*******************************************//
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        mainFragment = new MainFragment();
        searchFragment = new SearchFragment();
        ingredientFragment = new IngredientFragment();
        myRecipesFragment = new MyRecipesFragment();
        thematicFragment = new ThematicFragment();

        ft = getSupportFragmentManager().beginTransaction();
        displayView(R.id.nav_home);
        fragment = R.id.nav_home;


        //*************************************************//
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.iammaksimus.recipespro"));
                switch (fragment){
                    case R.id.nav_home:
                        setTitle(R.string.search_fab);
                        MainFragment.mainPresenter.search();
                        break;
                    case R.id.nav_ingredients:
                        setTitle(R.string.search_fab);
                        returnIngredientFragment = new ReturnIngredientFragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.main_a, returnIngredientFragment);
                        ft.addToBackStack(null);
                        ft.commit();

            //            startActivity(intent);

                        break;
                    case R.id.nav_MyRecipes:
                        break;
                    case R.id.nav_search:
                        setTitle(R.string.search_fab);
                        returnSearchFragment = new ReturnSearchFragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.main_a, returnSearchFragment);
                        ft.addToBackStack(null);
                        ft.commit();

                        break;

                    case R.id.nav_Thematic:
                        setTitle(R.string.search_fab);
                        thematicFragmentReturn = new ThematicFragmentReturn();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.main_a, thematicFragmentReturn);
                        ft.addToBackStack(null);
                        ft.commit();

//                        startActivity(intent);

                        break;
                }


            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           super.onBackPressed();
       /*     if(f && fragment == R.id.nav_home) {
                showAlert();
            }else{
                super.onBackPressed();
            }
*/
        }

    }


    private void showAlert(){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Рецептика")
                    .setMessage("Оцените приложение")
                    .setIcon(R.drawable.icon)
                    .setCancelable(false)
                    .setNeutralButton("Выход",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            }).setNegativeButton("Оценить",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("market://details?id=com.iammaksimus.recipes"));
                            startActivity(intent);
                            finish();
                        }
                    }).setPositiveButton("Купить Pro",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("market://details?id=com.iammaksimus.recipespro"));
                            startActivity(intent);
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            f = !f;
    }
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //
        ft = getSupportFragmentManager().beginTransaction();
        displayView(item.getItemId());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void displayView(int item){
        fragment = item;

        if(fragment == R.id.nav_home){
            setTitle(R.string.home);
            ft.replace(R.id.main_a, mainFragment);
            ft.addToBackStack(null);
        }else{
            if(fragment == R.id.nav_ingredients){
                setTitle(R.string.ingredient);
                ft.replace(R.id.main_a, ingredientFragment);
                ft.addToBackStack(null);
            }else{
                if(fragment == R.id.nav_MyRecipes){
                    setTitle(R.string.my_recipes);
                    ft.replace(R.id.main_a, myRecipesFragment);
                    ft.addToBackStack(null);
                }else{
                    if(fragment == R.id.nav_search){
                        setTitle(R.string.search);
                        ft.replace(R.id.main_a, searchFragment);
                        ft.addToBackStack(null);
                    }else{
                        if(fragment == R.id.nav_Thematic){
                            setTitle(R.string.thematic);
                            ft.replace(R.id.main_a, thematicFragment);
                            ft.addToBackStack(null);
                        }
                    }

                }
            }
        }
        ft.commit();
    }

}
