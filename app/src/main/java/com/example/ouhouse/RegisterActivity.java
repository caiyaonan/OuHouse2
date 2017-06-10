package com.example.ouhouse;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private TextView tv;
    private EditText username;
    private EditText password;
    private EditText name;
    private EditText sex;
    private EditText number;
    private Button reg;
    private ProgressDialog dialog;
    private String info;
    private static Handler handler = new Handler();
    private Spinner sex_spinner;
    private List<String> sex_data_list;
    private ArrayAdapter<String> arr_adapter;
    private String select_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);
        reg = (Button) findViewById(R.id.reg);
        sex_spinner = (Spinner) findViewById(R.id.spinner);
        //数据
        sex_data_list = new ArrayList<String>();
        sex_data_list.add("男");
        sex_data_list.add("女");
        //适配器
        arr_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sex_data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        sex_spinner.setAdapter(arr_adapter);
        sex_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_sex = sex_data_list.get(position);
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkNetwork()){
                    Toast toast = Toast.makeText(RegisterActivity.this,"网络未连接",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }

                dialog = new ProgressDialog(RegisterActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("正在注册，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                //创建子线程，分别进行get和post传输
                new Thread(new RegisterActivity.MyThread()).start();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //子线程接收数据，主线程修改数据
    public  class MyThread implements Runnable{
        @Override
        public void run() {
            info = WebService.regeditUser(username.getText().toString(),password.getText().toString(),name.getText().toString(),select_sex,number.getText().toString());
            //info =WebServicePost.executeHttpPost(username.getText().toString(),password.getText().toString());
            if(info == "注册成功"){

            }
            handler.post(new Runnable(){
                @Override
                public void run() {
                        switch (info){
                            case "0":
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("注册成功，即将跳转到登陆界面");
                                builder.setTitle("提示");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RegisterActivity.this,login_Activity.class);
                                        startActivity(intent);
                                    }
                                });
                                builder.create().show();
                                break;
                            case "1":
                                tv.setText("注册失败,请重新填写资料");
                                break;
                        }
//                    tv.setText(info);
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
