package com.example.ouhouse;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Magot on 2017/6/6.
 */

public class User_like_Adapter extends RecyclerView.Adapter<User_like_Adapter.ViewHolder> {

    private List<User_like> mUser_like;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView user_like_img;
        TextView user_like_name;
        TextView user_like_number;
        TextView user_like_val;
        Button user_like_delete;
        View user_like_view;
        private User_tools user_tools;
        public ViewHolder(View itemView) {
            super(itemView);
            user_like_view = itemView;
            user_like_img= (ImageView) itemView.findViewById(R.id.user_like_img);
            user_like_name = (TextView) itemView.findViewById(R.id.user_like_name);
            user_like_val = (TextView) itemView.findViewById(R.id.user_like_val);
            user_like_number = (TextView) itemView.findViewById(R.id.user_like_number);
            user_like_delete = (Button) itemView.findViewById(R.id.user_like_delete);
        }
    }

    public User_like_Adapter(List<User_like> user_likes){
        mUser_like = user_likes;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_like_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.user_like_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(Build.VERSION_CODES.GINGERBREAD)
            @SuppressLint("NewApi")
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                int position = holder.getAdapterPosition();
                User_like user_like = mUser_like.get(position);
                String name = user_like.getUser_like_name();
                User_tools user_tools = new User_tools();
                String username = user_tools.getUsername();
                WebService.user_like_delete(username,name);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final User_like user_like = mUser_like.get(position);
        setPicBitmap(user_like.getUser_like_img(),holder);
        holder.user_like_name.setText(user_like.getUser_like_name());
        holder.user_like_val.setText(user_like.getUser_like_val());
        holder.user_like_number.setText(user_like.getUser_like_number());
    }

    @Override
    public int getItemCount() {
        return mUser_like.size();
    }

    public void setPicBitmap(final String pic_url, final ViewHolder holder){
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
                        holder.user_like_img.setImageBitmap(bitmap);
//                        Message msg = handler2.obtainMessage();
//                        msg.obj = bitmap;
//                        handler2.sendMessage(msg);
                    }else{
                        Toast.makeText(holder.user_like_view.getContext(),"获取图片失败",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
