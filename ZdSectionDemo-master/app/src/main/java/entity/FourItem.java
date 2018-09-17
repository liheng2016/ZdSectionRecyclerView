package entity;

/**
 * Created by niudong on 2017/10/13.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */

public class FourItem extends BaseItem {
    public String imageUrl;
    public String title;
    public int viewType;

    public FourItem(String imageUrl, String title, int type) {
        super(type);
        this.imageUrl = imageUrl;
        this.title = title;
    }
}
