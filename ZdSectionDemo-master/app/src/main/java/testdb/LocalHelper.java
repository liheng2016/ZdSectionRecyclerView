package testdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库DBHelper类
 *
 * @author niudong
 */
public class LocalHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "zhidong.db";
    public final static String TABLE = "zhidong";
    public final static int DATABASE_VERSION = 1;
    private static LocalHelper mInstance;

    private LocalHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 双重锁定
     */
    public static LocalHelper getInstance(Context context) {
        /**
         * 只有在为空的时候，会有同步锁的影响
         */
        if (mInstance == null) {
            synchronized (LocalHelper.class) {
                if (mInstance == null) {
                    mInstance = new LocalHelper(context);
                }
            }
        }
        return mInstance;
    }

    ;

    //测试
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //autoincrement
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name," +
                    "price," +
                    "age)");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 删除原来的数据表
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        // 重新创建
        onCreate(db);
    }

}
