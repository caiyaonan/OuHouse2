package com.example.ouhouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class heart_Activity extends AppCompatActivity {

    ImageButton home_button;
    ImageButton heart_button;
    ImageButton person_button;

    private User_tools user_tools_sesion_id;
    private StringBuilder user_like_info;
    private JSONObject rs;
    private String user_like_img;
    private String user_like_name;
    private String user_like_val;
    private String user_like_number;
    private static Handler handler = new Handler();

    private List<User_like> user_likes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

//        username = (TextView) findViewById(R.id.ha_username);
//        name = (TextView) findViewById(R.id.title);
//        sex = (TextView) findViewById(R.id.ha_sex);
//        number = (TextView) findViewById(R.id.ha_number);
//        session_id = (TextView) findViewById(R.id.ha_session);

//        us_name = new User_tools();
//        name.setText(us_name.getName());
        new Thread(new heart_Activity.MyThread()).start();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.user_like_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(heart_Activity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        User_like_Adapter adapter = new User_like_Adapter(user_likes);
        recyclerView.setAdapter(adapter);

        home_button = (ImageButton) findViewById(R.id.button_home);
        heart_button = (ImageButton) findViewById(R.id.button_heart);
        person_button = (ImageButton) findViewById(R.id.button_person);

        //底部按钮start
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home_intent = new Intent(heart_Activity.this,MainActivity.class);
                startActivity(home_intent);
                finish();
            }
        });

        person_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_tools_sesion_id = new User_tools();
                if(user_tools_sesion_id.getSession_id().equals("null")){
                    Intent login_intent = new Intent(heart_Activity.this,login_Activity.class);
                    startActivity(login_intent);
                    finish();
                }else{
                    Intent person_intent = new Intent(heart_Activity.this,person_Activity.class);
                    startActivity(person_intent);
                    finish();
                }
//                Intent person_intent = new Intent(heart_Activity.this,login_Activity.class);
//                startActivity(person_intent);
//                finish();
            }
        });

        //底部按钮end
    }
    public class MyThread implements Runnable{
        @Override
        public void run() {
            user_tools_sesion_id = new User_tools();
            String username = user_tools_sesion_id.getUsername();
            StringBuilder list = WebService.user_like_select(username);
            try {
                JSONObject jsonObject = new JSONObject(list.toString());
                for(int i =1;i<jsonObject.length()+1;i++){
                    String position = String.valueOf(i);
                String jsonObject1 =jsonObject.getString(position);
                JSONObject jsonObject2 = new JSONObject(jsonObject1.toString());
                user_like_name = jsonObject2.getString("楼盘别名");
                user_like_number = jsonObject2.getString("联系号码");
                user_like_img = jsonObject2.getString("图片");
                user_like_val = jsonObject2.getString("价格");
                user_likes.add(new User_like(user_like_name, user_like_number, user_like_img, user_like_val));
                }
//                Iterator it = jsonObject.keys();
//                while (it.hasNext()){
//                        String key = (String) it.next();
//                        String value = jsonObject.getString(key);
//                        JSONArray array1 = jsonObject.getJSONArray(key);
//                    for(int i = 1;i<array.length();i++){
//                        JSONObject jsonObject1 = array1.getJSONObject(i);
//                        jsonObject1.put("")
//                        user_like_name = jsonObject1.getString("楼盘别名");
//                        user_like_number = jsonObject1.getString("联系号码");
//                        user_like_img = jsonObject1.getString("图片");
//                        user_like_val = jsonObject1.getString("价格");
//                        User_like user_like = new User_like(user_like_name, user_like_number, user_like_img, user_like_val);
//                        user_likes.add(user_like);
//                    }
//                }
                }catch(JSONException e){
                    e.printStackTrace();
                }

        }
    }
//    private void inituser_like(){
//        User_like user_like = new User_like(user_like_name,user_like_number,user_like_img,user_like_val);
//        user_likes.add(user_like);
//    }
}
