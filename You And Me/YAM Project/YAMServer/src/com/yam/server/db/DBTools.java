package com.yam.server.db;
import java.sql.*;

import com.yam.common.*;
public class DBTools {
	public static String sql,sql2,sql3;
	public static SqlHelper sqlHelp,sqlHelp2,sqlHelp3;
	public static ResultSet rs;
	public static boolean checkUserRegister(User u) {
		boolean result = false;
		int i = 0;
		sql = "select username from userpassword";
		sqlHelp = new SqlHelper(sql,SqlHelper.url1);
		try {
			rs = sqlHelp.pst.executeQuery();
			String str = null;
			while(rs.next()) {
				str = rs.getString(1);
				if(u.getUserId().equals(str)) {
					result = true;
					return result;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean checkUserLogin(User u) {
		int i = 0;
		boolean judge = false;
		sql = "select username,password from userpassword";
		sqlHelp = new SqlHelper(sql,SqlHelper.url1);
		try {
			rs = sqlHelp.pst.executeQuery();
			/*String[] str1 = new String[100];
			String[] str2 = new String[100];//假设所有用户数不超过100
			while(rs.next()) {
				str1[i] = rs.getString(1);
				str2[i] = rs.getString(2);
				//System.out.println(str1[i]+"\t"+str2[i]);
				i++;
			}
			for(int j = 0;j < i;j++) {
				if(u.getUserId().equals(str1[j])&&u.getPasswd().equals(str2[j])) {
					judge = true;
				}
			}*/
			String str1 = null;
			String str2 = null;
			while(rs.next()) {
				str1 = rs.getString(1);
				str2 = rs.getString(2);
				if(u.getUserId().equals(str1)&&u.getPasswd().equals(str2)) {
					judge = true;
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return judge;
	}
	//将注册信息插入数据库中
	public static void UserInsert(User u) {
		//插入好友信息中
		sql = "insert into userpassword(username,password) values(?,?)";
		String str = "user"+u.getUserId();
		//创建对应好友列表
		sql2 = "create table "+str+"(username char(12) not null,friendname char(12) not null unique)";
		//创建对应群聊列表
		sql3 = "create table "+str+"(groupname char(16) not null unique,member VARCHAR(100) not null)";
		sqlHelp = new SqlHelper(sql,SqlHelper.url1);
		sqlHelp2 = new SqlHelper(sql2, SqlHelper.url2);
		sqlHelp3 = new SqlHelper(sql3, SqlHelper.url3);
		try {
			sqlHelp.pst.setString(1, u.getUserId());
			sqlHelp.pst.setString(2, u.getPasswd());
			sqlHelp.pst.executeUpdate();
			sqlHelp2.pst.execute(sql2);
			sqlHelp3.pst.execute(sql3);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//从数据库中取出用户好友
	public static String getFriends(String userId) {
		String str = "user"+userId;
		sql = "select friendname from "+str+"";//采用两面两个+号还有双引号可以实现传递表名的功能
		sqlHelp = new SqlHelper(sql,SqlHelper.url2);
		String friends="";
		try {
			//sqlHelp.pst.setString(1, "User1");
			rs = sqlHelp.pst.executeQuery();
			while(rs.next()) {
				friends += rs.getString(1)+" ";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return friends;
	}
	//从数据库中取出用户群聊
	public static String getGroups(String userId) {
		String str = "user"+userId;
		sql = "select groupname from "+str+"";//采用两面两个+号还有双引号可以实现传递表名的功能
		sqlHelp = new SqlHelper(sql,SqlHelper.url3);
		String groups="";
		try {
			//sqlHelp.pst.setString(1, "User1");
			rs = sqlHelp.pst.executeQuery();
			while(rs.next()) {
				groups += rs.getString(1)+" ";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return groups;
	}
	//判断两个用户是否为好友关系
	public static boolean areFriends(String UserId1,String UserId2) {
		boolean result = false;
		String str = "user"+UserId1;
		sql = "select friendname from "+str+"";
		sqlHelp = new SqlHelper(sql,SqlHelper.url2);
		try {
			rs = sqlHelp.pst.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(UserId2)) {
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	//判断该添加用户操作是否合法
	public static boolean checkAddFriend(String userId,String friendId) {
		boolean result = false;
		int flag1 = 0,flag2 = 1;//提供两个判断条件
		//判断想添加的用户是否存在
		sql = "select username from userpassword";
		sqlHelp = new SqlHelper(sql, SqlHelper.url1);
		try {
			rs = sqlHelp.pst.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(friendId)) {
					flag1 = 1;//若存在该用户，则让flag为1
					break;
				}
}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//判断该用户是否已经是好友
		String str = "user"+userId;
		sql2 = "select friendname from "+str+"";
		sqlHelp2 = new SqlHelper(sql2, SqlHelper.url2);
		try {
			rs = sqlHelp2.pst.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(friendId)) {
					flag2 = 0;//若该用户已经是好友，则让flag2为0
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(flag1==1&&flag2==1) {
			result = true;
		}
		return result;
	}
	//将新添加的好友加入到用户好友列表中
	public static void addFriends(String userId,String friendId) {
		//更新用户好友列表
		String str = "user"+userId;
		sql = "insert into "+str+" values(?,?)";
		sqlHelp = new SqlHelper(sql, SqlHelper.url2);
		try {
			sqlHelp.pst.setString(1, userId);
			sqlHelp.pst.setString(2, friendId);
			sqlHelp.pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//同时也要更新好友的好友列表
		String str2 = "user"+friendId;
		sql2 = "insert into "+str2+" values(?,?)";
		sqlHelp2 = new SqlHelper(sql2, SqlHelper.url2);
		try {
			sqlHelp2.pst.setString(1, friendId);
			sqlHelp2.pst.setString(2, userId);
			sqlHelp2.pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		
	}
	}
	//判断群聊创建是否合法
		public static boolean checkAddGroup(String uid,String friends) {
			String[] allFriends = friends.split(",");
			for(int i = 0;i< allFriends.length;i++) {//对每个用户，判断其是否为好友
				if(!areFriends(uid,allFriends[i])) {//只要有一个用户不是好友，群聊不可创建
					return false;
				}
			}
			return true;
		}
	//将群聊插入数据库中
		public static void insertGroup(String groupname,String groupmembers) {
			String[] members = groupmembers.split(",");
			//更新所有用户(包括自己)群聊列表
			for(int i = 0 ;i < members.length;i++) {
				String str = "user"+members[i];
				sql = "insert into "+str+" values(?,?)";
				sqlHelp = new SqlHelper(sql, SqlHelper.url3);
				try {
					sqlHelp.pst.setString(1, groupname);
					sqlHelp.pst.setString(2, groupmembers);
					sqlHelp.pst.executeUpdate();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
       }
		//返回一个群的群成员
		public static String retGroupMembers(String uid, String groupName) {
			String str = "user"+uid;
			String result=null;
			sql = "select member from "+str+" where groupname='"+groupName+"'";
			sqlHelp = new SqlHelper(sql, SqlHelper.url3);
			try {
				rs = sqlHelp.pst.executeQuery();
				rs.next();
				result = rs.getString(1);
				System.out.println(rs.getString(1));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return result;
		}
	//测试用的main函数
//	public static void main(String[] args) {
//		System.out.println(DBTools.getFriends("1"));
//	}

}
