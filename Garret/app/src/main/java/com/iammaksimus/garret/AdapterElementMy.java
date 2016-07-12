package com.iammaksimus.garret;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 111 on 15.01.2016.
 */
public class AdapterElementMy extends BaseAdapter {

    ArrayList<ElementTvShow> data = new ArrayList<ElementTvShow>();
    Context context;

    public AdapterElementMy(Context context, ArrayList<ElementTvShow> arr) {
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
            someView = inflater.inflate(R.layout.my_elemint_list, arg2, false);
        }
        //Обявляем наши текствьюшки и связываем их с разметкой
        TextView name = (TextView) someView.findViewById(R.id.name_tv_show_my);
        TextView sezon = (TextView) someView.findViewById(R.id.sezon_tv_show_my);
        TextView episodNum = (TextView) someView.findViewById(R.id.episod_tv_show_my);
        TextView episodName = (TextView) someView.findViewById(R.id.name_episod_tv_show_my);
        TextView dataTv = (TextView) someView.findViewById(R.id.tv_data_my);
        ImageView coverTv = (ImageView) someView.findViewById(R.id.cover_tv_show_my);
        //Устанавливаем в каждую текствьюшку соответствующий текст
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "font1.ttf");
        dataTv.setTypeface(typeFace);

        name.setText(data.get(i).getRusName()); //+ "(" + data.get(i).getEngName() + ")");
        sezon.setText(data.get(i).getNumSezon());
        episodNum.setText(data.get(i).getNumEpisod());
        episodName.setText(data.get(i).getNameEpisod());
        dataTv.setText(data.get(i).getDataTv());
        coverTv.setImageBitmap(data.get(i).getCover());
        return someView;
    }
}
