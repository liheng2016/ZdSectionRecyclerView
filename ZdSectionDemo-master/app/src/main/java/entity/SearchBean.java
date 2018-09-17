package entity;

import contants.TypeFactory;
import contants.Visitable;

/**
 * 名称：
 * Created by niudong on 2017/12/16.
 * Tel：18811793194
 * VersionChange：港股通5.5.2
 */
public class SearchBean implements Visitable{
    public SearchBean(String message) {
        this.message = message;
    }

    public String message;
    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
