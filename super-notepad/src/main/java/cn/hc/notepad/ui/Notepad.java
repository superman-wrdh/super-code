package cn.hc.notepad.ui;
 import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.io.*;

import javax.swing.undo.*;
import javax.swing.border.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import cn.hc.notepad.bean.ColorBean;
import cn.hc.notepad.bean.NoteBean;
import cn.hc.notepad.dao.NoteDao;
import cn.hc.notepad.util.DBUtil;
import cn.hc.notepad.util.StringUtil;

import java.awt.datatransfer.*;


public class Notepad extends JFrame implements ActionListener,DocumentListener
{	
	JMenu fileMenu,editMenu,formatMenu,viewMenu,helpMenu;//菜单
	
	JPopupMenu popupMenu;//右键弹出菜单项
	JMenuItem popupMenu_Undo,popupMenu_Cut,popupMenu_Copy,popupMenu_Paste,popupMenu_Delete,popupMenu_SelectAll;
	//“文件”的菜单项
	JMenuItem fileMenu_New,fileMenu_Open,fileMenu_Save,fileMenu_SaveAs,fileMenu_PageSetUp,fileMenu_Print,fileMenu_Exit;
	//“编辑”的菜单项
	JMenuItem editMenu_Undo,editMenu_Cut,editMenu_Copy,editMenu_Paste,editMenu_Delete,editMenu_Find,editMenu_FindNext,editMenu_Replace,editMenu_GoTo,editMenu_SelectAll,editMenu_TimeDate;	
	JCheckBoxMenuItem formatMenu_LineWrap;//“格式”的菜单项
	JMenuItem formatMenu_Font;	
	JCheckBoxMenuItem viewMenu_Status;//“查看”的菜单项	
	JMenuItem helpMenu_HelpTopics,helpMenu_AboutNotepad;//“帮助”的菜单项	
	JTextArea editArea;//“文本”编辑区域	
	JLabel statusLabel;//状态栏标签	
	Toolkit toolkit=Toolkit.getDefaultToolkit();//系统剪贴板
	Clipboard clipBoard=toolkit.getSystemClipboard();	
	protected UndoManager undo=new UndoManager();//创建撤销操作管理器(与撤销操作有关)
	protected UndoableEditListener undoHandler=new UndoHandler();
	//其他变量
	String oldValue;//存放编辑区原来的内容，用于比较文本是否有改动
	boolean isNewFile=true;//是否新文件(未保存过的)
	File currentFile;//当前文件名
	private JMenu menu;
	private JMenuItem saveToDB;
	private JMenuItem dbinfo;
	private JTextArea taTitle;
	private JMenu menu_1;
	private JMenu mnNewMenu;
	private JMenu mnNewMenu_1;
	private JMenu mnNewMenu_2;
	private JMenu mnNewMenu_3;
	private JMenuItem mntmNewMenuItem_2;
	private JMenuItem mntmNewMenuItem_3;
	private JMenuItem menuItem;
	private JMenuItem mntmNewMenuItem_1;
	
	DBUtil dbUtil = new DBUtil();
	NoteDao noteDao = new NoteDao();
	NoteBean noteBean = new NoteBean();
	StringUtil sutil=new StringUtil();
	private JMenuItem viewArticle;
	
