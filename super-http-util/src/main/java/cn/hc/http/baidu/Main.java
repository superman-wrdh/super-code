package cn.hc.http.baidu;
public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20160627000024095";
    private static final String SECURITY_KEY = "_ejd7AM7gQRsfdHurfVl";

    public static void main(String[] args) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String src = "7月18日，无锡宜兴发生一起抢夺事件。91岁的老翁独自一人到银行取了6500元现金，" +
                "回家途中遭抢。现代快报记者7月26日从警方获悉，抢走钱的，竟是68岁的老太太。";
        try {
        	String result=api.getTransResult(src, "auto", "en");
            System.out.println(api.getTransResult(result));
        } catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        
    }

}
