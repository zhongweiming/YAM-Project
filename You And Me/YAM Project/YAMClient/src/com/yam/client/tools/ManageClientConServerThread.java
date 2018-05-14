/**
 * 这是一个管理客户端与服务器保持通讯的线程的类
虽然每个客户端其实就一个，但很有必要，目的是可以通过HashMap调出相应对象
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
	//销毁并删除该线程
	public static void remove(String userId) {
		((ClientConServerThread) hm.get(userId)).interrupt();
		hm.remove(userId);
	}

}
