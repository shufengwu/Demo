package com.example.wushufeng.demo.entity;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by wushufeng on 2018/4/27.
 */

public class Comment extends DataSupport {
    private int id;
    private String content;
    private Date publishDate;
    private News news;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", publishDate=" + publishDate +
                ", news=" + news +
                '}';
    }
}
