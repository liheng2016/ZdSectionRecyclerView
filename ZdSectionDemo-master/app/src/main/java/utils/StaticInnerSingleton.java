package utils;

/**
 * 名称：
 * Created by niudong on 2018/7/4.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class StaticInnerSingleton {
    private StaticInnerSingleton() {
    }


    public static StaticInnerSingleton getInstance() {
        return InnerHolder.mInstance;
    }

    //内部类  创建实例 final 无法修改  线程安全的
    private static class InnerHolder {
        private static final StaticInnerSingleton mInstance = new StaticInnerSingleton();
    }
}
