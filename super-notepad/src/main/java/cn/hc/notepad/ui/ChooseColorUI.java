package cn.hc.notepad.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;

import cn.hc.notepad.bean.ColorBean;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseColorUI extends JFrame {

	private JPanel contentPane;
	
	ColorBean nowcolorBean = new ColorBean();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseColorUI frame = new ChooseColorUI(new ColorBean());
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
	public ChooseColorUI(final ColorBean colorBean) {
		setResizable(false);
		setTitle("\u8BBE\u7F6E\u989C\u8272");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 692, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u6807\u9898\u80CC\u666F\u989C\u8272");
		lblNewLabel.setBounds(10, 55, 107, 37);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u6807\u9898\u5B57\u4F53\u989C\u8272");
		lblNewLabel_1.setBounds(172, 52, 132, 42);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u5185\u5BB9\u80CC\u666F\u989C\u8272");
		lblNewLabel_2.setBounds(382, 52, 120, 42);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u5185\u5BB9\u5B57\u4F53\u989C\u8272");
		lblNewLabel_3.setBounds(534, 50, 132, 47);
		contentPane.add(lblNewLabel_3);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 10, 267, 37);
		contentPane.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(372, 10, 272, 37);
		contentPane.add(textArea_1);
		
		String[] titleColor ={"白","黑","绿","蓝","红"};
		final JComboBox cobTitleBGcolor = new JComboBox(titleColor);
		cobTitleBGcolor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=cobTitleBGcolor.getSelectedIndex();
				colorBean.setTitlebgcolor(3);
				nowcolorBean=colorBean;   //*************************************
				System.out.println(colorBean.getTitlebgcolor());
			}
		});
		cobTitleBGcolor.setBounds(10, 102, 107, 50);
		contentPane.add(cobTitleBGcolor);
		
		String[] TitleFontcolor ={"白","黑","绿","蓝","红"};
		final JComboBox cobTitleFontcolor = new JComboBox(TitleFontcolor);
		cobTitleFontcolor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=cobTitleFontcolor.getSelectedIndex();
				colorBean.setTitlefontcolor(3);
				nowcolorBean=colorBean; //**************************************
				System.out.println(colorBean.getTitlefontcolor());
			}
		});
		cobTitleFontcolor.setBounds(159, 100, 127, 50);
		contentPane.add(cobTitleFontcolor);
		
		String[] ContentBGColor ={"白","黑","绿","蓝","红"};
		final JComboBox cobContentBGColor = new JComboBox(ContentBGColor);
		cobContentBGColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=cobContentBGColor.getSelectedIndex();
				colorBean.setContentbgcolor(3);
				nowcolorBean=colorBean;   //*************************************
				System.out.println(colorBean.getContentbgcolor());
				
			}
		});
		cobContentBGColor.setBounds(382, 102, 107, 50);
		contentPane.add(cobContentBGColor);
		
		final JTextArea tashow = new JTextArea();
		String[] ContentFontColor ={"白","黑","绿","蓝","红"};
		final JComboBox cobContentFontColor = new JComboBox(ContentFontColor);
		cobContentFontColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=cobContentFontColor.getSelectedIndex();
				colorBean.setContentfontcolor(i+1);
				nowcolorBean=colorBean;  //******************************
				System.out.println(colorBean.getContentfontcolor());
			}
		});
		cobContentFontColor.setBounds(524, 102, 120, 50);
		contentPane.add(cobContentFontColor);
		
		JButton btok = new JButton("\u5E94\u7528");
		btok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tashow.setText(colorBean.toString());
				dispose();
			}
		});
		btok.setBounds(430, 261, 214, 67);
		contentPane.add(btok);
		
		JButton btnNewButton_1 = new JButton("\u6062\u590D\u9ED8\u8BA4");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorBean.setContentbgcolor(1);
				colorBean.setContentfontcolor(2);
				colorBean.setTitlebgcolor(1);
				colorBean.setTitlefontcolor(2);
			}
		});
		btnNewButton_1.setBounds(10, 261, 184, 67);
		contentPane.add(btnNewButton_1);
		
		tashow.setBounds(28, 172, 577, 67);
		contentPane.add(tashow);
	}
	
	public ColorBean getColorBean()
	{		
		return nowcolorBean;
	}
}
