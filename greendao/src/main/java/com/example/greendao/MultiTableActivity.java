package com.example.greendao;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greendao.entity.Book;
import com.example.greendao.entity.Person;
import com.example.greendao.entity.PersonDao;
import com.example.greendao.entity.Picture;
import com.example.greendao.entity.PictureDao;
import com.example.greendao.entity.StudentDao;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MultiTableActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MultiTableActivity.class.getSimpleName();
    private Button btnToOneAdd;
    private Button btnToOneDelete;
    private TextView tvResult;
    List<Picture> pictures;
    private Button testAddToMany;
    Button btnUpdateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_table);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        btnToOneAdd = findViewById(R.id.btn_to_one_add);
        btnToOneAdd.setOnClickListener(this);
        btnToOneDelete = findViewById(R.id.btn_to_one_delete);
        btnToOneDelete.setOnClickListener(this);
        tvResult = findViewById(R.id.tv_result);
        testAddToMany = findViewById(R.id.testAddToMany);
        testAddToMany.setOnClickListener(this);
        btnUpdateData = findViewById(R.id.btn_update_data);
        btnUpdateData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.btn_to_one_add:
                testAddToOne();
                break;
            case R.id.btn_to_one_delete:
                //testToOneDelete1();
                testToOneDelete2();
                break;
            case R.id.testAddToMany:
                //testAddToMany();
                break;
            case R.id.btn_update_data:
                updateData("pic_2222222222222222222222222");
                break;
            default:
                break;
        }
    }

    private void testToOneDelete2() {
        Observable<List<Person>> observable = App.getDaoSession().getPersonDao().queryBuilder().where(PersonDao.Properties.Address.eq("beijing")).rx().list().observeOn(AndroidSchedulers.mainThread());
        observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {

            }
        }).subscribe(new Action1<List<Person>>() {
            @Override
            public void call(final List<Person> personList) {
                if (personList.size() == 0) {
                    Toast.makeText(MultiTableActivity.this, "数据不存在", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(MultiTableActivity.this, ""+pictures.size(), Toast.LENGTH_SHORT).show();
                    Observable<Void> observable = App.getDaoSession().getPersonDao().rx().deleteInTx(personList);
                    observable.observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Void>() {
                                @Override
                                public void call(Void v) {
                                    Toast.makeText(MultiTableActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < personList.size(); i++) {
                                        testToOneDelete(personList.get(i).getId());
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Toast.makeText(MultiTableActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                loadAllPerson();

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "call: query failed");
                Toast.makeText(MultiTableActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                loadAllPerson();
            }
        });
    }

    private void testAddToOne() {


        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Picture picture = new Picture();
                picture.setId(null);
                picture.setPictureName("pic_1");
                App.getDaoSession().getPictureDao().insertOrReplace(picture);
                //App.getDaoSession().getBookDao().insertOrReplaceInTx(list);
                Person person = new Person();
                person.setId(null);
                person.setAddress("beijing");
                person.setPid(picture.getId());
                person.setPicture(picture);
                person.setTag(1L);
                List<Book> listBook = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    Book book = new Book();
                    book.setId(null);
                    book.setBid(person.getTag());
                    book.setName("book" + (i + 1));
                    App.getDaoSession().insert(book);
                    listBook.add(book);
                }
                App.getDaoSession().insertOrReplace(person);
                person.getBooks().addAll(listBook);
                subscriber.onNext("成功");

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {

            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(MultiTableActivity.this, s, Toast.LENGTH_SHORT).show();
                loadAllPerson();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(MultiTableActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                loadAllPerson();
            }
        });

    }

    private void loadAllPerson() {
        Observable<List<Person>> observable = App.getDaoSession().getPersonDao().rx().loadAll().observeOn(AndroidSchedulers.mainThread());
        observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {

            }
        }).subscribe(new Action1<List<Person>>() {
            @Override
            public void call(List<Person> personList) {
                String res = "";
                for (int i = 0; i < personList.size(); i++) {
                    res = res + personList.get(i).toString() + "\n";
                }
                tvResult.setText(res);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(MultiTableActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 删除Picture对象
     *
     * @param id
     */
    private void testToOneDelete(Long id) {
        Observable<List<Picture>> observable = App.getDaoSession().getPictureDao().queryBuilder().where(PictureDao.Properties.Id.eq(id)).rx().list().observeOn(AndroidSchedulers.mainThread());
        observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {

            }
        }).subscribe(new Action1<List<Picture>>() {
            @Override
            public void call(List<Picture> pictures) {
                if (pictures.size() == 0) {
                    Toast.makeText(MultiTableActivity.this, "数据不存在", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(MultiTableActivity.this, ""+pictures.size(), Toast.LENGTH_SHORT).show();
                    Observable<Void> observable = App.getDaoSession().getPictureDao().rx().deleteInTx(pictures);
                    observable.observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Void>() {
                                @Override
                                public void call(Void v) {
                                    Toast.makeText(MultiTableActivity.this, "删除数据成功！", Toast.LENGTH_SHORT).show();
                                    loadAllPerson();
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Toast.makeText(MultiTableActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    loadAllPerson();
                                }
                            });
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(MultiTableActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                loadAllPerson();
            }
        });

    }

    /*private void testAddToMany() {
        final List<Book> list = new ArrayList<>();
        list.add(new Book(null, "book1"));
        list.add(new Book(null, "book2"));
        list.add(new Book(null, "book3"));
        list.add(new Book(null, "book4"));
        list.add(new Book(null, "book5"));
        Observable<Person> observable = App.getDaoSession().getPersonDao()
                .queryBuilder()
                .where(PersonDao.Properties.Address.eq("beijing"))
                .rx()
                .unique()
                .observeOn(AndroidSchedulers.mainThread());
        observable.doOnSubscribe(new Action0() {
            @Override
            public void call() {

            }
        }).subscribe(new Action1<Person>() {
            @Override
            public void call(Person person) {
                person.getBooks().clear();
                person.getBooks().addAll(list);
                App.getDaoSession().getPersonDao().rx().update(person).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                }).subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person person) {
                        Toast.makeText(MultiTableActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MultiTableActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(MultiTableActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Observable<Person> observable2 = App.getDaoSession().getPersonDao().queryBuilder().where(PersonDao.Properties.Address.eq("beijing")).rx().unique();

    }*/

    void updateData(final String s) {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Picture picture = App.getDaoSession().getPictureDao().queryBuilder().where(PictureDao.Properties.PictureName.eq("pic_1")).unique();
                picture.setPictureName(s);
                App.getDaoSession().getPictureDao().update(picture);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                 loadAllPerson();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                loadAllPerson();
                Toast.makeText(MultiTableActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
