package com.example.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greendao.entity.DaoMaster;
import com.example.greendao.entity.DaoSession;
import com.example.greendao.entity.Picture;
import com.example.greendao.entity.Student;
import com.example.greendao.entity.StudentDao;

import java.util.ArrayList;
import java.util.List;

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
    private void updateData() {
        List<Student> stuList = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("wsf")).list();
        if (stuList != null) {
            for (int i = 0; i < stuList.size(); i++) {
                stuList.get(i).setStuName("sfw");
                App.getStuDao().update(stuList.get(i));
            }
            Toast.makeText(this, "更新数据成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除指定数据
     */
    private void deleteDataNameSfw() {
        App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("sfw")).buildDelete().executeDeleteWithoutDetachingEntities();
        Toast.makeText(this, "删除名为sfw的数据！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 删除指定数据
     */
    private void deleteDataNameWsf() {
        App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("wsf")).buildDelete().executeDeleteWithoutDetachingEntities();
        Toast.makeText(this, "删除名为wsf的数据！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 查询数据总数
     */
    private void queryDataAmount() {
        int count = App.getStuDao().queryBuilder().list().size();
        Toast.makeText(this, "数据总条数 "+count, Toast.LENGTH_SHORT).show();
    }

    /**
     * 前五条数据，跳过前三条
     */
    private void queryDataFirstFiveJumpThree() {
        List<Student> stuList = App.getStuDao().queryBuilder().limit(5).offset(3).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }

    /**
     * 前五条数据
     */
    private void queryDataFirstFive() {
        List<Student> stuList = App.getStuDao().queryBuilder().limit(5).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }

    /**
     * lt 小于
     * gt 大于
     * eq 等于
     * le 小于等于
     * ge 大于等于
     */
    private void queryDataBelow() {
        List<Student> stuList = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuScore.lt(50)).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }

    private void queryDataAbove() {
        List<Student> stuList = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuScore.gt(50)).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }

    /**
     * 查询数据降序排列
     */
    private void queryDataByOrderDown() {
        List<Student> stuList = App.getStuDao().queryBuilder().orderDesc(StudentDao.Properties.StuScore).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }

    /**
     * 查询数据升序排列
     */
    private void queryDataByOrderUp() {
        List<Student> stuList = App.getStuDao().queryBuilder().orderAsc(StudentDao.Properties.StuScore).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }

    /**
     * 根据条件查询
     */
    private void queryDataByName() {
        List<Student> stuList = App.getStuDao().queryBuilder().where(StudentDao.Properties.StuName.eq("wsf")).list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }

    /**
     * 查询所有数据
     */
    private void queryData() {
        List<Student> stuList = App.getStuDao().queryBuilder().list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Student stu = stuList.get(i);
                searchAllInfo += "id：" + stu.getStuId() + " 编号：" + stu.getStuNo() + " 姓名：" + stu.getStuName() + " 性别：" + stu.getStuSex() + " 成绩：" + stu.getStuScore() + "\n";
            }
            tvData.setText(searchAllInfo);
        }
    }

    /**
     * 插入多条数据
     */
    private void insertMultipleData() {
        List<Student> stuList = new ArrayList<Student>();
        Picture picture = new Picture(null,"test","url_1");
        App.getPictureDao().insert(picture);
        stuList.add(new Student(null, "005", "sfw", "M", "43",picture.getId()));
        stuList.add(new Student(null, "006", "sfw", "M", "35",picture.getId()));
        stuList.add(new Student(null, "007", "sfw", "M", "99",picture.getId()));
        stuList.add(new Student(null, "008", "sfw", "M", "88",picture.getId()));
        App.getStuDao().insertInTx(stuList);
        Toast.makeText(this, "新增成功~", Toast.LENGTH_SHORT).show();
    }

    /**
     * 插入一条数据
     */
    private void insertOneData() {
        Picture picture = new Picture(null,"test2","url_2");
        App.getPictureDao().insert(picture);
        Student stu = new Student(null, "001", "wsf", "F", "50",picture.getId());
        long end = App.getStuDao().insert(stu);
        String msg = "";
        if (end > 0) {
            msg = "001新增成功~";
        } else {
            msg = "新增失败~";
        }
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }


}
