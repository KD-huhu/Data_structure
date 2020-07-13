package com.KD;

import com.KD.tools.Asserts;

public class Main {

	public static void main(String[] args) {
//		Asserts.test(BruteForce.indexOf2("Hello world", "H") == 0);
//		Asserts.test(BruteForce.indexOf2("Hello world", "d") == 10);
//		Asserts.test(BruteForce.indexOf2("Hello world", "or") == 7);
//		Asserts.test(BruteForce.indexOf2("Hello world", "abc") == -1);
//		Asserts.test(BruteForce.indexOf3("Hello world", "H") == 0);
//		Asserts.test(BruteForce.indexOf3("Hello world", "d") == 10);
//		Asserts.test(BruteForce.indexOf3("Hello world", "or") == 7);
//		Asserts.test(BruteForce.indexOf3("Hello world", "abc") == -1);
		Asserts.test(KMP.indexOf1("Hello world", "H") == 0);
		Asserts.test(KMP.indexOf1("Hello world", "d") == 10);
		Asserts.test(KMP.indexOf1("Hello world", "or") == 7);
		Asserts.test(KMP.indexOf1("Hello world", "abc") == -1);
		
	}

}
