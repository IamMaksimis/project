package com.iammaksimus.garret;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 111 on 23.01.2016.
 */
public class AdapterFriends  extends BaseAdapter {

    ArrayList<Friend> data = new ArrayList<Friend>();
    Context context;

    public AdapterFriends(Context context, ArrayList<Friend> arr) {
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
            someView = inflater.inflate(R.layout.friends_element, arg2, false);
        }
        //Обявляем наши текствьюшки и связываем их с разметкой
        TextView name = (TextView) someView.findViewById(R.id.name_friend);
        TextView nick = (TextView) someView.findViewById(R.id.nick_friend);
        TextView fname = (TextView) someView.findViewById(R.id.fname_friend);

        //Устанавливаем в каждую текствьюшку соответствующий текст
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "font1.ttf");
        name.setTypeface(typeFace);
        nick.setTypeface(typeFace);
        fname.setTypeface(typeFace);

        name.setText(data.get(i).getName()); //+ "(" + data.get(i).getEngName() + ")");
        nick.setText(data.get(i).getNick());
        fname.setText(data.get(i).getFname());
        return someView;
    }

}
