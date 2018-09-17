package view.niudong.com.demo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import base.BaseActivity;
import entity.Category;
import entity.ImageItem;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import mulitbinder.CategoryViewBinder;
import mulitbinder.ImageItemViewBinder;

import static view.niudong.com.demo.MyApplication.mContext;

public class MultiActivity extends BaseActivity {
    private MultiTypeAdapter adapter;
    private Items items = new Items();
    private RecyclerView recyclerView;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_multi);
        recyclerView = (RecyclerView) findViewById(R.id.rv_question_list);

        adapter = new MultiTypeAdapter(items);
        //TODO  注册

        adapter.register(Category.class, new CategoryViewBinder());
        adapter.register(ImageItem.class, new ImageItemViewBinder());
        //TODO  设置适配器

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        //       添加分割线

//       添加分割线
//        recyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(), LinearLayoutManager.HORIZONTAL, R.drawable.custom_recycler_view_divier, false));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, false));

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        //TODO 模拟数据 Mock the data
        ImageItem imageItem = new ImageItem(R.mipmap.ic_launcher);
        items.clear();

        items.add(new Category("MultiType数据,左滑哦...."));
        for (int i = 0; i < 10; i++) {
            items.add(imageItem);
        }
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }
}

