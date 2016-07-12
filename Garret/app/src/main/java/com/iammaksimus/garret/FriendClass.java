package com.iammaksimus.garret;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 111 on 23.01.2016.
 */
public class FriendClass extends AppCompatActivity {
    static HorizontalListView list;
    static ArrayList<ElementTvShowFriends> tv = new ArrayList<>();
    static Toolbar toolbar;
    static int countCover = 0;
    TextView countTv, name, fname;
    ImageView largeCover;
    Switch my;
    Intent tvShow;
    ProgressBar bar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);
        Values.setCover = true;
        Values.open = false;
        tvShow = new Intent(this, TvShow.class);
        bar = (ProgressBar)findViewById(R.id.progressBar);
        largeCover = (ImageView)findViewById(R.id.large_cover);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        list = (HorizontalListView)findViewById(R.id.HorizontalListView);
        countTv = (TextView)findViewById(R.id.count_tv);
        name = (TextView)findViewById(R.id.nameF);
        fname = (TextView)findViewById(R.id.fnameF);
        String[] tmp = Values.friendsTvShow.split("@@@");
        if(tmp.length > 0){
            for(int i = 0; i < tmp.length; i++) {

                    tv.add(new ElementTvShowFriends(tmp[i]));
                    new ParseTvFriend().execute(tmp[i], i + "");

            }
            createFriendsList();
        }

        toolbar.setTitle(Values.friendNick);
        countTv.setText("Количество сериалов:" + tv.size());
        name.setText(Values.friendName);
        fname.setText(Values.friendFname);
        my = (Switch) findViewById(R.id.myFriends);
        my.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String tmp = Values.friendNick + "###" + Values.friendName + "###" + Values.friendFname;
                    GarretHomeActivity.myFriendsList.add(tmp);
                    GarretHomeActivity.changeMeFriendsFlag = true;
                    set();
                    GarretHomeActivity.writeMyTvShow();
                } else {
                    String tmp = Values.friendNick + "###" + Values.friendName + "###" + Values.friendFname;
                    GarretHomeActivity.myFriendsList.remove(tmp);
                    GarretHomeActivity.changeMeFriendsFlag = true;
                    set();
                    GarretHomeActivity.writeMyTvShow();
                }
            }
        });
        checkSwitch();

    }


    public void checkCover(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(countCover >= tv.size()){
                                list.setVisibility(View.GONE);
                                list.setVisibility(View.VISIBLE);
                            }else{
                                checkCover();
                            }

                        }
                    });
                } catch (Exception exception) {
                    // createAndShowDialog(exception, "Error");
                }
                return null;
            }
        }.execute();
    }




    public void checkSwitch(){
        for(int i = 0; i < GarretHomeActivity.myFriends.size(); i++){
            if((Values.friendNick + "###" + Values.friendName + "###" + Values.friendFname).equals(GarretHomeActivity.myFriends.get(i).getTmp())){
                my.setChecked(true);
            }
        }
    }

    public void set(){
        GarretHomeActivity.item.setTvshow(Values.tvshow);
        GarretHomeActivity.item.setId(Values.nick);
        GarretHomeActivity.item.setNick(Values.nick);
        GarretHomeActivity.item.setPassword(Values.password);
        GarretHomeActivity.item.setName(Values.name);
        GarretHomeActivity.item.setFirstname(Values.fname);
        GarretHomeActivity.item.setFriends(sendFriends());
        Values.friends = sendFriends();
    }

    String sendFriends() {
        String tmp = "";
        for(int i = 0; i < GarretHomeActivity.myFriendsList.size(); i++){
            tmp += GarretHomeActivity.myFriendsList.toArray()[i] + "@@@";
        }
        return tmp;
    }
    public void createFriendsList(){
        AdapterFriendTv adapter;
//        list.setAdapter(null);
        adapter = new AdapterFriendTv(this, tv);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Values.friendNick = tv.get((int)id).getNick();
                // openFriends();
//                largeCover.setImageBitmap(Blur.fastblur(FriendClass.this, tv.get((int) id).getCover(), 4));
                bar.setVisibility(View.VISIBLE);
                Values.url = tv.get((int) id).getUrl();
                Values.id = (int) id;
                Values.open = false;
                Values.coverFriendTv = tv.get((int)id).getCover();
               // circl.setVisibility(View.VISIBLE);
                new ParsetvShow().execute();
                checkTv();
            }
        });
        //circl.setVisibility(View.INVISIBLE);

    }

    public void checkTv(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(Values.open){
                                //circl.setVisibility(View.INVISIBLE);
                                //circl_my.setVisibility(View.INVISIBLE);
                                startActivity(tvShow);
                                bar.setVisibility(View.INVISIBLE);
                            }else{
                                checkTv();
                            }

                        }
                    });
                } catch (Exception exception) {
                    // createAndShowDialog(exception, "Error");
                }
                return null;
            }
        }.execute();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        tv.clear();
        Values.coverFriendTv = null;
        Values.setCover = false;
    }
}
