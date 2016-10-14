package test.zt.com.mutilitemsdemo;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/8/18.
 */
public class MyApp extends Application {
    private static MyApp app;
    RequestQueue requestQueue;

    public static MyApp getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
        initVolley();

    }

    private void initVolley() {
        requestQueue = Volley.newRequestQueue(this);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
