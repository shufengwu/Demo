package com.example.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

@Entity
public class Person {
    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String address;

    private Long pid;

    @ToOne(joinProperty = "pid")
    private Picture picture;

    @Unique
    private Long tag;

    @ToMany(joinProperties = {
            @JoinProperty(name = "tag", referencedName = "bid")
    })
    private List<Book> books;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 778611619)
    private transient PersonDao myDao;


    @Generated(hash = 799881381)
    public Person(Long id, String address, Long pid, Long tag) {
        this.id = id;
        this.address = address;
        this.pid = pid;
        this.tag = tag;
    }


    @Generated(hash = 1024547259)
    public Person() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getAddress() {
        return this.address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public Long getPid() {
        return this.pid;
    }


    public void setPid(Long pid) {
        this.pid = pid;
    }


    @Generated(hash = 1986840853)
    private transient Long picture__resolvedKey;


    /** To-one relationship, resolved on first access. */
    @Generated(hash = 581927261)
    public Picture getPicture() {
        Long __key = this.pid;
        if (picture__resolvedKey == null || !picture__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PictureDao targetDao = daoSession.getPictureDao();
            Picture pictureNew = targetDao.load(__key);
            synchronized (this) {
                picture = pictureNew;
                picture__resolvedKey = __key;
            }
        }
        return picture;
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1105966646)
    public void setPicture(Picture picture) {
        synchronized (this) {
            this.picture = picture;
            pid = picture == null ? null : picture.getId();
            picture__resolvedKey = pid;
        }
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 268939490)
    public List<Book> getBooks() {
        if (books == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookDao targetDao = daoSession.getBookDao();
            List<Book> booksNew = targetDao._queryPerson_Books(tag);
            synchronized (this) {
                if (books == null) {
                    books = booksNew;
                }
            }
        }
        return books;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 353255226)
    public synchronized void resetBooks() {
        books = null;
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    public Long getTag() {
        return this.tag;
    }


    public void setTag(Long tag) {
        this.tag = tag;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", pid=" + pid +
                ", picture=" + picture +
                ", tag=" + tag +
                ", books=" + books +
                '}';
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2056799268)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPersonDao() : null;
    }
}
