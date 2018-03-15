package cn.hc.notepad.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

public class DBinfoView extends JFrame {

	private JPanel contentPane;
	private JTextField textDriver;
	private JTextField textDBname;
	private JTextField dbUser;
	private JTextField DBpassworld;		
	private static String driverClassName;
	private static String url;
	private static String username;
	private static String password;
	static{
		try {
			InputStream in = DBinfoView.class.getClassLoader().getResourceAsStream("DBinfo.properties");
			Properties props = new Properties();
			props.load(in);
			driverClassName = props.getProperty("driverClassName");
			url = props.getProperty("url");
			username = props.getProperty("username");
			password = props.getProperty("password");
			Class.forName(driverClassName);
		} catch (Exception e) {
			throw new RuntimeException(e);			
		}		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBinfoView frame = new DBinfoView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DBinfoView() {
		setTitle("\u6570\u636E\u5E93\u8BE6\u60C5");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 711, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u6570\u636E\u5E93\u9A71\u52A8\u5730\u5740");
		lblNewLabel.setBounds(10, 20, 118, 40);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u8FDE\u63A5\u7684\u6570\u636E\u5E93");
		lblNewLabel_1.setBounds(10, 70, 102, 46);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u7528\u6237\u540D");
		lblNewLabel_2.setBounds(10, 148, 89, 40);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u5BC6\u7801");
		lblNewLabel_3.setBounds(10, 218, 89, 46);
		contentPane.add(lblNewLabel_3);
		
		textDriver = new JTextField();
		textDriver.setFont(new Font("宋体", Font.PLAIN, 18));
		textDriver.setText(driverClassName);
		textDriver.setBounds(149, 21, 443, 40);
		contentPane.add(textDriver);
		textDriver.setColumns(10);
		
		textDBname = new JTextField();
		textDBname.setFont(new Font("宋体", Font.PLAIN, 18));
		textDBname.setText(url);
		textDBname.setBounds(149, 80, 443, 40);
		contentPane.add(textDBname);
		textDBname.setColumns(10);
		
		dbUser = new JTextField();
		dbUser.setFont(new Font("宋体", Font.PLAIN, 18));
		dbUser.setText(username);
		dbUser.setBounds(149, 150, 443, 38);
		contentPane.add(dbUser);
		dbUser.setColumns(10);
		
		DBpassworld = new JTextField();
		DBpassworld.setFont(new Font("宋体", Font.PLAIN, 18));
		DBpassworld.setText(password);
		DBpassworld.setBounds(149, 222, 443, 40);
		contentPane.add(DBpassworld);
		DBpassworld.setColumns(10);
		
		JButton btnNewButton = new JButton("\u4FEE\u6539\u914D\u7F6E");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, "请确认修改数据库配置是否正确，若配置错误则数据库功能将发生异常");
			}
		});
		btnNewButton.setBounds(432, 450, 174, 40);
		contentPane.add(btnNewButton);
		
		JTextArea tatableinfo = new JTextArea();
		tatableinfo.setForeground(Color.GREEN);
		tatableinfo.setBackground(Color.BLACK);
		tatableinfo.setFont(new Font("Monospaced", Font.PLAIN, 14));
		tatableinfo.setText("             此表配置不可修改 只能查看 \n"+
				"______________________________________________________________\n"+
				"字段     id  | title | content      |  other     |  otherkey \n"
				 +"类型     int | text  | varchar(100) | varchar(50)|  varchar(50)"+
				 "\n______________________________________________________________"+
				"\n \n此信息是帮助使用者快速配置数据库");
		tatableinfo.setEditable(false);
		tatableinfo.setBounds(155, 295, 443, 119);
		contentPane.add(tatableinfo);
		
		JLabel lblNewLabel_4 = new JLabel("\u6570\u636E\u5E93\u8868\u8BE6\u60C5");
		lblNewLabel_4.setBounds(10, 307, 118, 107);
		contentPane.add(lblNewLabel_4);
	}
}
