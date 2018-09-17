package mvp.presenter;


/**
 * Created by niudong on 2017/5/12.
 * Tel： 18811793194
 * VersionChange:2.0  XXX
 * 作用： ListDataPresenter --请求网络数据需要传递的参数
 */


public interface ListDataPresenter {

    /**
     * * 请求网络数据需要传递的参数
     *
     * @param type        功能表示     必传项:showQuestion
     * @param flag        列表标识     0为推荐列表，1为最新列表
     * @param protocolVer 协议版本号   必传项，此次版本号为2.0
     * @param userMobile  用户手机号   必传项
     * @param pageNo      页数        必传项
     * @param pageSize    每页数量     传项：2.0版本传入150
     */
    void reqNetData(String type, int flag, String protocolVer, String userMobile, int pageNo, int pageSize);


}
