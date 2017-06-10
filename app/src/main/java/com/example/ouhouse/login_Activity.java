package com.example.ouhouse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.util.HashMap;


public class login_Activity extends AppCompatActivity {

    ImageButton home_button;
    ImageButton heart_button;
    ImageButton person_button;
    EditText username;
    EditText password;
    Button login;
    TextView reg;
    private ProgressDialog dialog;
    private HashMap<String, String> info;
    private static Handler handler = new Handler();
    private User_tools user_tools_session_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        home_button = (ImageButton) findViewById(R.id.button_home);
        heart_button = (ImageButton) findViewById(R.id.button_heart);
        person_button = (ImageButton) findViewById(R.id.button_person);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        reg = (TextView) findViewById(R.id.reg);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_Activity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkNetwork()){
                    Toast toast = Toast.makeText(login_Activity.this,"网络未连接",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                //提示框
                dialog = new ProgressDialog(login_Activity.this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                //创建子线程，分别进行get和post传输
                new Thread(new MyThread()).start();
            }
        });

        reg.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //底部按钮start
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home_intent = new Intent(login_Activity.this,MainActivity.class);
                startActivity(home_intent);
                finish();
            }
        });
        heart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_tools_session_id = new User_tools();
                if(user_tools_session_id.getSession_id().equals("null")){
                    Toast.makeText(login_Activity.this,"请先登陆帐号",Toast.LENGTH_SHORT).show();
                }else{
                    Intent person_intent = new Intent(login_Activity.this,heart_Activity.class);
                    startActivity(person_intent);
                    finish();
                }
            }
        });
        //底部按钮end
    }

    //子线程接收数据，主线程修改数据
    public  class MyThread implements Runnable{
        @Override
        public void run() {
            info = WebService.executeHttpGet(username.getText().toString(),password.getText().toString());
            //info =WebServicePost.executeHttpPost(username.getText().toString(),password.getText().toString());
            handler.post(new Runnable(){
                @Override
                public void run() {


                    if(info.get("flag").equals("true")){
                        Toast.makeText(login_Activity.this,"用户登陆成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login_Activity.this,person_Activity.class);

                        String username_info = info.get("s_username");
                        String name_info = info.get("s_name");
                        String sex_info = info.get("s_sex");
                        String number_info = info.get("s_number");
                        String session_info = info.get("s_session_id");

                        User_tools user_tools =new User_tools();
                        user_tools.setUsername(username_info);
                        user_tools.setName(name_info);
                        user_tools.setSex(sex_info);
                        user_tools.setNumber(number_info);
                        user_tools.setSession_id(session_info);


                        startActivity(intent);


                    }else{
                        Toast.makeText(login_Activity.this,"登陆失败，请重新登陆",Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
    }}
    //检测网络
    private boolean checkNetwork(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo()!=null){
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

}
