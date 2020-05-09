package com.KD;

public class Car {
	private String name;
	private String color;
	private int age;
	
	public Car(String name, String color, int age) {
		super();
		this.name = name;
		this.color = color;
		this.age = age;
	}
	
	
	@Override
	public String toString() {
		return "Car [name=" + name + ", color=" + color + ", age=" + age + "]";
	}


	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("Car - finalize!");
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		// Check is obj belongs to Car
		if (obj instanceof Car) {
			// Get the parameter
			Car new_carCar = (Car) obj;
			// If they have the same name, they are equal
			return this.name == new_carCar.name;
		}
		return false;
	}
}
