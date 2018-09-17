package testdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import view.niudong.com.demo.MyApplication;
import contants.Constant;


/**
 * Created by niudong on 2017/7/17.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：
 */


public class MySqLiteHelper extends SQLiteOpenHelper {

    private static MySqLiteHelper mInstance;

    public static final String CREATE_NiuDong = "create table NiuDong(" + "id integer primary key autoincrement,"
            + "name text,"
            + "price real,"
            + "age varchar)";

    /**
     * 双重锁定
     */
    public  static MySqLiteHelper getInstance(Context context) {
        /**
         * 只有在为空的时候，会有同步锁的影响
         */
        if (mInstance == null) {
            synchronized (MySqLiteHelper .class) {
                if (mInstance == null) {
                    mInstance = new MySqLiteHelper(context);
                }
            }
        }
        return mInstance;
    };


    public MySqLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqLiteHelper(Context mContext) {
        super(mContext, Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NiuDong);
        Toast.makeText(MyApplication.mContext, "创建DB成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists NiuDong");
        onCreate(sqLiteDatabase);
    }
}
