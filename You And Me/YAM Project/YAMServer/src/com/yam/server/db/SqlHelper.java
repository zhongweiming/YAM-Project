//这是一个java连接Mysql数据库的帮助类
package com.yam.server.db;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;  
  
public class SqlHelper {  
    public static final String url1 = "jdbc:mysql://127.0.0.1/UserInfo";  //用户信息数据库
    public static final String url2 = "jdbc:mysql://127.0.0.1/Friends";  //好友列表数据库
    public static final String url3 = "jdbc:mysql://127.0.0.1/groupchat";  //群聊列表数据库
    
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "YAM";  
    public static final String password = "3777116zmdsz";  
  
    public Connection conn;  
    public PreparedStatement pst;
    
    public SqlHelper(String sql,String url) {//由于可能用到不同数据库，所以传入不同的url  
        try {  
            Class.forName(name);//加载jdbc驱动程序
            conn = DriverManager.getConnection(url, user, password);//建立连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {      
    	try {   
    		this.pst.close();  
    		this.conn.close(); 
        } catch (SQLException e) {
        	e.printStackTrace();  
    }  
}  
}   

