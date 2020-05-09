package com.kd;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Main {
	
	// Recursive method implementation
	public static int fib1(int n) {
		if (n<=1) return n;
		return fib1(n-1) + fib1(n-2);
	}
	
	
	
	// Use for loop
	public static int fib2(int n) {
		if (n<=1) return n;
		
		int first = 0;
		int second = 1;
		for (int i = 0; i < n-1; i++) {
			int sum = first + second;
			first = second;
			second = sum;
		}
		return second;
	}
	
	// Use while loop
	public static int fib3(int n) {
		if (n<=1) return n;
		
		int first = 0;
		int second = 1;
		while (n-->1) {
			second += first;
			first = second - first;
		}
		return second;
	}
	
	// Solve with linear algebra
	public static int fib4(int n) {
		double c = Math.sqrt(5);
	    return (int)((Math.pow((1+c)/2,n) - Math.pow((1-c)/2,n)) / c);   
	}
	
	
	private static final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss.SSS");
	public interface Task {
		void execute();
	}
	public static void test(String title, Task task) {
		if (task == null) return;
		title = (title == null) ? "" : ("【" + title + "】");
		System.out.println(title);
		System.out.println("开始：" + fmt.format(new Date()));
		long begin = System.currentTimeMillis();
		task.execute();
		long end = System.currentTimeMillis();
		System.out.println("结束：" + fmt.format(new Date()));
		double delta = (end - begin) / 1000.0;
		System.out.println("耗时：" + delta + "秒");
		System.out.println("-------------------------------------");
	}
	
	public static void main(String[] args) {
		int n = 30;
//		System.out.println(fib1(n));
//		System.out.println(fib2(n));
//		System.out.println(fib3(n));
//		System.out.println(fib4(n));
		
		// test time
		test("fib1", new Task() {
			public void execute() {
				System.out.println(fib1(n));
			}
		});
		test("fib2", new Task() {
			public void execute() {
				System.out.println(fib2(n));
			}
		});
		test("fib3", new Task() {
			public void execute() {
				System.out.println(fib3(n));
			}
		});
		test("fib4", new Task() {
			public void execute() {
				System.out.println(fib4(n));
			}
		});
	}
}
