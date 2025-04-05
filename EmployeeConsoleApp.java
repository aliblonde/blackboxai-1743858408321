import java.util.Scanner;

public class EmployeeConsoleApp {
    private static DBConnection dbConnection;
    private static Scanner scanner;

    public static void main(String[] args) {
        try {
            dbConnection = new DBConnection();
            scanner = new Scanner(System.in);
            
            System.out.println("Employee Management System (Console Version)");
            System.out.println("-------------------------------------------");
            
            while (true) {
                System.out.println("\nOptions:");
                System.out.println("1. Search Employee");
                System.out.println("2. Exit");
                System.out.print("Enter choice: ");
                
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        searchEmployee();
                        break;
                    case 2:
                        System.out.println("Exiting...");
                        dbConnection.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void searchEmployee() {
        try {
            System.out.print("\nEnter Employee Number: ");
            int empNumber = Integer.parseInt(scanner.nextLine());
            
            Employee employee = dbConnection.getEmployeeByNumber(empNumber);
            
            if (employee != null) {
                System.out.println("\nEmployee Details:");
                System.out.println("-----------------");
                System.out.println("Number: " + employee.getEmpNumber());
                System.out.println("Name: " + employee.getName());
                System.out.println("Position: " + employee.getPosition());
                System.out.println("Company: " + employee.getCompany());
                System.out.println("Residence Card URL: " + employee.getResidenceCardUrl());
                System.out.println("Unified ID URL: " + employee.getUnifiedIdUrl());
                System.out.println("Passport URL: " + employee.getPassportUrl());
            } else {
                System.out.println("\nEmployee not found. Would you like to add this employee? (Y/N)");
                String choice = scanner.nextLine();
                
                if (choice.equalsIgnoreCase("Y")) {
                    addNewEmployee(empNumber);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addNewEmployee(int empNumber) {
        try {
            System.out.println("\nEnter New Employee Details");
            System.out.println("--------------------------");
            
            System.out.print("Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Position: ");
            String position = scanner.nextLine();
            
            System.out.print("Company: ");
            String company = scanner.nextLine();
            
            System.out.print("Residence Card URL: ");
            String residenceUrl = scanner.nextLine();
            
            System.out.print("Unified ID URL: ");
            String unifiedUrl = scanner.nextLine();
            
            System.out.print("Passport URL: ");
            String passportUrl = scanner.nextLine();
            
            Employee newEmployee = new Employee(empNumber, name, position, company, 
                                             residenceUrl, unifiedUrl, passportUrl);
            
            if (dbConnection.saveEmployee(newEmployee)) {
                System.out.println("Employee added successfully!");
            } else {
                System.out.println("Failed to add employee");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}