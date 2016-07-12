package com.iammaksimus.garret;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.TimeZone;

/**
 * Created by 111 on 11.01.2016.
 */
public class GarretHomeActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    TabHost.TabSpec tabSpec;
    protected static final String LOG_TAG = "my_tag";
    public static ArrayList<ElementTvShow> element = new ArrayList<ElementTvShow>();
    public static ArrayList<ElementTvShow> searchEl = new ArrayList<ElementTvShow>();
    public static ArrayList<ElementTvShow> myElement = new ArrayList<ElementTvShow>();
    public static ArrayList<Friend> myFriends = new ArrayList<Friend>();
    public static ArrayList<Friend> myFriendsSearch = new ArrayList<Friend>();
    public static TextView test, name, nick, fname;
    public static ToDoItemAdapter mAdapter;
    static EditText serchText, searchTextFriends;
    public static boolean startCreateList = false, startCreateList2 = false, sorry = false, changeMeListFlag = false, changeMeFriendsFlag = false, searchTVorFriends = false;
    public static MobileServiceClient mClient;
    public static MobileServiceTable<user> mToDoTable;
    static ListView list, myList, listFriends;
    static int countImage = 0, dialog;
    static HashSet<String> mySerials = new HashSet<String>();
    static HashSet<String> myFriendsList = new HashSet<String>();
    ImageButton search, clear, searchFriends, clearFrieands;
    static ProgressBar circl, circl_my, fcircl;
    Intent tvShow;
    static ImageView testIm;
    static Toolbar toolbar;
    public static user item = new user();
    Intent friendActivity;
    Button yourTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garret_home_activity);
        //получаем TabHost
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbar_text_color));
        //toolbar.setLogo(getResources().getDrawable(R.drawable.logo));

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        //инициализация
        tabHost.setup();
        yourTv = (Button)findViewById(R.id.yourTv);
        yourTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Values.friendNick = Values.nick;
                Values.friendName = Values.name;
                Values.friendFname = Values.fname;
                circl_my.setVisibility(View.VISIBLE);
                openFriends();
            }
        });

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("", getResources().getDrawable(R.drawable.friends));
        tabSpec.setContent(R.id.tab2);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag4");
        tabSpec.setIndicator("", getResources().getDrawable(R.drawable.home));
        tabSpec.setContent(R.id.tab4);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("", getResources().getDrawable(R.drawable.cal));
        tabSpec.setContent(R.id.tab3);
        tabHost.addTab(tabSpec);

        // вторая вкладка по умолчанию активна
        tabHost.setCurrentTabByTag("tag4");

        // логгируем переключение вкладок
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                //Log.d(LOG_TAG, "tabId = " + tabId);
                if (tabId.equals("tag3")) {
                    toolbar.setTitle("График на 7 дней");
                    searchTVorFriends = true;
                }
                if (tabId.equals("tag4")) {
                    toolbar.setTitle(Values.nick);
                }
                if (tabId.equals("tag2")) {
                    toolbar.setTitle("Подписки");
                    searchTVorFriends = false;
                }

            }
        });
        friendActivity = new Intent(this, FriendClass.class);
       // startService(new Intent(this, ServiceValues.class));
        azureConect();
       // testIm = (ImageView)findViewById(R.id.imageView);
        tvShow = new Intent(this, TvShow.class);
        circl = (ProgressBar)findViewById(R.id.progressBar);
        circl_my = (ProgressBar)findViewById(R.id.progressBar_my);
        fcircl = (ProgressBar)findViewById(R.id.progressBarFriends);
        serchText = (EditText) this.findViewById(R.id.searchText);
        serchText.setOnEditorActionListener(this);
        searchTextFriends = (EditText) this.findViewById(R.id.searchFriends);
        searchTextFriends.setOnEditorActionListener(this);
        Typeface typeFace = Typeface.createFromAsset(this.getAssets(), "font1.ttf");
        list = (ListView) this.findViewById(R.id.all_tv_show_list);
        myList = (ListView) this.findViewById(R.id.all_tv_show_list_my);
        listFriends = (ListView) this.findViewById(R.id.list_friends);
        //test = (TextView) findViewById(R.id.test);
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        int month = now.get(Calendar.MONTH) + 1;
        int year = now.get(Calendar.YEAR);
        int day = now.get(Calendar.DATE);

        if((Values.elementTvShows.size() > 0) && (Values.month == month) && (Values.day == day) && (Values.year == year)){
            element = Values.elementTvShows;
            createList();
            circl_my.setVisibility(View.INVISIBLE);
        }else{

            Values.month = now.get(Calendar.MONTH) + 1;
            Values.year = now.get(Calendar.YEAR);
            Values.day = now.get(Calendar.DATE);
            new  ParseTask().execute();
        }


        search = (ImageButton)findViewById(R.id.searchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                circl.setVisibility(View.VISIBLE);
                new SearchTv().execute();
                checkList2();

            }

        });
        searchFriends = (ImageButton)findViewById(R.id.searchBtnFriends);
        searchFriends.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                searchFreands();

            }

        });

        clear = (ImageButton)findViewById(R.id.backspase);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                list.setAdapter(null);
                createList();
                serchText.setText("");

            }

        });
        clearFrieands = (ImageButton)findViewById(R.id.backspaseFriends);
        clearFrieands.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listFriends.setAdapter(null);
                createFriendsList();
                searchTextFriends.setText("");

            }

        });



        String us = "";
        us = readFileSD();
        if(!us.equals("")){
            String[] tmp;
            tmp = us.split("///");
            if(tmp.length == 4){
                Values.nick = tmp[0];
                Values.fname = tmp[1];
                Values.name = tmp[2];
                Values.password = tmp[3];
                circl_my.setVisibility(View.INVISIBLE);
            }else{
                if(tmp.length == 5){
                    Values.nick = tmp[0];
                    Values.fname = tmp[1];
                    Values.name = tmp[2];
                    Values.password = tmp[3];
                    Values.tvshow = tmp[4];
                }else{
                    Values.nick = tmp[0];
                    Values.fname = tmp[1];
                    Values.name = tmp[2];
                    Values.password = tmp[3];
                    Values.tvshow = tmp[4];
                    Values.friends = tmp[5];

                }
            }
            if(Values.friends.length() > 0){
                String[] temp;
                temp = Values.friends.split("@@@");
                for(int i = 0; i < temp.length; i++){
                    if(!temp[i].equals(null)) {
                        myFriendsList.add(temp[i]);
                    }
                }
            }

            if(Values.tvShow.equals("")){
                readTvShow();
            }
            checkList();
        }
        if(Values.nick.equals("")){
            startActivity(new Intent(this, SwitchClass.class));
            finish();
            //changeProf();
        }else {
        }
       // azureConect();
        changeMyList();





        String[] tmp = Values.friends.split("@@@");
        if(tmp.length > 0) {
            for (int i = 0; i < tmp.length; i++) {
                myFriendsList.add(tmp[i]);
                //myFriends.add(new Friend(tmp[i]));
            }

            createFriendsList();
        }

        Context context = getApplicationContext();
        setRecurringAlarm(context);
        if(readDialod().equals("0")){
            dialog = 0;
        }else {
            if (readDialod().equals(day)) {
                dialog = 1;
            }else{
                dialog = 2;
            }
        }

    }

    public static int getLevenshteinDistance(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int n = s.length(); // length of s
        int m = t.length(); // length of t
        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }
        if (n > m) {
            // Меняем строки местами, для экономии памяти
            String tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }
        int p[] = new int[n+1]; //предыдущий массив цен по горизонтали
        int d[] = new int[n+1]; // массив цен по горизонтали
        int _d[]; //для обмена p и d
        // индексы s и t
        int i;
        int j;
        char t_j; // j-ый символ строки t
        int cost; // стоимость
        for (i = 0; i<=n; i++) {
            p[i] = i;
        }
        for (j = 1; j<=m; j++) {
            t_j = t.charAt(j-1);
            d[0] = j;
            for (i=1; i<=n; i++) {
                cost = s.charAt(i-1)==t_j ? 0 : 1;
                // минимальная ячейка слева + 1,сверху + 1, по диагонали слева сверху + стоимость
                d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1),  p[i-1]+cost);
            }
            _d = p;
            p = d;
            d = _d;
        }
        return p[n];
    }

    public void searchFreands(){
        myFriendsSearch.clear();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<user> result = mToDoTable.where().execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //mAdapter.clear();
                            for (user item : result) {
                                if(getLevenshteinDistance(item.getName(), searchTextFriends.getText().toString()) < 3){
                                    myFriendsSearch.add(new Friend(item.getName() + "###" + item.getNick() + "###" + item.getFirstname()));
                                }
                            }
                            createFriendsListSearch();
                        }
                    });
                } catch (Exception exception) {
                   // createAndShowDialog(exception, "Error");
                }
                return null;
            }
        }.execute();
    }




    private void setRecurringAlarm(Context context) {

        // we know mobiletuts updates at right around 1130 GMT.
        // let's grab new stuff at around 11:45 GMT, inexactly
        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        updateTime.set(Calendar.HOUR_OF_DAY, 11);
        updateTime.set(Calendar.MINUTE, 45);

        Intent downloader = new Intent(context, BroadCast.class);
        PendingIntent recurringDownload = PendingIntent.getBroadcast(context,
                0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) getSystemService(
                Context.ALARM_SERVICE);
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                updateTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, recurringDownload);
    }


    public void checkList(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(startCreateList){
                                createList();
                            }else{
                                checkList();
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


    public void checkList2(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(startCreateList){
                                if(sorry){
                                    Toast.makeText(GarretHomeActivity.this, "По Вашему запросу ничего не найдено.", Toast.LENGTH_LONG).show();
                                    sorry = false;
                                    circl.setVisibility(View.INVISIBLE);
                                }else{
                                    createList2();
                                }
                            }else{
                                checkList2();
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



    public void createFriendsList(){
        myFriends.clear();
        for (int i = 0; i < myFriendsList.size(); i++) {
            myFriends.add(new Friend((String) myFriendsList.toArray()[i]));
        }
            AdapterFriends adapter;
            listFriends.setAdapter(null);
            adapter = new AdapterFriends(this, myFriends);
            adapter.notifyDataSetChanged();
        listFriends.setAdapter(adapter);
        listFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Values.friendNick = myFriends.get((int) id).getNick();
                Values.friendName = myFriends.get((int) id).getName();
                Values.friendFname = myFriends.get((int) id).getFname();
                openFriends();
            }
        });
            //circl.setVisibility(View.INVISIBLE);

    }



    public void openFriends(){
        fcircl.setVisibility(View.VISIBLE);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<user> result = mToDoTable.where().execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //mAdapter.clear();
                            for (user item : result) {
                                if(item.getNick().equals(Values.friendNick.toString())){
                                    Values.friendsTvShow = item.getTvshow();
                                    startActivity(friendActivity);
                                    fcircl.setVisibility(View.INVISIBLE);
                                    circl_my.setVisibility(View.INVISIBLE);
                                    checkFriends();
                                    break;

                                }
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



    public void createFriendsListSearch(){
      /*  String[] tmp = Values.friends.split("@@@");
        if(tmp.length > 1) {
            for (int i = 0; i < tmp.length; i++) {
                myFriends.add(new Friend(tmp[i]));
            }*/
        fcircl.setVisibility(View.INVISIBLE);

        AdapterFriends adapter;
        listFriends.setAdapter(null);
        adapter = new AdapterFriends(this, myFriendsSearch);
        adapter.notifyDataSetChanged();
        listFriends.setAdapter(adapter);
        listFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Values.friendNick = myFriendsSearch.get((int) id).getName();
                Values.friendName = myFriendsSearch.get((int) id).getFname();
                Values.friendFname = myFriendsSearch.get((int) id).getNick();
                fcircl.setVisibility(View.VISIBLE);
                openFriends();
            }
        });
        //circl.setVisibility(View.INVISIBLE);
        //}
    }


    public void checkFriends(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(changeMeFriendsFlag){
                                createFriendsList();
                                changeMeFriendsFlag = false;
                            }else{
                                checkFriends();
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

    public void checkTv(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(startCreateList2){
                                circl.setVisibility(View.INVISIBLE);
                                circl_my.setVisibility(View.INVISIBLE);
                                startActivity(tvShow);
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








    private void createList() {
        SimpleAdapter adapter;
        list.setAdapter(new AdapterElement(this, element));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Values.url = element.get((int) id).getUrl();
                Values.id = (int) id;
                startCreateList2 = false;
                circl.setVisibility(View.VISIBLE);
                new ParsetvShow().execute();
                checkTv();
            }
        });
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // Log.d(LOG_TAG, "scrollState = " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (int i = firstVisibleItem; i < (firstVisibleItem + visibleItemCount) && i < totalItemCount; i++) {
                    element.get(i).loadCover();
                }

            }
        });
        startCreateList = false;
        circl.setVisibility(View.INVISIBLE);
        Values.elementTvShows = element;
        createMyList();
    }

    private void createList2() {
        AdapterElement adapter;
        list.setAdapter(null);
        adapter = new AdapterElement(this, searchEl);adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Values.url = searchEl.get((int) id).getUrl();
                Values.id = (int) id;
                startCreateList2 = false;
                circl.setVisibility(View.VISIBLE);
                new ParsetvShow().execute();
                checkTv();
            }
        });
        for(int i = 0; i < searchEl.size(); i++){
            searchEl.get(i).loadCover();
        }
        startCreateList = false;
        circl.setVisibility(View.INVISIBLE);
    }

    public static void checkMyList(){
        myElement.clear();
        for(int i = 0; i < element.size(); i++){
            for(int j = 0; j < mySerials.size(); j++){
                if(element.get(i).getRusName().toString().equals(mySerials.toArray()[j].toString())){
                    myElement.add(element.get(i));

                }
            }
        }

    }

    private void createMyList(){
        circl_my.setVisibility(View.INVISIBLE);
        checkMyList();
        //myElement.add(element.get(Values.id));
        ServiceValues.myelementTvShows = myElement;
        AdapterElement adapter;
        myList.setAdapter(null);
        adapter = new AdapterElement(this, myElement);
        adapter.notifyDataSetChanged();
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Values.url = myElement.get((int) id).getUrl();
                circl_my.setVisibility(View.VISIBLE);
                Values.id = (int) id;
                startCreateList2 = false;
                new ParsetvShow().execute();
                checkTv();
            }
        });
        myList.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // Log.d(LOG_TAG, "scrollState = " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (int i = firstVisibleItem; i < (firstVisibleItem + visibleItemCount) && i < totalItemCount; i++) {
                    myElement.get(i).loadCover();
                }

            }
        });
        //ServiceNotification.myelementTvShows = myElement;
       // writeChache();
    }

    public void changeMyList(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!changeMeListFlag){
                                changeMyList();
                            }else{
                                createMyList();
                                changeMeListFlag = false;
                                changeMyList();
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


    static void writeMyTvShow(){
        Values.tvShow = "";
        for(int i = 0; i < mySerials.size(); i++){
            Values.tvShow += mySerials.toArray()[i] + "@@@";
        }
        writeFileSD(Values.tvShow);
        mToDoTable.update(item);
    }


    public void azureConect(){
        try {
            mClient = new MobileServiceClient(
                    "https://garret.azure-mobile.net/",
                    "WKgdlGLtIduwvbsvSrIakaLdHRmUFi32",
                    this
            );
            mToDoTable = mClient.getTable(user.class);
        } catch (MalformedURLException e) {

        }
    }



    public String readFileSD() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return "";
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret");
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "us.dat");
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
                str = br.readLine();
            } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return str;
    }

    public String readDialod() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return "";
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret");
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "dialog.dat");
        String str = "";
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            str = br.readLine();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return str;
    }

    static void writeFileSD(String tv) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        String tmp = "";
        tmp += Values.nick + "///" + Values.fname + "///" + Values.name + "///" + Values.password + "///" + Values.tvshow + "///" + Values.friends;
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret");
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "us.dat");
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            bw.write(tmp);
            // закрываем поток
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void writeFileCache(String tv) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret");
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "cache.dat");
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            bw.write(tv);
            // закрываем поток
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeFileCacheAll(String tv) {
        // проверяем доступность SD
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        int month = now.get(Calendar.MONTH) + 1;
        int year = now.get(Calendar.YEAR);
        int day = now.get(Calendar.DATE);
        String data = "" + day + " " + month + " " + year;
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret/" + "allTvShow");
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "cache.dat");
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            bw.write(tv + "\n");
            bw.write(data);
            // закрываем поток
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    static void n(){
        new ParserForNotification().execute("");
    }

    public void readTvShow() {

        String[] tmp;
        tmp = Values.tvshow.split("@@@");
        for(int i = 0; i < tmp.length; i++){
            mySerials.add(tmp[i]);
        }

    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if(searchTVorFriends) {
                // обрабатываем нажатие кнопки поиска
                circl.setVisibility(View.VISIBLE);
                new SearchTv().execute();
                checkList2();
                return true;
            }else{
                fcircl.setVisibility(View.VISIBLE);
                searchFreands();
                return true;
            }
        }

        return false;
    }




    static void cache(ElementTvShow el, int i){
        String[] tmp = new String[8];
        tmp[0] = el.getRusName();
        tmp[1] = el.getEngName();
        tmp[2] = el.getDataTv();
        tmp[3] = el.getNumSezon().replaceAll("Сезон: ", "");
        tmp[4] = el.getNameEpisod().replaceAll("Название: ", "").replaceAll("\n", "");
        tmp[5] = el.getNumEpisod().replaceAll("Эпизод: ", "");
        tmp[6] = el.getUrl();
        tmp[7] = el.getUrlCover();
        Bitmap bmp = el.getCover();


        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret/" + "garretTvShowCache" + i);
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "garret" + i + ".dat");
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            bw.write(tmp[0] + "\n");
            bw.write(tmp[1] + "\n");
            bw.write(tmp[2] + "\n");
            bw.write(tmp[3] + "\n");
            bw.write(tmp[4] + "\n");
            bw.write(tmp[5] + "\n");
            bw.write(tmp[6] + "\n");
            bw.write(tmp[7]);
            // закрываем поток
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private static void SaveImage(Bitmap finalBitmap, int i) {
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret/" + "allTvShow/" + "garretTvShowCache" + i);
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "garret" + i + ".jpg");
        String root = Environment.getExternalStorageDirectory().toString();

        if (sdFile.exists()) sdFile.delete();
        try {
            FileOutputStream out = new FileOutputStream(sdFile);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
       // element.clear();
          //createMyList();
        element = Values.elementTvShows;

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Values.elementTvShows = element;
        element.clear();
        myElement.clear();
        myFriends.clear();
    }


    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    @Override
    public void onBackPressed() {
        if(dialog == 2){
            openQuitDialog();
        }else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onPause() {

        super.onPause();
    }



    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                GarretHomeActivity.this);
        quitDialog.setTitle("Пожалуйста, оцените наше приложение");

        quitDialog.setPositiveButton("Оценить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.iammaksimus.garret"));
                startActivity(intent);
                GarretHomeActivity.dialog = 0;
                chackDialog(000000000);
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Calendar now = Calendar.getInstance(TimeZone.getDefault());
                int day = now.get(Calendar.DATE);
                if(GarretHomeActivity.dialog == 0){

                }else{
                    chackDialog(day);
                }
                GarretHomeActivity.dialog = 1;
            }
        });

        quitDialog.show();
    }



    static void chackDialog(int data){


        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "garret/");
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, "dialog" + ".dat");
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            bw.write(data + "");
            // закрываем поток
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
