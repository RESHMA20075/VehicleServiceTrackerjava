import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class Vehicle {
    String vehicleNumber;
    String ownerName;
    LocalDate lastServiceDate;
    LocalDate nextServiceDate;

    Vehicle(String number, String owner,
            LocalDate lastService, LocalDate nextService) {
        vehicleNumber = number;
        ownerName = owner;
        lastServiceDate = lastService;
        nextServiceDate = nextService;
    }

    void display() {
        System.out.println("\nVehicle Number: " + vehicleNumber);
        System.out.println("Owner Name: " + ownerName);
        System.out.println("Last Service: " + lastServiceDate);
        System.out.println("Next Service: " + nextServiceDate);

        if (nextServiceDate.isBefore(LocalDate.now())
                || nextServiceDate.isEqual(LocalDate.now())) {
            System.out.println("Reminder: Service is due!");
        } else {
            System.out.println("Reminder: Service is upcoming.");
        }
    }
}

public class VehicleServiceTrcaker {
    static ArrayList<Vehicle> vehicles = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static void addVehicle() {
        System.out.print("Enter vehicle number: ");
        String number = sc.nextLine().trim();

        System.out.print("Enter owner name: ");
        String owner = sc.nextLine().trim();

        if (number.isEmpty() || owner.isEmpty()) {
            System.out.println("Vehicle number and owner name are required.");
            return;
        }

        for (Vehicle v : vehicles) {
            if (v.vehicleNumber.equalsIgnoreCase(number)) {
                System.out.println("Vehicle already exists!");
                return;
            }
        }

        try {
            System.out.print("Enter last service date (YYYY-MM-DD): ");
            LocalDate last = LocalDate.parse(sc.nextLine().trim());

            System.out.print("Enter next service date (YYYY-MM-DD): ");
            LocalDate next = LocalDate.parse(sc.nextLine().trim());

            if (next.isBefore(last)) {
                System.out.println("Next service date cannot be before last service date.");
                return;
            }

            vehicles.add(new Vehicle(number, owner, last, next));
            System.out.println("Vehicle details added successfully!");

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date! Use YYYY-MM-DD format.");
        }
    }

    static void viewVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicle details available.");
            return;
        }

        for (Vehicle v : vehicles) {
            v.display();
        }
    }

    static void searchVehicle() {
        System.out.print("Enter vehicle number to search: ");
        String number = sc.nextLine().trim();

        for (Vehicle v : vehicles) {
            if (v.vehicleNumber.equalsIgnoreCase(number)) {
                v.display();
                return;
            }
        }

        System.out.println("Vehicle not found.");
    }

    static void checkReminders() {
        boolean found = false;

        for (Vehicle v : vehicles) {
            if (!v.nextServiceDate.isAfter(LocalDate.now())) {
                v.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No service reminders due today.");
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== VEHICLE SERVICE REMINDER AND MAINTENANCE TRACKER ===");
            System.out.println("1. Add Vehicle");
            System.out.println("2. View All Vehicles");
            System.out.println("3. Search Vehicle");
            System.out.println("4. Check Service Reminders");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    addVehicle();
                    break;
                case "2":
                    viewVehicles();
                    break;
                case "3":
                    searchVehicle();
                    break;
                case "4":
                    checkReminders();
                    break;
                case "5":
                    System.out.println("Thank you for using the application!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Enter 1 to 5.");
            }
        }
    }
}