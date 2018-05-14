package com.mutithread.test;


public class MyThread extends Thread{
	private int i;
	public MyThread(int i) {
		this.i = i;
	}
	public void run() {
		System.out.println(i);
	}
}

class MyThread2 extends Thread{
	public void run() {
		System.out.println("MyThread2");
	}
}

class MyThread3 extends Thread{
	public void run() {
		System.out.println("MyThread3");
	}
}
