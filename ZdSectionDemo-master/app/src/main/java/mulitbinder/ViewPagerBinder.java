package mulitbinder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.MPagerAdapter;
import customview.PointView;
import entity.ViewPagerItem;
import me.drakeet.multitype.ItemViewBinder;
import view.niudong.com.demo.R;

/**
 * Created by niudong on 2017/10/13.
 * Tel：18811793194
 * VersionChange：5.4
 * 作用：
 */

public class ViewPagerBinder extends ItemViewBinder<ViewPagerItem, ViewPagerBinder.ViewHolder> {
    private Context mContext;
    private ArrayList<View> mPagerView;

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_one_item, parent, false);
        if (mContext == null) {
            mContext = root.getContext();
        }
        return new ViewHolder(root);
    }

    public ViewPagerBinder(ArrayList<View> mData) {
        this.mPagerView = mData;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ViewPagerItem twoItem) {
//        TwoItem mTwoItem = (TwoItem) twoItem;
        holder.tv_title.setText(twoItem.title);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewPager viewpager;
        public TextView tv_title;
        public PointView imagePointOne;
        public PointView imagePointTwo, image_point_three;

        ViewHolder(View itemView) {
            super(itemView);
            viewpager = (ViewPager) itemView.findViewById(R.id.viewpager);
            tv_title = (TextView) itemView.findViewById(R.id.tv_one);
            imagePointOne = (PointView) itemView.findViewById(R.id.image_point_one);
            imagePointTwo = (PointView) itemView.findViewById(R.id.image_point_two);
            image_point_three = (PointView) itemView.findViewById(R.id.image_point_three);
            MPagerAdapter mPagerAdapter = new MPagerAdapter(mPagerView);
            setCurDot(0, imagePointOne, imagePointTwo, image_point_three);
            viewpager.setAdapter(mPagerAdapter);

            viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    viewpager.setCurrentItem(position);
                    setCurDot(position, imagePointOne, imagePointTwo, image_point_three);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon, PointView imagePointOne, PointView imagePointTwo, PointView imagePointThree) {
        if (positon == 0) {
            imagePointOne.setParame(Color.parseColor("#ffff7582"));
            imagePointTwo.setParame(Color.parseColor("#d4d4d4"));
            imagePointThree.setParame(Color.parseColor("#d4d4d4"));
        } else if (positon == 1) {
            imagePointTwo.setParame(Color.parseColor("#ffff7582"));
            imagePointOne.setParame(Color.parseColor("#d4d4d4"));
            imagePointThree.setParame(Color.parseColor("#d4d4d4"));
        } else if (positon == 2) {
            imagePointThree.setParame(Color.parseColor("#ffff7582"));
            imagePointTwo.setParame(Color.parseColor("#d4d4d4"));
            imagePointOne.setParame(Color.parseColor("#d4d4d4"));

        }
    }
}
