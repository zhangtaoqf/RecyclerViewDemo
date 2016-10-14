package test.zt.com.rvtheaderandfooter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 *
 *
 * 在网格布局效果中添加头部和尾部
 *
 */
public class Main2Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView recyclerView;
    RVAdapter2 adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.activity_main2_refreshLayoutId));
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = ((RecyclerView) findViewById(R.id.activity_main2_recyclerViewId));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new RVAdapter2(this);
        //设置头布局
        View headerView = getLayoutInflater().inflate(R.layout.rv_headerview, recyclerView, false);

        adapter.addHeaderView(headerView);
        //设置头布局
        View footerView = getLayoutInflater().inflate(R.layout.rv_footer, recyclerView, false);

        adapter.addHFooterView(footerView);

        adapter.setLoaderListener(new RVAdapter2.LoaderListener() {
            @Override
            public void loadMore() {
                loadData();
            }
        });

        recyclerView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        ArrayList<String> dd = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            dd.add("item:"+i);
        }
        adapter.addAll(dd);
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Main2Activity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadData();
                            //隐藏刷新进度
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
