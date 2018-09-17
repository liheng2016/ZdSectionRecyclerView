package entity;

/**
 * Created by niudong on 2017/8/5.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */


public class Boy {
    public Boy(String name, int score) {
        this.name = name;
        this.score = score;
    }
    public Boy() {
    }

    public String name;//姓名
    public int score;// 分数
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
