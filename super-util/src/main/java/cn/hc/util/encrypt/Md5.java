package cn.hc.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	public static void main(String[] args) {
		String s="superman";
		Md5 md5 = new Md5();
		System.out.println("加密前\n"+s+"\n"+"加密后\n"+toMd5(s));		
	}
	public static String toMd5(String string)
	{
		byte[] by=string.getBytes();
		MessageDigest digest;
		String str="";
		String str16;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.update(by);
			byte[] data=digest.digest();			
			for (int i = 0; i < data.length; i++) {
				str16=Integer.toHexString(0XFF & data[i]);	
				if (str16.length()==1) {							
					str=str+"0"+str16;
				}
				else{
					str=str+str16;
				}
			}			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("code failed--superman");
			e.printStackTrace();
		}		
		return str;
	}
}










/*
//***************************************************************************	
	public String md5Code(String string)
	{
		byte[] data=string.getBytes();
		String result="";
		try {
			byte[] data2=md5(data);
			result=mybyteToString(data2);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
//***************************************************************************	
	public static byte[] md5(byte[] data) throws NoSuchAlgorithmException
	{		
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(data);
		return digest.digest();
	}
//***************************************************************************	
	public static String mybyteToString(byte[] data)
	{
		String str="";
		String str16;
		for (int i = 0; i < data.length; i++) {
			str16=Integer.toHexString(0XFF & data[i]);	
			if (str16.length()==1) {							
				str=str+"0"+str16;
			}
			else{
				str=str+str16;
			}
		}
		return str;
	}
//***************************************************************************	

*/
















