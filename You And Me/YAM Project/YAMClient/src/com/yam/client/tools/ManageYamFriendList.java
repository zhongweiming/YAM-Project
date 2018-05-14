/**
 * 管理好友界面类
 */
package com.yam.client.tools;
import java.util.*;

import com.yam.client.view.YamFriendList;

import java.io.*;
public class ManageYamFriendList {
	static HashMap hm = new HashMap<String,YamFriendList>();
	
	//判断是否有该用户的好友列表(即该用户是否在线)
	public static boolean ifOnline(String userId) {
		Iterator it = hm.keySet().iterator();
		while(it.hasNext()) {
			if(it.next().equals(userId)) {
				return true;
			}
		}
		return false;
	}
	public static HashMap getHm() {
		return hm;
	}
	
	public static void addYamFriendList(String userId,YamFriendList yamFriendList) {
		hm.put(userId, yamFriendList);
	}
	public static YamFriendList getYamFriendList(String userId) {
		return (YamFriendList)hm.get(userId);
	}

}
