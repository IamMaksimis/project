package com.iammaksimus.recipes;

import android.content.Context;
import android.widget.Spinner;

/**
 * Created by 111 on 02.06.2016.
 */
public interface ISearch {

    Spinner getNational();
    Spinner getCategory();
    Context getContext();
}
