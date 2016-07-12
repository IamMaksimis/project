package com.iammaksimus.recipes;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class ActivityRecipe extends AppCompatActivity implements IRecipe{
    private ListView
            ingredient,
            learningList;
    private ImageButton
            fab;
    private LinearLayout linearLayout;
    private RecipePresenter recipePresenter;
    private View
            header,
            header2;
    private ImageView cover;
    private TabHost.TabSpec tabSpec;
    private InterstitialAd mInterstitialAd;
    private boolean closeBanner = false;
    private TextView footCoast;
    AdView mAdView;
    private Recipe r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        //инициализация
        tabHost.setup();
        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Ингредиенты");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Рецепт");
        tabHost.addTab(tabSpec);


        header = createHeader();
        learningList = (ListView) findViewById(R.id.listView);
        ingredient = (ListView) findViewById(R.id.ingredientList);
        learningList.addHeaderView(header);
        ingredient.addHeaderView(header);
        recipePresenter = new RecipePresenter(this);
        mAdView = (AdView) findViewById(R.id.adView);
       // MobileAds.initialize(getApplicationContext(), "ca-app-pub-1381706751840636~9339535308");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1381706751840636/7723201300");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                finish();
            }
        });
        requestNewInterstitial();
        MainActivity.progressBar.setVisibility(View.GONE);
        footCoast.setText(recipePresenter.getCalories());
        Values.recipe.getTime();



    }
    // создание Header
    View createHeader() {
        View v = getLayoutInflater().inflate(R.layout.header, null);
        cover = (ImageView) v.findViewById(R.id.imageView2);
       // cover.setImageBitmap(Values.cover);
        Values.im = cover;
        TextView nameRexipe = (TextView)v.findViewById(R.id.nameRecipe);
        final TextView min = (TextView)v.findViewById(R.id.minRecipe);
      //  minRecipe.setText(Values.minRecipe);
        TextView headerText = (TextView)v.findViewById(R.id.headerText);
        Typeface typeFace = Typeface.createFromAsset(this.getAssets(), "9368.ttf");
        Values.h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                // обновляем TextView
//                int m = msg.what;
                min.setText(Values.min);
//                Log.d("min", Values.min);
            };
        };
        headerText.setTypeface(typeFace);
        headerText.setTextSize(30);
        nameRexipe.setText(R.string.header_name);
        footCoast = (TextView)v.findViewById(R.id.footCoast);
        setTitle(Values.name);
        fab = (ImageButton) v.findViewById(R.id.imageButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return v;
    }


    @Override
    public ListView getList() {
        return learningList;
    }

    @Override
    public ListView getIngredient() {
        return ingredient;
    }

    @Override
    public ImageButton getFab() {
        return fab;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public LinearLayout getLayout() {
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  if (mInterstitialAd.isLoaded() && !closeBanner) {
            mInterstitialAd.show();
            closeBanner = true;
        } else {
            recipePresenter = null;
            super.onBackPressed();
        }
*/
       //Values.cover = null;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
        mAdView.loadAd(adRequest);
    }

}
