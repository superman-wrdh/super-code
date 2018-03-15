package cn.hc.notepad.ui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;




import cn.hc.notepad.bean.NoteBean;
import cn.hc.notepad.dao.NoteDao;
import cn.hc.notepad.util.DBUtil;

import java.awt.Font;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;

public class TA extends JFrame {
	static int index=-1;
	private JPanel contentPane;
	JTextArea taselect = new JTextArea();
	DBUtil dbUtil = new DBUtil();
	NoteDao noteDao = new NoteDao();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					TA frame = new TA();
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
	public TA() {
		setResizable(false);
		setTitle("\u9884\u89C8\u6587\u7AE0");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 667, 568);
		contentPane = new JPanel();						
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
//**************************************************************************************************
		String[] color={"红色","绿色","白色"};
		ArrayList<String> al=null;
		try {
			Connection con =dbUtil.con();
			al =noteDao.quarryid(con);
			dbUtil.DBclose(con);
		} catch (Exception e) {
			// TODO: handle exception
		}		
//**************************************************************************************************
		final JComboBox box = new JComboBox(al.toArray());
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taselect.setText("");
				index=box.getSelectedIndex();
				if (index!=-1) {
					//System.out.println();
					taselect.setText("选择了"+(index+1));
					try {
						Connection con =dbUtil.con();
						NoteBean noteBean = noteDao.myQuarry(con, index+1);
						taselect.append(noteBean.getContent());
						dbUtil.DBclose(con);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		box.setBounds(33, 10, 597, 52);
		contentPane.add(box);
		
		JButton btOk = new JButton("\u786E\u5B9A");
		btOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {    			//**********************************************************************************
				
				dispose();
				
			}
		});
		btOk.setFont(new Font("宋体", Font.PLAIN, 15));
		btOk.setBounds(468, 446, 162, 65);
		contentPane.add(btOk);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 72, 597, 351);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(taselect);
		
		taselect.setFont(new Font("Monospaced", Font.PLAIN, 18));
	}
}
