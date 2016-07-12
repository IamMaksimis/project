package com.iammaksimus.recipes;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 111 on 30.06.2016.
 */
public class ThematicFragmentModel {
    Map<String, Thematic> map;
    ThematicFragmentModel(){
        map = new TreeMap<>();
    }

    void setMap(){
        map.put("Вечеринка", new Thematic("Закуски", "Напитки", "Бутерброды"));
        map.put("День рождения", new Thematic("Десерты", "Напитки", "Закуски"));
        map.put("Ужин", new Thematic("Основные блюда", "Мясо", "Соусы и заправки"));
        map.put("Обед", new Thematic("Основные блюда", "Супы и бульоны", "Каши"));
        map.put("Завтрак", new Thematic("Блины, оладьи, сырники", "Бутерброды", "Каши"));
        map.put("Легкий перекус", new Thematic("Закуски", "Бутерброды", "Хлеб"));
        map.put("Отдых на природе", new Thematic("Закуски", "Напитки", "Шашлык"));
        map.put("Новый год", new Thematic("Мясо", "Напитки", "Соусы и заправки"));
    }

    public Map<String, Thematic> getMap() {
        return map;
    }

    public ArrayList<String> getThematic(String key){
        return map.get(key).getThematicItem();
    }
}
