package org.cap.exercise.headlist;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.cap.exercise.headlist.adapter.MyAdapter;
import org.cap.exercise.headlist.bean.DataBean;
import org.cap.exercise.headlist.bean.ItemBean;
import org.cap.exercise.headlist.utils.CommonMethod;
import org.cap.exercise.headlist.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private XRecyclerView recyclerView;
    private MyAdapter adapter;
    private int loadMoreTime = 0;
    private List<ItemBean> mDataBeanList = new ArrayList<>();
    private List<ItemBean> allList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initLocalJson();

        initViews();

        setListeners();

        validateData();


    }

    private void validateData() {
        mDataBeanList = new ArrayList<>();
        adapter = new MyAdapter(MainActivity.this,mDataBeanList);
        adapter.setClickCallBack(
                new MyAdapter.ItemClickCallBack() {
                    @Override
                    public void onItemClick(int pos) {
                        // a demo for notifyItemRemoved
                        mDataBeanList.remove(pos);
                        recyclerView.notifyItemRemoved(mDataBeanList,pos);
                    }
                }
        );
        recyclerView.setAdapter(adapter);
        recyclerView.refresh();
    }

    private void setListeners() {
        final int itemLimit = 5;

        // When the item number of the screen number is list.size-2,we call the onLoadMore
        recyclerView.setLimitNumberToCallLoadMore(2);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mDataBeanList.clear();
                        for(int i = 0; i < itemLimit ;i++){
                            mDataBeanList.add(allList.get(i));
                        }
                        adapter.notifyDataSetChanged();
                        if(recyclerView != null)
                            recyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                loadMoreTime++;
                Log.e("aaaaa","call onLoadMore");
                new Handler().postDelayed(new Runnable(){
                    public void run() {

                        if(recyclerView != null) {
                            if (mDataBeanList.size() < allList.size()){
                                for(int i = 0; i < itemLimit ;i++){
                                    if (mDataBeanList.size() < allList.size()){
                                        mDataBeanList.add(allList.get(itemLimit * loadMoreTime + i) );
                                    }
                                }
                                recyclerView.loadMoreComplete();
                            }else{
                                recyclerView.setNoMore(true);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, 1000);

            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        recyclerView
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup)findViewById(android.R.id.content),false);
        recyclerView.addHeaderView(header);

        recyclerView.getDefaultFootView().setLoadingHint("自定义加载中提示");
        recyclerView.getDefaultFootView().setNoMoreHint("自定义加载完毕提示");
    }

    private void initLocalJson() {
        String json = CommonMethod.readLocalJson(this, "data.json");
        DataBean dataBean = GsonUtil.parseJsonToBean(json, DataBean.class);
        if(dataBean!=null){
            List<ItemBean> rows = dataBean.getRows();
            allList.addAll(rows);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(recyclerView != null){
            recyclerView.destroy();
            recyclerView = null;
        }
    }
}
