package com.test;
class Super{
	public void fun1() {
		System.out.println("SuperMan");
	}
	public void fun2() {
		System.out.println("SuperWoman");
	}
}
class Sub extends Super{
	public void fun1(String s) {
		System.out.println(s);
	}
	public void fun2() {
		System.out.println("Children");
	}
}
public class PolymorphicTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Super s = new Sub();
		//s.fun1("love");
		s.fun2();

	}

}
