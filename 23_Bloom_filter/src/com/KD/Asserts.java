package com.KD;

public class Asserts {
	public static void test(boolean value) {
		try {
			if (!value) throw new Exception("Test failed!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
