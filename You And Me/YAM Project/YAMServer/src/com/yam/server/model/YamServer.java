/**
 * 这是YAM服务器，它在监听，等待某个客户端连接
 */
package com.yam.server.model;
import java.net.*;
import java.io.*;
import java.util.*;

import com.yam.common.Message;
import com.yam.common.MessageType;
import com.yam.common.User;
import com.yam.server.db.DBTools;
public class YamServer{
	
	ServerSocket ss;
	public YamServer() {
		try {
			//在9999监听
			System.out.println("我是服务器，在9999监听");
			ss = new ServerSocket(9999);
			//阻塞，等待连接
			while(true) {
				Socket s = ss.accept();
				
				//接收客户端发来的消息
				ObjectInputStream ois1 = new ObjectInputStream(s.getInputStream());
				Message mt = (Message)ois1.readObject();
				if(mt.getMesType().equals(MessageType.message_login_request)) {
					ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
					User u = (User)ois.readObject();
					Message m = new Message();
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					System.out.println("服务器接收到用户ID ："+u.getUserId()+"密码："+u.getPasswd());
					if(DBTools.checkUserLogin(u)&&!ManageServerConClientThread.ifOnline(u.getUserId())) {//如果该用户用户名密码正确并且之前不在线则登录成功
						//返回一个成功登录的信息包
						m.setMesType(MessageType.message_login_succeed);
						oos.writeObject(m);
						
						//单开一个线程，让该线程与该客户端保持通讯
						ServerConClientThread scct = new ServerConClientThread(s);
						ManageServerConClientThread.addClientThread(u.getUserId(), scct);
						//启动与该客户端通讯的线程
						scct.start();
						
						//并通知其他在线用户
						scct.notifyOther(u.getUserId());
					}else {
						m.setMesType(MessageType.message_login_failed);
						oos.writeObject(m);
					}
				}else if(mt.getMesType().equals(MessageType.message_register_request)) {
					ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
					User u = (User)ois.readObject();
					Message m = new Message();
					if(DBTools.checkUserRegister(u)) {
						//如果用户名已经存在，返回一个注册失败的包
						m.setMesType(MessageType.message_register_failed);
						new ObjectOutputStream(s.getOutputStream()).writeObject(m);
					}else {
						//如果用户名不存在，将该用户添加到数据库中，完成注册
						//并且返回一个注册成功的包
						DBTools.UserInsert(u);
						m.setMesType(MessageType.message_register_succeed);
						new ObjectOutputStream(s.getOutputStream()).writeObject(m);
						
					}
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
