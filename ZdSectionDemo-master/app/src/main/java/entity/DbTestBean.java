package entity;

/**
 * Created by niudong on 2017/7/18.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：
 */


public class DbTestBean {

public     String name;
    public int age;
    public int price;
    public String mID;

    public DbTestBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public DbTestBean(String name, int age, int price) {
        this.name = name;
        this.age = age;
        this.price = price;
    }
}
