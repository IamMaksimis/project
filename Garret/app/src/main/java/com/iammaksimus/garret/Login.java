package com.iammaksimus.garret;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 111 on 13.01.2016.
 */
public class Login extends AppCompatActivity {

    public static MobileServiceClient mClient;
    public static MobileServiceTable<user> mToDoTable;
    public static user item = new user();
    public static List<user> toDoItemList = new ArrayList<user>();
    Button logBtn;
    TextView nick, name, fname, password, error;
    boolean regist = false;
    ProgressBar circl;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        intent = new Intent(this, GarretHomeActivity.class);
        azureConect();
        nick = (EditText)findViewById(R.id.nickText);
        password = (EditText)findViewById(R.id.passText);
        error = (TextView)findViewById(R.id.errorText);
        logBtn = (Button)findViewById(R.id.logBtn);
        logBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                circl.setVisibility(View.VISIBLE);
                selectItems();
            }

        });
        circl = (ProgressBar)findViewById(R.id.progressBar2);
    }

    //Метод запроса селект
    public void selectItems(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<user> result = mToDoTable.execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(user item : result){
                                //toDoItemList.add(item);
                                if((String.valueOf(item.getNick().toString()).equals(String.valueOf(nick.getText().toString()))) && (String.valueOf(item.getPassword().toString()).equals(String.valueOf(password.getText().toString())))){
                               // if((nick.getText().equals(item.getNick())) && (password.getText().equals(item.getPassword()))){
                                    Values.nick = item.getNick().toString();
                                    Values.fname = item.getFirstname().toString();
                                    Values.name = item.getName().toString();
                                    Values.password = item.getPassword().toString();
                                    if(!item.getTvshow().toString().equals(null)) {
                                        Values.tvshow = item.getTvshow().toString();
                                    }
                                    if(!item.getFriends().toString().equals(null)){
                                        Values.friends = item.getFriends().toString();
                                    }

                                    if((Values.friends.equals("")) && (Values.friends.equals(null)) && (Values.friends.equals("null"))){
                                        Values.friends = "null";
                                    }
                                    writeFileSD(item.getNick().toString() + "///" + item.getName().toString() + "///" + item.getFirstname().toString() + "///" + item.getPassword().toString() + "///" + item.getTvshow().toString() + "///" + Values.friends.toString());
                                    writeFileSDTV(Values.tvshow);

                                    regist = true;
                                    ServiceValues.item = item;
                                    ServiceValues.mToDoTable = mToDoTable;
                                    if(!item.getTvshow().equals(null)){
                                        tv(item.getTvshow());
                                    }
                                    GarretHomeActivity.startCreateList2 = true;
                                    break;
                                }else{
                                    regist = false;
                                }
                            }
                            if(regist){
                                circl.setVisibility(View.INVISIBLE);
                                startActivity(intent);
                                finish();
                            }else{
                                circl.setVisibility(View.INVISIBLE);
                                error.setText("Такого пользователя не существет");
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

    public void tv(String t){
        String[] tmp;
        tmp = t.split("@@@");
        for(int i = 0; i < tmp.length; i++){
            GarretHomeActivity.mySerials.add(tmp[i]);
        }
    }

    //Метод подключения к бд
    public void azureConect(){
        try {
            mClient = new MobileServiceClient(
                    "https://garret.azure-mobile.net/",
                    "WKgdlGLtIduwvbsvSrIakaLdHRmUFi32",
                    this
            );
            mToDoTable = mClient.getTable(user.class);
        } catch (MalformedURLException e) {
            error.setText("error");
        }
    }

    public void write(String user){
        try {
            OutputStreamWriter outStream =
                    new OutputStreamWriter(openFileOutput(Environment.getDataDirectory() + "us.txt", 0));

            outStream.write("gfhf");
            outStream.close();


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + e.toString(), Toast.LENGTH_LONG)
                    .show();

        }

    }


    static void writeFileSDTV(String tv) {
        tv = tv.replaceAll("@@@", "///");
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
        File sdFile = new File(sdPath, "tv.dat");
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


    void writeFileSD(String user) {
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
        File sdFile = new File(sdPath, "us.dat");
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            bw.write(user);
            // закрываем поток
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
