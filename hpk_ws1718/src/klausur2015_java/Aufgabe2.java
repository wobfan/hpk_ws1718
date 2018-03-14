package klausur2015_java;

import java.util.*;

class Vehicle {}
class Car extends Vehicle {}
class Boat extends Vehicle {};

@SuppressWarnings("unused")
public class Aufgabe2 {
	public static void a1() {
		List v0 = new ArrayList();
		List v1 = new ArrayList<Vehicle>();
		List<Vehicle> v2 = new ArrayList<Vehicle>();
		List<Car> v3 = new ArrayList<Vehicle>();
		List<Vehicle> v4 = new ArrayList<Car>();
		List<?> v5 = new ArrayList<Car>();
		List<Car> v6 = new ArrayList<?>;
	} 
	
	public static void a2() {
		List<Vehicle> v = new ArrayList<Vehicle>();
		v.add(new Boat());
		v.add(new Car());
		v.forEach(s->System.out.println(s));
		Boat b = (Boat) v.get(0);
		Vehicle c = v.get(1);
	}
	
	public static void a3() {
		List<?> v = new ArrayList<Vehicle>();
		v.add(new Boat());
		v.add(new Car());
		Boat b = (Boat) v.get(0);
		Car c = (Car) v.get(1);
	}
	
	public static void a4() {
		List<? extends Vehicle> v1 = new ArrayList<Vehicle>();
		List<? super Vehicle> v2 = new ArrayList<Vehicle>();
		v1.add(new Boat());
		v2.add(new Car());
		Vehicle b = v1.get(0);
		Car c = (Car) v2.get(0);
	}
	
	public static void a5() {
		Vehicle v1[] = new Vehicle[10];
		Vehicle v2[] = new Car[10];
		v1[0] = new Boat();
		v1[1] = new Car();
		Vehicle b1 = v1[0];
		Car c1 = (Car) v1[1];
		v2[0] = new Boat();
		v2[1] = new Car();
		Boat b2 = (Boat) v2[0];
		Car c2 = (Car) v2[1];
	}
	
	public static void main(String[] args) {
		a2();
	}
}
