package com.test2;
import java.net.*;
import java.io.*;
import com.common.User;
public class MyClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyClient mc = new MyClient();

	}
	public MyClient() {
		try {
			Socket s = new Socket("127.0.0.1",3456);
			//通过ObjectOutputStream给服务器传送对象
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			User u = new User();
			u.setName(" 钟伟明");
			u.setPass("123456");
			Thread.sleep(5000);//可以先睡眠5秒钟，哈哈
			oos.writeObject(u);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
