package com.test;
import java.io.IOException;
import java.net.*;
public class UDPClient {
	DatagramSocket ds;
	DatagramPacket dp;
	byte[] bt;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UDPClient();

	}
	public UDPClient() {
		InetAddress serverIP;
		try {
			//发送消息给服务器
			serverIP = InetAddress.getByName("127.0.0.1");
			int serverPort = 8888;
			bt = "我真的好爱你啊，服务器".getBytes();
			dp = new DatagramPacket(bt, bt.length, serverIP, serverPort);
			ds = new DatagramSocket();
			ds.send(dp);
			
			//从服务器接收消息
			byte[] bt2 = new byte[1024];
			DatagramPacket dp2 = new DatagramPacket(bt2, bt2.length);
			ds.receive(dp2);
			String receive = new String(bt2,0,bt2.length);//不能用tostring，有问题。
			System.out.println("我是客户端，从服务器接收到消息："+receive);
			ds.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
