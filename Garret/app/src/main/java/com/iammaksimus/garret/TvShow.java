package com.iammaksimus.garret;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by 111 on 14.01.2016.
 */
public class TvShow extends AppCompatActivity {
    public static TextView name, country, acters, status, info;
    static TvShowObject tvShow;
    static boolean startCreateList = false, startCreateList2 = false;
    static ImageView cover;
    Switch my;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tvshowtest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTV);
        toolbar.setTitle(Values.tv.getName());
        //toolbar.setLogo(getResources().getDrawable(R.drawable.logo));
       // toolbar.setSubtitle(Values.tv.getName());

        my = (Switch) findViewById(R.id.switch2);
        my.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   GarretHomeActivity.mySerials.add(Values.tv.getName());
                   GarretHomeActivity.changeMeListFlag = true;
                   set();
                   GarretHomeActivity.writeMyTvShow();
                }else{
                    GarretHomeActivity.mySerials.remove(Values.tv.getName());
                    GarretHomeActivity.changeMeListFlag = true;
                   set();
                   GarretHomeActivity.writeMyTvShow();
                }
            }
        });


        country = (TextView) findViewById(R.id.countryTV);
       // name = (TextView) findViewById(R.id.nameTV);
        acters = (TextView) findViewById(R.id.actersTV);
        status = (TextView) findViewById(R.id.statusTV);
        info = (TextView) findViewById(R.id.infoTV);
        cover = (ImageView) findViewById(R.id.coverTV);

        country.setText("Страна: " + Values.tv.getCountry());
        toolbar.setTitle(Values.tv.getName());
        toolbar.invalidate();

        acters.setText(Values.tv.getActers());
        status.setText("Статус: " + Values.tv.getStatus());
        info.setText(Values.tv.getInfo());
        if(Values.coverFriendTv == null) {
            cover.setImageBitmap(Values.tv.getCover());
        }else{
            cover.setImageBitmap(Values.coverFriendTv);
        }
        checkSwitch();

    }

    public void set(){
        GarretHomeActivity.item.setTvshow(sendTv());
        GarretHomeActivity.item.setId(Values.nick);
        GarretHomeActivity.item.setNick(Values.nick);
        GarretHomeActivity.item.setPassword(Values.password);
        GarretHomeActivity.item.setName(Values.name);
        GarretHomeActivity.item.setFirstname(Values.fname);
        GarretHomeActivity.item.setFriends(Values.friends);
        Values.tvshow = sendTv();
    }

    String sendTv() {
        String tmp = "";
        for(int i = 0; i < GarretHomeActivity.mySerials.size(); i++){
            tmp += GarretHomeActivity.mySerials.toArray()[i] + "@@@";
        }
        return tmp;
    }




    public void checkSwitch(){
        for(int i = 0; i < GarretHomeActivity.mySerials.size(); i++){
            if(Values.tv.getName().equals(GarretHomeActivity.mySerials.toArray()[i])){
                my.setChecked(true);
            }
        }
    }



}
