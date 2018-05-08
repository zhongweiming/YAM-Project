package com.test;

import java.util.Date;

public class TestFormat {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = String.format("%tA ,%<tB %<td", new Date());
		System.out.println(s);

	}

}
