package cn.hc.http.baidu;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to)throws Exception {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

    public Map<String,String > getTransResult(String result){
        Map<String,String> map = new HashMap<String,String>();
        JSONObject jsonObject=JSONObject.fromObject(result);
        map.put("from",jsonObject.get("from").toString());
        map.put("to",jsonObject.get("to").toString());
        JSONArray trans_resultArr = jsonObject.getJSONArray("trans_result");
        JSONObject resultString = JSONObject.fromObject(trans_resultArr.get(0));
        map.put("src",resultString.get("src").toString());
        map.put("dst",resultString.get("dst").toString());
        return map;
    }

}
