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
	//让该线程通知其他用户自己上线啦
	public void notifyOther(String iAm) {
		//得到所有在线用户的线程
		HashMap hm = ManageServerConClientThread.hm;
		Iterator it = hm.keySet().iterator();
		Message m = new Message();
		m.setSender(iAm);
		m.setMesType(MessageType.message_update_friendState);
		while(it.hasNext()) {
			//取出在线人的线程
			String onLineUserId = it.next().toString();
			//如果该在线用户是其好友，则通知，否则不通知
			if(DBTools.areFriends(iAm, onLineUserId)) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(ManageServerConClientThread.getClientThread(onLineUserId).s.getOutputStream());
					m.setGetter(onLineUserId);
					//加上内容，让客户端区分这是通知好友自己上线的消息
					m.setCon("I'm on");
					oos.writeObject(m);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	//通知好友自己下线啦，并更新好友列表
	public void notifyOtherOff(String iAm) {
		//得到所有在线用户的线程
		HashMap hm = ManageServerConClientThread.hm;
		Iterator it = hm.keySet().iterator();
		Message m = new Message();
		m.setSender(iAm);
		m.setMesType(MessageType.message_update_friendState);
		while(it.hasNext()) {
			//取出在线人的线程
			String onLineUserId = it.next().toString();
			//如果该在线用户是其好友，则通知，否则不通知
			if(DBTools.areFriends(iAm, onLineUserId)) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(ManageServerConClientThread.getClientThread(onLineUserId).s.getOutputStream());
					m.setGetter(onLineUserId);
					//加上内容，让客户端区分这是通知好友自己下线的消息
					m.setCon("I'm off");
					oos.writeObject(m);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void run() {
		while(!isInterrupted()) {
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
					//将其好友状态和群聊传给客户端
					System.out.println(m.getSender()+"要他的好友");
					//把在线的好友和该用户的所有好友给该客户端返回
					//返回好友
					String allFriends = DBTools.getFriends(m.getSender());
					System.out.println(allFriends);
					String onlineFriends = ManageServerConClientThread.getAllOnLineUserId();
					//返回群聊
					String allGroups = DBTools.getGroups(m.getSender());
					System.out.println("用户"+m.getSender()+"所有群聊"+allGroups);
					Message m2 = new Message();
					m2.setMesType(MessageType.message_ret_onLineFriend);
					m2.setCon(allFriends+","+onlineFriends);
					m2.setCon2(allGroups);
					m2.setGetter(m.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m2);
				}else if(m.getMesType().equals(MessageType.message_addfriends_request)) {
					Message m1 = new Message();
					m1.setGetter(m.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					if(DBTools.checkAddFriend(m.getSender(),m.getCon())){//如果该添加好友请求合法
						if(ManageServerConClientThread.ifOnline(m.getCon())) {//如果被添加的好友在线，通过与好友通讯线程修改其好友列表
							m1.setCon(m.getCon()+","+"true");
							Message m2 = new Message();
							m2.setMesType(MessageType.message_wasaddfriends_succeed);
							m2.setGetter(m.getCon());
							m2.setCon(m.getSender()+","+"true");
							new ObjectOutputStream(ManageServerConClientThread.getClientThread(m.getCon()).s.getOutputStream()).writeObject(m2);;
						}else {
							m1.setCon(m.getCon()+","+"false");
						}
						
						m1.setMesType(MessageType.message_addfriends_succeed);//返回成功信息
						oos.writeObject(m1);
						//调用数据库插入好友方法
						DBTools.addFriends(m.getSender(), m.getCon());
					}else {
						m1.setMesType(MessageType.message_addfriends_failed);
						oos.writeObject(m1);
					}
				}else if(m.getMesType().equals(MessageType.message_userexit)) {//用户下线
					//先通知好友自己下线了
					this.notifyOtherOff(m.getSender());
					ManageServerConClientThread.remove(m.getSender());
				}else if(m.getMesType().equals(MessageType.message_addgroup_request)) {
					Message m1 = new Message();
					m1.setGetter(m.getSender());
					m1.setCon(m.getCon());//传回群聊名
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					
					if(DBTools.checkAddGroup(m.getSender(), m.getCon2())){
						//如果验证成功，将该群聊插入数据库中，并且返回成功信息
						DBTools.insertGroup(m.getCon(),m.getSender()+","+m.getCon2());//第二个参数为所有群成员包括自己
						
						//判断群聊中其他成员是否在线
						String[] friends = m.getCon2().split(",");
						for(int i = 0;i < friends.length;i++) {
							if(ManageServerConClientThread.ifOnline(friends[i])) {//如果该好友在线，立马更新其群聊列表
								Message m2 = new Message();
								m2.setGetter(friends[i]);
								m2.setMesType(MessageType.message_wasaddgroup);
								m2.setCon(m.getCon());//传回去群聊名
								new ObjectOutputStream(ManageServerConClientThread.getClientThread(friends[i]).s.getOutputStream()).writeObject(m2);
								
							}
						}
						m1.setMesType(MessageType.message_addgroup_succeed);
						oos.writeObject(m1);
						
					}else {
						m1.setMesType(MessageType.message_addgroup_failed);
						oos.writeObject(m1);
					}
				}else if(m.getMesType().equals(MessageType.message_groupmember_request)) {
					//获取群成员
					String members = DBTools.retGroupMembers(m.getSender(), m.getCon());
					//获取在线用户
					String onlineFriends = ManageServerConClientThread.getAllOnLineUserId();
					Message m1 = new Message();
					m1.setMesType(MessageType.message_groupmember_ret);
					m1.setGetter(m.getSender());
					m1.setCon(members);
					m1.setCon2(onlineFriends);
					m1.setCon3(m.getCon());//传回群名
					new ObjectOutputStream(this.s.getOutputStream()).writeObject(m1);;
					
				}else if(m.getMesType().equals(MessageType.message_group_mes)) {
					//如果是群聊消息，完成转发
					String[] toUser = m.getGetter().split(" ");
					for(int i = 0;i < toUser.length;i++) {
						Message m1 = new Message();
						m1.setSender(m.getSender());
						m1.setSendTime(m.getSendTime());
						m1.setGetter(toUser[i]);
						m1.setMesType(MessageType.message_group_trans_mes);
						m1.setCon(m.getCon());
						new ObjectOutputStream(ManageServerConClientThread.getClientThread(toUser[i]).s.getOutputStream()).writeObject(m1);		
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
	}

}
