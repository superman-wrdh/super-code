package cn.hc.robot.service;

import cn.hc.http.Request;
import cn.hc.http.Response;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by hc on 2017/5/5.
 */
@Service
public class TuringChatService {
    private static final String url = "http://www.tuling123.com/openapi/api"; //图灵聊天请求地址
    private static final String key = "d1e264707c1b44c8b07584a16549c2de"; //key 妥善保管
    private JSONObject paramaters = null;

    public Response send(String info) throws Exception {
        System.out.println(info);
        paramaters = new JSONObject();
        paramaters.put("key", key);
        paramaters.put("info", info);
        Request r = new Request(url);
        r.contentType(ContentType.APPLICATION_JSON);
        r.body(paramaters.toString(), "UTF-8");
        return r.execute();
    }

    public JSONObject getJSONMsg(Response response) {
        response.setCharset(StandardCharsets.UTF_8);
        String string = response.string();
        return JSONObject.fromObject(string);
    }

    public Result getResult(Response response) {
        response.setCharset(StandardCharsets.UTF_8);
        String string = response.string();
        Gson gson = new Gson();
        return gson.fromJson(string, Result.class);
    }

//    public static void main(String[] args) throws Exception {
//        TuringChatService turingChatService = new TuringChatService();
//        Response response = turingChatService.send("hello");
//        System.out.println(turingChatService.getJSONMsg(response));
//        Result result = turingChatService.getResult(response);
//        System.out.println(turingChatService.getJSONMsg(response));
//        System.out.println(result);
//    }
}


class Result {
    private int code;
    private String text;

    public void setCode(int code) {
        this.code = code;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}