package com.kson.mvpframework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kson.mvpframework.R;
import com.kson.mvpframework.model.entity.ImageEntity;

import java.util.List;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/12
 * Description:
 */
public class LvAdapter extends RecyclerView.Adapter<LvAdapter.MyHolder>{
    private Context context;
    private  List<ImageEntity.Image> list ;

    public LvAdapter(Context context, List<ImageEntity.Image> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.activity_img_item,null);
        MyHolder myHolder = new MyHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        Glide.with(context).load(list.get(position).imgurl).into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int pos
                if (onItemClickListener!=null){
                    onItemClickListener.onClick(view,position);//在没有增加删除局部刷新的使用，能记录正确位置，否则不行
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onLongClick(view,position);//在没有增加删除局部刷新的使用，能记录正确位置，否则不行
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        private ImageView iv;

        public MyHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface  OnItemClickListener{
        void onClick(View view,int pos);
        void onLongClick(View view,int pos);
    }
}
