/**
 * 这是一个管理用户聊天界面的类
 */
package com.yam.client.tools;
import java.util.*;

import com.yam.client.view.YamChat;
public class ManageYamChat {
	
	private static HashMap hm = new HashMap<String,YamChat>();
	
	public static HashMap getHm() {
		return hm;
	}
	//加入
	public static void addYamChat(String userAndFriendId,YamChat yamChat)
	{
		hm.put(userAndFriendId, yamChat);
	}
	//移除
	public static void removeYamChat(String userAndFriendId) {
		hm.remove(userAndFriendId);
	}
	//取出
	public static YamChat getYamChat(String userAndFriendId) {
		return (YamChat)hm.get(userAndFriendId);
	}

}
