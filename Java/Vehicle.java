public class Vehicle {
// Public data members
public String brand;
public String model;
// Private data members
private double price;
private double mileage;
private int speed;
private String fuelType;
// ===== GETTERS AND SETTERS =====
public void setPrice(double price) {
this.price = price;
}
public double getPrice() {
return price;
}
public void setMileage(double mileage) {
this.mileage = mileage;
}
public double getMileage() {
return mileage;
}
public void setSpeed(int speed) {
this.speed = speed;
}
public int getSpeed() {
return speed;
}
public void setFuelType(String fuelType) {
this.fuelType = fuelType;
}
public String getFuelType() {
return fuelType;
}
// ===== CONSTRUCTORS =====
// Default Constructor
public Vehicle() {
this.brand = "Tata";
this.model = "Nexon";
this.price = 1250000.99;
this.mileage = 15.5;
this.speed = 0;
this.fuelType = "Petrol";
}
// Parameterized Constructor
public Vehicle(String brand, String model, double price, double mileage,
String fuelType) {
this.brand = brand;
this.model = model;
this.price = price;
this.mileage = mileage;
this.speed = 0;
this.fuelType = fuelType;
}
// Copy Constructor
public Vehicle(Vehicle other) {
this.brand = other.brand;
this.model = other.model;
this.price = other.price;
this.mileage = other.mileage;
this.speed = other.speed;
this.fuelType = other.fuelType;
}
// ===== METHODS =====
public void start() {
System.out.println(brand + " " + model + " engine started.");
this.speed = 0;
}
public void stop() {
System.out.println(brand + " " + model + " engine stopped.");
this.speed = 0;
}
public void drive() {
if (speed == 0) {
System.out.println("Vehicle is driving at normal speed.");
} else {
System.out.println("Vehicle is already in motion.");
}
}
public void changeSpeed(int newSpeed) {
if (newSpeed < 0) {
System.out.println("Speed cannot be negative!");
} else if (newSpeed == 0) {
System.out.println("Vehicle stopped.");
this.speed = 0;
} else {
this.speed = newSpeed;
System.out.println("Speed changed to " + newSpeed + " kmph.");
}
}
public double calcMileage(double fuelConsumed, double distanceTraveled) {
if (fuelConsumed > 0) {
return distanceTraveled / fuelConsumed;
}
return 0;
}
}