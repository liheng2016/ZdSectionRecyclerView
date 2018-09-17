package testdb;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import base.BaseLazyFragment;
import entity.DbTestBean;
import entity.DetailModel;
import utils.DBApiManager;
import utils.Logger;
import utils.ThreadFactory;
import utils.ToastUtils;
import view.niudong.com.demo.MyApplication;
import view.niudong.com.demo.R;

/**
 * 名称：
 * Created by niudong on 2017/12/16.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 * setModelapi db
 * 12-16 22:13:04.842 4007-4007/view.niudong.com.demo D/LTHJ: setModelsql db
 * 12-16 22:13:04.845 4007-4007/view.niudong.com.demo D/LTHJ: initDataapi db
 * 12-16 22:13:04.848 4007-4007/view.niudong.com.demo D/LTHJ: initDatasql db
 */
public class DbSqlFragment extends BaseLazyFragment implements View.OnClickListener {
    private Button bt_create, bt_find, bt_add, bt_update;

    private DetailModel model;
    private TextView tv_db_name;
    private int currentType;
    private Activity mActivity;
    private List<DbTestBean> testData;
    private TextView bt_copy;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;

    }

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_db_sql, null);
        bt_create = (Button) view.findViewById(R.id.bt_create);
        bt_add = (Button) view.findViewById(R.id.bt_add);
        bt_find = (Button) view.findViewById(R.id.bt_find);
        bt_update = (Button) view.findViewById(R.id.bt_update);
        tv_db_name = (TextView) view.findViewById(R.id.tv_db_name);
        bt_copy = (TextView) view.findViewById(R.id.bt_copy);

        bt_create.setOnClickListener(this);
        bt_add.setOnClickListener(this);
        bt_find.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_copy.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData() {
        Logger.d("initData" + model.titleData);
    }

    public void setModel(DetailModel detailModel) {
        this.model = detailModel;

        Logger.d("setModel" + model.titleData);
    }

    /**
     * 当前页面更新  只执行一次
     */
    public void invalidate(DetailModel model) {
        //TODO 设置数据
        Logger.d("invalidate" + model.titleData);

    }

    @Override
    protected void lazyData() {
        super.lazyData();
        Logger.d("lazyData" + model.titleData);
        tv_db_name.setText(model.titleData);
        currentType = model.currentType;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_create:
                DBApiManager.getInstence(mActivity);
                break;
            case R.id.bt_add:
                if (currentType == 0) {
                    DBApiManager.getInstence(mActivity).inSertData();
                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LocalHelperManager localHelperManager = LocalHelperManager.getIntance(mActivity);
                            SQLiteDatabase sqLiteDatabase = localHelperManager.openDb();
                            localHelperManager.insertAll(getTestData());
                            localHelperManager.CloseDb(sqLiteDatabase);

                        }
                    }).start();
                }
                break;
            case R.id.bt_update:
                DBApiManager.getInstence(mActivity).update();
                break;
            case R.id.bt_copy:
                save();
                break;
            case R.id.bt_find:
                if (currentType == 0) {
                    //查询数据库
//                    List<DbTestBean> dbTestBeen = DBApiManager.getInstence(mActivity).query();
//                    for (DbTestBean dbTestBean : dbTestBeen) {
//                        int age = dbTestBean.getAge();
//                        String name = dbTestBean.getName();
//                        int price = dbTestBean.getPrice();
//                        ToastUtils.showToast(getContext(), name + age + price);
//                    }
                    try {
                        String allTotalProfit = DBApiManager.getInstence(mActivity).getAllTotalProfit();
                        ToastUtils.showToast(mActivity, allTotalProfit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LocalHelperManager localHelperManager = LocalHelperManager.getIntance(mActivity);
                            SQLiteDatabase sqLiteDatabase = localHelperManager.openDb();
                            final List<DbTestBean> query = LocalHelperManager.getIntance(mActivity).query();
                            localHelperManager.CloseDb(sqLiteDatabase);
                            MyApplication.mContext.getHandler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToast(mActivity, query.toString());
                                }
                            }, 100);


                        }
                    }).start();

                }

                break;
            default:
                break;
        }
    }

    public List<DbTestBean> getTestData() {
        List<DbTestBean> testData = new ArrayList<>();
        testData.add(new DbTestBean("niudong", 24, 99999));
        testData.add(new DbTestBean("zhidong", 22, 66666));
        return testData;
    }

    /**
     * 保存数据库
     */
    public void save() {
        String dbpath = "/data/data/view.niudong.com.demo/databases/"
                + "NiuDong";
        boolean success = copyFile(dbpath, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + "NiuDong");
        ToastUtils.showToast(getActivity(), "是否Copy 成功？..." + success);
    }

    /**
     * 复制文件
     *
     * @param source
     * @param dest
     * @return
     */
    public static boolean copyFile(String source, String dest) {
        try {
            File oldfile = new File(source);
            File newfile = new File(dest);
            if (!newfile.exists()) {
                newfile.createNewFile();
            }
            if (oldfile.exists()) { // 文件存在时
                InputStream in = new FileInputStream(oldfile);

                OutputStream out = new FileOutputStream(newfile);

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0)
                    out.write(buf, 0, len);

                in.close();
                out.close();
            }
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;

    }

}
