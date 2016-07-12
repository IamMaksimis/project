package com.iammaksimus.recipes;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 111 on 27.05.2016.
 */
public class AdapterRecipe extends BaseAdapter {

    ArrayList<Recipe> data = new ArrayList<>();
    Context context;

    public AdapterRecipe(Context context, ArrayList<Recipe> arr) {
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

    static class ViewHolder {
        TextView name;
        TextView recipe;
        TextView national;
        TextView category;
        TextView ingredients;
        TextView min;
        ImageView cover;
    }


    @Override
    public View getView(int i, View someView, ViewGroup arg2) {
        //Получение объекта inflater из контекста
        LayoutInflater inflater = LayoutInflater.from(context);
        //Если someView (View из ListView) вдруг оказался равен
        //null тогда мы загружаем его с помошью inflater
        ViewHolder viewHolder;
        if (someView == null) {
            someView = inflater.inflate(R.layout.item, arg2, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) someView.findViewById(R.id.nameItem);
            viewHolder.national = (TextView) someView.findViewById(R.id.nationalItem);
            viewHolder.category = (TextView) someView.findViewById(R.id.categoryItem);
            viewHolder.ingredients = (TextView) someView.findViewById(R.id.ingredientsItem);
            viewHolder.min = (TextView) someView.findViewById(R.id.minRecipe);
            viewHolder.cover = (ImageView) someView.findViewById(R.id.coverItem);

            viewHolder.recipe = (TextView) someView.findViewById(R.id.textView7);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "9368.ttf");
            viewHolder.recipe.setTypeface(typeFace);
            viewHolder.recipe.setTextSize(20);
            someView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) someView.getTag();
        }

     /*   //Обявляем наши текствьюшки и связываем их с разметкой
        TextView name = (TextView) someView.findViewById(R.id.nameItem);
       // TextView cover = (TextView) someView.findViewById(R.id.textView2);
        //TextView url = (TextView) someView.findViewById(R.id.textView3);
        TextView national = (TextView) someView.findViewById(R.id.nationalItem);
        TextView category = (TextView) someView.findViewById(R.id.categoryItem);
        TextView ingredients = (TextView) someView.findViewById(R.id.ingredientsItem);
        ImageView cover = (ImageView) someView.findViewById(R.id.coverItem);
        //Устанавливаем в каждую текствьюшку соответствующий текст
*/
        final int f = i;
        viewHolder.name.setText(data.get(i).getName());
        //cover.setText(data.get(i).getCover());
        //url.setText(data.get(i).getUrl());
        viewHolder.national.setText(data.get(i).getNational());
        viewHolder.category.setText(data.get(i).getCategory());
        viewHolder.ingredients.setText(data.get(i).getIngredients());
        viewHolder.min.setText(data.get(i).getMin());
        //cover.setImageDrawable(null);
        viewHolder.cover.setImageBitmap(data.get(i).getImage());
        someView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.progressBar.setVisibility(View.VISIBLE);
                Values.ingredients = data.get(f).getIngredients();
                Values.context = context;
                Values.name = data.get(f).getName();
                Values.recipe = data.get(f);
                //data.get(f).getTime();
                new ParserRecipes().execute(data.get(f).getUrl());
                Log.d("All ok", f + "");
            }
        });
        return someView;
    }
}
