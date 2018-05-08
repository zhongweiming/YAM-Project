/**
 * 功能：服务器和某个客户端的通讯线程
 */
package com.yam.server.model;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.io.*;

import com.yam.common.Message;
import com.yam.common.MessageType;
import com.yam.server.db.DBTools;

import java.io.*;
public class ServerConClientThread extends Thread{
	Socket s;
	
	public ServerConClientThread(Socket s) {
		//把服务器和该客户端的连接赋给s
		this.s= s;
	}
	//让该线程通知其他用户
	public void notifyOther(String iAm) {
		//得到所有在线用户的线程
		HashMap hm = ManageServerConClientThread.hm;
		Iterator it = hm.keySet().iterator();
		Message m = new Message();
		m.setSender(iAm);
		m.setMesType(MessageType.message_ret_onLineFriend);
		while(it.hasNext()) {
			//取出在线人的线程
			String onLineUserId = it.next().toString();
			//如果该在线用户是其好友，则通知，否则不通知
			if(DBTools.areFriends(iAm, onLineUserId)) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(ManageServerConClientThread.getClientThread(onLineUserId).s.getOutputStream());
					m.setGetter(onLineUserId);
					//加上内容，让客户端区分这是通知好友自己上线的消息
					m.setCon("I'am on");
					oos.writeObject(m);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	//从数据库中取出用户所有好友
	public String getUserFriends(String UserId) {
		return DBTools.getFriends(UserId);
	}
	
	public void run() {
		while(true) {
			//这里该线程就可以接收客户端的信息
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message)ois.readObject();
				
				//System.out.println(m.getSender()+"给"+m.getGetter()+"说"+m.getCon());
				
				//对从客户端取得的message进行类型判断，然后做相应处理
				if(m.getMesType().equals(MessageType.message_comm_mes)) {
					//完成转发
					//取得接收人的通讯线程
					ServerConClientThread scct = ManageServerConClientThread.getClientThread(m.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(scct.s.getOutputStream());
					oos.writeObject(m);
				}else if(m.getMesType().equals(MessageType.message_get_onLineFriend)) {
					System.out.println(m.getSender()+"要他的好友");
					//把在线的好友和该用户的所有好友给该客户端返回
					String allFriends = this.getUserFriends(m.getSender());
					System.out.println(allFriends);
					String onlineFriends = ManageServerConClientThread.getAllOnLineUserId();
					Message m2 = new Message();
					m2.setMesType(MessageType.message_ret_onLineFriend);
					m2.setCon(allFriends+","+onlineFriends);
					m2.setGetter(m.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m2);
				}else if(m.getMesType().equals(MessageType.message_addfriends_request)) {
					Message m1 = new Message();
					m1.setGetter(m.getSender());
					m1.setCon(m.getCon());
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					if(DBTools.checkAddFriend(m.getSender(),m.getCon())){//如果该添加好友请求合法
						m1.setMesType(MessageType.message_addfriends_succeed);//返回成功信息
						oos.writeObject(m1);
						//调用数据库插入好友方法
						DBTools.addFriends(m.getSender(), m.getCon());
					}else {
						m1.setMesType(MessageType.message_addfriends_failed);
						oos.writeObject(m1);
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
	}

}
