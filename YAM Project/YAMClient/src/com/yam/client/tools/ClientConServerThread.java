/**
 * 这时候客户端与服务端保持通讯的线程
 */
package com.yam.client.tools;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;

import com.yam.client.view.*;
import com.yam.common.Message;
import com.yam.common.MessageType;
public class ClientConServerThread extends Thread{
	private Socket s;
	public boolean judge = false;

	public Socket getS() {
		return s;
	}

	//构造函数
	public ClientConServerThread(Socket s) {
		this.s = s;
	}
	public void run() {
		while(true) {
			//不停读取从服务器端发来的消息
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message)ois.readObject();
				//System.out.println("从服务器读取到消息："+m.getSender()+"给"+m.getGetter()+"消息："+m.getCon());
				if(m.getMesType().equals(MessageType.message_comm_mes)) {
					//把从服务器获得的消息显示到该聊天界面
					YamChat qqChat = ManageYamChat.getYamChat(m.getGetter()+" "+m.getSender());
					//显示出来
					qqChat.messageShow(m);
				}else if(m.getMesType().equals(MessageType.message_ret_onLineFriend)) {
					/*String con = m.getCon();
					String friends[] = con.split(" ");
					String getter = m.getGetter();
					//修改相应的好友列表
					YamFriendList yamFriendList = ManageYamFriendList.getYamFriendList(getter);
					//System.out.println(m.getSender());
					//更新在线好友
					yamFriendList.updateFriendList(m);*/
					
					//如果是通知好友自己上线的消息，则更改好友状态
					if(m.getCon().equals("I'am on")) {
						YamFriendList yfl = ManageYamFriendList.getYamFriendList(m.getGetter());
						yfl.updateFriendList(m.getSender());
					}else {
						//利用返回的好友情况来创建好友界面
						YamFriendList yamFriendList = new YamFriendList(m.getGetter(), m);
						ManageYamFriendList.addYamFriendList(m.getGetter(), yamFriendList);
					}
				}else if(m.getMesType().equals(MessageType.message_addfriends_succeed)) {
					this.judge = true;
					YamFriendList yamFriendList = ManageYamFriendList.getYamFriendList(m.getGetter());
					//调用FriendList中的方法来更新好友列表
					yamFriendList.addFriendUpdate(m.getCon());
					
					//同时如果对方在线的话更新对方好友列表
					HashMap<String,YamFriendList> hm = ManageYamFriendList.getHm();
					Iterator it = hm.keySet().iterator();
					while(it.hasNext()) {
						if(it.next().toString().equals(m.getCon())){
							//更新对方好友列表
							YamFriendList yamFriendList2 = ManageYamFriendList.getYamFriendList(m.getCon());
							yamFriendList2.addFriendUpdate(m.getGetter());
							
						}
					}
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

}
