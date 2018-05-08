package com.test;
import java.util.*;
class Animal{
	
}
class Dog extends Animal{
	
}
public class ListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//由此测试可见，一般情况List以及实现List借口的泛型中的类型必须一致
		//才能相互引用，即使是父类和子类也不行。
		//当然，List和实现其接口的类可以实现多态 。
//		ArrayList<Dog> dogs1 = new ArrayList<Animal>();//不行
//		ArrayList<Animal> animals1 = new ArrayList<Dog>();//不行
//		List<Animal> list = new ArrayList<Animal>();//可以
//		ArrayList<Dog> dogs = new ArrayList<Dog>();
//		ArrayList<Animal> animal = dogs;
//		List<Dog> dogList = dogs;
//		ArrayList<Object> objects = new ArrayList<Object>();
//		List<Object> objList = objects;
//	    ArrayList<Object> objs = new ArrayList<Dog>();
	}

}
