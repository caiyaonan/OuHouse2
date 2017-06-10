package com.example.ouhouse;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class house_show_Activity extends AppCompatActivity {


    private ImageView img,img1,img2,img3,img4,img5,img6,img7,img8;
    private TextView title;
    private TextView x_title;
    private TextView val;
    private TextView age;
    private TextView status;
    private TextView user_count;
    private TextView car;
    private TextView cover_area;
    private TextView covered_are;
    private TextView property_company;
    private TextView property_fee;
    private TextView phone;
    private User_tools user_tools;
    private Button like;
    private ProgressDialog dialog;
    private JSONObject info;
    private static Handler handler = new Handler();
    String value;
    String house_name;
    String val_net;
    String img_url;
    String phone_net;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_show_);
        title = (TextView) findViewById(R.id.title);
        x_title = (TextView) findViewById(R.id.x_title);
        val = (TextView) findViewById(R.id.val);
        age = (TextView) findViewById(R.id.age);
        status = (TextView) findViewById(R.id.status);
        user_count = (TextView) findViewById(R.id.user_count);
        car = (TextView) findViewById(R.id.car);
        cover_area = (TextView) findViewById(R.id.cover_area);
        covered_are = (TextView) findViewById(R.id.covered_are);
        property_company = (TextView) findViewById(R.id.property_company);
        property_fee = (TextView) findViewById(R.id.property_fee);
        phone = (TextView) findViewById(R.id.phone);
        img = (ImageView) findViewById(R.id.house_img);
        like= (Button) findViewById(R.id.like_btn);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Intent intent = getIntent();
        value = intent.getStringExtra("house_name");
        if(!checkNetwork()){
            Toast toast = Toast.makeText(house_show_Activity.this,"网络未连接",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
        //提示框
        dialog = new ProgressDialog(house_show_Activity.this);
        dialog.setTitle("提示");
        dialog.setMessage("正在登陆，请稍后...");
        dialog.setCancelable(false);
        dialog.show();
        //创建子线程，分别进行get和post传输
        new Thread(new house_show_Activity.MyThread()).start();

        final PullUpToLoadMore ptlm = (PullUpToLoadMore) findViewById(R.id.ptlm);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ptlm.scrollToTop();
            }
        });
        //收藏按钮
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @SuppressLint("NewApi")
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                user_tools = new User_tools();
                String username=user_tools.getUsername();

                WebService.user_like_insert(username,house_name,img_url,phone_net,val_net);
            }
        });
    }
    //子线程接收数据，主线程修改数据
    public  class MyThread implements Runnable{
        @Override
        public void run() {
            info = WebService.house_show(value);
            //info =WebServicePost.executeHttpPost(username.getText().toString(),password.getText().toString());
            handler.post(new Runnable(){
                @Override
                public void run() {
                    try {
                        house_name = info.getString("楼盘别名");//1
                        val_net = info.getString("价格");//2
                        int age_net = info.getInt("产权年限");//3
                        String status_net = info.getString("装修情况");//4
                        int user_count_net = info.getInt("规划户数");//5
                        int car_net = info.getInt("车位数");//6
                        int cover_area_net = info.getInt("占地面积");//7
                        int covered_are_net = info.getInt("建筑面积");//8
                        String property_company_net = info.getString("物业公司");//9
                        float property_fee_net = (float) info.getDouble("物业费");//10
                        img_url = info.getString("图片");//11
                        phone_net = info.getString("联系号码");//12

//                        Bitmap bitmap = getHttpBitmap(img_url);
                        setPicBitmap(img,img_url);



                        title.setText(house_name);
                        x_title.setText(house_name);
                        val.setText("价格："+val_net);
                        age.setText("产权年限："+age_net);
                        status.setText("装修情况："+status_net);
                        user_count.setText("规划户数："+user_count_net);
                        car.setText("车位数："+car_net);
                        cover_area.setText("占地面积："+cover_area_net);
                        covered_are.setText("建筑面积："+covered_are_net);
                        property_company.setText("物业公司："+property_company_net);
                        property_fee.setText("物业费："+property_fee_net);
                        phone.setText("联系号码："+phone_net);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            });
        }}
    //检测网络类
    private boolean checkNetwork(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo()!=null){
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
    //得到网络图片类
//    public static Bitmap getHttpBitmap(String url){
//        URL myFileURL;
//        Bitmap bitmap = null;
//        try {
//            Log.d("img_url", url);
//            myFileURL = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try{
//            myFileURL = new URL(url);
//            //获得链接
//            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
//            //，conn.setConnectionTiem(0);表示没有时间限制
//            conn.setConnectTimeout(0);
//            //连接设置获得的数据流
//            conn.setDoInput(true);
//            conn.connect();
//            //得到数据流
//            InputStream is = conn.getInputStream();
//            //解析得到图片
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return bitmap;
//    }

    private  Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            img.setImageBitmap(bitmap);
        }
    };
    public void setPicBitmap(final ImageView ivPiv,final String pic_url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HttpURLConnection conn = (HttpURLConnection) new URL(pic_url).openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(0);
                    conn.setUseCaches(false);
                    conn.connect();

                    int code = conn.getResponseCode();
                    if(code == 200){
                        InputStream is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);

                        Message msg = handler2.obtainMessage();
                        msg.obj = bitmap;
                        handler2.sendMessage(msg);
                    }else{
                        Toast.makeText(house_show_Activity.this,"请求失败",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
