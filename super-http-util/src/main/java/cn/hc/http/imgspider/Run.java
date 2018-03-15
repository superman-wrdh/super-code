package cn.hc.http.imgspider;

public class Run {
	
	public static void main(String[] args) {
		String url="http://www.tupianzj.com/meinv/xiezhen/";
		
		
		for (int i = 0; i <17; i+=4) {
			
			DownLoad d1= new DownLoad(url, i);
			DownLoad d2= new DownLoad(url, i+1);
			DownLoad d3= new DownLoad(url, i+2);
			DownLoad d4= new DownLoad(url, i+3);
			
			Thread t1=new Thread(d1);
			Thread t2=new Thread(d2);
			Thread t3=new Thread(d3);
			Thread t4=new Thread(d4);
			
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			
		}
		
	}
}
