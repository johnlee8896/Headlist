package org.cap.exercise.headlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.cap.exercise.headlist.R;
import org.cap.exercise.headlist.bean.DataBean;
import org.cap.exercise.headlist.bean.ItemBean;

import java.util.List;

/**
 * Created by liweifeng on 29/03/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ItemBean> dataList;
    private Context context;
    private ItemClickCallBack clickCallBack;

    public MyAdapter(Context context, List<ItemBean> dataList) {

        this.context = context;
        this.dataList = dataList;
    }

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Glide.with(context).load(dataList.get(position).getImageHref()).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.progressloading).error(R.mipmap.image_failure).into(viewHolder.imageView);

        String title = dataList.get(position).getTitle();
        String description = dataList.get(position).getDescription();
        viewHolder.titleTextView.setText(title == null ? "" : title);
        viewHolder.descriptionTextView.setText(description == null ? "" : description);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.title);
            descriptionTextView = view.findViewById(R.id.description);
            imageView = view.findViewById(R.id.image);
        }
    }
}

