// IMyAidl.aidl
package view.niudong.com.demo.bean;
import view.niudong.com.demo.bean.TestAidlBean;

// Declare any non-default types here with import statements

interface IMyAidl {
    void addPerson(in TestAidlBean person);

    List<TestAidlBean> getPersonList();
}
