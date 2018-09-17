package utils;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 5.4 消息总线
 * Created by niudong on 2017/9/6.
 */
public class Bus {
    private static Bus sInstance;

    private Map<String, List<ISubscriber>> subscriberMap = new HashMap<>();
    private Handler handler = new Handler(Looper.getMainLooper());

    public static Bus getInstance() {
        if (null == sInstance) {
            synchronized (Bus.class) {
                if (null == sInstance) {
                    sInstance = new Bus();
                }
            }
        }

        return sInstance;
    }

    private Bus() {
    }

    public void register(String tag, ISubscriber subscriber) {
        if (null == subscriber) return;

        List<ISubscriber> list = subscriberMap.get(tag);
        if (null == list) {
            list = new ArrayList<>();
            subscriberMap.put(tag, list);
        }

        if (!list.contains(subscriber)) {
            list.add(subscriber);
        }
    }

    public void unregister(String tag, ISubscriber subscriber) {
        if (null == subscriber) return;

        List<ISubscriber> list = subscriberMap.get(tag);
        if (null != list) {
            list.remove(subscriber);
        }
    }

    /**
     * 投递消息
     */
    public void post(String tag, Object obj) {
        Task task = new Task(tag, obj);
        handler.post(task);
    }


    private class Task implements Runnable {
        private final String tag;
        private final Object obj;

        private Task(String tag, Object obj) {
            this.tag = tag;
            this.obj = obj;
        }

        @Override
        public void run() {
            List<ISubscriber> list = subscriberMap.get(tag);
            if (null != list) {
                for (ISubscriber subscriber : list) {
                    subscriber.onSubscribe(obj);
                }
            }
        }
    }


    /**
     * 订阅接口
     */
    public interface ISubscriber {
        void onSubscribe(Object obj);
    }
}
