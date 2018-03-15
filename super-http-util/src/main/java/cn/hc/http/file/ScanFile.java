package cn.hc.http.file;

import java.io.*;
class  ScanFile
{
	public static void main(String[] args) 
	{
		//System.out.println("Hello World!");
		File dir = new File("C:\\");
		myshowDir(dir);
	}

	
	public static void myshowDir(File dir)
	{				
		File[] files = dir.listFiles();
		try
		{
			for(int x=0; x<files.length; x++)
		{
			if(files[x].isDirectory())
				myshowDir(files[x]);
			else{
				System.out.println(files[x]);
				myWrite(files[x].toString());
			}
		}
		}
		catch (Exception e)
		{
			System.out.println("�쳣");
		}
		
	}
	public static void myWrite(String str)
	{	FileWriter fw = null;
		try
		{
			fw = new FileWriter("recordC.txt",true);			
			fw.append(str+"\r\n");
			
			fw.flush();
		}
		catch (Exception e)
		{
		}finally{
			try
			{
					if (fw!=null)
					{
						fw.close();
					}
			}
			catch (Exception e1)
			{
			}
			
			
		}
	}	
}
