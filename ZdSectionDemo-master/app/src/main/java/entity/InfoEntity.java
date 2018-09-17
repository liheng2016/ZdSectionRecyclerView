package entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by niudong on 2017/6/15.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：
 */


public class InfoEntity implements Parcelable {

    public String name;
    public int age;

    public InfoEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public InfoEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
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

    protected InfoEntity(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Parcelable.Creator<InfoEntity> CREATOR = new Parcelable.Creator<InfoEntity>() {
        @Override
        public InfoEntity createFromParcel(Parcel source) {
            return new InfoEntity(source);
        }

        @Override
        public InfoEntity[] newArray(int size) {
            return new InfoEntity[size];
        }
    };
}
