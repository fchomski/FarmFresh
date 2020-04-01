package com.example.farmfresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class searchAdapter<searchItemViewHoder> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private Context mname;
    private List<product> mDatas;
    private TextView name;
    private TextView price;
    private ImageView img;


    public searchAdapter(Context m,List<product> result){
        mname=m;
        mLayoutInflater=LayoutInflater.from(mname);
        mDatas=result;
    }
    public void addItem(product msg){
        mDatas.add(msg);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=mLayoutInflater.inflate(R.layout.item_layout2,parent,false);
        return new searchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        product pro=mDatas.get(position);
        String c=pro.getCategory();
        int price=pro.getPrice();
        String name=pro.getName();
        ((searchItemViewHolder) holder).mimg.setImageResource(R.drawable.watermelon);
        ((searchItemViewHolder) holder).name.setText(name);
        ((searchItemViewHolder) holder).price.setText(price);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    static class searchItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView mimg;
        private TextView name;
        private TextView price;

        public searchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mimg.findViewById(R.id.img);
            name.findViewById(R.id.pname);
            price.findViewById(R.id.pprice);


        }
    }
}
