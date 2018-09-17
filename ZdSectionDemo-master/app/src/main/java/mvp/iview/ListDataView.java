package mvp.iview;

import entity.ListEntity;

/**
 * Created by niudong on 2017/5/12.
 * Tel： 18811793194
 * VersionChange:2.0  XXX
 * 作用： ListDataView --Psenter构造添加引用， 供Activity 去实现 ，回调请求数据完成 返回的结果在该方法中处理
 */


public interface ListDataView {
    /**
     * 执行网络请求回调返回的结果，做下一步操作
     *
     * @param listEntity 数据实体
     * @param flag       成功或者失败
     * @param msg        携带的消息
     */
    void onloadData(ListEntity listEntity, boolean flag, String msg);


}
