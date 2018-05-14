package com.yam.client.tools;
import java.util.*;
import com.yam.client.view.*;
public class ManageAddGroup {

    public static HashMap<String,AddGroup> hm= new HashMap<String,AddGroup>();
	
	public static void add(String ownerId, AddGroup addGroup) {
		hm.put(ownerId,addGroup);
	}
	public static AddGroup get(String ownerId) {
		return hm.get(ownerId);
	}
	public static void remove(String ownerId) {
		hm.remove(ownerId);
	}
}
