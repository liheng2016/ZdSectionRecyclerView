package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import contants.Constant;
import entity.DbTestBean;
import testdb.MySqLiteHelper;

import static contants.Constant.DB_NAME;

/**
 * Created by niudong on 2017/7/18.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：
 */


public class DBApiManager {

    private static final Object lock = new Object();
    private MySqLiteHelper mySqLiteHelper;
    private static DBApiManager mDBManager;

    public static DBApiManager getInstence(Context context) {
        if (null == mDBManager) {
            synchronized (lock) {
                if (mDBManager == null) {
                    mDBManager = new DBApiManager(context);
                }
            }
        }
        return mDBManager;
    }

    private DBApiManager(Context context) {
        mySqLiteHelper = MySqLiteHelper.getInstance(context);
    }


    public void inSertData() {
        SQLiteDatabase insert = mySqLiteHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "lidong");
        contentValues.put("price", 999);
        contentValues.put("age", "38.8632");
        insert.insert(DB_NAME, null, contentValues);

        ContentValues   contentValues1 = new ContentValues();
        contentValues1.put("name", "lidong");
        contentValues1.put("price", 999);
        contentValues1.put("age", "2532.889");
        insert.insert(DB_NAME, null, contentValues1);


        ContentValues   contentValues2 = new ContentValues();
        contentValues2.put("name", "lidong");
        contentValues2.put("price", 999);
        contentValues2.put("age", "2532.889");

        insert.insert(DB_NAME, null, contentValues2);
        insert.close();
    }


    public synchronized void update() {
        SQLiteDatabase db2 = mySqLiteHelper.getReadableDatabase();
        ContentValues contentValuesu = new ContentValues();
        contentValuesu.put("price", 100);

        db2.update(DB_NAME, contentValuesu, "name=?", new String[]{"lidong"});
        //记得释放数据库资源
        db2.close();
    }

    public synchronized List<DbTestBean> query() {
        SQLiteDatabase db3 = mySqLiteHelper.getReadableDatabase();
        String sql = "select * from " + DB_NAME;
       return selectDataCursor(selectDataBySql(db3, sql, null));
    }


    public List<DbTestBean> selectDataCursor(Cursor cursor) {
        SQLiteDatabase query = mySqLiteHelper.getReadableDatabase();
        cursor = query.query(DB_NAME, null, null, null, null, null, null);
        List<DbTestBean> stndNoisesByCid = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                stndNoisesByCid.add(new DbTestBean(name, age, price));
            } while (cursor.moveToNext());  //判断游标中的数据
        }
        //关闭数据库
        query.close();
        return stndNoisesByCid;
    }

    public synchronized Cursor selectDataBySql(SQLiteDatabase sqLiteDatabase, String sql, String[] selectArgs) {
        Cursor cursor = null;

        if (sqLiteDatabase != null) {
            sqLiteDatabase.rawQuery(sql, selectArgs);
        }
        return cursor;

    }


    public List<DbTestBean> selectDataStr() {
        SQLiteDatabase query = mySqLiteHelper.getReadableDatabase();
        Cursor cursor = query.query(DB_NAME, null, null, null, null, null, null);
        List<DbTestBean> stndNoisesByCid = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                stndNoisesByCid.add(new DbTestBean(name, age, price));
            } while (cursor.moveToNext());  //判断游标中的数据
        }
        //关闭数据库
//        dbHelper.close();
        return stndNoisesByCid;
    }

    /**
     * 获得港股 时间和总收益   强制子类必须try catch   告知外部我死了，近而结束计算流程....
     */
    public String getAllTotalProfit() throws Exception{
        String day = "";
        Cursor cursor = null;
        SQLiteDatabase db = null;
        try {
            db = mySqLiteHelper.getReadableDatabase();
            //获取更新日期
            String sql = "select sum(cast(age as decimal(35,0))) from "+Constant.DB_NAME;
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                day = cursor.getString(0);
            }
        } catch (Exception e) {
            Logger.printExceptionStackTrace(e);
            throw e;
        } finally {
            //关闭数据库
            db.close();
        }
        return day;
    }

}
