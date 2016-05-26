package com.xyan.code.test;

public class Test {
	
	public static void main(String[] args) {
		Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		Integer d = 3;
		Integer e = 321;
		Integer f = 321;
		Long g = 3L;
		
		System.out.println(c==d);
		System.out.println(e==f);
		System.out.println(c==(a+b));
		System.out.println(c.equals((a+b)));
		System.out.println(g==(a+b));
		System.out.println(g.equals((a+b)));
		System.out.println(">>>>>>>>>>>>>");
		Integer aa=1,bb=1;
		System.out.println(aa==bb);
		Integer cc=128,dd=128;
		System.out.println(cc==dd);
	}
}
