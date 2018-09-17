package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niudong on 2017/7/15.
 * Tel：18811793194
 * VersionChange：
 * <p>
 * 作用：理财师主页面数据 模型Bean
 * 钱数以后考虑是否转为double
 */
public class UpdatePlannBillListBean {
    //累计分成金
    public String totalIncome;
    //账户余额
    public String withdrawBalance;
    //客户欠款金额
    public String arrear;
    //账单生成时间
    public String billTime;

    public UpdatePlannBillListBean(String totalIncome, String withdrawBalance, String arrear, String billTime) {
        this.totalIncome = totalIncome;
        this.withdrawBalance = withdrawBalance;
        this.arrear = arrear;
        this.billTime = billTime;
    }

    public List<BillListBean> billList = new ArrayList<>();

    public List<BillListBean> getBillList() {
        return billList;
    }

    public void setBillList(List<BillListBean> billList) {
        this.billList = billList;
    }

    public static class BillListBean {
        public BillListBean(String billDate, String totalIncome, String contractNum, String endNum, String billType) {
            this.billDate = billDate;
            this.totalIncome = totalIncome;
            this.contractNum = contractNum;
            this.endNum = endNum;
            this.billType = billType;
        }



        //账单日期
        public String billDate;
        //        总分成金额
        public String totalIncome;
        //        本月新增签约
        public String contractNum;
        //        新增解约数
        public String endNum;
        public String billType;

    }
}
