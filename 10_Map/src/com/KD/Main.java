package com.KD;

import com.KD.Map.Map;
import com.KD.Map.Map.Visitor;
import com.KD.Map.TreeMap;
import com.KD.SetbyMap.MapSet;
import com.KD.SetbyMap.Set;

public class Main {

	static void test1() {
		Map<String, Integer> map = new TreeMap<>();
		map.put("c", 2);
		map.put("a", 5);
		map.put("b", 6);
		map.put("a", 8);
		
		map.traversal(new Visitor<String, Integer>() {
			public boolean visit(String key, Integer value) {
				System.out.println(key + "_" + value);
				return false;
			}
		});
	}
	
	static void test2() {
		Set<String> set = new MapSet<>();
		set.add("c");
		set.add("b");
		set.add("c");
		set.add("c");
		set.add("a");
		
		set.traversal(new Set.Visitor<String>() {
			public boolean visit(String element) {
				System.out.println(element);
				return false;
			}
		});
	}

	
	public static void main(String[] args) {
		test2();
	}

}
