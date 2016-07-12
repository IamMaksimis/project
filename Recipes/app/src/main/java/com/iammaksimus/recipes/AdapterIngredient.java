package com.iammaksimus.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashSet;

/**
 * Created by 111 on 09.06.2016.
 */
public class AdapterIngredient  extends BaseAdapter {

    String[] data = {};
    Context context;

    public AdapterIngredient(Context context, HashSet<String> arr) {
        if (arr != null) {
            data = arr.toArray(new String[arr.size()]);
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int num) {
        // TODO Auto-generated method stub
        return data[num];
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }


    @Override
    public View getView(int i, View someView, ViewGroup arg2) {
        //Получение объекта inflater из контекста
        LayoutInflater inflater = LayoutInflater.from(context);
        //Если someView (View из ListView) вдруг оказался равен
        //null тогда мы загружаем его с помошью inflater
        if (someView == null) {
            someView = inflater.inflate(R.layout.ingredient, arg2, false);
        }
        //Обявляем наши текствьюшки и связываем их с разметкой
        TextView name = (TextView) someView.findViewById(R.id.name);

        //Устанавливаем в каждую текствьюшку соответствующий текст


        name.setText(data[i]);

        return someView;
    }
}
