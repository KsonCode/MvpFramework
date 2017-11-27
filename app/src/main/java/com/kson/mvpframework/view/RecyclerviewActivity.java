package com.kson.mvpframework.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.kson.mvpframework.R;
import com.kson.mvpframework.adapter.ImageGridviewAdapter;
import com.kson.mvpframework.adapter.ImageListviewAdapter;
import com.kson.mvpframework.adapter.ImageStaggeredAdapter;
import com.kson.mvpframework.base.BaseActivity;
import com.kson.mvpframework.common.Api;
import com.kson.mvpframework.model.entity.ImageEntity;
import com.kson.mvpframework.utils.LoggerManager;
import com.kson.mvpframework.utils.OkCallback;
import com.kson.mvpframework.utils.OkHttpMethod;
import com.kson.mvpframework.utils.OkhttpUtils;
import com.kson.mvpframework.utils.recyclerview.DividerItemDecoration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerviewActivity extends BaseActivity {


    private Button lvBtn, gvBtn, staggerBtn, addBtn, deleteBtn;
    private int page = 1;
    private RecyclerView recyclerView;
    private List<ImageEntity.Image> list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    @Override
    public int bindLayout() {
        return R.layout.activity_recyclerview;
    }

    @Override
    public void setListener() {
        lvBtn.setOnClickListener(this);
        gvBtn.setOnClickListener(this);
        staggerBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    ImageListviewAdapter lvAdapter;
    ImageStaggeredAdapter staggerAdapter;
    LinearLayoutManager lmanager;

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.btn_lv:
                lvAdapter = new ImageListviewAdapter(this, list);
                lmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(lmanager);
                recyclerView.setAdapter(lvAdapter);

                lvAdapter.setOnItemClickLitener(new ImageListviewAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        System.out.println("clickpossss:"+position);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

                break;
            case R.id.btn_gv:
                ImageGridviewAdapter gvAdapter = new ImageGridviewAdapter(this, list);
                GridLayoutManager gmanager = new GridLayoutManager(this, 2);
                recyclerView.setLayoutManager(gmanager);
                recyclerView.setAdapter(gvAdapter);
                break;
            case R.id.btn_staggered:
                staggerAdapter = new ImageStaggeredAdapter(this, list);

                StaggeredGridLayoutManager smanager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(smanager);
                recyclerView.setAdapter(staggerAdapter);
                staggerAdapter.setOnItemClickLitener(new ImageStaggeredAdapter.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        LoggerManager.getInstance(tag).e("clickpos:" + position);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        LoggerManager.getInstance(tag).e("longclickpos:" + position);

                    }
                });
                break;
            case R.id.btn_add:
                lvAdapter.add();
                lmanager.scrollToPosition(0);


                break;
            case R.id.btn_delete:
                lvAdapter.removeItem();
                lmanager.scrollToPosition(0);
                break;
        }

    }

    @Override
    public void initView() {

        lvBtn = (Button) findViewById(R.id.btn_lv);
        gvBtn = (Button) findViewById(R.id.btn_gv);
        staggerBtn = (Button) findViewById(R.id.btn_staggered);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        addBtn = (Button) findViewById(R.id.btn_add);
        deleteBtn = (Button) findViewById(R.id.btn_delete);
    }

    @Override
    public void initData() {
        //动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        list = new ArrayList<>();
//        loadData();
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });


        loadDatas();


    }

    /**
     * 请求图片列表
     */
    private void loadData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
//        OkhttpUtils.getOkhttpInstance(this)
//                .call(OkHttpMethod.GET, Api.GETIMAGES, params, new OkCallback() {
//                    @Override
//                    public void onFailure(String e, String msg) {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onResponse(String result) {
//                        System.out.println("1------------------");
//
//                        swipeRefreshLayout.setRefreshing(false);
//                        ImageEntity imageEntity = new Gson().fromJson(result, ImageEntity.class);
//                        list = imageEntity.data;
//                        setAdapter();
//
//                    }
//                });

        OkhttpUtils.getOkhttpInstance(this)
                .call(OkHttpMethod.GET, Api.GETAD, params, new OkCallback() {
                    @Override
                    public void onFailure(String e, String msg) {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String result) {
                        System.out.println("2------------");

                        swipeRefreshLayout.setRefreshing(false);
//                        ImageEntity imageEntity = new Gson().fromJson(result, ImageEntity.class);
//                        list = imageEntity.data;
//                        setAdapter();

                    }
                });
    }

    private void loadDatas() {
        Map<String, Object> params = new HashMap<>();
        params.put("method","baidu.ting.billboard.billList");
        params.put("type","1");
        params.put("size","10");
        params.put("offset","0");

        OkhttpUtils.getOkhttpInstance(this)
                .call(OkHttpMethod.GET, Api.MUSIC, params, new OkCallback() {
                    @Override
                    public void onFailure(String e, String msg) {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String result) {
                        System.out.println("1------------------");
                        System.out.println("1result:"+result);

//                        swipeRefreshLayout.setRefreshing(false);
//                        ImageEntity imageEntity = new Gson().fromJson(result, ImageEntity.class);
//                        list = imageEntity.data;
//                        setAdapter();

                    }
                });


    }

    /**
     * 设置适配器
     */
    private void setAdapter() {


    }



    public static void main(String[] args){

        List<Double> doubles = new ArrayList<>();
        doubles.add(100.12);
        doubles.add(199.12);

        System.out.println("++++++++"+add(doubles));
        System.out.println("--------"+subtract(doubles));

    }

    public static double add(List<Double> values){
        double sum = 0;
        for (int i = 0; i < values.size(); i++) {
            double value = values.get(i);
            BigDecimal b1 = new BigDecimal(Double.toString(sum));
            BigDecimal b2 = new BigDecimal(Double.toString(value));
            sum = b1.add(b2).doubleValue();
        }

        return sum;
    }

    /**
     * 提供精确加法计算的add方法
     * @param values 值几何
     * @return 累加的和
     */
    public static double subtract(List<Double> values){
        double sum = 0;
        for (int i = 0; i < values.size(); i++) {
            double value = values.get(i);
            BigDecimal b1 = new BigDecimal(Double.toString(sum));
            BigDecimal b2 = new BigDecimal(Double.toString(value));
            sum = b1.subtract(b2).doubleValue();
        }

        return sum;
    }


}
