package test.zt.com.mutilitemsdemo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import test.zt.com.mutilitemsdemo.MyApp;
import test.zt.com.mutilitemsdemo.R;
import test.zt.com.mutilitemsdemo.adapter.RVAdapter;
import test.zt.com.mutilitemsdemo.bean.QsLatesdEntity;
import test.zt.com.mutilitemsdemo.uri.AppInterface;

/**
 *
 * SwipeRefreshLayout：只支持下拉刷新
 *
 *
 */
public class Main2Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    RVAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //1.查找swipeRefreshLayout控件
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.activity_main2_swipeRefreshLayoutId));
        //设置刷新的主题样式
        swipeRefreshLayout.setColorSchemeColors(new int[]{Color.BLUE,Color.RED,Color.YELLOW});
        //查找控件
        recyclerView = ((RecyclerView) findViewById(R.id.activity_main_recycerViewId));
        //固定RecyclerView大小
        recyclerView.setHasFixedSize(true);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //实例化适配器
        adapter = new RVAdapter(this);
        //设置适配器
        recyclerView.setAdapter(adapter);
        //2.设置刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                loadData();
            }
        });


        //加载数据
        loadData();
    }

    private void loadData() {
        StringRequest request = new StringRequest(
                String.format(AppInterface.URL_LATEST, 1),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        QsLatesdEntity qsLatesdEntity = gson.fromJson(response, QsLatesdEntity.class);
                        adapter.addAll(qsLatesdEntity.getItems());
                        //3.加载完成，隐藏刷新进度
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },
                null
        );
        request.setTag("qsLatesd");
        MyApp.getApp().getRequestQueue().add(request);
    }

    @Override
    protected void onDestroy() {
        MyApp.getApp().getRequestQueue().cancelAll("qsLatesd");
        super.onDestroy();
    }
}
