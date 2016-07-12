package com.iammaksimus.garret;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 111 on 13.01.2016.
 */
public class SwitchClass extends AppCompatActivity {
    Button  reg, log;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        reg = (Button)findViewById(R.id.openRegistrBtn);
        final Intent registr = new Intent(this, Registr.class);
        final Intent login = new Intent(this, Login.class);
        reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(registr);
                finish();
            }

        });
        log = (Button)findViewById(R.id.openLoginBtn);
        log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(login);
                finish();
            }

        });
    }
}
