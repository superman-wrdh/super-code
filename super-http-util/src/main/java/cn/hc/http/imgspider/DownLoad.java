package cn.hc.http.imgspider;

import java.util.HashSet;
import java.util.Set;

public class DownLoad implements Runnable{
	String url;
	int i;
	Photo photo=null;
	public DownLoad(String url,int i) {
		// TODO Auto-generated constructor stub
		this.url=url;
		this.i=i;
		photo=new Photo();
	}
	@Override
	public void run() {
		try {

			// TODO Auto-generated method stub
			Set<String> urx = new HashSet<String>();
			Set<String> TrueSrcUrl = new HashSet<String>();
			String html = "";
			if (i==0) {
				html = photo.getdizhi(url);
			}else {
				html = photo.getdizhi(url+"list_179_"+i+".html");
			}
			urx = photo.getAHref(html);

			System.out.println("-----------------------------------------"+i+"----------------------------------------");
			for (String page : urx ) {
				int k = photo.getPage(page);
				for (int u = 1; u <= k; u++) {
					if (u==1) {
						String shurl = page;
						TrueSrcUrl.add(photo.getJpgSrc(shurl));
					}else {
						String shurl = page.replaceAll(".html", "_"+u+".html");
						TrueSrcUrl.add(photo.getJpgSrc(shurl));
					}
				}
				photo.saveImg(TrueSrcUrl, i);
				TrueSrcUrl.clear();
			}
			System.out.println("---------------------------本次查询到："+TrueSrcUrl.size()+"：张---------------------------");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
