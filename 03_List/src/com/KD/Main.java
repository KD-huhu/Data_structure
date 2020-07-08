package com.KD;

import java.awt.geom.CubicCurve2D;
import java.sql.Savepoint;

import com.KD.*;
import circle_list.*;

import single_dir.LinkedList_single_dir;

public class Main {
	
	static void testList(List<Integer> list) {
		list.add(11);
		list.add(22);
		list.add(33);
		list.add(44);
		list.add(0, 55); // [55, 11, 22, 33, 44]
		list.add(2, 66); // [55, 11, 66, 22, 33, 44]
		list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]
		list.remove(0); // [11, 66, 22, 33, 44, 77]
		list.remove(2); // [11, 66, 33, 44, 77]
		list.remove(list.size() - 1); // [11, 66, 33, 44]

		
		System.out.println(list);
	}
	
	public static void main(String[] args) {
//		testList(new LinkedList_single_dir<>());
//		testList(new SingleCircleList<>());
//		testList(new CircleLinkedList<>());
		josephus_priblem solve = new josephus_priblem();
		solve.josephus(8, 3);
	}

}
