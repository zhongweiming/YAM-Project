package com.test;
import java.io.IOException;
import java.net.*;
public class UDPServer {
	DatagramSocket ds;
	DatagramPacket dp;
	byte[] buffer;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UDPServer();

	}
	public UDPServer() {
		try {
			ds = new DatagramSocket(8888);
			System.out.println("我是服务器，在8888端口监听...");
			buffer = new byte[1024];
			dp = new DatagramPacket(buffer, buffer.length);
			while(true) {//不断接受从客户端发来的消息
				
				//等待客户端发UDP报文
				ds.receive(dp);
				//解析客户端发来的报文
				String str = new String(buffer,0,buffer.length);
				System.out.println("我是服务器，客户端说："+str);
				
				//获取客户端的ip和端口号
				InetAddress clientIP = dp.getAddress();
				int clientPort = dp.getPort();
				
				//给客户端发送消息
				byte[] b = "I love you,too,Client".getBytes();//厉害厉害，这操作可以
				DatagramPacket dp2 = new DatagramPacket(b, b.length, clientIP,clientPort);
				ds.send(dp2);
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
