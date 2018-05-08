package com.yam.client.tools;

import java.util.HashMap;

public class ManageUserState {
	
	public static HashMap<String,Boolean> hm = new HashMap<>();
	
	public static HashMap<String, Boolean> getHm() {
		return hm;
	}
	
	public static void addUserState(String userId,Boolean state) {
		hm.put(userId, state);
	}
	
	public static Boolean getUserState(String userId) {
		return (Boolean)hm.get(userId);
	}

}
