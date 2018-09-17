package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangxc on 2016/9/22.
 *
 * @version 1.0.0
 *          主要是线程工厂管理类
 *          主要包含以下几个线程池管理
 *          1、交易线程使用系统自带的 AsyTask,所有交易协议独享该线程
 *          2、行情socket，独享一个线程
 *          3、咨询后台行情 、http行情独享一个线程
 *          4、客户端后台更新的数据，独享一个线程
 *          5、其他的网络下载独享一个线程
 */
public class ThreadFactory {
    /**
     * 其他执行需要线程池
     */
    public static ExecutorService otherTasks;


    /**
     * 分配线程数
     */
    public static void allotThreadPool() {
        //交易线程2个、行情sokcet1个、咨询后台行情、其他网络下载、客户端后台下载1个、其他线程使用
        otherTasks = Executors.newFixedThreadPool(3);
    }

    /**
     * 关闭线程池
     */
    public static void closeThreadPool() {
        //关闭其他需要执行的线程池
        if (otherTasks != null) {
            otherTasks.shutdown();
        }
    }
        /**
         * 执行非网络的异步线程
         * 应用中所有需要thread来实现的线程应该需都走该线程池处理，来统一管理线程
         * @param task 线程执行的方法
         */

    public static void executeOtherTasksThread(final Runnable task) {
        if (otherTasks != null) {
            otherTasks.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    } catch (Exception e) {
                        Logger.printExceptionStackTrace(e);
                    }
                }
            });
        }

    }
}
