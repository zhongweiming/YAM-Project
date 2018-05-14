package com.yam.client.view;
import javax.swing.*;
import java.util.*;

import com.yam.client.tools.ManageClientConServerThread;
import com.yam.common.Message;
import com.yam.common.MessageType;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
public class GroupChat extends JFrame implements ActionListener,KeyListener{

	//定义界面所需组件
	
	//南部
	JPanel jp1;
	JTextField jtf;
	JButton jb;
	
	//西部
	JTextArea jta;
	JScrollPane jsp;
	//东部
	JPanel jp2;
	JLabel[] jlb = new JLabel[100];
	JScrollPane jsp2;
	
	JPanel jp3;
	int number;
	String userId;
	String onlineFriends="";
	//把消息显示在屏幕上
	public void messageShow(Message m) {
		String info = m.getSendTime()+"  "+m.getSender()+"说："+m.getCon()+"\r\n";//目的是让自己发送的也能显示在TextArea上
		this.jta.append(info);
	}
	public GroupChat(String uid,String groupName, String[] allMembers,String[] onlineUsers) {
		this.userId = uid;
		//南部
		jp1 = new JPanel();
		jtf = new JTextField(20);
		jtf.addKeyListener(this);
		jb = new JButton("发送");
		jb.addActionListener(this);
		jp1.add(jtf);
		jp1.add(jb);
		
		//西部
		jta = new JTextArea();
		jsp = new JScrollPane(jta);
		
		//东部
		this.number = allMembers.length;
		jp2 = new JPanel(new GridLayout(100,1,5,5));
		jlb[0] = new JLabel("群成员",JLabel.CENTER);
		jlb[0].setEnabled(true);
		jp2.add(jlb[0]);
		for(int i=1;i <= allMembers.length;i++) {
			jlb[i] = new JLabel(allMembers[i-1],new ImageIcon("images/touxiang.jpeg"),JLabel.LEFT);
			jlb[i].setEnabled(false);
			jp2.add(jlb[i]);
		}
		for(int i = 1;i <= allMembers.length;i++) {
        	for(int j = 1;j <= onlineUsers.length;j++) {
        		if(allMembers[i-1].equals(onlineUsers[j-1])) {
        			jlb[i].setEnabled(true);
        			this.onlineFriends+=jlb[i].getText()+" ";
        		}
        	}
		}
		jsp2 = new JScrollPane(jp2);
		
		jp3 = new JPanel(new GridLayout(1,2));
		//jp3 = new JPanel(new BorderLayout(10,0));
		//JPanel jp4 = new JPanel();
		//jp3.add(BorderLayout.CENTER,jsp);
		//jp3.add(BorderLayout.EAST, jsp2);
		jp3.add(jsp);
		jp3.add(jsp2);
		//添加到主框架中
		this.getContentPane().add(BorderLayout.SOUTH, jp1);

		this.getContentPane().add(BorderLayout.CENTER, jp3);
//		this.getContentPane().add(BorderLayout.WEST, jsp);
//		this.getContentPane().add(BorderLayout.EAST, jp2);
		
		//设置框架
		this.setVisible(true);
		this.setBounds(300,200,600,400);
		this.setTitle(groupName);
		jtf.requestFocus();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb) {
			//发送消息
			Message m = new Message();
			m.setSender(this.userId);
			m.setGetter(this.onlineFriends);
			m.setCon(jtf.getText());
			Date dd = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			m.setSendTime(sdf.format(dd));
			m.setMesType(MessageType.message_group_mes);
			jtf.setText("");
			jtf.requestFocus();
			//发送给服务器
			try {
				ObjectOutputStream oos = new ObjectOutputStream
						(ManageClientConServerThread.getClientConServerThread(this.userId).getS().getOutputStream());
				oos.writeObject(m);
				//String info = m.getSendTime()+"  "+m.getSender()+"对"+m.getGetter()+"说："+m.getCon()+"\r\n";//目的是让自己发送的也能显示在TextArea上
				//this.jta.append(info);//将自己发送的消息也能显示到聊天界面中
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			//回车发送消息
			Message m = new Message();
			m.setSender(this.userId);
			m.setGetter(this.onlineFriends);
			m.setCon(jtf.getText());
			Date dd = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			m.setSendTime(sdf.format(dd));
			m.setMesType(MessageType.message_group_mes);
			jtf.setText("");
			jtf.requestFocus();
			//发送给服务器
			try {
				ObjectOutputStream oos = new ObjectOutputStream
						(ManageClientConServerThread.getClientConServerThread(this.userId).getS().getOutputStream());
				oos.writeObject(m);
				//String info = m.getSendTime()+"  "+m.getSender()+"对"+m.getGetter()+"说："+m.getCon()+"\r\n";//目的是让自己发送的也能显示在TextArea上
				//this.jta.append(info);//将自己发送的消息也能显示到聊天界面中
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
