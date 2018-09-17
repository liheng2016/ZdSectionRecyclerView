package view.niudong.com.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import view.niudong.com.demo.bean.IMyAidl;
import view.niudong.com.demo.bean.TestAidlBean;

/**
 * Created by niudong on 2018/9/5.
 */

public class SeverAidlSerVice extends Service {

    List<TestAidlBean> dataList;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        dataList=new ArrayList<>();
        return mIBinder;
    }

    private IBinder  mIBinder= new IMyAidl.Stub() {
        @Override
        public void addPerson(TestAidlBean person) throws RemoteException {
            dataList.add(person);
        }

        @Override
        public List<TestAidlBean> getPersonList() throws RemoteException {
            return dataList;
        }
    };
}
