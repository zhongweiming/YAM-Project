package com.yam.client.tools;

import java.util.HashMap;

import com.yam.client.view.*;;

public class ManageGroupChat {

    private static HashMap hm = new HashMap<String,GroupChat>();
	
	public static HashMap getHm() {
		return hm;
	}
	//加入
	public static void addGroupChat(String userId,GroupChat groupChat)
	{
		hm.put(userId, groupChat);
	}
	//移除
	public static void removeGroupChat(String userId) {
		hm.remove(userId);
	}
	//取出
	public static GroupChat getGroupChat(String userId) {
		return (GroupChat)hm.get(userId);
	}
}
