
Built by https://www.blackbox.ai

---

```markdown
# Employee Management System

## Project Overview
The Employee Management System is a Java-based application that allows users to manage employee records effectively. It provides a graphical user interface (GUI) and a console version for interacting with employee data stored in an in-memory H2 database. Users can search for employees, view their details, and add new employees if they do not already exist in the system.

## Installation
To set up the Employee Management System, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone <repository_url>
   cd employee-management-system
   ```

2. **Ensure you have Java installed:**
   This project requires Java Development Kit (JDK) installed on your machine. You can download it from [Oracle's Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use a package manager.

3. **Compile the Java files:**
   ```bash
   javac *.java
   ```

4. **Run the application:**
   - **For GUI version:**
     ```bash
     java EmployeeManagementUI
     ```
   - **For Console version:**
     ```bash
     java EmployeeConsoleApp
     ```

## Usage
- **GUI Version:** Enter the employee number in the input field and click "Search" to view employee details. You can also clear the fields using the "Clear" button.
- **Console Version:** Follow the prompts in the terminal to search for employees and add new records.

## Features
- Search for employees by their unique employee number.
- View employee details including name, position, company, and image links for residence card, unified ID, and passport.
- Option to add a new employee if not found.
- Both a graphical interface (using Swing) and a console interface for different user preferences.

## Dependencies
This project uses the following library:
- **H2 Database:** For in-memory database management.

If there were a `package.json` file, dependencies would be listed there. However, as this project is in Java, it primarily uses Java standard libraries and the H2 database dependency.

## Project Structure
```
/employee-management-system
│
├── Employee.java             # Class representing an employee
├── DBConnection.java         # Class managing database connections and operations
├── EmployeeManagementUI.java # GUI class for the employee management system
└── EmployeeConsoleApp.java   # Console application class for the employee management system
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
- The project utilizes H2 database for data persistence.
- Thank you to the Java community for building such a powerful ecosystem for application development.
```