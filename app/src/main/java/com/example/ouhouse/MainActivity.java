package com.example.ouhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<House> houseList = new ArrayList<>();
    ImageButton home_button;
    ImageButton heart_button;
    ImageButton person_button;

    private User_tools user_tools_sesion_id;


    private Banner banner;
    String[] images = new String[]{"url"};
    String[] titles = new String[]{"标题"};

    //banner 图片数据列别
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initHouse();
        home_button = (ImageButton) findViewById(R.id.button_home);
        heart_button = (ImageButton) findViewById(R.id.button_heart);
        person_button = (ImageButton) findViewById(R.id.button_person);
        banner = (Banner) findViewById(R.id.banner);
        banner.setImages(imageUrls);


        //banner的点击事件
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                switch (position){
                    case 0:
                        Toast.makeText(getApplicationContext(),"你选择了第一个", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(),"你选择了第二个", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),"你选择了第三个", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(),"你选择了第四个", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(),"你选择了第五个", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        HouseAdapter adapter = new HouseAdapter(houseList,this);
        recyclerView.setAdapter(adapter);

        //底部按钮start
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        heart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_tools_sesion_id = new User_tools();
                if(user_tools_sesion_id.getSession_id().equals("null")){
                    Intent login_intent = new Intent(MainActivity.this,login_Activity.class);
                    startActivity(login_intent);
                    finish();
                }else{
                    Intent person_intent = new Intent(MainActivity.this,heart_Activity.class);
                    startActivity(person_intent);
                    finish();
                }
//                Intent heart_intent = new Intent(MainActivity.this,heart_Activity.class);
//                startActivity(heart_intent);
//                finish();
            }
        });

        person_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Log.d("Session_id",user_tools_sesion_id.getSession_id());
                user_tools_sesion_id = new User_tools();
               if(user_tools_sesion_id.getSession_id().equals("null")){
                   Intent login_intent = new Intent(MainActivity.this,login_Activity.class);
                   startActivity(login_intent);
                   finish();
                }else{
                   Intent person_intent = new Intent(MainActivity.this,person_Activity.class);
                   startActivity(person_intent);
                   finish();
               }
               }

        });
        //底部按钮end

    }

    private void initHouse() {
        House one = new House("南沙保利城",R.drawable.one);
        houseList.add(one);
        House two = new House("南沙金茂湾",R.drawable.two);
        houseList.add(two);
        House three = new House("合景天峻",R.drawable.three);
        houseList.add(three);
        House four = new House("合生广场",R.drawable.four);
        houseList.add(four);
        House five = new House("好美华庭",R.drawable.five);
        houseList.add(five);
        House six = new House("实地常春藤",R.drawable.six);
        houseList.add(six);
        House seven = new House("广州中航城",R.drawable.seven);
        houseList.add(seven);
        House eighth = new House("广州时代天启",R.drawable.eighth);
        houseList.add(eighth);
        House ninth = new House("水韵翔庭",R.drawable.ninth);
        houseList.add(ninth);
        House ten = new House("碧桂园琥珀湾",R.drawable.ten);
        houseList.add(ten);
        House eleventh = new House("碧桂园豪进左岸",R.drawable.eleventh);
        houseList.add(eleventh);
        House twelfth = new House("科城山庄",R.drawable.twelfth);
        houseList.add(twelfth);
        House thirteenth = new House("翡翠蓝湾",R.drawable.thirteenth);
        houseList.add(thirteenth);
        House fourteenth = new House("阳光城丽景湾(广州)",R.drawable.fourteenth);
        houseList.add(fourteenth);
    }
}
