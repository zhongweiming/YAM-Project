package com.yam.server.db;
import java.sql.*;
public class Demo {
	static String sql;
	static SqlHelper sh;
	static ResultSet ret;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sql = "select * from UserPassword";
		sh = new SqlHelper(sql,SqlHelper.url1);
		
		try {
			ret = sh.pst.executeQuery();
			while(ret.next()) {
				String userName = ret.getString(1);//取得第一列的意思
				String password = ret.getString(2);
				System.out.println(userName+"\t"+password);
			}
			ret.close();
			sh.close();//关闭连接
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
