/**
 * 这是客户端连接服务器的后台
 */
package com.yam.client.model;
import java.util.*;
import com.yam.client.tools.*;

import com.yam.common.Message;
import com.yam.common.MessageType;
import com.yam.common.User;

import java.net.*;
import java.io.*;
public class YamClientConServer {
	
	public Socket s;
	//发送登录请求
	public boolean sendLoginInfoToServer(Object o) {
		boolean b = false;
		try {
			s = new Socket("127.0.0.1",9999);
			//先给服务器发送一个请求登录的包
			Message m = new Message();
			m.setMesType(MessageType.message_login_request);
			new ObjectOutputStream(s.getOutputStream()).writeObject(m);
			//接下来再发送用户信息
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			Message ms = (Message)ois.readObject();
			//验证用户登录
			if(ms.getMesType().equals(MessageType.message_login_succeed)) {
				b= true;
				//登录成功则创建一个该id与服务器通讯连接的线程
				ClientConServerThread ccst = new ClientConServerThread(s);
				ManageClientConServerThread.addClientConServerThread
				(((User)o).getUserId(), ccst);
				ccst.start();
				
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
		return b;
	}
	//发送注册请求
	public boolean sendRegisterInfoToServer(Object o) {
		boolean a = false;
		try {
			s = new Socket("127.0.0.1",9999);
			//先给服务器发送一个请求注册的包
			Message m = new Message();
			m.setMesType(MessageType.message_register_request);
			new ObjectOutputStream(s.getOutputStream()).writeObject(m);
			//接下来再发送用户信息
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			Message ms = (Message)ois.readObject();
			if(ms.getMesType().equals(MessageType.message_register_succeed)) {
				a = true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return a;
	}

}
