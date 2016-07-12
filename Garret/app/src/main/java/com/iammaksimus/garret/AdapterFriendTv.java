package com.iammaksimus.garret;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 111 on 23.01.2016.
 */
public class AdapterFriendTv  extends BaseAdapter {

    ArrayList<ElementTvShowFriends> data = new ArrayList<ElementTvShowFriends>();
    Context context;

    public AdapterFriendTv(Context context, ArrayList<ElementTvShowFriends> arr) {
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
            someView = inflater.inflate(R.layout.friend_tv_show_el, arg2, false);
        }
        //Обявляем наши текствьюшки и связываем их с разметкой
        TextView name = (TextView) someView.findViewById(R.id.nameTvFriend);
        ImageView coverTv = (ImageView) someView.findViewById(R.id.cover);

        name.setText(data.get(i).getName()); //+ "(" + data.get(i).getEngName() + ")");

        coverTv.setImageBitmap(data.get(i).getCover());
        return someView;
    }
}