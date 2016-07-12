package com.iammaksimus.garret;

/**
 * Created by 111 on 12.01.2016.
 */
public class Data {
    private String data;

    Data(int day, int month){
        String[] m = {"Января", "Феврля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
        this.data = day + " " + m[month - 1];
    }

    public String getData() {
        return data;
    }
}
