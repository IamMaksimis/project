package com.iammaksimus.recipes;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by 111 on 02.06.2016.
 */
public class SearchPresenter {
    MainPresenter mainPresenter;
    Spinner
            nationalSpinner,
            categorySpinner;
    ISearch iSearch;
    Context context;
    String[]
            national = {"", "Авторская", "Русская", "Японская", "Итальянская", "Народная",
            "Арабская", "Эстонская", "Азиатская", "Интернациональная", "Американская",
            "Английская", "Греческая", "Немецкая", "Турецкая", "Французская", "Грузинская",
            "Индийская", "Европейская", "Польская", "Испанская", "Финская", "Украинская",
            "Абхазская", "Азербайджанская", "Чешская", "Сербская", "Армянская"},
            category = {"", "Десерты", "Выпечка", "Основные блюда", "Закуски", "Мясо", "Блины, оладьи, сырники",
                    "Каши", "Супы и бульоны", "Запеканки", "Напитки", "Салаты и винегреты", "Хлеб",
                    "Бутерброды", "Домашние заготовки", "Пельмени и вареники", "Соусы и заправки", "Шашлык"};
    ArrayAdapter<String>
            adapterNational,
            adapterCategory;
    SearchPresenter(ISearch iSearch){
        Values.category = "";
        Values.national = "";
        this.iSearch = iSearch;
        this.context = iSearch.getContext();
        this.categorySpinner = iSearch.getCategory();
        this.nationalSpinner = iSearch.getNational();
        mainPresenter = MainFragment.mainPresenter;
        adapterNational = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, national);
        adapterNational.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCategory = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, category);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nationalSpinner.setAdapter(adapterNational);
        nationalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Values.national = nationalSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        categorySpinner.setAdapter(adapterCategory);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Values.category = categorySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }




}
