package cn.hc.http.file;

import cn.hc.http.Request;
import cn.hc.http.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.entity.ContentType;
//import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hc on 2017/5/15.
 */
public class SupplierReport {
    private static Log logger = LogFactory.getLog(SupplierReport.class);
    //@Autowired
    HttpsGetData httpsGetData;
    public SupplierReport() {
       httpsGetData = new HttpsGetData();
    }

    public String getToken(){
        Request request=new Request("http://erp.genetalks.com/gtalks/api/get_token/?appid=BbbJjBMfMh1FGQg7");
        request.contentType(ContentType.TEXT_HTML);
        Response response = request.execute();
        String result=response.string();
        result=result.replace("{","").replace("}","");
        String token=result.split(",")[2].split(":")[1].trim();
        token=token.substring(1,token.length()-1);
        return token;
    }

    public List<String> getSampleNumberHasReport()throws Exception{
        String token = getToken();
        logger.info("--------------------"+token);
        String url="https://erp.genetalks.com/api/get_pdf_no/cd?token="+token;
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        httpsGetData.set_url(url);
        httpsGetData.set_params(map);
        String strings = httpsGetData.Do();
        List<String> sampleNumList = httpsGetData.toList(strings);
        return sampleNumList;
    }

    public String getSampleQuestion(String no){
        String token = getToken();
        String url= "http://erp.genetalks.com/gtalks/api/get_info/cd?token="+token+"&no="+no;
        Request r=new Request(url);
        r.contentType(ContentType.TEXT_HTML);
        Response response = r.execute();
        String string = response.string();
        return string;
    }

}
