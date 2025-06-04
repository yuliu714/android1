package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {//这个类继承了SQLite轻型数据库

    private  static final String DATABASE_NAME="books.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_BOOKS="books";

    public DatabaseHelper(Context context){//调用父类方法，创建数据库。要求配套onCreate()、onUpgrade()方法
        super(context,DATABASE_NAME,null,DATABASE_VERSION);//Context是应用的环境信息，代表很多
    }

    @Override
    public void onCreate(SQLiteDatabase db){//创表
        db.execSQL("create table "+TABLE_BOOKS+"(id INTEGER primary key autoincrement,title text,author text,publisher text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//
        System.out.println("不会更新数据表，要更新也不是这种方式");
    }

    //增删改查
    public boolean addBook(String title,String author,String publisher){//
        SQLiteDatabase db = this.getWritableDatabase();//反正就是调用数据库
        ContentValues values =new ContentValues();//ContentValues，键值对
        values.put("title",title);
        values.put("author",author);
        values.put("publisher",publisher);
        //反正第二个参数nullColumnHack我也看不懂，剩下就是insert语句
        long result = db.insert(TABLE_BOOKS,null,values);
        return result!=-1;
    }

    public List<String> getAllBooks(){
        List<String>books = new ArrayList<>();//List<String>对象
        SQLiteDatabase db = this.getReadableDatabase();//又是获取数据库对象
        //Cursor迭代器对象，db.rawQuery()执行sql语句。
        Cursor cursor =db.rawQuery("select * from "+TABLE_BOOKS,null);
        if(cursor.moveToFirst()){//如果cursor的第一行有数据
            do {
                books.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return books;//最后returnList<String>的book对象
    }
}
