package tabfragment;

/**
 * 主页面(框架页面)与5个子页面的交互
 * Created by niudong on 2017/11/30.
 */
public interface IFramePage {
    /** 各顶级页面的导航
     *
     * @param index 各页面的序号, 从0开始
     */
    void navigate(int index);

    /** 设置主页面Tab的可见性 */
    void setMainTabVisible(boolean visible);

}
