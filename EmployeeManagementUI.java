import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

public class EmployeeManagementUI extends JFrame {
    private DBConnection dbConnection;
    private JTextField empNumberField;
    private JLabel nameLabel, positionLabel, companyLabel;
    private JLabel residenceImageLabel, unifiedImageLabel, passportImageLabel;
    private JButton searchButton, clearButton;

    public EmployeeManagementUI() {
        super("Employee Management System");
        dbConnection = new DBConnection();
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));

        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        empNumberField = new JTextField(15);
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        
        inputPanel.add(new JLabel("Employee Number:"));
        inputPanel.add(empNumberField);
        inputPanel.add(searchButton);
        inputPanel.add(clearButton);

        // Create display panel
        JPanel displayPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        nameLabel = new JLabel("Name: ");
        positionLabel = new JLabel("Position: ");
        companyLabel = new JLabel("Company: ");
        
        residenceImageLabel = new JLabel("Residence Card: ", JLabel.CENTER);
        unifiedImageLabel = new JLabel("Unified ID: ", JLabel.CENTER);
        passportImageLabel = new JLabel("Passport: ", JLabel.CENTER);
        
        displayPanel.add(nameLabel);
        displayPanel.add(positionLabel);
        displayPanel.add(companyLabel);
        displayPanel.add(new JLabel()); // spacer
        displayPanel.add(residenceImageLabel);
        displayPanel.add(unifiedImageLabel);
        displayPanel.add(passportImageLabel);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayPanel), BorderLayout.CENTER);

        // Add action listeners
        searchButton.addActionListener(e -> searchEmployee());
        clearButton.addActionListener(e -> clearFields());
        empNumberField.addActionListener(e -> searchEmployee());

        setLocationRelativeTo(null);
    }

    private void searchEmployee() {
        try {
            String empNumberText = empNumberField.getText();
            if (empNumberText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter employee number", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int empNumber = Integer.parseInt(empNumberText);
            Employee employee = dbConnection.getEmployeeByNumber(empNumber);

            if (employee != null) {
                // Display employee details
                nameLabel.setText("Name: " + employee.getName());
                positionLabel.setText("Position: " + employee.getPosition());
                companyLabel.setText("Company: " + employee.getCompany());

                // Load and display images
                loadAndDisplayImage(residenceImageLabel, employee.getResidenceCardUrl());
                loadAndDisplayImage(unifiedImageLabel, employee.getUnifiedIdUrl());
                loadAndDisplayImage(passportImageLabel, employee.getPassportUrl());
            } else {
                int option = JOptionPane.showConfirmDialog(this, 
                    "Employee not found. Would you like to add this employee?", 
                    "Employee Not Found", JOptionPane.YES_NO_OPTION);
                
                if (option == JOptionPane.YES_OPTION) {
                    addNewEmployee(empNumber);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid employee number", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAndDisplayImage(JLabel label, String imageUrl) {
        try {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                URL url = new URL(imageUrl);
                BufferedImage image = ImageIO.read(url);
                if (image != null) {
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(200, 150, Image.SCALE_SMOOTH));
                    label.setIcon(icon);
                } else {
                    label.setIcon(null);
                    label.setText(label.getText().split(":")[0] + ": Image not available");
                }
            } else {
                label.setIcon(null);
                label.setText(label.getText().split(":")[0] + ": No image provided");
            }
        } catch (Exception e) {
            label.setIcon(null);
            label.setText(label.getText().split(":")[0] + ": Error loading image");
        }
    }

    private void addNewEmployee(int empNumber) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        JTextField nameField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField companyField = new JTextField();
        JTextField residenceField = new JTextField();
        JTextField unifiedField = new JTextField();
        JTextField passportField = new JTextField();
        
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Position:"));
        panel.add(positionField);
        panel.add(new JLabel("Company:"));
        panel.add(companyField);
        panel.add(new JLabel("Residence Card URL:"));
        panel.add(residenceField);
        panel.add(new JLabel("Unified ID URL:"));
        panel.add(unifiedField);
        panel.add(new JLabel("Passport URL:"));
        panel.add(passportField);

        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Add New Employee", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                Employee newEmployee = new Employee(
                    empNumber,
                    nameField.getText(),
                    positionField.getText(),
                    companyField.getText(),
                    residenceField.getText(),
                    unifiedField.getText(),
                    passportField.getText()
                );
                
                if (dbConnection.saveEmployee(newEmployee)) {
                    JOptionPane.showMessageDialog(this, "Employee added successfully");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add employee");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields() {
        empNumberField.setText("");
        nameLabel.setText("Name: ");
        positionLabel.setText("Position: ");
        companyLabel.setText("Company: ");
        residenceImageLabel.setIcon(null);
        unifiedImageLabel.setIcon(null);
        passportImageLabel.setIcon(null);
        residenceImageLabel.setText("Residence Card: ");
        unifiedImageLabel.setText("Unified ID: ");
        passportImageLabel.setText("Passport: ");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            EmployeeManagementUI app = new EmployeeManagementUI();
            app.setVisible(true);
        });
    }
}