package com.yam.client.tools;

import java.util.HashMap;

import com.yam.client.view.AddFriends;

public class ManageAddFriends {

	public static HashMap<String,AddFriends> hm= new HashMap<String,AddFriends>();
	
	public static void add(String ownerId, AddFriends addFriends) {
		hm.put(ownerId, addFriends);
	}
	public static AddFriends get(String ownerId) {
		return hm.get(ownerId);
	}
	public static void remove(String ownerId) {
		hm.remove(ownerId);
	}
}
