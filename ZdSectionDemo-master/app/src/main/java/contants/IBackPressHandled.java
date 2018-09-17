package contants;

/**
 * 5.3 物理返回键处理
 * Created by niudong on 2017/1/26.
 */
public interface IBackPressHandled {
    /**
     * 添加返回键处理
     */
    void addOnBackPressedListener(OnBackPressedListener listener);

    /**
     * 删除返回键处理
     */
    void removeOnBackPressedListener(OnBackPressedListener listener);
}
