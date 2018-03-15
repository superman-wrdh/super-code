package cn.hc.util.encrypt;

import java.io.IOException;

import com.sun.org.apache.regexp.internal.recompile;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {
	public static void main(String[] args) {
		String s=Base64code("hc199310@163.com");
		System.out.println("加密后"+s);
		System.out.println("解密"+base64unCode(s));		
	}
	
	public static String Base64code(String str)
	{
		byte[] b=str.getBytes();
		return new BASE64Encoder().encode(b);
	}
	
	public static String base64unCode(String str)
	{	
		byte[] b=null;
		try {
			b= new BASE64Decoder().decodeBuffer(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(b);
	}
	
	public String co(String str,String pass)
	{
		return null;
	}
	
	public String unco(String str,String pass)
	{
		return null;
	}
	
	
}
/*
aGMxOTkzMTBAMTYzLmNvbQ==


aGN6d2JzMTk5MzEwMTU= 

 * */

