package com.yam.client.view;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;

import com.yam.client.tools.ManageClientConServerThread;
import com.yam.common.*;
public class AddGroup extends JFrame implements ActionListener{

	
	//所需组件
	JPanel jp;
	JLabel jlb1,jlb2;
	JTextField jtf1,jtf2;
	JButton jb1,jb2;
	//参数
	String  ownerId;

	public AddGroup(String ownerId) {
		this.ownerId = ownerId;
		//界面创建
		jp = new JPanel(new GridLayout(3,2));
		jlb1 = new JLabel("名字：",JLabel.RIGHT);
		jlb2 = new JLabel("好友们(用逗号隔开)：",JLabel.RIGHT);
		jtf1 = new JTextField();
		jtf2 = new JTextField();
		jb1 = new JButton("取消");
		jb1.addActionListener(this);
		jb2 = new JButton("创建");
		jb2.addActionListener(this);
		jp.add(jlb1);
		jp.add(jtf1);
		jp.add(jlb2);
		jp.add(jtf2);
		jp.add(jb1);
		jp.add(jb2);
		this.getContentPane().add(BorderLayout.CENTER,jp);
		this.setVisible(true);
		this.setBounds(400, 200, 300, 150);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1) {
			this.dispose();
		}else if(e.getSource()==jb2) {
			Message m = new Message();
			m.setSender(this.ownerId);
			m.setCon(jtf1.getText());//获取群聊名字
			m.setCon2(jtf2.getText());//获取群聊成员
			m.setMesType(MessageType.message_addgroup_request);
			try {
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(this.ownerId).getS().getOutputStream());
				oos.writeObject(m);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
