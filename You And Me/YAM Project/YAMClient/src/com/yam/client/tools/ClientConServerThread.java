/**
 * 这时候客户端与服务端保持通讯的线程
 */
package com.yam.client.tools;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

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
		while(!isInterrupted()) {//非阻塞情况可以通过中断标志终止该线程
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
					//利用返回的好友情况来创建好友界面
					
					YamFriendList yamFriendList = new YamFriendList(m.getGetter(), m);
					ManageYamFriendList.addYamFriendList(m.getGetter(), yamFriendList);
					
				}else if(m.getMesType().equals(MessageType.message_update_friendState)) {
					//如果是更新好友状态的消息
					
					YamFriendList yfl = ManageYamFriendList.getYamFriendList(m.getGetter());
					yfl.updateFriendList(m);
					
				}else if(m.getMesType().equals(MessageType.message_addfriends_succeed)) {
					//如果是添加好友成功的消息
					
					AddFriends ad = ManageAddFriends.get(m.getGetter());
					JOptionPane.showMessageDialog(ad,"添加成功");
					YamFriendList yamFriendList = ManageYamFriendList.getYamFriendList(m.getGetter());
					//调用FriendList中的方法来更新好友列表
					yamFriendList.addFriendUpdate(m.getCon());	
					
				}else if(m.getMesType().equals(MessageType.message_addfriends_failed)) {
					//如果是添加好友失败的消息
					
					AddFriends ad = ManageAddFriends.get(m.getGetter());
					JOptionPane.showMessageDialog(ad,"添加失败");
					
				}else if(m.getMesType().equals(MessageType.message_wasaddfriends_succeed)) {
					//如果是被添加好友成功的消息
					
					YamFriendList yamFriendList = ManageYamFriendList.getYamFriendList(m.getGetter());
					//调用FriendList中的方法来更新好友列表
					yamFriendList.addFriendUpdate(m.getCon());
		
				}else if(m.getMesType().equals(MessageType.message_addgroup_succeed)) {
					//如果是建群成功的消息
					ManageYamFriendList.getYamFriendList(m.getGetter()).addGroupUpdate(m.getCon());
					AddGroup ag = ManageAddGroup.get(m.getGetter());
					JOptionPane.showMessageDialog(ag,"建群成功");
					
					
				}else if(m.getMesType().equals(MessageType.message_addgroup_failed)) {
					//如果是建群失败的消息
					AddGroup ag = ManageAddGroup.get(m.getGetter());
					JOptionPane.showMessageDialog(ag,"建群失败，请检查用户是否都为你的好友");
					
				}else if(m.getMesType().equals(MessageType.message_wasaddgroup)) {
					//如果是被邀请进群的消息
					ManageYamFriendList.getYamFriendList(m.getGetter()).addGroupUpdate(m.getCon());
				}else if(m.getMesType().equals(MessageType.message_groupmember_ret)) {
					String[] allMembers = m.getCon().split(",");
					String[] onlineUsers = m.getCon2().split(" ");
					String groupName = m.getCon3();
					GroupChat groupChat = new GroupChat(m.getGetter(),groupName, allMembers, onlineUsers);
					ManageGroupChat.addGroupChat(m.getGetter(), groupChat);
				}else if(m.getMesType().equals(MessageType.message_group_trans_mes)) {
					GroupChat gc = ManageGroupChat.getGroupChat(m.getGetter());
					gc.messageShow(m);
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

}
