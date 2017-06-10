package com.example.ouhouse;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.R.attr.start;

/**
 * Created by Magot on 2017/5/16.
 */

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder> {
    private List<House> mHouseList;
    private Context mContext;
    private LayoutInflater house_show_layout = null;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View houseView;
        ImageView HouseImage;
        TextView HouseName;

        public ViewHolder(View view){
            super(view);
            houseView = view;
            HouseImage = (ImageView) view.findViewById(R.id.House_image);
            HouseName = (TextView) view.findViewById(R.id.House_name);
        }

    }

    public HouseAdapter(List<House> houseList,Context context){
        mHouseList = houseList;
        this.mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.house_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.houseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                House house = mHouseList.get(position);
                Toast.makeText(v.getContext(),"你点击了"+house.getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("house_name",house.getName());
                intent.setClass(mContext,house_show_Activity.class);
                mContext.startActivity(intent);
            }
        });

        holder.HouseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                House house = mHouseList.get(position);
                Toast.makeText(v.getContext(),"你点了图片"+house.getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("house_name",house.getName());
                intent.setClass(mContext,house_show_Activity.class);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        House house = mHouseList.get(position);
        holder.HouseImage.setImageResource(house.getImageId());
        holder.HouseName.setText(house.getName());
    }

    @Override
    public int getItemCount() {
        return mHouseList.size();
    }
}
