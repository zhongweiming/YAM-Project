/**
 * 这是用户注册的界面
 */
package com.yam.client.view;
import java.awt.*;
import javax.swing.*;

import com.yam.client.model.YamClientConServer;
import com.yam.common.Message;
import com.yam.common.User;

import java.awt.event.*;
import java.io.*;

public class YamUserRegister extends JFrame implements ActionListener{
	
	//定义中部需要的组件
	JPanel jp1;
	JLabel jlb1,jlb2,jlb3,jlb4,jlb5,jlb6,jlb7,jlb8,jlb9;
	JTextField jtf1,jtf2,jtf3;
	JPasswordField jpf1,jpf2;
	JButton jb;
	
	//定义南部需要的组件
	JPanel jp2;
	JButton jb1,jb2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new YamUserRegister();

	}
	public YamUserRegister() {
		
		//实现中部界面
		jp1 = new JPanel(new GridLayout(5,3));
		jlb1 = new JLabel("用户名：",JLabel.RIGHT);
		jlb2 = new JLabel("密码：",JLabel.RIGHT);
		jlb3 = new JLabel("确认密码：",JLabel.RIGHT);
		jlb4 = new JLabel("手机/邮箱：",JLabel.RIGHT);
		jlb5 = new JLabel("验证码：",JLabel.RIGHT);
		jlb6 = new JLabel("");
		jlb7 = new JLabel("");
		jlb8 = new JLabel("");
		jlb9 = new JLabel("");
		jb = new JButton("发送");
		jtf1 = new JTextField();
		jtf2 = new JTextField();
		jtf3 = new JTextField();
		jpf1 = new JPasswordField();
		jpf2 = new JPasswordField();
		jp1.add(jlb1);
		jp1.add(jtf1);
		jp1.add(jlb6);
		jp1.add(jlb2);
		jp1.add(jpf1);
		jp1.add(jlb7);
		jp1.add(jlb3);
		jp1.add(jpf2);
		jp1.add(jlb8);
		jp1.add(jlb4);
		jp1.add(jtf2);
		jp1.add(jlb9);
		jp1.add(jlb5);
		jp1.add(jtf3);
		jp1.add(jb);
		this.getContentPane().add(BorderLayout.CENTER, jp1);
		//实现北部，调整中部大小
//		JPanel jp3 = new JPanel();
//		this.getContentPane().add(BorderLayout.NORTH, jp3);
		//实现南部
		jp2 = new JPanel();
		jb1 = new JButton("取消");
		//响应用户点击取消按钮
		jb1.addActionListener(this);
		jb2 = new JButton("注册");
		//实现注册按钮的监听
		jb2.addActionListener(this);
		jp2.add(jb1);
		jp2.add(jb2);
		jp2.setPreferredSize(new Dimension(300,60));
		this.getContentPane().add(BorderLayout.SOUTH,jp2);
		
		//实现框架
		this.setBounds(800, 200, 350, 300);
		this.setTitle("用户注册");
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1) {
			this.dispose();
		}else if(e.getSource()==jb2) {
			if(!((new String(jpf1.getPassword()).equals(new String(jpf2.getPassword()))))){
				JOptionPane.showMessageDialog(this, "两次输入的密码不一致，请重新输入！");
			}else {
				User u = new User();
				u.setUserId(jtf1.getText());
				u.setPasswd(new String(jpf1.getPassword()));
				if(new YamClientConServer().sendRegisterInfoToServer(u)) {
					JOptionPane.showMessageDialog(this, "注册成功！");
				} else {
					JOptionPane.showMessageDialog(this, "注册失败，请换一个用户名试试");
				}
			}
		}
		
	}

}
