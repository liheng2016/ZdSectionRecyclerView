package recycleview.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import entity.News;
import recycleview.adapter.InvestorBillDeAdapter;
import view.niudong.com.demo.R;

public class InvestorBillMainActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<News> stringList = new ArrayList<>();
    private InvestorBillDeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_investor_bill_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_investor_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(), LinearLayoutManager.VERTICAL, R.drawable.custom_recycler_view_divier, false));
        adapter = new InvestorBillDeAdapter(null, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        addData();
        adapter.updateData(stringList);
    }

    private void addData() {
        for (int i = 12; i >0; i--) {
            stringList.add( new News(i+""));
        }
    }
}
