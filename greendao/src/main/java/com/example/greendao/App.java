package com.example.greendao;

import android.app.Application;

import com.example.greendao.entity.DaoMaster;
import com.example.greendao.entity.DaoSession;
import com.example.greendao.entity.PictureDao;
import com.example.greendao.entity.StudentDao;

public class App extends Application {
    DaoMaster.DevOpenHelper devOpenHelper;
    DaoMaster daoMaster;
    static DaoSession daoSession;
    static StudentDao stuDao = null;
    static PictureDao pictureDao = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // 创建数据
        devOpenHelper = new DaoMaster.DevOpenHelper(this, "hlq.db", null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static StudentDao getStuDao() {

        if (stuDao == null) {
            stuDao = daoSession.getStudentDao();
        }
        return stuDao;

    }

    public static PictureDao getPictureDao(){
        if(pictureDao==null){
            pictureDao = daoSession.getPictureDao();
        }
        return pictureDao;
    }
}
