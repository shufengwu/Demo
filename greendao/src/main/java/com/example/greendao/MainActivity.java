package com.example.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greendao.entity.Student;
import com.example.greendao.entity.StudentDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnInsertOneData;
    Button btnInsertMultipleData;
    Button btnQueryData;
    Button btnQueryData1;
    Button btnQueryDataOrderDown;
    Button btnQueryDataOrderUp;
    Button btnQueryDataOrderAbove;
    Button btnQueryDataOrderBelow;
    Button btnQueryDataFirstFive;
    Button btnQueryDataFirstFiveJumpThree;
    Button btnQueryDataAmount;
    Button btnDeleteDataNameSfw;
    Button btnDeleteDataNameWsf;
    Button btnUpdateData;
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsertOneData = findViewById(R.id.btn_insert_one_data);
        btnInsertOneData.setOnClickListener(this);
        btnInsertMultipleData = findViewById(R.id.btn_insert_multiple_data);
        btnInsertMultipleData.setOnClickListener(this);
        btnQueryData = findViewById(R.id.btn_query_data);
        btnQueryData.setOnClickListener(this);
        btnQueryData1 = findViewById(R.id.btn_query_data_1);
        btnQueryData1.setOnClickListener(this);
        btnQueryDataOrderDown = findViewById(R.id.btn_query_data_order_down);
        btnQueryDataOrderDown.setOnClickListener(this);
        btnQueryDataOrderUp = findViewById(R.id.btn_query_data_order_up);
        btnQueryDataOrderUp.setOnClickListener(this);
        btnQueryDataOrderAbove = findViewById(R.id.btn_query_data_above);
        btnQueryDataOrderAbove.setOnClickListener(this);
        btnQueryDataOrderBelow = findViewById(R.id.btn_query_data_below);
        btnQueryDataOrderBelow.setOnClickListener(this);
        btnQueryDataFirstFive = findViewById(R.id.btn_query_data_first_five);
        btnQueryDataFirstFive.setOnClickListener(this);
        btnQueryDataFirstFiveJumpThree = findViewById(R.id.btn_query_data_first_five_jump_three);
        btnQueryDataFirstFiveJumpThree.setOnClickListener(this);
        btnQueryDataAmount = findViewById(R.id.btn_query_data_amount);
        btnQueryDataAmount.setOnClickListener(this);
        btnDeleteDataNameSfw = findViewById(R.id.btn_delete_data_name_sfw);
        btnDeleteDataNameSfw.setOnClickListener(this);
        btnDeleteDataNameWsf = findViewById(R.id.btn_delete_data_name_wsf);
        btnDeleteDataNameWsf.setOnClickListener(this);
        btnUpdateData = findViewById(R.id.btn_update_data);
        btnUpdateData.setOnClickListener(this);
        tvData = findViewById(R.id.tv_data);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_insert_one_data:
                insertOneData();
                break;
            case R.id.btn_insert_multiple_data:
                insertMultipleData();
                break;
            case R.id.btn_query_data:
                queryData();
                break;
            case R.id.btn_query_data_1:
                queryDataByName();
                break;
            case R.id.btn_query_data_order_down:
                queryDataByOrderDown();
                break;
            case R.id.btn_query_data_order_up:
                queryDataByOrderUp();
                break;
            case R.id.btn_query_data_above:
                queryDataAbove();
                break;
            case R.id.btn_query_data_below:
                queryDataBelow();
                break;
            case R.id.btn_query_data_first_five:
                queryDataFirstFive();
                break;
            case R.id.btn_query_data_first_five_jump_three:
                queryDataFirstFiveJumpThree();
                break;
            case R.id.btn_query_data_amount:
                queryDataAmount();
                break;
            case R.id.btn_delete_data_name_wsf:
                deleteDataNameWsf();
                break;
            case R.id.btn_delete_data_name_sfw:
                deleteDataNameSfw();
                break;
            case R.id.btn_update_data:
                updateData();
                break;
            default:
                break;
        }
    }

    /**
     * 更新数据
     */
    /*private void updateData() {
        List<Student> stuList = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("wsf")).list();
        if (stuList != null) {
            for (int i = 0; i < stuList.size(); i++) {
                stuList.get(i).setStuName("sfw");
                App.getStuDao().update(stuList.get(i));
            }
            Toast.makeText(this, "更新数据成功", Toast.LENGTH_SHORT).show();
        }
    }*/
    private void updateData() {
        List<Student> list = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("wsf")).list();
        for(Student s:list){
            s.setStuName("sfw");
        }
        Observable<Iterable<Student>> observable = App.getStuDao().rx()
                .updateInTx(list).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Action1<Iterable<Student>>() {
            @Override
            public void call(Iterable<Student> students) {
                
                Toast.makeText(MainActivity.this, "更新数据成功", Toast.LENGTH_SHORT).show();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 删除指定数据
     */
    /*private void deleteDataNameSfw() {
        App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("sfw")).buildDelete().executeDeleteWithoutDetachingEntities();
        Toast.makeText(this, "删除名为sfw的数据！", Toast.LENGTH_SHORT).show();
    }*/

    /**
     * 删除指定数据:RxJava
     */
    private void deleteDataNameSfw() {
        Observable<Void> observable = App.getStuDao().rx()
                .deleteInTx(
                        App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("sfw")
                        ).list())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 删除指定数据
     */
    /*private void deleteDataNameWsf() {
        //App.getRxQueryStuDaoBuilder().where(StudentDao.Properties.StuName.eq("wsf")).rx()
        App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("wsf")).buildDelete().executeDeleteWithoutDetachingEntities();
        Toast.makeText(this, "删除名为wsf的数据！", Toast.LENGTH_SHORT).show();
    }*/

    /**
     * 删除指定数据    RxJava
     */
    private void deleteDataNameWsf() {
        Observable<Void> observable = App.getStuDao().rx().deleteInTx(App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("wsf")).list());
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void v) {
                        Toast.makeText(MainActivity.this, "删除名为wsf的数据！", Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    /**
     * 查询数据总数
     */
    /*private void queryDataAmount() {
        int count = App.getStuDao().queryBuilder().list().size();
        Toast.makeText(this, "数据总条数 " + count, Toast.LENGTH_SHORT).show();
    }*/

    /**
     * 查询数据总数  Rxjava
     */
    private void queryDataAmount() {
        Observable<List<Student>> observable = App.getStuDao().queryBuilder().rx().list();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        Toast.makeText(MainActivity.this, "数据总条数 " + students.size(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    /**
     * 前五条数据，跳过前三条
     */
    /*private void queryDataFirstFiveJumpThree() {
        List<Student> stuList = App.getStuDao().queryBuilder().limit(5).offset(3).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }*/

    /**
     * 前五条数据，跳过前三条    RxJava
     */
    private void queryDataFirstFiveJumpThree() {
        Observable<List<Student>> observable = App.getStuDao().queryBuilder().limit(5).offset(3).rx().list();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        if (students != null) {
                            String searchAllInfo = "";
                            for (int i = 0; i < students.size(); i++) {
                                Student stu = students.get(i);
                                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
                            }
                            tvData.setText(searchAllInfo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    /**
     * 前五条数据
     */
    /*private void queryDataFirstFive() {
        List<Student> stuList = App.getStuDao().queryBuilder().limit(5).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }*/

    /**
     * 前五条数据    RxJava
     */
    private void queryDataFirstFive() {
        Observable<List<Student>> observable = App.getStuDao().queryBuilder().limit(5).rx().list();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        if (students != null) {
                            String searchAllInfo = "";
                            for (int i = 0; i < students.size(); i++) {
                                Student stu = students.get(i);
                                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
                            }
                            tvData.setText(searchAllInfo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    /**
     * lt 小于
     * gt 大于
     * eq 等于
     * le 小于等于
     * ge 大于等于
     */
    /*private void queryDataBelow() {
        List<Student> stuList = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuScore.lt(50)).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }*/
    private void queryDataBelow() {
        Observable<List<Student>> observable = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuScore.lt(50)).rx().list();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        if (students != null) {
                            String searchAllInfo = "";
                            for (int i = 0; i < students.size(); i++) {
                                Student stu = students.get(i);
                                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
                            }
                            tvData.setText(searchAllInfo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    /*private void queryDataAbove() {
        List<Student> stuList = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuScore.gt(50)).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }*/

    private void queryDataAbove() {
        Observable<List<Student>> observable = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuScore.gt(50)).rx().list();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        if (students != null) {
                            String searchAllInfo = "";
                            for (int i = 0; i < students.size(); i++) {
                                Student stu = students.get(i);
                                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
                            }
                            tvData.setText(searchAllInfo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    /**
     * 查询数据降序排列
     */
    /*private void queryDataByOrderDown() {
        List<Student> stuList = App.getStuDao().queryBuilder().orderDesc(StudentDao.Properties.StuScore).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }*/

    /**
     * 查询数据降序排列:Rxjava
     */
    private void queryDataByOrderDown() {
        Observable<List<Student>> observable = App.getStuDao().queryBuilder().orderDesc(StudentDao.Properties.StuScore).rx().list();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        if (students != null) {
                            String searchAllInfo = "";
                            for (int i = 0; i < students.size(); i++) {
                                Student stu = students.get(i);
                                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
                            }
                            tvData.setText(searchAllInfo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * 查询数据升序排列
     */
    /*private void queryDataByOrderUp() {
        List<Student> stuList = App.getStuDao().queryBuilder().orderAsc(StudentDao.Properties.StuScore).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }*/

    /**
     * 查询数据升序排列:Rxjava
     */
    private void queryDataByOrderUp() {
        Observable<List<Student>> observable = App.getStuDao().queryBuilder().orderAsc(StudentDao.Properties.StuScore).rx().list();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        if (students != null) {
                            String searchAllInfo = "";
                            for (int i = 0; i < students.size(); i++) {
                                Student stu = students.get(i);
                                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
                            }
                            tvData.setText(searchAllInfo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * 根据条件查询
     */
    /*private void queryDataByName() {
        List<Student> stuList = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("wsf")).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }*/

    /**
     * 根据条件查询:Rxjava QueryBuilder
     */
    private void queryDataByName() {
        Observable<List<Student>> observable = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("wsf")).rx().list();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        if (students != null) {
                            String searchAllInfo = "";
                            for (int i = 0; i < students.size(); i++) {
                                Student stu = students.get(i);
                                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
                            }
                            tvData.setText(searchAllInfo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    /**
     * 查询所有数据
     */
    /*private void queryData() {
        List<Student> stuList = App.getStuDao().queryBuilder().list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }*/

    /**
     * 查询所有数据:Rxjava QueryBuilder
     */
    /*private void queryData() {
        App.getRxQueryStuDaoBuilder().rx().list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        if (students != null) {
                            String searchAllInfo = "";
                            for (int i = 0; i < students.size(); i++) {
                                Student stu = students.get(i);
                                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
                            }
                            tvData.setText(searchAllInfo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

    /**
     * 查询所有数据:Rxjava loadAll
     */

    private void queryData() {
        Observable<List<Student>> observable = App.getStuDao().rx().loadAll();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Student>>() {
                    @Override
                    public void call(List<Student> students) {
                        if (students != null) {
                            String searchAllInfo = "";
                            for (int i = 0; i < students.size(); i++) {
                                Student stu = students.get(i);
                                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
                            }
                            tvData.setText(searchAllInfo);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    /**
     * 插入多条数据
     */
    /*private void insertMultipleData() {
        List<Student> stuList = new ArrayList<Student>();
        stuList.add(new Student(null, "005", "sfw", "M", "43"));
        stuList.add(new Student(null, "006", "sfw", "M", "35"));
        stuList.add(new Student(null, "007", "sfw", "M", "99"));
        stuList.add(new Student(null, "008", "sfw", "M", "88"));
        App.getStuDao().insertInTx(stuList);
        Toast.makeText(this, "新增成功~", Toast.LENGTH_SHORT).show();
    }*/

    /**
     * 插入多条数据:Rxjava
     */
    private void insertMultipleData() {
        List<Student> stuList = new ArrayList<Student>();
        stuList.add(new Student(null, "005", "sfw", "M", "43"));
        stuList.add(new Student(null, "006", "sfw", "M", "35"));
        stuList.add(new Student(null, "007", "sfw", "M", "99"));
        stuList.add(new Student(null, "008", "sfw", "M", "88"));

        Observable<Iterable<Student>> observable = App.getStuDao().rx().insertInTx(stuList)
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Action1<Iterable<Student>>() {
            @Override
            public void call(Iterable<Student> students) {
                Toast.makeText(MainActivity.this, "新增成功！", Toast.LENGTH_SHORT).show();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    /**
     * 插入一条数据
     */
    /*private void insertOneData() {
        Student stu = new Student(null, "001", "wsf", "F", "50");
        long end = App.getStuDao().insert(stu);
        String msg = "";
        if (end > 0) {
            msg = "001新增成功~";
        } else {
            msg = "新增失败~";
        }
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }*/

    /**
     * 插入一条数据：RxJava
     */
    private void insertOneData() {
        Student stu = new Student(null, "001", "wsf", "F", "50");
        Observable<Student> observable = App.getStuDao().rx().insert(stu).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Action1<Student>() {
            @Override
            public void call(Student student) {
                Toast.makeText(MainActivity.this, "新增成功！", Toast.LENGTH_SHORT).show();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
