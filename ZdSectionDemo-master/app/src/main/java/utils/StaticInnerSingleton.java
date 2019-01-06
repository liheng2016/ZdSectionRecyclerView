package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 名称：
 * Created by niudong on 2018/7/4.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class StaticInnerSingleton {

    private Map<Integer,Boolean> clickTab=new HashMap<>();
    private StaticInnerSingleton() {
        clickTab.put(0,false);
    }


    public static StaticInnerSingleton getInstance() {
        return InnerHolder.mInstance;
    }

    public boolean getclickTab() {
        return clickTab.get(0);
    }

    //内部类  创建实例 final 无法修改  线程安全的
    private static class InnerHolder {
        private static final StaticInnerSingleton mInstance = new StaticInnerSingleton();
    }

    public  void  clickTab(Boolean  isCLick){

        clickTab.put(0,isCLick);
    }

    public void  cleanTab(){
        clickTab.put(0,false);
    }
}
