package com.example.greendao;

import android.app.Application;

import com.example.greendao.entity.DaoMaster;
import com.example.greendao.entity.DaoSession;
import com.example.greendao.entity.Student;
import com.example.greendao.entity.StudentDao;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.rx.RxDao;

public class App extends Application {
    DaoMaster.DevOpenHelper devOpenHelper;
    DaoMaster daoMaster;
    static DaoSession daoSession;
    static StudentDao stuDao = null;
    static RxDao<Student,Long> rxStuDao = null;
    //static RxQuery<Student> rxQueryStuDao = null;
    static QueryBuilder<Student> rxQueryStuDaoBuilder = null;

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

    public static RxDao<Student, Long> getRxStuDao() {

        if (rxStuDao == null) {
            rxStuDao = daoSession.getStudentDao().rx();
        }
        return rxStuDao;

    }

    public static QueryBuilder<Student> getRxQueryStuDaoBuilder() {

        if (rxQueryStuDaoBuilder == null) {
            rxQueryStuDaoBuilder = daoSession.getStudentDao().queryBuilder();
        }
        return rxQueryStuDaoBuilder;

    }
}
