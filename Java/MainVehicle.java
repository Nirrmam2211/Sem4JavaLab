public class MainVehicle {
public static void main(String[] args) {
// ===== CREATE VEHICLE OBJECTS =====
// Using Default Constructor
Vehicle v1 = new Vehicle();
// rename default vehicle
v1.brand = "Ford";
v1.model = "EcoSport";
// Using Parameterized Constructor
Vehicle v2 = new Vehicle("Jeep", "Wrangler", 1500000.50, 18.5, "Diesel");
Vehicle v3 = new Vehicle("Kia", "Seltos", 1200000.00, 17.0, "Petrol");
Vehicle v4 = new Vehicle("Toyota", "Yaris", 800000.75, 20.5, "Petrol");
// Using Copy Constructor
Vehicle v5 = new Vehicle(v3);
v5.brand = "Skoda";
v5.model = "Octavia";
// ===== CREATE ARRAY OF VEHICLES =====
Vehicle[] garage = new Vehicle[5];
garage[0] = v1;
garage[1] = v2;
garage[2] = v3;
garage[3] = v4;
garage[4] = v5;
// ===== DEMONSTRATE VEHICLE OPERATIONS =====
System.out.println("===== Vehicle Operations =====\n");
v1.start();
v1.changeSpeed(60);
v1.drive();
double calculatedMileage = v1.calcMileage(5, 100); // 5 liters, 100 km
System.out.println("Mileage: " + calculatedMileage + " kmpl");
v1.stop();
System.out.println("\n===== Vehicle Garage Details =====\n");
// Print in tabular format
System.out.println("Brand Name\t\tModel Name\t\tPrice\t\t\tMileage");
System.out.println("-".repeat(80));
for (int i = 0; i < garage.length; i++) {
System.out.printf("%-20s%-20s%-20.2f%-20.1f\n",
garage[i].brand,
garage[i].model,
garage[i].getPrice(),
garage[i].getMileage());
}
}
}