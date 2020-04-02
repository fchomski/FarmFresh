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

import butterknife.BindView;
import butterknife.ButterKnife;

public class searchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater mLayoutInflater;
    private Context mname;
    private List<product> mDatas;
    private TextView name;
    private TextView price;
    private ImageView img;
    private OnItemClickListener mOnItemClickListener;


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

        int price=pro.getPrice();
        String name=pro.getName();
        ((searchItemViewHolder) holder).mimg.setImageResource(R.drawable.watermelon);
        ((searchItemViewHolder) holder).name.setText(name);
        ((searchItemViewHolder) holder).price.setText("$"+ price);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }

        // item long click
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemClickListener.onItemLongClick(holder.itemView, position);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }





    static class searchItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_pic)
        ImageView mimg;
        @BindView(R.id.pname)
        TextView name;
        @BindView(R.id.pprice)
        TextView price;

        public searchItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);



        }
    }
}
