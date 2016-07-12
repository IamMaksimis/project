package com.iammaksimus.recipes;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by 111 on 30.06.2016.
 */
public class ThematicFragmentPresenter {
    ISearch iSearch;
    Context context;
    ThematicFragmentModel model;
    String[] list;
    ArrayList<String> map;
    Spinner thematicSpinner;
    ArrayAdapter<String> thematicAdapter;
    ThematicFragmentPresenter(ISearch iSearch){
        this.iSearch = iSearch;
        this.context = iSearch.getContext();
        this.model = new ThematicFragmentModel();
        this.thematicSpinner = iSearch.getCategory();
        this.list = new String[]{"Вечеринка", "День рождения", "Ужин", "Обед", "Завтрак", "Легкий перекус"};
        this.thematicAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        this.thematicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.thematicSpinner.setAdapter(thematicAdapter);
        this.model.setMap();
        this.thematicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Values.map = model.getThematic(thematicSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

}
