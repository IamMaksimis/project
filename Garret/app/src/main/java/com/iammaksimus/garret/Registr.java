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

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 111 on 13.01.2016.
 */
public class Registr extends AppCompatActivity {

    public static MobileServiceClient mClient;
    public static MobileServiceTable<user> mToDoTable;
    public static user item = new user();
    public static List<user> toDoItemList = new ArrayList<user>();
    Button regBtn;
    EditText nick, name, fname, password; TextView error;
    boolean regist = true;
    ProgressBar circl;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr);
        azureConect();
        intent = new Intent(this, GarretHomeActivity.class);
        nick = (EditText)findViewById(R.id.nickText);
        name = (EditText)findViewById(R.id.nameText);
        fname = (EditText)findViewById(R.id.fnameText);
        password = (EditText)findViewById(R.id.passText);
        error = (TextView)findViewById(R.id.errorText);
        regBtn = (Button)findViewById(R.id.regBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( (nick.getText().toString().length() > 0) && (name.getText().toString().length() > 0) && (fname.getText().toString().length() > 0) && (password.getText().toString().length() > 5)) {
                    circl.setVisibility(View.VISIBLE);
                    selectItems();
                }else{
                    circl.setVisibility(View.INVISIBLE);
                    error.setText("Проверьте данные");
                }
            }

        });
        circl = (ProgressBar)findViewById(R.id.progressBar3);
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

    public void createUser(){
        item.setId(String.valueOf( nick.getText()));
        item.setNick(String.valueOf(nick.getText()));
        item.setName(String.valueOf(name.getText()));
        item.setFirstname(String.valueOf(fname.getText()));
        item.setPassword(String.valueOf(password.getText()));
        item.setTvshow("");
        item.setFriends("");
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        mToDoTable.insert(item).get();
                    } catch (Exception exception) {
                        // createAndShowDialog(exception, "Error");
                    }
                    return null;
                }
            }.execute();
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
                            for (user item : result) {
                                toDoItemList.add(item);
                                if (nick.getText().equals(item.getNick())) {
                                    regist = false;
                                    //error.setText("Такой пользователь уже есть");
                                    break;
                                } else {
                                    regist = true;
                                }
                            }
                            if (regist) {
                                createUser();
                                Values.nick = item.getNick().toString();
                                Values.fname = item.getFirstname().toString();
                                Values.name = item.getName().toString();
                                Values.password = item.getPassword().toString();
                                Values.tvshow = "";
                                Values.friends = "";
                                writeFileSD(item.getNick() + "///" + item.getName() + "///" + item.getFirstname()+ "///" + item.getPassword().toString());
                                circl.setVisibility(View.INVISIBLE);
                                startActivity(intent);
                                finish();
                            } else {
                                error.setText("Такой пользователь уже есть");
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

    public void write(String user) throws IOException {
        FileOutputStream fOut = openFileOutput("garret\\us.dat", MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);
        // записываем строку в файл
        osw.write(user);
 /* проверяем, что все действительно записалось и закрываем файл */
        osw.flush();
        osw.close();
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
