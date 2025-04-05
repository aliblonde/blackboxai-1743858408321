#!/bin/bash

# Download H2 database jar if not present
if [ ! -f h2-*.jar ]; then
    echo "Downloading H2 database..."
    wget https://repo1.maven.org/maven2/com/h2database/h2/2.1.214/h2-2.1.214.jar
fi

# Compile all Java files
echo "Compiling Java files..."
javac -cp h2-*.jar Employee.java DBConnection.java EmployeeConsoleApp.java

if [ $? -eq 0 ]; then
    echo "Compilation successful"
    
    # Run the application
    echo "Starting Employee Management System (Console Version)..."
    java -cp .:h2-*.jar EmployeeConsoleApp
else
    echo "Compilation failed"
fi
