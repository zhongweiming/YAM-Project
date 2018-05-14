/**
 * 与好友聊天界面
 * 由于客户端要一直处于读取状态，因此把它做成一个线程
 */
package com.yam.client.view;
import javax.swing.*;

import com.yam.client.model.YamClientConServer;
import com.yam.client.tools.ManageClientConServerThread;
import com.yam.client.tools.ManageYamChat;
import com.yam.common.Message;
import com.yam.common.MessageType;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class YamChat extends JFrame implements ActionListener,WindowListener,KeyListener{
	
	JTextField jtf;
	JTextArea jta;
	JButton jb;
	JPanel jp;
	String ownerId;
	String friendId;
	JScrollPane jsp;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//YamChat chat = new YamChat("1");

	}
	public YamChat(String ownerId,String friend) {
		this.ownerId = ownerId;
		this.friendId = friend;
		jta = new JTextArea();
		jsp = new JScrollPane(jta);
		jtf = new JTextField(18);
		jb = new JButton("发送");
		jb.addActionListener(this);//第一个表示监听对象(事件源)，第二个(参数)表示要将这个事件对象传给谁(实现了监听接口的类对象)
		jtf.addKeyListener(this);//要想回车发消息，监听文本框，为什么里？
		jp = new JPanel();
		jp.add(jtf);
		jp.add(jb);
		this.addWindowListener(this);
		this.getContentPane().add(BorderLayout.CENTER, jsp);
		this.getContentPane().add(BorderLayout.SOUTH, jp);
		this.setBounds(300,200,500,300);
		this.setTitle(ownerId+"正在和"+friend+"聊天");
		this.setVisible(true);
		jta.setEditable(false);
		jtf.requestFocus();//设置光标停留在输入框
		//!!注意：要放在最后才有效，等到框架设置好后设置焦点才行
	}
	//一个方法，用来显示消息
	public void messageShow(Message m) {
		String info = m.getSendTime()+"  "+m.getSender()+"对"+m.getGetter()+"说："+m.getCon()+"\r\n";//目的是让自己发送的也能显示在TextArea上
		this.jta.append(info);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb) {
			//如果用户点击了发送按钮
			Message m = new Message();
			m.setSender(this.ownerId);
			m.setGetter(this.friendId);
			m.setCon(jtf.getText());
			m.setSendTime(new java.util.Date().toString());
			m.setMesType(MessageType.message_comm_mes);
			jtf.setText("");
			jtf.requestFocus();
			//发送给服务器
			try {
				ObjectOutputStream oos = new ObjectOutputStream
						(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
				oos.writeObject(m);
				String info = m.getSendTime()+"  "+m.getSender()+"对"+m.getGetter()+"说："+m.getCon()+"\r\n";//目的是让自己发送的也能显示在TextArea上
				this.jta.append(info);//将自己发送的消息也能显示到聊天界面中
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		while(true){
//			try {
//				//从自己端口读取服务器转发过来的消息(读不到会一直等待）
//				ObjectInputStream ois = new ObjectInputStream(YamClientConServer.s.getInputStream());
//				Message m = (Message)ois.readObject();
//				/*
//				 * ！！！会出现一个问题,当打开两个对话窗口时，会争夺这个socket，导致其中一个窗口无法接收。
//				 */
//				//显示
//				String info = m.getSendTime()+"  "+m.getSender()+"对"+m.getGetter()+"说："+m.getCon()+"\r\n";
//				this.jta.append(info);
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//		
//	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

		ManageYamChat.removeYamChat(ownerId+" "+friendId);
		System.out.println("窗口关闭啦");
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
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			//回车默认发送消息
			Message m = new Message();
			m.setSender(this.ownerId);
			m.setGetter(this.friendId);
			m.setCon(jtf.getText());
			m.setSendTime(new java.util.Date().toString());
			m.setMesType(MessageType.message_comm_mes);
			jtf.setText("");
			jtf.requestFocus();
			//发送给服务器
			try {
				ObjectOutputStream oos = new ObjectOutputStream
						(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
				oos.writeObject(m);
				String info = m.getSendTime()+"  "+m.getSender()+"对"+m.getGetter()+"说："+m.getCon()+"\r\n";//目的是让自己发送的也能显示在TextArea上
				this.jta.append(info);//将自己发送的消息也能显示到聊天界面中
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
