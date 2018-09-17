package view.niudong.com.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import fragment.OneFragment;
import fragment.ThreeFragment;
import fragment.TwoFragment;
import fragment.ZhiDongFragment;

/**
 * replace   点击B  销毁在其上的所有Fragment，然后创建B frag
 * <p>
 * TODO 推荐add    show  hide  不走生命周期
 */
public class FragmentStackActivity extends FragmentActivity implements View.OnClickListener {

    private Button butOne;
    private Button butTwo;
    private Button butThree;
    private ZhiDongFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_ment);
        //
        butOne = (Button) findViewById(R.id.bt_one);
        butTwo = (Button) findViewById(R.id.bt_two);
        butThree = (Button) findViewById(R.id.bt_three);


        butThree.setOnClickListener(this);
        butTwo.setOnClickListener(this);
        butOne.setOnClickListener(this);
        //
        oneFragment = new ZhiDongFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();

        //开启事务动态添加Fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .add(R.id.fl, oneFragment, "oneFragment")
                .add(R.id.fl, twoFragment, "twoFragment")
                .add(R.id.fl, threeFragment, "threeFragment")
                .hide(twoFragment)
                .hide(threeFragment)
                .commit();

        addToBackStack(oneFragment);
    }

    /**
     * 添加回退栈
     *
     * @param fragment
     */
    private void addToBackStack(Fragment fragment) {
        if (fragmentList.contains(fragment)) {
            fragmentList.remove(fragment);//为了改变顺序
            fragmentList.add(fragment);
        } else {
            fragmentList.add(fragment);
        }
    }

    @Override
    public void onClick(View view) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(oneFragment).hide(twoFragment).hide(threeFragment);
        switch (view.getId()) {
            case R.id.bt_one:
                fragmentTransaction.show(oneFragment);
                addToBackStack(oneFragment);
                break;
            case R.id.bt_two:
                fragmentTransaction.show(twoFragment);
                addToBackStack(twoFragment);
                break;
            case R.id.bt_three:
                fragmentTransaction.show(threeFragment);
                addToBackStack(threeFragment);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();   自己处理返回键
        if (fragmentList.size() > 1) {
            fragmentList.remove(fragmentList.size() - 1);
            showFragment(fragmentList.get(fragmentList.size() - 1));
        } else {
            finish();
        }
    }

    private void showFragment(Fragment i) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .hide(oneFragment)
                .hide(twoFragment)
                .hide(threeFragment)
                .show(i).commit();

    }


}
