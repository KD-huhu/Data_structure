package com.KD;

public class Main {

	public static void main(String[] args) {
//		Dynamic_array<Integer> array_int = new Dynamic_array<Integer>();
//		for (int i = 0; i < 10; i++) {
//			array_int.add(i*2);
//		}
//		System.out.println(array_int);
		
		Dynamic_array<Car> array_car = new Dynamic_array<Car>();
		array_car.add(new Car("BMW", "Black", 10));
		array_car.add(new Car("MINI", "Yellow", 1));
		array_car.add(new Car("BENS", "White", 23));
		
		System.out.println(array_car.contains(new Car("BENS", "Red", 1)));
	}

}
