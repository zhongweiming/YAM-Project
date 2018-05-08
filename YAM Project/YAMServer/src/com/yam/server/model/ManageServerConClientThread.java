package com.yam.server.model;
import java.util.*;
public class ManageServerConClientThread {
	
	public static HashMap hm = new HashMap<String,ServerConClientThread>();
	
	//向hm中添加一个客户端通讯线程
	public static void addClientThread(String uid,ServerConClientThread sct) {
		hm.put(uid, sct);
	}
	public static ServerConClientThread getClientThread(String uid) {
		return (ServerConClientThread)hm.get(uid);
	}
	//返回当前在线的人的情况
	public static String getAllOnLineUserId() {
		//使用迭代器
		Iterator it = hm.keySet().iterator();
		String res = "";
		while(it.hasNext()) {
			res += it.next().toString()+" ";
		}
		return res;
	}

}
