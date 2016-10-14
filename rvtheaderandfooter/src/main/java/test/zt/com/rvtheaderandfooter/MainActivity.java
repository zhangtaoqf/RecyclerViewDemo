package test.zt.com.rvtheaderandfooter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

/**
 *
 * RecyclerView的线性布局效果添加头部或者尾部
 *
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    RVAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayoutId));
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = ((RecyclerView) findViewById(R.id.recyclerViewId));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        //layout.setReverseLayout(true);
        recyclerView.setLayoutManager(layout);
        adapter = new RVAdapter(this);
        //设置头布局
        View headerView = getLayoutInflater().inflate(R.layout.rv_headerview, recyclerView, false);
        adapter.addHeaderView(headerView);

        //设置头布局
        View footerView = getLayoutInflater().inflate(R.layout.rv_footer, recyclerView, false);

        adapter.addHFooterView(footerView);

        adapter.setLoaderListener(new RVAdapter.LoaderListener() {
            @Override
            public void loadMore() {
                loadData();
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                /*int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                Log.i("info","first:"+firstVisibleItemPosition+"\tsecond:"+lastVisibleItemPosition);*/
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                Log.i("info","first:"+firstVisibleItemPosition+"\tsecond:"+lastVisibleItemPosition);
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        recyclerView.setAdapter(adapter);
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"跳转到头");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                ((LinearLayoutManager) recyclerView.getLayoutManager()).smoothScrollToPosition(recyclerView,null,0);
                break;
            case 1:
                break;
        }
        return super.onOptionsItemSelected(item);
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
                    MainActivity.this.runOnUiThread(new Runnable() {
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
