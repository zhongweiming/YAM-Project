/**
 * 管理好友界面类
 */
package com.yam.client.tools;
import java.util.*;

import com.yam.client.view.YamFriendList;

import java.io.*;
public class ManageYamFriendList {
	static HashMap hm = new HashMap<String,YamFriendList>();
	
	public static void show() {
		Iterator it = hm.keySet().iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
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
