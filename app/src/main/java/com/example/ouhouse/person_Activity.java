package com.example.ouhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

public class person_Activity extends AppCompatActivity {

    private HashMap<String,String> session;
    private TextView username;
    private TextView name;
    private TextView sex;
    private TextView number;
    private TextView session_id;
    private User_tools user_tools;

    ImageButton home_button;
    ImageButton heart_button;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_);
        home_button = (ImageButton) findViewById(R.id.button_home);
        heart_button = (ImageButton) findViewById(R.id.button_heart);
        logout = (Button) findViewById(R.id.logout);



        username = (TextView) findViewById(R.id.username);
        name = (TextView) findViewById(R.id.name);
        sex = (TextView) findViewById(R.id.sex);
        number = (TextView) findViewById(R.id. number);

        user_tools = new User_tools();
        username.setText(user_tools.getUsername());
        name.setText(user_tools.getName());
        sex.setText(user_tools.getSex());
        number.setText(user_tools.getNumber());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_tools user_tools =new User_tools();
                user_tools.setUsername(null);
                user_tools.setName(null);
                user_tools.setSex(null);
                user_tools.setNumber(null);
                user_tools.setSession_id("null");
                Intent intent = new Intent(person_Activity.this,login_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        //底部按钮start
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home_intent = new Intent(person_Activity.this,MainActivity.class);
                startActivity(home_intent);
                finish();
            }
        });
        heart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent heart_intent = new Intent(person_Activity.this,heart_Activity.class);
                startActivity(heart_intent);
                finish();
            }
        });
        //底部按钮end
    }
    }

