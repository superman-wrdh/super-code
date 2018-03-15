package cn.hc.notepad.bean;

public class ColorBean {
	/* 
	 *String[] TitleFontcolor ={"白","黑","绿","蓝","红"};   //0 1 2 3 4
	 * */
	public int titlebgcolor=0;   
	public int titlefontcolor=1;
	public int contentbgcolor=0;
	public int contentfontcolor=1;
	public int getTitlebgcolor() {
		return titlebgcolor;
	}		
	public ColorBean(int titlebgcolor, int titlefontcolor, int contentbgcolor,
			int contentfontcolor) {
		super();
		this.titlebgcolor = titlebgcolor;
		this.titlefontcolor = titlefontcolor;
		this.contentbgcolor = contentbgcolor;
		this.contentfontcolor = contentfontcolor;
	}

	public ColorBean() {
		super();
	}
	public void setTitlebgcolor(int titlebgcolor) {
		this.titlebgcolor = titlebgcolor;
	}
	public int getTitlefontcolor() {
		return titlefontcolor;
	}
	public void setTitlefontcolor(int titlefontcolor) {
		this.titlefontcolor = titlefontcolor;
	}
	public int getContentbgcolor() {
		return contentbgcolor;
	}
	public void setContentbgcolor(int contentbgcolor) {
		this.contentbgcolor = contentbgcolor;
	}
	public int getContentfontcolor() {
		return contentfontcolor;
	}
	public void setContentfontcolor(int contentfontcolor) {
		this.contentfontcolor = contentfontcolor;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "标题字体颜色--"+titlebgcolor +"\n标题背景颜色--"+titlefontcolor+"\n内容背景颜色--"+contentbgcolor+"\n内容字体颜色--"+contentfontcolor;
	}
}


 