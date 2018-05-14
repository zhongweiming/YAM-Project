package com.yam.client.view;
import java.awt.*;
import javax.swing.*;

import com.yam.client.tools.ClientConServerThread;
import com.yam.client.tools.ManageAddFriends;
import com.yam.client.tools.ManageClientConServerThread;
import com.yam.common.Message;
import com.yam.common.MessageType;

import java.awt.event.*;
import java.io.*;
public class AddFriends extends JFrame implements ActionListener,WindowListener{
	
	//定义界面所需组件
	JPanel jp1,jp2;
	JLabel jlb1;
	JTextField jtf;
	JButton jb1,jb2;
	
	//参数
	String ownerId;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new AddFriends();

	}
	public AddFriends(String ownerId) {
		//创建界面
		this.ownerId = ownerId;
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp1.setLayout(new GridLayout(1,2));
		jlb1 = new JLabel("用户名：    ",JLabel.RIGHT);
		jtf = new JTextField();
		jb1 = new JButton("取消");
		jb1.addActionListener(this);
		jb2 = new JButton("添加");
		jb2.addActionListener(this);
		jp1.add(jlb1);
		jp1.add(jtf);
		jp2.add(jb1);
		jp2.add(jb2);
		this.addWindowListener(this);
		this.getContentPane().add(BorderLayout.CENTER,jp1);
		this.getContentPane().add(BorderLayout.SOUTH,jp2);
		this.setTitle("添加好友");
		this.setVisible(true);
		this.setBounds(400, 200, 250, 100);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1) {
			this.dispose();
		}else if(e.getSource()==jb2) {
			if(jtf.getText().equals(this.ownerId)) {
				JOptionPane.showMessageDialog(this, "不能添加自己为好友！"); 
			}else {
				Message m = new Message();
				m.setSender(this.ownerId);
				m.setMesType(MessageType.message_addfriends_request);
				m.setCon(jtf.getText());
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
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		ManageAddFriends.remove(ownerId);
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
