/**
 * 这是一个管理客户端与服务器保持通讯的线程的类
 */
package com.yam.client.tools;
import java.util.*;
public class ManageClientConServerThread {
	static HashMap hm = new HashMap<String,ClientConServerThread>();
	
	public static HashMap getHm() {
		return hm;
	}

	

	//把创建好的ClientConServerThread放入HashMap中
	public static void addClientConServerThread(String userId,ClientConServerThread ccst) {
		hm.put(userId, ccst);
	}
	
	//通过userId返回该线程
	public static ClientConServerThread getClientConServerThread(String userId) {
		return (ClientConServerThread)hm.get(userId);
	}

}
