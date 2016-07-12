package com.iammaksimus.recipes;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by 111 on 11.06.2016.
 */
public interface IRecipe {

    ListView getList();
    ListView getIngredient();
    ImageButton getFab();
    Context getContext();
    LinearLayout getLayout();
}
