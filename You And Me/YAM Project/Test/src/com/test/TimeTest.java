package com.test;
import java.text.SimpleDateFormat;
import java.util.*;
public class TimeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Date dd = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String dt = sdf.format(dd);
		System.out.println(dt);
	}

}
