package testdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import entity.DbTestBean;

/**
 * TODO https://www.cnblogs.com/zhoushenglei/p/7153581.html  7月17
 * 数据库操作类   在方法中添加同步锁，多线程并发的时候的执行顺序为，依次执行，避免数据库锁定的异常。
 * 数据本地化帮助类
 *
 *                     db.execSQL("insert into Book(author,price,pages,name) values(?,?,?,?)",new String[]{"周生磊","22","33","android"});
 db.execSQL("delete from book where name = ?",new String[]{"android"});
 db.execSQL("update Book set price = ? where name =?",new String[]{"4533","android"});

 */
public class LocalHelperManager {
    private LocalHelper helper;
    private static SQLiteDatabase db;
    /**
     * 记数器 应该设置静态的类变量
     *
     * @param context
     */
    private static int mCount;
    //同一个数据库连接
    private static LocalHelperManager mMnanagerInstance;

    private LocalHelperManager(Context context) {
        helper = LocalHelper.getInstance(context);
    }

    //单例
    public static synchronized LocalHelperManager getIntance(Context context) {
        if (mMnanagerInstance == null) {
            return new LocalHelperManager(context);
        }
        return mMnanagerInstance;
    }

    public synchronized SQLiteDatabase openDb() {
        if (mCount == 0) {
            db = helper.getWritableDatabase();
        }
        mCount++;
        return db;
    }

    public synchronized void CloseDb(SQLiteDatabase database) {
        mCount--;
        if (mCount == 0) {
            database.close();
        }
    }

    // 插入
    public void insert(DbTestBean object) {
        try {
            ContentValues values = new ContentValues();
            values.put("name", object.getName());
            values.put("price", object.getPrice());
            values.put("age", object.getAge());
            db.insert(LocalHelper.TABLE, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     */
    public void insertAll(List<DbTestBean> objects) {
        try {
            if (objects == null) {
                return;
            }
            db.beginTransaction();
//			for (DataObject student : objects) {
//				insert(student);
//			}
            for (int i = 0; i < objects.size(); i++) {
                insert(objects.get(i));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            db.endTransaction();
            Log.i("com.example.persistent", "插入成功");
        }
    }


    // 查找所有的Students
    public List<DbTestBean> query() {
        List<DbTestBean> students = new ArrayList<>();
        Cursor c = queryTheCursor();
        DbTestBean student = null;
        try {
            while (c.moveToNext()) {
                student = new DbTestBean();
                student.mID = c.getString(c.getColumnIndex("_id"));
                student.name = c.getString(c.getColumnIndex("name"));
                student.age = c.getInt(c.getColumnIndex("age"));
                student.price = c.getInt(c.getColumnIndex("price"));
                students.add(student);
                student = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    // 获取游标
    public Cursor queryTheCursor() {
        try {
            if (!db.isOpen()) {
                db = helper.getWritableDatabase();
            }
            Cursor c = db.rawQuery("SELECT * FROM " + LocalHelper.TABLE, null);
            return c;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
    /**
     * @param student
     *//*
        private void update(Student student) {
	    		try {
	    			db.execSQL("UPDATE " + DBHelper.TABLE
	    					+ " SET mName = ? WHERE _id = ?",
	    					new Object[] { student.mName,student.mID });
	    			Log.e("lthj.exchangestock2", "正在更新。。数据=+"+student.getmID()+student.getmName());
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    }

	    *//**
     * 更新所有
     * @param students
     *//*
          public void  updateAll(List<Student> students) {
		        try {
					if (students == null) {
					    return;
					}
					for (Student student : students) {
					    	update(student);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }

	    // 删除指定数据
	   public void delete(String id) {
	            try {
	                db.execSQL("DELETE FROM " + DBHelper.TABLE + " WHERE _id = ? ",
	                        new String[] { id });
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	    }

	    // 删除所有数据
	    public void delete() {
	            try {
	                db.execSQL("DELETE * FROM " + DBHelper.TABLE);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	    }



	    // 查找指定ID的Student
	    public Student query(String id) {
	        Student student = null;
	            Cursor c = queryTheCursor(id);
	            if(c==null)return null;
	            try {
	                while (c.moveToNext()) {
	                	student=new Student();
	                	student.mID=c.getString(c.getColumnIndex("_id"));
	                	student.mName=c.getString(c.getColumnIndex("mName"));
	                	student.mAge=c.getInt(c.getColumnIndex("mAge"));
	                    break;
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        return student;
	    }

	    // 获取游标
	    public Cursor queryTheCursor(String id) {
	    	try {
	    		Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE
	    				+ " WHERE _id = ?", new String[] { id });
	    		return c;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
	    }

	    // 获取游标
	    public Cursor queryTheCursor() {  
	    	try {
	    		  if (!db.isOpen()) {  
	    			  db = helper.getWritableDatabase();  
		            } 
	    		Cursor c = db.rawQuery("SELECT * FROM " + DBHelper.TABLE, null);  
	    		return c;  
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();return null;
			}
	    }  
	    */

}
