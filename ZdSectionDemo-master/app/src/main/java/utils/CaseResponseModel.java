package utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wangxc on 2017/8/11.
 * 测试用列
 */
public class CaseResponseModel extends JSONObject {
    /**
     * 用例名称
     */
    private String caseName;

    public CaseResponseModel(String name) {
        this.caseName = name;
    }

    public CaseResponseModel(String name, String json) throws JSONException {
        super(json);
        this.caseName = name;
    }


    public String getCaseName() {
        return caseName;
    }
}
