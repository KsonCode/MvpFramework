package com.kson.mvpframework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kson.mvpframework.App;
import com.kson.mvpframework.R;
import com.kson.mvpframework.model.entity.ImageEntity;

import java.util.List;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/12
 * Description:
 */
public class ImageGridviewAdapter extends RecyclerView.Adapter<ImageGridviewAdapter.MyViewHolder> {
    private int height;
    private List<ImageEntity.Image> list;
    private Context mContext;

    public ImageGridviewAdapter(Context context, List<ImageEntity.Image> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.activity_img_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageEntity.Image image = list.get(position);
        ViewGroup.LayoutParams layoutParams = holder.iv.getLayoutParams();
        layoutParams.height = App.screen_width/2;
        holder.iv.setLayoutParams(layoutParams);
        Glide.with(mContext).load(image.imgurl).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

}