	public Notepad()//构造函数开始
	{	
		super("Java记事本");
		setTitle("\u8D85\u7EA7\u8BB0\u4E8B\u672C");	
		Font font = new Font("Dialog", Font.PLAIN, 12);//改变系统默认字体
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
		
		JMenuBar menuBar=new JMenuBar(); //创建菜单条
		
		fileMenu=new JMenu("文件(F)");//创建文件菜单及菜单项并注册事件监听
		fileMenu.setMnemonic('F');//设置快捷键ALT+F
		fileMenu_New=new JMenuItem("新建(N)");
		fileMenu_New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		fileMenu_New.addActionListener(this);

		fileMenu_Open=new JMenuItem("打开(O)...");
		fileMenu_Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		fileMenu_Open.addActionListener(this);

		fileMenu_Save=new JMenuItem("保存(S)");
		fileMenu_Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		fileMenu_Save.addActionListener(this);

		fileMenu_SaveAs=new JMenuItem("另存为(A)...");
		fileMenu_SaveAs.addActionListener(this);

		fileMenu_PageSetUp=new JMenuItem("页面设置(U)...");
		fileMenu_PageSetUp.addActionListener(this);

		fileMenu_Print=new JMenuItem("打印(P)...");
		fileMenu_Print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK)); 
		fileMenu_Print.addActionListener(this);

		fileMenu_Exit=new JMenuItem("退出(X)");
		fileMenu_Exit.addActionListener(this);

		
		editMenu=new JMenu("编辑(E)"); //创建编辑菜单及菜单项并注册事件监听
		editMenu.setMnemonic('E');//设置快捷键ALT+E
		
		editMenu.addMenuListener(new MenuListener()  //当选择编辑菜单时，设置剪切、复制、粘贴、删除等功能的可用性
		{	public void menuCanceled(MenuEvent e)//取消菜单时调用
			{	checkMenuItemEnabled();//设置剪切、复制、粘贴、删除等功能的可用性
			}
			public void menuDeselected(MenuEvent e)//取消选择某个菜单时调用
			{	checkMenuItemEnabled();//设置剪切、复制、粘贴、删除等功能的可用性
			}
			public void menuSelected(MenuEvent e)//选择某个菜单时调用
			{	checkMenuItemEnabled();//设置剪切、复制、粘贴、删除等功能的可用性
			}
		});

		editMenu_Undo=new JMenuItem("撤销(U)");
		editMenu_Undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK));
		editMenu_Undo.addActionListener(this);
		editMenu_Undo.setEnabled(false);

		editMenu_Cut=new JMenuItem("剪切(T)");
		editMenu_Cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
		editMenu_Cut.addActionListener(this);

		editMenu_Copy=new JMenuItem("复制(C)");
		editMenu_Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
		editMenu_Copy.addActionListener(this);

		editMenu_Paste=new JMenuItem("粘贴(P)");
		editMenu_Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));
		editMenu_Paste.addActionListener(this);

		editMenu_Delete=new JMenuItem("删除(D)");
		editMenu_Delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
		editMenu_Delete.addActionListener(this);

		editMenu_Find=new JMenuItem("查找(F)...");
		editMenu_Find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,InputEvent.CTRL_MASK));
		editMenu_Find.addActionListener(this);

		editMenu_FindNext=new JMenuItem("查找下一个(N)");
		editMenu_FindNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));
		editMenu_FindNext.addActionListener(this);

		editMenu_Replace = new JMenuItem("替换(R)...",'R'); 
		editMenu_Replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK)); 
		editMenu_Replace.addActionListener(this);

		editMenu_GoTo = new JMenuItem("转到(G)...",'G'); 
		editMenu_GoTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK)); 
		editMenu_GoTo.addActionListener(this);

		editMenu_SelectAll = new JMenuItem("全选",'A'); 
		editMenu_SelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK)); 
		editMenu_SelectAll.addActionListener(this);

		editMenu_TimeDate = new JMenuItem("时间/日期(D)",'D');
		editMenu_TimeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
		editMenu_TimeDate.addActionListener(this);

		//创建格式菜单及菜单项并注册事件监听
		formatMenu=new JMenu("格式(O)");
		formatMenu.setMnemonic('O');//设置快捷键ALT+O

		formatMenu_LineWrap=new JCheckBoxMenuItem("自动换行(W)");
		formatMenu_LineWrap.setMnemonic('W');//设置快捷键ALT+W
		formatMenu_LineWrap.setState(true);
		formatMenu_LineWrap.addActionListener(this);

		formatMenu_Font=new JMenuItem("字体(F)...");
		formatMenu_Font.addActionListener(this);

		//创建查看菜单及菜单项并注册事件监听
		viewMenu=new JMenu("查看(V)");
		viewMenu.setMnemonic('V');//设置快捷键ALT+V

		viewMenu_Status=new JCheckBoxMenuItem("状态栏(S)");
		viewMenu_Status.setMnemonic('S');//设置快捷键ALT+S
		viewMenu_Status.setState(true);
		viewMenu_Status.addActionListener(this);

		//创建帮助菜单及菜单项并注册事件监听
		helpMenu = new JMenu("帮助(H)");
		helpMenu.setMnemonic('H');//设置快捷键ALT+H

		helpMenu_HelpTopics = new JMenuItem("帮助主题(H)"); 
		helpMenu_HelpTopics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		helpMenu_HelpTopics.addActionListener(this);

		helpMenu_AboutNotepad = new JMenuItem("关于记事本(A)"); 
		helpMenu_AboutNotepad.addActionListener(this);

		//向菜单条添加"文件"菜单及菜单项
		menuBar.add(fileMenu); 
		fileMenu.add(fileMenu_New); 
		fileMenu.add(fileMenu_Open); 
		fileMenu.add(fileMenu_Save); 
		fileMenu.add(fileMenu_SaveAs); 
		fileMenu.addSeparator();		//分隔线
		fileMenu.add(fileMenu_PageSetUp); 
		fileMenu.add(fileMenu_Print); 
		fileMenu.addSeparator();		//分隔线 
		fileMenu.add(fileMenu_Exit); 

		//向菜单条添加"编辑"菜单及菜单项 
		menuBar.add(editMenu); 
		editMenu.add(editMenu_Undo);  
		editMenu.addSeparator();		//分隔线 
		editMenu.add(editMenu_Cut); 
		editMenu.add(editMenu_Copy); 
		editMenu.add(editMenu_Paste); 
		editMenu.add(editMenu_Delete); 
		editMenu.addSeparator(); 		//分隔线
		editMenu.add(editMenu_Find); 
		editMenu.add(editMenu_FindNext); 
		editMenu.add(editMenu_Replace);
		editMenu.add(editMenu_GoTo); 
		editMenu.addSeparator();  		//分隔线
		editMenu.add(editMenu_SelectAll); 
		editMenu.add(editMenu_TimeDate);
		
		menu_1 = new JMenu("\u4E3B\u9898\u8BBE\u7F6E");
		menuBar.add(menu_1);
		
		mnNewMenu = new JMenu("\u6807\u9898\u6846\u80CC\u666F\u989C\u8272");
		menu_1.add(mnNewMenu);
		
		mnNewMenu_1 = new JMenu("\u5185\u5BB9\u6846\u80CC\u666F\u989C\u8272");
		menu_1.add(mnNewMenu_1);
		
		mnNewMenu_2 = new JMenu("\u6807\u9898\u5B57\u4F53\u989C\u8272");
		menu_1.add(mnNewMenu_2);
		
		mnNewMenu_3 = new JMenu("\u5185\u5BB9\u5B57\u4F53\u989C\u8272");
		menu_1.add(mnNewMenu_3);
		
		mntmNewMenuItem_1 = new JMenuItem("\u81EA\u5B9A\u4E49\u4E3B\u9898");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {               //*****************************设置自定义颜色
			public void actionPerformed(ActionEvent e) {
				ColorBean colorBean = new ColorBean();
				ChooseColorUI chooseColorUI = new ChooseColorUI(colorBean);
				chooseColorUI.setVisible(true);
				
				if (chooseColorUI.isActive()) {					
					setColor(colorBean, taTitle, editArea);
				}
			}
		});
		menu_1.add(mntmNewMenuItem_1);
		
		mntmNewMenuItem_2 = new JMenuItem("\u4E00\u952E\u9ED1\u8272\u4E3B\u9898");  //*************************一键黑色主题
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//new Notepad().setColor(colorBean);
				
			}
		});
		menu_1.add(mntmNewMenuItem_2);
		
		mntmNewMenuItem_3 = new JMenuItem("\u4E00\u952E\u9ED8\u8BA4\u4E3B\u9898");
		menu_1.add(mntmNewMenuItem_3);
		
		menu = new JMenu("\u6570\u636E\u5E93");
		menuBar.add(menu);
		
		saveToDB = new JMenuItem("\u4FDD\u5B58\u5230\u6570\u636E\u5E93");  //***************保存到数据库********************
		saveToDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title=taTitle.getText().toString();
				String content =editArea.getText().toString();
				int titleisExit=0;
				Connection con =null;
				try {
					con=dbUtil.con();
					titleisExit =noteDao.isTitleExit(con, title);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}								
				if (isNotEmpty(title)) {					
					if (titleisExit!=0) {  									//存在相同文章标题 使用Update
							System.out.println("更新成功");
							try {
								noteDao.myModify(con, title, content, "", "", titleisExit);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "修改记事成功");
					}else {            										//不存在相同文章使用插入
							
							try {
								int resultcode=noteDao.myInsert(con, title, content, "", "");
								if (resultcode!=0) {
									JOptionPane.showMessageDialog(null, "新增记事成功");
								}
							} catch (Exception e2) {
								// TODO: handle exception
							}
						
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "标题不能为空");
				}
				
				
			}
		});
		menu.add(saveToDB);
		
		dbinfo = new JMenuItem("\u6570\u636E\u5E93\u914D\u7F6E\u8BE6\u60C5");
		dbinfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DBinfoView().setVisible(true);
			}
		});
		
		menuItem = new JMenuItem("\u67E5\u8BE2\u6587\u7AE0");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "该功能正在开发");
				String id=JOptionPane.showInputDialog(null, "输入数字");
				if (sutil.isNotEmpty(id)) {
					try {
						Connection con=dbUtil.con();
						noteBean=noteDao.myQuarry(con, Integer.parseInt(id));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
				}
				editArea.setText(noteBean.getContent());
				taTitle.setText(noteBean.getTitle());
				
				
			}
		});
		menu.add(menuItem);
		
		viewArticle = new JMenuItem("\u67E5\u770B\u6587\u7AE0");    //***************************馋看文章**********************************
		viewArticle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TA().setVisible(true);
				
			}
		});
		menu.add(viewArticle);
		menu.add(dbinfo);

		//向菜单条添加"格式"菜单及菜单项		
		menuBar.add(formatMenu); 
		formatMenu.add(formatMenu_LineWrap); 
		formatMenu.add(formatMenu_Font);

		//向菜单条添加"查看"菜单及菜单项 
		menuBar.add(viewMenu); 
		viewMenu.add(viewMenu_Status);

		//向菜单条添加"帮助"菜单及菜单项
		menuBar.add(helpMenu);
		helpMenu.add(helpMenu_HelpTopics);
		helpMenu.addSeparator();
		helpMenu.add(helpMenu_AboutNotepad);
				
		//向窗口添加菜单条				
		this.setJMenuBar(menuBar);

		//创建文本编辑区并添加滚动条
		editArea=new JTextArea(20,50);
		JScrollPane scroller=new JScrollPane(editArea);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scroller,BorderLayout.CENTER);//向窗口添加文本编辑区
		editArea.setWrapStyleWord(true);//设置单词在一行不足容纳时换行
		editArea.setLineWrap(true);
		
		taTitle = new JTextArea(5,20);
		taTitle.setFont(new Font("Monospaced", Font.PLAIN, 18));
		taTitle.setLineWrap(true);
		//taTitle.setBackground(Color.gray);
		taTitle.setBackground(new Color(192,208,192));
		scroller.setColumnHeaderView(taTitle);
		//this.add(editArea,BorderLayout.CENTER);//向窗口添加文本编辑区
		oldValue=editArea.getText();//获取原文本编辑区的内容

		//编辑区注册事件监听(与撤销操作有关)
		editArea.getDocument().addUndoableEditListener(undoHandler);
		editArea.getDocument().addDocumentListener(this);

		//创建右键弹出菜单
		popupMenu=new JPopupMenu();
		popupMenu_Undo=new JMenuItem("撤销(U)");
		popupMenu_Cut=new JMenuItem("剪切(T)");
		popupMenu_Copy=new JMenuItem("复制(C)");
		popupMenu_Paste=new JMenuItem("粘帖(P)");
		popupMenu_Delete=new JMenuItem("删除(D)");
		popupMenu_SelectAll=new JMenuItem("全选(A)");

		popupMenu_Undo.setEnabled(false);

		//向右键菜单添加菜单项和分隔符
		popupMenu.add(popupMenu_Undo);
		popupMenu.addSeparator();
		popupMenu.add(popupMenu_Cut);
		popupMenu.add(popupMenu_Copy);
		popupMenu.add(popupMenu_Paste);
		popupMenu.add(popupMenu_Delete);
		popupMenu.addSeparator();
		popupMenu.add(popupMenu_SelectAll);

		//文本编辑区注册右键菜单事件
		popupMenu_Undo.addActionListener(this);
		popupMenu_Cut.addActionListener(this);
		popupMenu_Copy.addActionListener(this);
		popupMenu_Paste.addActionListener(this);
		popupMenu_Delete.addActionListener(this);
		popupMenu_SelectAll.addActionListener(this);

		//文本编辑区注册右键菜单事件
		editArea.addMouseListener(new MouseAdapter()
		{	public void mousePressed(MouseEvent e)
			{	if(e.isPopupTrigger())//返回此鼠标事件是否为该平台的弹出菜单触发事件
				{	popupMenu.show(e.getComponent(),e.getX(),e.getY());//在组件调用者的坐标空间中的位置 X、Y 显示弹出菜单
				}
				checkMenuItemEnabled();//设置剪切，复制，粘帖，删除等功能的可用性
				editArea.requestFocus();//编辑区获取焦点
			}
			public void mouseReleased(MouseEvent e)
			{	if(e.isPopupTrigger())//返回此鼠标事件是否为该平台的弹出菜单触发事件
				{	popupMenu.show(e.getComponent(),e.getX(),e.getY());//在组件调用者的坐标空间中的位置 X、Y 显示弹出菜单
				}
				checkMenuItemEnabled();//设置剪切，复制，粘帖，删除等功能的可用性
				editArea.requestFocus();//编辑区获取焦点
			}
		});//文本编辑区注册右键菜单事件结束

		//创建和添加状态栏
		statusLabel=new JLabel("　按F1获取帮助");
		getContentPane().add(statusLabel,BorderLayout.SOUTH);//向窗口添加状态栏标签

		//设置窗口在屏幕上的位置、大小和可见性 
		this.setLocation(100,100);
		this.setSize(698,660);
		this.setVisible(true);
		//添加窗口监听器
		addWindowListener(new WindowAdapter()
		{	public void windowClosing(WindowEvent e)
			{	exitWindowChoose();
			}
		});

		checkMenuItemEnabled();
		editArea.requestFocus();
	}//构造函数Notepad结束
	
	//设置菜单项的可用性：剪切，复制，粘帖，删除功能
	public void checkMenuItemEnabled()
	{	String selectText=editArea.getSelectedText();
		if(selectText==null)
		{	editMenu_Cut.setEnabled(false);
			popupMenu_Cut.setEnabled(false);
			editMenu_Copy.setEnabled(false);
			popupMenu_Copy.setEnabled(false);
			editMenu_Delete.setEnabled(false);
			popupMenu_Delete.setEnabled(false);
		}
		else
		{	editMenu_Cut.setEnabled(true);
			popupMenu_Cut.setEnabled(true); 
			editMenu_Copy.setEnabled(true);
			popupMenu_Copy.setEnabled(true);
			editMenu_Delete.setEnabled(true);
			popupMenu_Delete.setEnabled(true);
		}
		//粘帖功能可用性判断
		Transferable contents=clipBoard.getContents(this);
		if(contents==null)
		{	editMenu_Paste.setEnabled(false);
			popupMenu_Paste.setEnabled(false);
		}
		else
		{	editMenu_Paste.setEnabled(true);
			popupMenu_Paste.setEnabled(true);	
		}
	}//方法checkMenuItemEnabled()结束

	//关闭窗口时调用
	public void exitWindowChoose()
	{	editArea.requestFocus();
		String currentValue=editArea.getText();
		if(currentValue.equals(oldValue)==true)
		{	System.exit(0);
		}
		else
		{	int exitChoose=JOptionPane.showConfirmDialog(this,"您的文件尚未保存，是否保存？","退出提示",JOptionPane.YES_NO_CANCEL_OPTION);
			if(exitChoose==JOptionPane.YES_OPTION)
			{	//boolean isSave=false;
				if(isNewFile)
				{	
					String str=null;
					JFileChooser fileChooser=new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fileChooser.setApproveButtonText("确定");
					fileChooser.setDialogTitle("另存为");
					
					int result=fileChooser.showSaveDialog(this);
					
					if(result==JFileChooser.CANCEL_OPTION)
					{	statusLabel.setText("　您没有保存文件");
						return;
					}					
	
					File saveFileName=fileChooser.getSelectedFile();
				
					if(saveFileName==null||saveFileName.getName().equals(""))
					{	JOptionPane.showMessageDialog(this,"不合法的文件名","不合法的文件名",JOptionPane.ERROR_MESSAGE);
					}
					else 
					{	try
						{	FileWriter fw=new FileWriter(saveFileName);
							BufferedWriter bfw=new BufferedWriter(fw);
							bfw.write(editArea.getText(),0,editArea.getText().length());
							bfw.flush();
							fw.close();
							
							isNewFile=false;
							currentFile=saveFileName;
							oldValue=editArea.getText();
							
							this.setTitle(saveFileName.getName()+"  - 记事本");
							statusLabel.setText("　当前打开文件:"+saveFileName.getAbsoluteFile());
							//isSave=true;
						}							
						catch(IOException ioException){					
						}				
					}
				}
				else
				{
					try
					{	FileWriter fw=new FileWriter(currentFile);
						BufferedWriter bfw=new BufferedWriter(fw);
						bfw.write(editArea.getText(),0,editArea.getText().length());
						bfw.flush();
						fw.close();
						//isSave=true;
					}							
					catch(IOException ioException){					
					}
				}
				System.exit(0);
				//if(isSave)System.exit(0);
				//else return;
			}
			else if(exitChoose==JOptionPane.NO_OPTION)
			{	System.exit(0);
			}
			else
			{	return;
			}
		}
	}//关闭窗口时调用方法结束

	//查找方法
	public void find()
	{	final JDialog findDialog=new JDialog(this,"查找",false);//false时允许其他窗口同时处于激活状态(即无模式)
		Container con=findDialog.getContentPane();//返回此对话框的contentPane对象	
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel findContentLabel=new JLabel("查找内容(N)：");
		final JTextField findText=new JTextField(15);
		JButton findNextButton=new JButton("查找下一个(F)：");
		final JCheckBox matchCheckBox=new JCheckBox("区分大小写(C)");
		ButtonGroup bGroup=new ButtonGroup();
		final JRadioButton upButton=new JRadioButton("向上(U)");
		final JRadioButton downButton=new JRadioButton("向下(U)");
		downButton.setSelected(true);
		bGroup.add(upButton);
		bGroup.add(downButton);
		JButton cancel=new JButton("取消");
		//取消按钮事件处理
		cancel.addActionListener(new ActionListener()
		{	public void actionPerformed(ActionEvent e)
			{	findDialog.dispose();
			}
		});
		//"查找下一个"按钮监听
		findNextButton.addActionListener(new ActionListener()
		{	public void actionPerformed(ActionEvent e)
			{	//"区分大小写(C)"的JCheckBox是否被选中
				int k=0,m=0;
				final String str1,str2,str3,str4,strA,strB;
				str1=editArea.getText();
				str2=findText.getText();
				str3=str1.toUpperCase();
				str4=str2.toUpperCase();
				if(matchCheckBox.isSelected())//区分大小写
				{	strA=str1;
					strB=str2;
				}
				else//不区分大小写,此时把所选内容全部化成大写(或小写)，以便于查找 
				{	strA=str3;
					strB=str4;
				}
				if(upButton.isSelected())
				{	//k=strA.lastIndexOf(strB,editArea.getCaretPosition()-1);
					if(editArea.getSelectedText()==null)
						k=strA.lastIndexOf(strB,editArea.getCaretPosition()-1);
					else
						k=strA.lastIndexOf(strB, editArea.getCaretPosition()-findText.getText().length()-1);	
					if(k>-1)
					{	//String strData=strA.subString(k,strB.getText().length()+1);
						editArea.setCaretPosition(k);
						editArea.select(k,k+strB.length());
					}
					else
					{	JOptionPane.showMessageDialog(null,"找不到您查找的内容！","查找",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else if(downButton.isSelected())
				{	if(editArea.getSelectedText()==null)
						k=strA.indexOf(strB,editArea.getCaretPosition()+1);
					else
						k=strA.indexOf(strB, editArea.getCaretPosition()-findText.getText().length()+1);	
					if(k>-1)
					{	//String strData=strA.subString(k,strB.getText().length()+1);
						editArea.setCaretPosition(k);
						editArea.select(k,k+strB.length());
					}
					else
					{	JOptionPane.showMessageDialog(null,"找不到您查找的内容！","查找",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});//"查找下一个"按钮监听结束
		//创建"查找"对话框的界面
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		JPanel panel3=new JPanel();
		JPanel directionPanel=new JPanel();
		directionPanel.setBorder(BorderFactory.createTitledBorder("方向"));
		//设置directionPanel组件的边框;
		//BorderFactory.createTitledBorder(String title)创建一个新标题边框，使用默认边框（浮雕化）、默认文本位置（位于顶线上）、默认调整 (leading) 以及由当前外观确定的默认字体和文本颜色，并指定了标题文本。
		directionPanel.add(upButton);
		directionPanel.add(downButton);
		panel1.setLayout(new GridLayout(2,1));
		panel1.add(findNextButton);
		panel1.add(cancel);
		panel2.add(findContentLabel);
		panel2.add(findText);
		panel2.add(panel1);
		panel3.add(matchCheckBox);
		panel3.add(directionPanel);
		con.add(panel2);
		con.add(panel3);
		findDialog.setSize(410,180);
		findDialog.setResizable(false);//不可调整大小
		findDialog.setLocation(230,280);
		findDialog.setVisible(true);
	}//查找方法结束
	
	//替换方法
	public void replace()
	{	final JDialog replaceDialog=new JDialog(this,"替换",false);//false时允许其他窗口同时处于激活状态(即无模式)
		Container con=replaceDialog.getContentPane();//返回此对话框的contentPane对象
		con.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel findContentLabel=new JLabel("查找内容(N)：");
		final JTextField findText=new JTextField(15);
		JButton findNextButton=new JButton("查找下一个(F):");
		JLabel replaceLabel=new JLabel("替换为(P)：");
		final JTextField replaceText=new JTextField(15);
		JButton replaceButton=new JButton("替换(R)");
		JButton replaceAllButton=new JButton("全部替换(A)");
		JButton cancel=new JButton("取消");
		cancel.addActionListener(new ActionListener()
		{	public void actionPerformed(ActionEvent e)
			{	replaceDialog.dispose();
			}
		});
		final JCheckBox matchCheckBox=new JCheckBox("区分大小写(C)");
		ButtonGroup bGroup=new ButtonGroup();
		final JRadioButton upButton=new JRadioButton("向上(U)");
		final JRadioButton downButton=new JRadioButton("向下(U)");
		downButton.setSelected(true);
		bGroup.add(upButton);
		bGroup.add(downButton);		
		
		//"查找下一个"按钮监听
		findNextButton.addActionListener(new ActionListener()
		{	public void actionPerformed(ActionEvent e)
			{	//"区分大小写(C)"的JCheckBox是否被选中
				int k=0,m=0;
				final String str1,str2,str3,str4,strA,strB;
				str1=editArea.getText();
				str2=findText.getText();
				str3=str1.toUpperCase();
				str4=str2.toUpperCase();
				if(matchCheckBox.isSelected())//区分大小写
				{	strA=str1;
					strB=str2;
				}
				else//不区分大小写,此时把所选内容全部化成大写(或小写)，以便于查找 
				{	strA=str3;
					strB=str4;
				}
				if(upButton.isSelected())
				{	//k=strA.lastIndexOf(strB,editArea.getCaretPosition()-1);
					if(editArea.getSelectedText()==null)
						k=strA.lastIndexOf(strB,editArea.getCaretPosition()-1);
					else
						k=strA.lastIndexOf(strB, editArea.getCaretPosition()-findText.getText().length()-1);	
					if(k>-1)
					{	//String strData=strA.subString(k,strB.getText().length()+1);
						editArea.setCaretPosition(k);
						editArea.select(k,k+strB.length());
					}
					else
					{	JOptionPane.showMessageDialog(null,"找不到您查找的内容！","查找",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else if(downButton.isSelected())
				{	if(editArea.getSelectedText()==null)
						k=strA.indexOf(strB,editArea.getCaretPosition()+1);
					else
						k=strA.indexOf(strB, editArea.getCaretPosition()-findText.getText().length()+1);	
					if(k>-1)
					{	//String strData=strA.subString(k,strB.getText().length()+1);
						editArea.setCaretPosition(k);
						editArea.select(k,k+strB.length());
					}
					else
					{	JOptionPane.showMessageDialog(null,"找不到您查找的内容！","查找",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});//"查找下一个"按钮监听结束
		
		//"替换"按钮监听
		replaceButton.addActionListener(new ActionListener()
		{	public void actionPerformed(ActionEvent e)
			{	if(replaceText.getText().length()==0 && editArea.getSelectedText()!=null) 
					editArea.replaceSelection(""); 
				if(replaceText.getText().length()>0 && editArea.getSelectedText()!=null) 
					editArea.replaceSelection(replaceText.getText());
			}
		});//"替换"按钮监听结束
		
		//"全部替换"按钮监听
		replaceAllButton.addActionListener(new ActionListener()
		{	public void actionPerformed(ActionEvent e)
			{	editArea.setCaretPosition(0);	//将光标放到编辑区开头	
				int k=0,m=0,replaceCount=0;
				if(findText.getText().length()==0)
				{	JOptionPane.showMessageDialog(replaceDialog,"请填写查找内容!","提示",JOptionPane.WARNING_MESSAGE);
					findText.requestFocus(true);
					return;
				}
				while(k>-1)//当文本中有内容被选中时(k>-1被选中)进行替换，否则不进行while循环
				{	//"区分大小写(C)"的JCheckBox是否被选中
					//int k=0,m=0;
					final String str1,str2,str3,str4,strA,strB;
					str1=editArea.getText();
					str2=findText.getText();
					str3=str1.toUpperCase();
					str4=str2.toUpperCase();
					if(matchCheckBox.isSelected())//区分大小写
					{	strA=str1;
						strB=str2;
					}
					else//不区分大小写,此时把所选内容全部化成大写(或小写)，以便于查找 
					{	strA=str3;
						strB=str4;
					}
					if(upButton.isSelected())
					{	//k=strA.lastIndexOf(strB,editArea.getCaretPosition()-1);
						if(editArea.getSelectedText()==null)
							k=strA.lastIndexOf(strB,editArea.getCaretPosition()-1);
						else
							k=strA.lastIndexOf(strB, editArea.getCaretPosition()-findText.getText().length()-1);	
						if(k>-1)
						{	//String strData=strA.subString(k,strB.getText().length()+1);
							editArea.setCaretPosition(k);
							editArea.select(k,k+strB.length());
						}
						else
						{	if(replaceCount==0)
							{	JOptionPane.showMessageDialog(replaceDialog, "找不到您查找的内容!", "记事本",JOptionPane.INFORMATION_MESSAGE); 
							}
							else
							{	JOptionPane.showMessageDialog(replaceDialog,"成功替换"+replaceCount+"个匹配内容!","替换成功",JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
					else if(downButton.isSelected())
					{	if(editArea.getSelectedText()==null)
							k=strA.indexOf(strB,editArea.getCaretPosition()+1);
						else
							k=strA.indexOf(strB, editArea.getCaretPosition()-findText.getText().length()+1);	
						if(k>-1)
						{	//String strData=strA.subString(k,strB.getText().length()+1);
							editArea.setCaretPosition(k);
							editArea.select(k,k+strB.length());
						}
						else
						{	if(replaceCount==0)
							{	JOptionPane.showMessageDialog(replaceDialog, "找不到您查找的内容!", "记事本",JOptionPane.INFORMATION_MESSAGE); 
							}
							else
							{	JOptionPane.showMessageDialog(replaceDialog,"成功替换"+replaceCount+"个匹配内容!","替换成功",JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
					if(replaceText.getText().length()==0 && editArea.getSelectedText()!= null)
					{	editArea.replaceSelection("");
						replaceCount++;
					} 
					
					if(replaceText.getText().length()>0 && editArea.getSelectedText()!= null) 
					{	editArea.replaceSelection(replaceText.getText()); 
						replaceCount++;
					}
				}//while循环结束
			}
		});//"替换全部"方法结束
		
		//创建"替换"对话框的界面
		JPanel directionPanel=new JPanel();
		directionPanel.setBorder(BorderFactory.createTitledBorder("方向"));
		//设置directionPanel组件的边框;
		//BorderFactory.createTitledBorder(String title)创建一个新标题边框，使用默认边框（浮雕化）、默认文本位置（位于顶线上）、默认调整 (leading) 以及由当前外观确定的默认字体和文本颜色，并指定了标题文本。
		directionPanel.add(upButton);
		directionPanel.add(downButton);
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		JPanel panel3=new JPanel();
		JPanel panel4=new JPanel();
		panel4.setLayout(new GridLayout(2,1));
		panel1.add(findContentLabel);
		panel1.add(findText);
		panel1.add(findNextButton);
		panel4.add(replaceButton);
		panel4.add(replaceAllButton);
		panel2.add(replaceLabel);
		panel2.add(replaceText);
		panel2.add(panel4);
		panel3.add(matchCheckBox);
		panel3.add(directionPanel);
		panel3.add(cancel);
		con.add(panel1);
		con.add(panel2);
		con.add(panel3);
		replaceDialog.setSize(420,220);
		replaceDialog.setResizable(false);//不可调整大小
		replaceDialog.setLocation(230,280);
		replaceDialog.setVisible(true);
	}//"全部替换"按钮监听结束

	//"字体"方法
	public void font()
	{	final JDialog fontDialog=new JDialog(this,"字体设置",false);
		Container con=fontDialog.getContentPane();
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel fontLabel=new JLabel("字体(F)：");
		fontLabel.setPreferredSize(new Dimension(100,20));//构造一个Dimension，并将其初始化为指定宽度和高度
		JLabel styleLabel=new JLabel("字形(Y)：");
		styleLabel.setPreferredSize(new Dimension(100,20));
		JLabel sizeLabel=new JLabel("大小(S)：");
		sizeLabel.setPreferredSize(new Dimension(100,20));
		final JLabel sample=new JLabel("何超的记事本-HC's Notepad");
		//sample.setHorizontalAlignment(SwingConstants.CENTER);
		final JTextField fontText=new JTextField(9);
		fontText.setPreferredSize(new Dimension(200,20));
		final JTextField styleText=new JTextField(8);
		styleText.setPreferredSize(new Dimension(200,20));
		final int style[]={Font.PLAIN,Font.BOLD,Font.ITALIC,Font.BOLD+Font.ITALIC};
		final JTextField sizeText=new JTextField(5);
		sizeText.setPreferredSize(new Dimension(200,20));
		JButton okButton=new JButton("确定");
		JButton cancel=new JButton("取消");
		cancel.addActionListener(new ActionListener()
		{	public void actionPerformed(ActionEvent e)
			{	fontDialog.dispose();	
			}
		});
		Font currentFont=editArea.getFont();
		fontText.setText(currentFont.getFontName());
		fontText.selectAll();
		//styleText.setText(currentFont.getStyle());
		//styleText.selectAll();
		if(currentFont.getStyle()==Font.PLAIN)
			styleText.setText("常规");
		else if(currentFont.getStyle()==Font.BOLD)
			styleText.setText("粗体");
		else if(currentFont.getStyle()==Font.ITALIC)
			styleText.setText("斜体");
		else if(currentFont.getStyle()==(Font.BOLD+Font.ITALIC))
			styleText.setText("粗斜体");
		styleText.selectAll();
		String str=String.valueOf(currentFont.getSize());
		sizeText.setText(str);
		sizeText.selectAll();
		final JList fontList,styleList,sizeList;
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		final String fontName[]=ge.getAvailableFontFamilyNames();
		fontList=new JList(fontName);
		fontList.setFixedCellWidth(86);
		fontList.setFixedCellHeight(20);
		fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final String fontStyle[]={"常规","粗体","斜体","粗斜体"};
		styleList=new JList(fontStyle);
		styleList.setFixedCellWidth(86);
		styleList.setFixedCellHeight(20);
		styleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if(currentFont.getStyle()==Font.PLAIN)
			styleList.setSelectedIndex(0);
		else if(currentFont.getStyle()==Font.BOLD)
			styleList.setSelectedIndex(1);
		else if(currentFont.getStyle()==Font.ITALIC)
			styleList.setSelectedIndex(2);
		else if(currentFont.getStyle()==(Font.BOLD+Font.ITALIC))
			styleList.setSelectedIndex(3);
		final String fontSize[]={"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"};
		sizeList=new JList(fontSize);
		sizeList.setFixedCellWidth(43);
		sizeList.setFixedCellHeight(20);
		sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fontList.addListSelectionListener(new ListSelectionListener()
		{	public void valueChanged(ListSelectionEvent event)
			{	fontText.setText(fontName[fontList.getSelectedIndex()]);
				fontText.selectAll();
				Font sampleFont1=new Font(fontText.getText(),style[styleList.getSelectedIndex()],Integer.parseInt(sizeText.getText()));
				sample.setFont(sampleFont1);
			}
		});
		styleList.addListSelectionListener(new ListSelectionListener()
		{	public void valueChanged(ListSelectionEvent event)
			{	int s=style[styleList.getSelectedIndex()];
				styleText.setText(fontStyle[s]);
				styleText.selectAll();
				Font sampleFont2=new Font(fontText.getText(),style[styleList.getSelectedIndex()],Integer.parseInt(sizeText.getText()));
				sample.setFont(sampleFont2);
			}
		});
		sizeList.addListSelectionListener(new ListSelectionListener()
		{	public void valueChanged(ListSelectionEvent event)
			{	sizeText.setText(fontSize[sizeList.getSelectedIndex()]);
				//sizeText.requestFocus();
				sizeText.selectAll();	
				Font sampleFont3=new Font(fontText.getText(),style[styleList.getSelectedIndex()],Integer.parseInt(sizeText.getText()));
				sample.setFont(sampleFont3);
			}
		});
		okButton.addActionListener(new ActionListener()
		{	public void actionPerformed(ActionEvent e)
			{	Font okFont=new Font(fontText.getText(),style[styleList.getSelectedIndex()],Integer.parseInt(sizeText.getText()));
				editArea.setFont(okFont);
				fontDialog.dispose();
			}
		});
		JPanel samplePanel=new JPanel();
		samplePanel.setBorder(BorderFactory.createTitledBorder("示例"));
		//samplePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		samplePanel.add(sample);
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		JPanel panel3=new JPanel();
		
		panel2.add(fontText);
		panel2.add(styleText);
		panel2.add(sizeText);
		panel2.add(okButton);
		panel3.add(new JScrollPane(fontList));//JList不支持直接滚动，所以要让JList作为JScrollPane的视口视图
		panel3.add(new JScrollPane(styleList));
		panel3.add(new JScrollPane(sizeList));
		panel3.add(cancel);
		con.add(panel1);
		con.add(panel2);
		con.add(panel3);
		con.add(samplePanel);
		fontDialog.setSize(350,340);
		fontDialog.setLocation(200,200);
		fontDialog.setResizable(false);
		fontDialog.setVisible(true);
	}

	//public void menuPerformed(MenuEvent e)
	//{	checkMenuItemEnabled();//设置剪切、复制、粘贴、删除等功能的可用性
	//}

	public void actionPerformed(ActionEvent e)
	{	//新建
		if(e.getSource()==fileMenu_New)
		{	editArea.requestFocus();
			String currentValue=editArea.getText();
			boolean isTextChange=(currentValue.equals(oldValue))?false:true;
			if(isTextChange)
			{	int saveChoose=JOptionPane.showConfirmDialog(this,"您的文件尚未保存，是否保存？","提示",JOptionPane.YES_NO_CANCEL_OPTION);
				if(saveChoose==JOptionPane.YES_OPTION)
				{	String str=null;
					JFileChooser fileChooser=new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					//fileChooser.setApproveButtonText("确定");
					fileChooser.setDialogTitle("另存为");
					int result=fileChooser.showSaveDialog(this);
					if(result==JFileChooser.CANCEL_OPTION)
					{	statusLabel.setText("您没有选择任何文件");
						return;
					}
					File saveFileName=fileChooser.getSelectedFile();
					if(saveFileName==null || saveFileName.getName().equals(""))
					{	JOptionPane.showMessageDialog(this,"不合法的文件名","不合法的文件名",JOptionPane.ERROR_MESSAGE);
					}
					else 
					{	try
						{	FileWriter fw=new FileWriter(saveFileName);
							BufferedWriter bfw=new BufferedWriter(fw);
							bfw.write(editArea.getText(),0,editArea.getText().length());
							bfw.flush();//刷新该流的缓冲
							bfw.close();
							isNewFile=false;
							currentFile=saveFileName;
							oldValue=editArea.getText();
							this.setTitle(saveFileName.getName()+" - 记事本");
							statusLabel.setText("当前打开文件："+saveFileName.getAbsoluteFile());
						}
						catch (IOException ioException)
						{
						}
					}
				}
				else if(saveChoose==JOptionPane.NO_OPTION)
				{	editArea.replaceRange("",0,editArea.getText().length());
					statusLabel.setText(" 新建文件");
					this.setTitle("无标题 - 记事本");
					isNewFile=true;
					undo.discardAllEdits();	//撤消所有的"撤消"操作
					editMenu_Undo.setEnabled(false);
					oldValue=editArea.getText();
				}
				else if(saveChoose==JOptionPane.CANCEL_OPTION)
				{	return;
				}
			}
			else
			{	editArea.replaceRange("",0,editArea.getText().length());
				statusLabel.setText(" 新建文件");
				this.setTitle("无标题 - 记事本");
				isNewFile=true;
				undo.discardAllEdits();//撤消所有的"撤消"操作
				editMenu_Undo.setEnabled(false);
				oldValue=editArea.getText();
			}
		}//新建结束
//*****************************************打开*************************************************************************************************************************
		else if(e.getSource()==fileMenu_Open)
		{	editArea.requestFocus();
			String currentValue=editArea.getText();
			boolean isTextChange=(currentValue.equals(oldValue))?false:true;
			if(isTextChange)
			{	int saveChoose=JOptionPane.showConfirmDialog(this,"您的文件尚未保存，是否保存？","提示",JOptionPane.YES_NO_CANCEL_OPTION);
				if(saveChoose==JOptionPane.YES_OPTION)
				{	String str=null;
					JFileChooser fileChooser=new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					//fileChooser.setApproveButtonText("确定");
					fileChooser.setDialogTitle("另存为");
					int result=fileChooser.showSaveDialog(this);
					if(result==JFileChooser.CANCEL_OPTION)
					{	statusLabel.setText("您没有选择任何文件");
						return;
					}
					File saveFileName=fileChooser.getSelectedFile();
					if(saveFileName==null || saveFileName.getName().equals(""))
					{	JOptionPane.showMessageDialog(this,"不合法的文件名","不合法的文件名",JOptionPane.ERROR_MESSAGE);
					}
					else 
					{	try
						{	FileWriter fw=new FileWriter(saveFileName);
							BufferedWriter bfw=new BufferedWriter(fw);
							bfw.write(editArea.getText(),0,editArea.getText().length());
							bfw.flush();//刷新该流的缓冲
							bfw.close();
							isNewFile=false;
							currentFile=saveFileName;
							oldValue=editArea.getText();
							this.setTitle(saveFileName.getName()+" - 记事本");
							statusLabel.setText("当前打开文件："+saveFileName.getAbsoluteFile());
						}
						catch (IOException ioException)
						{
						}
					}
				}
				else if(saveChoose==JOptionPane.NO_OPTION)
				{	String str=null;
					JFileChooser fileChooser=new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					//fileChooser.setApproveButtonText("确定");
					fileChooser.setDialogTitle("打开文件");
					int result=fileChooser.showOpenDialog(this);
					if(result==JFileChooser.CANCEL_OPTION)
					{	statusLabel.setText("您没有选择任何文件");
						return;
					}
					File fileName=fileChooser.getSelectedFile();
					if(fileName==null || fileName.getName().equals(""))
					{	JOptionPane.showMessageDialog(this,"不合法的文件名","不合法的文件名",JOptionPane.ERROR_MESSAGE);
					}
					else
					{	try
						{	FileReader fr=new FileReader(fileName);
							BufferedReader bfr=new BufferedReader(fr);
							editArea.setText("");
							while((str=bfr.readLine())!=null)
							{	editArea.append(str);
							}
							this.setTitle(fileName.getName()+" - 记事本");
							statusLabel.setText(" 当前打开文件："+fileName.getAbsoluteFile());
							fr.close();
							isNewFile=false;
							currentFile=fileName;
							oldValue=editArea.getText();
						}
						catch (IOException ioException)
						{
						}
					}
				}
				else
				{	return;
				}
			}
			else
			{	String str=null;
				JFileChooser fileChooser=new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				//fileChooser.setApproveButtonText("确定");
				fileChooser.setDialogTitle("打开文件");
				int result=fileChooser.showOpenDialog(this);
				if(result==JFileChooser.CANCEL_OPTION)
				{	statusLabel.setText(" 您没有选择任何文件 ");
					return;
				}
				File fileName=fileChooser.getSelectedFile();
				if(fileName==null || fileName.getName().equals(""))
				{	JOptionPane.showMessageDialog(this,"不合法的文件名","不合法的文件名",JOptionPane.ERROR_MESSAGE);
				}
				else
				{	try
					{	FileReader fr=new FileReader(fileName);
						BufferedReader bfr=new BufferedReader(fr);
						editArea.setText("");
						while((str=bfr.readLine())!=null)
						{	editArea.append(str);
						}
						this.setTitle(fileName.getName()+" - 记事本");
						statusLabel.setText(" 当前打开文件："+fileName.getAbsoluteFile());
						fr.close();
						isNewFile=false;
						currentFile=fileName;
						oldValue=editArea.getText();
					}
					catch (IOException ioException)
					{
					}
				}
			}
		}//打开结束
//**************************************************************保存**********************************************************************************************
		else if(e.getSource()==fileMenu_Save)
		{	editArea.requestFocus();
			if(isNewFile)
			{	String str=null;
				JFileChooser fileChooser=new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				//fileChooser.setApproveButtonText("确定");
				fileChooser.setDialogTitle("保存");
				int result=fileChooser.showSaveDialog(this);
				if(result==JFileChooser.CANCEL_OPTION)
				{	statusLabel.setText("您没有选择任何文件");
					return;
				}
				File saveFileName=fileChooser.getSelectedFile();
				if(saveFileName==null || saveFileName.getName().equals(""))
				{	JOptionPane.showMessageDialog(this,"不合法的文件名","不合法的文件名",JOptionPane.ERROR_MESSAGE);
				}
				else 
				{	try
					{	FileWriter fw=new FileWriter(saveFileName);
						BufferedWriter bfw=new BufferedWriter(fw);
						bfw.write(editArea.getText(),0,editArea.getText().length());
						bfw.flush();//刷新该流的缓冲
						bfw.close();
						isNewFile=false;
						currentFile=saveFileName;
						oldValue=editArea.getText();
						this.setTitle(saveFileName.getName()+" - 记事本");
						statusLabel.setText("当前打开文件："+saveFileName.getAbsoluteFile());
					}
					catch (IOException ioException)
					{
					}
				}
			}
			else
			{	try
				{	FileWriter fw=new FileWriter(currentFile);
					BufferedWriter bfw=new BufferedWriter(fw);
					bfw.write(editArea.getText(),0,editArea.getText().length());
					bfw.flush();
					fw.close();
				}							
				catch(IOException ioException)
				{					
				}
			}
		}//保存结束
//********************************************************************另存为*******************************************************************
		else if(e.getSource()==fileMenu_SaveAs)
		{	editArea.requestFocus();
			String str=null;
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//fileChooser.setApproveButtonText("确定");
			fileChooser.setDialogTitle("另存为");
			int result=fileChooser.showSaveDialog(this);
			if(result==JFileChooser.CANCEL_OPTION)
			{	statusLabel.setText("　您没有选择任何文件");
				return;
			}				
			File saveFileName=fileChooser.getSelectedFile();
			if(saveFileName==null||saveFileName.getName().equals(""))
			{	JOptionPane.showMessageDialog(this,"不合法的文件名","不合法的文件名",JOptionPane.ERROR_MESSAGE);
			}	
			else 
			{	try
				{	FileWriter fw=new FileWriter(saveFileName);
					BufferedWriter bfw=new BufferedWriter(fw);
					bfw.write(editArea.getText(),0,editArea.getText().length());
					bfw.flush();
					fw.close();
					oldValue=editArea.getText();
					this.setTitle(saveFileName.getName()+"  - 记事本");
					statusLabel.setText("　当前打开文件:"+saveFileName.getAbsoluteFile());
				}						
				catch(IOException ioException)
				{					
				}				
			}
		}//另存为结束
		//页面设置
		else if(e.getSource()==fileMenu_PageSetUp)
		{	editArea.requestFocus();
			JOptionPane.showMessageDialog(this,"对不起，【页面设置】该功能超锅暂时无法实现！","提示",JOptionPane.WARNING_MESSAGE);
		}//页面设置结束
		//打印
		else if(e.getSource()==fileMenu_Print)
		{	editArea.requestFocus();
			JOptionPane.showMessageDialog(this,"对不起，【打印】此功能超锅没有资料参考无法实现！","提示",JOptionPane.WARNING_MESSAGE);
		}//打印结束
		//退出
		else if(e.getSource()==fileMenu_Exit)
		{	int exitChoose=JOptionPane.showConfirmDialog(this,"确定要退出吗?","退出提示",JOptionPane.OK_CANCEL_OPTION);
			if(exitChoose==JOptionPane.OK_OPTION)
			{	System.exit(0);
			}
			else
			{	return;
			}
		}//退出结束
		//编辑
		//else if(e.getSource()==editMenu)
		//{	checkMenuItemEnabled();//设置剪切、复制、粘贴、删除等功能的可用性
		//}
		//编辑结束
		//撤销
		else if(e.getSource()==editMenu_Undo || e.getSource()==popupMenu_Undo)
		{	editArea.requestFocus();
			if(undo.canUndo())
			{	try
				{	undo.undo();
				}
				catch (CannotUndoException ex)
				{	System.out.println("Unable to undo:" + ex);
					//ex.printStackTrace();
				}
			}
			if(!undo.canUndo())
				{	editMenu_Undo.setEnabled(false);
				}
		}//撤销结束
		//剪切
		else if(e.getSource()==editMenu_Cut || e.getSource()==popupMenu_Cut)
		{	editArea.requestFocus();
			String text=editArea.getSelectedText();
			StringSelection selection=new StringSelection(text);
			clipBoard.setContents(selection,null);
			editArea.replaceRange("",editArea.getSelectionStart(),editArea.getSelectionEnd());
			checkMenuItemEnabled();//设置剪切，复制，粘帖，删除功能的可用性
		}//剪切结束
		//复制
		else if(e.getSource()==editMenu_Copy || e.getSource()==popupMenu_Copy)
		{	editArea.requestFocus();
			String text=editArea.getSelectedText();
			StringSelection selection=new StringSelection(text);
			clipBoard.setContents(selection,null);
			checkMenuItemEnabled();//设置剪切，复制，粘帖，删除功能的可用性
		}//复制结束
		//粘帖
		else if(e.getSource()==editMenu_Paste || e.getSource()==popupMenu_Paste)
		{	editArea.requestFocus();
			Transferable contents=clipBoard.getContents(this);
			if(contents==null)return;
			String text="";
			try
			{	text=(String)contents.getTransferData(DataFlavor.stringFlavor);
			}
			catch (Exception exception)
			{
			}
			editArea.replaceRange(text,editArea.getSelectionStart(),editArea.getSelectionEnd());
			checkMenuItemEnabled();
		}//粘帖结束
		//删除
		else if(e.getSource()==editMenu_Delete || e.getSource()==popupMenu_Delete)
		{	editArea.requestFocus();
			editArea.replaceRange("",editArea.getSelectionStart(),editArea.getSelectionEnd());
			checkMenuItemEnabled();	//设置剪切、复制、粘贴、删除等功能的可用性	
		}//删除结束
		//查找
		else if(e.getSource()==editMenu_Find)
		{	editArea.requestFocus();
			find();
		}//查找结束
		//查找下一个
		else if(e.getSource()==editMenu_FindNext)
		{	editArea.requestFocus();
			find();
		}//查找下一个结束
		//替换
		else if(e.getSource()==editMenu_Replace)
		{	editArea.requestFocus();
			replace();
		}//替换结束
		//转到
		else if(e.getSource()==editMenu_GoTo)
		{	editArea.requestFocus();
			JOptionPane.showMessageDialog(this,"对不起，此功能尚未实现！","提示",JOptionPane.WARNING_MESSAGE);
		}//转到结束
		//时间日期
		else if(e.getSource()==editMenu_TimeDate)
		{	editArea.requestFocus();
			
			Calendar rightNow=Calendar.getInstance();
			Date date=rightNow.getTime();
			editArea.insert(date.toString(),editArea.getCaretPosition());
		}//时间日期结束
		//全选
		else if(e.getSource()==editMenu_SelectAll || e.getSource()==popupMenu_SelectAll)
		{	editArea.selectAll();
		}//全选结束
		//自动换行(已在前面设置)
		else if(e.getSource()==formatMenu_LineWrap)
		{	if(formatMenu_LineWrap.getState())
				editArea.setLineWrap(true);
			else 
				editArea.setLineWrap(false);

		}//自动换行结束
		//字体设置
		else if(e.getSource()==formatMenu_Font)
		{	editArea.requestFocus();
			font();
		}//字体设置结束
		//设置状态栏可见性
		else if(e.getSource()==viewMenu_Status)
		{	if(viewMenu_Status.getState())
				statusLabel.setVisible(true);
			else 
				statusLabel.setVisible(false);
		}//设置状态栏可见性结束
		//帮助主题
		else if(e.getSource()==helpMenu_HelpTopics)
		{	editArea.requestFocus();
			JOptionPane.showMessageDialog(this,"使用超锅记事本记事本还需要帮助-你逗我！\n超锅记事本- 你的专业记事本。","帮助主题",JOptionPane.INFORMATION_MESSAGE);
		}//帮助主题结束
		//关于
		else if(e.getSource()==helpMenu_AboutNotepad)
		{	editArea.requestFocus();
			JOptionPane.showMessageDialog(this,
				"__________________________________________________\n"+
				" 编写者：超人 \n"+
				" 编写时间：2015-11-20                         \n"+
				" 本人QQ：1359931498                            \n"+
				" e-mail：hc@163.com                \n"+
				" 使用者发现BUG欢迎指出，不胜感激！  \n"+
				"__________________________________________________\n",
				"记事本",JOptionPane.INFORMATION_MESSAGE);
		}//关于结束
	}//方法actionPerformed()结束

	//实现DocumentListener接口中的方法(与撤销操作有关)
	public void removeUpdate(DocumentEvent e)
	{	editMenu_Undo.setEnabled(true);
	}
	public void insertUpdate(DocumentEvent e)
	{	editMenu_Undo.setEnabled(true);
	}
	public void changedUpdate(DocumentEvent e)
	{	editMenu_Undo.setEnabled(true);
	}

	//实现接口UndoableEditListener的类UndoHandler(与撤销操作有关)
	class UndoHandler implements UndoableEditListener
	{	public void undoableEditHappened(UndoableEditEvent uee)
		{	undo.addEdit(uee.getEdit());
		}
	}
	
	public static void main(String args[])
	{	Notepad notepad=new Notepad();
		notepad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setColor(ColorBean colorBean,JTextArea taTitle,JTextArea editArea)
	{
		//{"白","黑","绿","蓝","红"};   //0 1 2 3 4
		Color[] colors={Color.WHITE,Color.BLACK,Color.GREEN,Color.BLUE,Color.RED};
		taTitle.setBackground(colors[0]);
		editArea.setBackground(colors[2]);
		
		taTitle.setForeground(colors[3]);
		editArea.setForeground(colors[3]);	
	}
	
	
	public static boolean isNotEmpty(String str){
		if(!"".equals(str)&&str!=null){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}

