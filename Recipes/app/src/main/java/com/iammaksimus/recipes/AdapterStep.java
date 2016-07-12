package com.iammaksimus.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 111 on 11.06.2016.
 */
public class AdapterStep  extends BaseAdapter {

    ArrayList<Step> data = new ArrayList<>();
    Context context;

    public AdapterStep(Context context, ArrayList<Step> arr) {
        if (arr != null) {
            data = arr;
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int num) {
        // TODO Auto-generated method stub
        return data.get(num);
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
            someView = inflater.inflate(R.layout.recipe_item, arg2, false);
        }
        //Обявляем наши текствьюшки и связываем их с разметкой
        TextView name = (TextView) someView.findViewById(R.id.stepText);
        // TextView cover = (TextView) someView.findViewById(R.id.textView2);

        ImageView cover = (ImageView) someView.findViewById(R.id.stepCover);
        //Устанавливаем в каждую текствьюшку соответствующий текст

        final int f = i;
        name.setText(data.get(i).getName());
        cover.setImageBitmap(data.get(i).getImage());
        return someView;
    }
}
