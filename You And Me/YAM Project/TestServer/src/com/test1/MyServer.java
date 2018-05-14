package com.test1;
import java.net.*;
import java.io.*;
import com.common.*;
public class MyServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyServer ms = new MyServer();

	}
	public MyServer() {
		try {
			System.out.println("在3456端口监听..");
			ServerSocket ss = new ServerSocket(3456);
			Socket s = ss.accept();
			//以对象流方式读取
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			User u = (User)ois.readObject();
			
			//输出
			System.out.println("从客户端接收到"+u.getName()+"  "+u.getPass());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
