package com.test;

import java.util.Scanner;

public class GBKTest {

	public static boolean isChinese(char c) {  
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断  
    }  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int count = 0;
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		for(char c:str.toCharArray()) {
			if(isChinese(c)) {
				count ++;
				System.out.print(c);
			}
		}
		System.out.println("");
		System.out.println("一共有"+count+"个汉字");

	}

}
