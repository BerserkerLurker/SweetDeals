package com.kallinikos.tech.sweetdeals.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kallinikos.tech.sweetdeals.R;
import com.kallinikos.tech.sweetdeals.model.Category;

import java.util.List;

/**
 * Created by kallinikos on 14/10/16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private static final String TAG = RecyclerAdapter.class.getSimpleName();

    private List<Category> mData;
    private LayoutInflater mInflater;

    public RecyclerAdapter(Context context, List<Category> data){
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cat_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category currentObj = mData.get(position);
        holder.setData(currentObj);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imgThumb;

        public MyViewHolder(View itemView) {
            super(itemView);
            title       = (TextView)  itemView.findViewById(R.id.txv_row);
            imgThumb    = (ImageView) itemView.findViewById(R.id.img_row);
        }

        //From db
        public void setData(Category current) {
            this.title.setText(current.getTitle());
            this.imgThumb.setImageResource(current.getImageId());
        }
    }
}
