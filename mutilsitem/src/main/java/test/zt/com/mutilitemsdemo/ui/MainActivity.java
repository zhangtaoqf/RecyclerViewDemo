package test.zt.com.mutilitemsdemo.ui;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import test.zt.com.mutilitemsdemo.MyApp;
import test.zt.com.mutilitemsdemo.R;
import test.zt.com.mutilitemsdemo.adapter.QsLatesdAdapter;
import test.zt.com.mutilitemsdemo.bean.QsLatesdEntity;
import test.zt.com.mutilitemsdemo.uri.AppInterface;

public class MainActivity extends BaseActivity {

    ListView listView;
    QsLatesdAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = ((ListView) findViewById(R.id.activity_main_listViewId));
        adapter = new QsLatesdAdapter(this);
        listView.setAdapter(adapter);
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
