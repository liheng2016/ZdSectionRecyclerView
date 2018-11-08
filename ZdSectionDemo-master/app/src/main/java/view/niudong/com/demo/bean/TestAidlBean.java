package view.niudong.com.demo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by niudong on 2018/9/4.yyyyy
 */

public class TestAidlBean implements Parcelable{
    public String name;
    public int age;

    protected TestAidlBean(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }
    public TestAidlBean(){}

    public static final Creator<TestAidlBean> CREATOR = new Creator<TestAidlBean>() {
        @Override
        public TestAidlBean createFromParcel(Parcel in) {
            return new TestAidlBean(in);
        }

        @Override
        public TestAidlBean[] newArray(int size) {
            return new TestAidlBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
