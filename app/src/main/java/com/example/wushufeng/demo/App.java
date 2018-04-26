package com.example.wushufeng.demo;

import android.app.Application;

import com.idescout.sql.SqlScoutServer;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by wushufeng on 2018/4/26.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        SqlScoutServer.create(this, getPackageName());
    }
}
