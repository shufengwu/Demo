package com.example.wushufeng.demo;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wushufeng.demo.entity.Comment;
import com.example.wushufeng.demo.entity.Introduction;
import com.example.wushufeng.demo.entity.News;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindCallback;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;
import org.litepal.exceptions.DataSupportException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    Button add;
    Button delete;
    Button update;
    Button query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.btn_add);
        add.setOnClickListener(this);
        delete = findViewById(R.id.btn_delete);
        delete.setOnClickListener(this);
        update = findViewById(R.id.btn_update);
        update.setOnClickListener(this);
        query = findViewById(R.id.btn_query);
        query.setOnClickListener(this);
        //保存数据
        /*Introduction introduction = new Introduction();
        introduction.setId(1);
        introduction.setGuide("guide1");
        introduction.setDigest("digest1");
        if (introduction.save()) {
            Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show();
        }*/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addNew();
                break;
            case R.id.btn_delete:
                break;
            case R.id.btn_update:
                updateNewsById();
                break;
            case R.id.btn_query:
                //News news = findOneNew();
                //Log.d(TAG, "onClick: "+news.toString());
                List<News> list = findMultipleNews();
                Log.d(TAG, "onClick: "+list.toString());
                break;
            default:
                break;
        }
    }

    private List<News> findMultipleNews() {
        /**
         * 查询指定多个id数据
         */
        //方式一：
        //List<News> newsList = DataSupport.findAll(News.class, 1, 3, 5, 7);

        //方式二：
        /*long[] ids = new long[] { 1, 3, 5, 7 };
        List<News> newsList = DataSupport.findAll(News.class, ids);*/


        /**
         * 连缀查询
         */
        List<News> newsList = DataSupport.select("title", "content")
                .where("commentcount > ?", "0")
                .order("publishdate desc")
                .offset(2)
                .limit(10)
                .find(News.class);
        return newsList;
    }

    /**
     * 查询单条数据
     * @return
     */
    private News findOneNew() {
        /**
         * 查询指定id数据
         */
        News news = DataSupport.find(News.class,1);
        /**
         * 查询第一条数据
         */
        News firstNews = DataSupport.findFirst(News.class);
        /**
         * 查询最后一条数据
         */
        News lastNews = DataSupport.findLast(News.class);
        return lastNews;

    }



    private void addNew() {

        /**
         * 保存单条数据
         */
        //addOneNew();

        /**
         * 保存多条数据
         */
        //addMultipleNews();

        addMultipleToOneNews();
    }

    private void addMultipleToOneNews() {
        Comment comment1 = new Comment();
        comment1.setContent("好评！");
        comment1.setPublishDate(new Date());
        comment1.save();                                    //保存了第一条评论
        Comment comment2 = new Comment();
        comment2.setContent("赞一个");
        comment2.setPublishDate(new Date());
        comment2.save();
        News news = new News();
        news.getCommentList().add(comment1);
        news.getCommentList().add(comment2);                //把前两条保存过的评论添加到News对象的评论集合中
        news.setTitle("新的新闻标题");
        news.setContent("新的新闻内容");
        news.setPublishDate(new Date());
        news.setCommentCount(news.getCommentList().size());
        news.save();
    }

    private void addMultipleNews() {
        News news1 = new News();
        news1.setTitle("标题1");
        news1.setContent("内容1");
        news1.setPublishDate(new Date());
        news1.setCommentCount(3);
        News news2 = new News();
        news2.setTitle("标题2");
        news2.setContent("内容2");
        news2.setPublishDate(new Date());
        news2.setCommentCount(3);
        News news3 = new News();
        news3.setTitle("标题3");
        news3.setContent("内容3");
        news3.setPublishDate(new Date());
        news3.setCommentCount(3);
        List<News> list = new ArrayList<>();
        list.add(news1);
        list.add(news2);
        list.add(news3);
        //同步保存
        //DataSupport.saveAll(list);
        //异步保存
        DataSupport.saveAllAsync(list).listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                if (success) {
                    Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 保存单条数据
     */
    private void addOneNew() {

        News news = new News();
        news.setTitle("标题");
        news.setContent("内容");
        news.setPublishDate(new Date());
        news.setCommentCount(3);
        /*if (news.save()) {
            Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show();
        }*/

        /**
         * 保存失败抛异常
         */
        /*try{
            news.saveThrows();
        }catch (DataSupportException e){
            e.printStackTrace();
        }*/

        /**
         * 异步保存
         */
        news.saveAsync().listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                if (success) {
                    Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void updateNews(){
        updateNewsById();
    }


    private void updateNewsById() {
        /**
         * 使用DataSupport类中的update()静态方法
         */
        /*ContentValues values = new ContentValues();
        values.put("title", "111111");
        values.put("commentcount","111");
        //DataSupport.update(News.class, values, 3);
        DataSupport.updateAsync(News.class, values, 3).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                Toast.makeText(MainActivity.this, rowsAffected+"", Toast.LENGTH_SHORT).show();
            }
        });*/

        /**
         * 使用继承了DataSupport的实体类中的方法
         */
        /*News updateNews = new News(); //News必须继承DataSupport
        updateNews.setTitle("今日iPhone6发布");
        updateNews.update(4);*/

        /**
         * 修改指定条件的数据：使用DataSupport类中的updateAll()静态方法
         */
        /*ContentValues values = new ContentValues();
        values.put("title", "今日iPhone6 Plus发布");
        DataSupport.updateAllAsync(News.class, values, "id > ? and id < ?", "4", "7").listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                Toast.makeText(MainActivity.this, rowsAffected+"", Toast.LENGTH_SHORT).show();
            }
        });*/

        /**
         * 修改全表数据
         */
        /*ContentValues values = new ContentValues();
        values.put("title", "今日iPhone6 Plus发布");
        DataSupport.updateAllAsync(News.class, values).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                Toast.makeText(MainActivity.this, rowsAffected+"", Toast.LENGTH_SHORT).show();
            }
        });*/

        /**
         * 将对应字段修改为默认值
         */
        News updateNews = new News();  //News必须继承DataSupport
        updateNews.setToDefault("commentCount");
        updateNews.updateAllAsync().listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                Toast.makeText(MainActivity.this, rowsAffected+"", Toast.LENGTH_SHORT).show();
            }
        });
        
        
    }
}
