package com.example.hoang.fooddy.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.fooddy.DAO.ItemType;
import com.example.hoang.fooddy.Interface.ItemClickListener;
import com.example.hoang.fooddy.R;

import java.util.List;

/**
 * Created by VT-99 on 10/10/2017.
 */

public class AdapterItem  extends RecyclerView.Adapter<AdapterItem.ViewHolder>{
    private List<ItemType> mList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public AdapterItem(List<ItemType> mList, Context context) {
        this.mList = mList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custome_item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemType item = mList.get(position);
        holder.mTitle.setText(item.getName());
        holder.mImgSmall.setImageResource(item.getImageSmall());
        holder.mImgLarge.setImageResource(item.getImageLarge());
    }

    public ItemType getItem(int id) {
        return mList.get(id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public ImageView mImgLarge;
        public ImageView mImgSmall;


        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title_item);
            mImgLarge = (ImageView) itemView.findViewById(R.id.image_out);
            mImgSmall = (ImageView) itemView.findViewById(R.id.image_in);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}
