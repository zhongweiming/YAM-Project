/**
 * 这是服务器端的控制界面，可以完成启动服务器，关闭服务器，管理监控用户
 */
package com.yam.server.view;
import javax.swing.*;

import com.yam.server.model.YamServer;

import java.awt.*;
import java.awt.event.*;

public class MyServerFrame extends JFrame implements ActionListener{
	
	JPanel jp1;
	JButton jb1,jb2;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyServerFrame serverFrame = new MyServerFrame();

	}
	public MyServerFrame() {
		jp1 = new JPanel();
		jb1 = new JButton("启动服务器");
		jb1.addActionListener(this);
		jb2 = new JButton("关闭服务器");
		jb2.addActionListener(this);
		jp1.add(jb1);
		jp1.add(jb2);
		
		this.getContentPane().add(BorderLayout.NORTH,jp1);
		this.setBounds(300, 200, 600, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1) {
			YamServer ys = new YamServer();
		}else if(e.getSource()==jb2) {
			
		}
		
	}

}
