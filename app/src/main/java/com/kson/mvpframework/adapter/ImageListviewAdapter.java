package com.kson.mvpframework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kson.mvpframework.R;
import com.kson.mvpframework.model.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2017/10/12
 * Description:
 */
public class ImageListviewAdapter extends RecyclerView.Adapter<ImageListviewAdapter.MyViewHolder> {
    private int height;
    private List<ImageEntity.Image> list;
    private Context mContext;

    public ImageListviewAdapter(Context context, List<ImageEntity.Image> list) {
        this.mContext = context;
        this.list = list;
    }

//    public void addItem(){
//        if (list == null){
//            list = new ArrayList<>();
//
//        }
//        list.add(0,new ImageEntity.Image("1","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507742676160&di=f14c9550d77ee6c26e4446e3d3bcba7a&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F962bd40735fae6cd85d642a106b30f2443a70f94.jpg"));
//        notifyItemInserted(0);
//    }
//
    public void removeItem(){
        if (list!=null){
            list.remove(1);
            notifyItemRemoved(1);
        }
    }

    public void add(){
        if (list==null){
            list = new ArrayList<>();
        }
        list.add(1,new ImageEntity.Image("1","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507742676160&di=f14c9550d77ee6c26e4446e3d3bcba7a&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F962bd40735fae6cd85d642a106b30f2443a70f94.jpg"));
        notifyItemInserted(1);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.activity_img_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ImageEntity.Image image = list.get(position);



        Glide.with(mContext).load(image.imgurl).into(holder.iv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //避免未知错乱
                int pos = holder.getLayoutPosition();
                if (mOnItemClickLitener!=null){
                    mOnItemClickLitener.onItemClick(holder.itemView,pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int pos = holder.getLayoutPosition();
                if (mOnItemClickLitener!=null){
                    mOnItemClickLitener.onItemLongClick(holder.itemView,pos);
                }
                return false;
            }
        });
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

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}