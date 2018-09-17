package mvp.presenter;


import net.MySubscriber;
import net.RetrofitManager;

import entity.ListEntity;
import mvp.iview.ListDataView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by niudong on 2017/5/12.
 * Tel： 18811793194
 * VersionChange:2.0  XXX
 * 作用：Presenter 的实现类   请求网络数据   构造中传入ListDataView  方便回调结果返回给Activity View
 */
public class ReqListDataPresenterImpl implements ListDataPresenter {

    public ListDataView onLoadListData;

    /**
     * 构造中传入ListDataView  方便将请求服务器返回后的数据 ---回调给Activity View
     *
     * @param onLoadListData
     */
    public ReqListDataPresenterImpl(ListDataView onLoadListData) {
        this.onLoadListData = onLoadListData;
    }

    /**
     * * 通过网络 请求服务器数据需要传递的参数
     *
     * @param type        功能表示     必传项:showQuestion
     * @param flag        列表标识     0为推荐列表，1为最新列表
     * @param protocolVer 协议版本号   必传项，此次版本号为2.0
     * @param userMobile  用户手机号   必传项
     * @param pageNo      页数        必传项
     * @param pageSize    每页数量     传项：2.0版本传入150
     */
    @Override
    public void reqNetData(String type, final int flag, String protocolVer, String userMobile, int pageNo, int pageSize) {
        //执行网络请求
        RetrofitManager.getInstance().getService().listData(type, flag, protocolVer, userMobile, pageNo, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<ListEntity>() {
                    //TODO 该网络请求返回的结果均在 主线程，可直接刷新UI

//                    *
//                     *
//                     * @param listEntities  成功会调用改方法

                    @Override
                    public void onNext(ListEntity listEntities) {

                        //判空处理和比较返回码是否为0000
                        if (listEntities.getResponseCode().equals("0000") && listEntities != null) {
//                             * 将成功结果数据传递给Activity
//                             *@param   listEntities     成功后的数据
//                             *@param   flag             传递成功
//                             *@param   msg              携带消息，也可传空

                            onLoadListData.onloadData(listEntities, true, null);
                        } else {

//                            *
//                             *
//                             *将失败结果数据传递给Activity
//                                    * @param listEntities 传递null 或者 ""
//                                    * @param flag 传递失败 false
//                                    * @param msg 传递失败的返回码

                            onLoadListData.onloadData(null, false, listEntities.getResponseDes());
                        }
                    }

//                    *
//                            *
//                            *
//                    @param
//                    e 失败会调用改方法

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

//                         *
//                         *将异常结果原因传递给Activity
//                                * @param listEntities 传递null 或者 ""
//                                * @param flag 传递失败 false
//                                * @param msg 传递失败异常消息

                        onLoadListData.onloadData(null, false, e.getMessage());

                    }
                });
    }
}
