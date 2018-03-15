package cn.hc.robot.api;

import cn.hc.robot.service.TuringChatService;
import cn.hc.http.Response;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by hc on 2017/5/5.
 */
@Controller
@RequestMapping("api/chat")
public class ChatApi {
    @Autowired
    TuringChatService turingChatService;

    @RequestMapping("/send")
    @ResponseBody
    public Object musicList(@RequestBody Map<String,String> map) throws Exception {
        String info = map.get("info");
        Response resp = turingChatService.send(info);
        JSONObject jsonObject=turingChatService.getJSONMsg(resp);
        return jsonObject;
    }
}
