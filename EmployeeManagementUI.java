import javax.swing.*;
import javax.swing.border.*;
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
    private JPanel residenceImagePanel, unifiedImagePanel, passportImagePanel;
    private JButton searchButton, clearButton;
    private JPanel statusBar;

    public EmployeeManagementUI() {
        super("Employee Management System");
        dbConnection = new DBConnection();
        setModernLookAndFeel();
        initializeUI();
    }

    private void setModernLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", new Color(46, 131, 219));
            UIManager.put("nimbusBlueGrey", new Color(120, 144, 156));
            UIManager.put("control", new Color(240, 240, 240));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout(0, 0));

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(Color.WHITE);

        // Search Panel
        JPanel searchPanel = createSearchPanel();
        contentPanel.add(searchPanel, BorderLayout.NORTH);

        // Details Panel
        JPanel detailsPanel = createDetailsPanel();
        contentPanel.add(new JScrollPane(detailsPanel), BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        // Status Bar
        statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        statusBar.add(new JLabel("Ready"));
        add(statusBar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(46, 131, 219));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Employee Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        panel.add(title, BorderLayout.WEST);
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Search Employee",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(80, 80, 80)
        ));
        panel.setBackground(Color.WHITE);

        empNumberField = new JTextField(20);
        empNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        empNumberField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        searchButton = createStyledButton("Search", new Color(46, 131, 219));
        clearButton = createStyledButton("Clear", new Color(120, 144, 156));

        panel.add(new JLabel("Employee Number:"));
        panel.add(empNumberField);
        panel.add(searchButton);
        panel.add(clearButton);

        // Add action listeners
        searchButton.addActionListener(e -> searchEmployee());
        clearButton.addActionListener(e -> clearFields());
        empNumberField.addActionListener(e -> searchEmployee());

        return panel;
    }

    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Employee Details",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12),
            new Color(80, 80, 80)
        ));
        panel.setBackground(Color.WHITE);

        // Basic Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setBackground(Color.WHITE);

        nameLabel = createDetailLabel("Name: ");
        positionLabel = createDetailLabel("Position: ");
        companyLabel = createDetailLabel("Company: ");

        infoPanel.add(new JLabel("Name:"));
        infoPanel.add(nameLabel);
        infoPanel.add(new JLabel("Position:"));
        infoPanel.add(positionLabel);
        infoPanel.add(new JLabel("Company:"));
        infoPanel.add(companyLabel);

        panel.add(infoPanel, BorderLayout.NORTH);

        // Images Panel
        JPanel imagesPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        imagesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        imagesPanel.setBackground(Color.WHITE);

        residenceImagePanel = createImagePanel("Residence Card");
        unifiedImagePanel = createImagePanel("Unified ID");
        passportImagePanel = createImagePanel("Passport");

        imagesPanel.add(residenceImagePanel);
        imagesPanel.add(unifiedImagePanel);
        imagesPanel.add(passportImagePanel);

        panel.add(imagesPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        label.setOpaque(true);
        label.setBackground(new Color(250, 250, 250));
        return label;
    }

    private JPanel createImagePanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(new Color(80, 80, 80));
        panel.add(titleLabel, BorderLayout.NORTH);

        JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(250, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(imageLabel, BorderLayout.CENTER);

        return panel;
    }

    private void searchEmployee() {
        try {
            String empNumberText = empNumberField.getText();
            if (empNumberText.isEmpty()) {
                showErrorDialog("Please enter employee number");
                return;
            }

            int empNumber = Integer.parseInt(empNumberText);
            updateStatus("Searching for employee...");

            // Show loading state
            setLoadingState(true);
            
            SwingWorker<Employee, Void> worker = new SwingWorker<Employee, Void>() {
                @Override
                protected Employee doInBackground() throws Exception {
                    return dbConnection.getEmployeeByNumber(empNumber);
                }

                @Override
                protected void done() {
                    try {
                        Employee employee = get();
                        setLoadingState(false);
                        
                        if (employee != null) {
                            displayEmployeeDetails(employee);
                            updateStatus("Employee found: " + employee.getName());
                        } else {
                            promptAddNewEmployee(empNumber);
                        }
                    } catch (Exception e) {
                        setLoadingState(false);
                        showErrorDialog("Error: " + e.getMessage());
                        updateStatus("Error occurred");
                    }
                }
            };
            worker.execute();

        } catch (NumberFormatException e) {
            showErrorDialog("Please enter a valid employee number");
            updateStatus("Invalid employee number");
        }
    }

    private void setLoadingState(boolean isLoading) {
        searchButton.setEnabled(!isLoading);
        clearButton.setEnabled(!isLoading);
        empNumberField.setEnabled(!isLoading);
        
        if (isLoading) {
            searchButton.setText("Searching...");
        } else {
            searchButton.setText("Search");
        }
    }

    private void displayEmployeeDetails(Employee employee) {
        nameLabel.setText(employee.getName());
        positionLabel.setText(employee.getPosition());
        companyLabel.setText(employee.getCompany());

        loadAndDisplayImage(residenceImagePanel, employee.getResidenceCardUrl());
        loadAndDisplayImage(unifiedImagePanel, employee.getUnifiedIdUrl());
        loadAndDisplayImage(passportImagePanel, employee.getPassportUrl());
    }

    private void loadAndDisplayImage(JPanel imagePanel, String imageUrl) {
        try {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                URL url = new URL(imageUrl);
                BufferedImage image = ImageIO.read(url);
                if (image != null) {
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(250, 200, Image.SCALE_SMOOTH));
                    ((JLabel)imagePanel.getComponent(1)).setIcon(icon);
                    ((JLabel)imagePanel.getComponent(1)).setText("");
                } else {
                    setDefaultImage(imagePanel);
                }
            } else {
                setDefaultImage(imagePanel);
            }
        } catch (Exception e) {
            setDefaultImage(imagePanel);
        }
    }

    private void setDefaultImage(JPanel imagePanel) {
        try {
            BufferedImage placeholder = ImageIO.read(new URL("https://images.pexels.com/photos/3561987/pexels-photo-3561987.jpeg"));
            ImageIcon icon = new ImageIcon(placeholder.getScaledInstance(250, 200, Image.SCALE_SMOOTH));
            ((JLabel)imagePanel.getComponent(1)).setIcon(icon);
            ((JLabel)imagePanel.getComponent(1)).setText("");
        } catch (Exception e) {
            ((JLabel)imagePanel.getComponent(1)).setIcon(null);
            ((JLabel)imagePanel.getComponent(1)).setText("Image not available");
        }
    }

    private void promptAddNewEmployee(int empNumber) {
        Object[] options = {"Add Employee", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this,
            "Employee #" + empNumber + " not found. Would you like to add them?",
            "Employee Not Found",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        if (choice == JOptionPane.YES_OPTION) {
            addNewEmployee(empNumber);
        } else {
            updateStatus("Operation cancelled");
        }
    }

    private void addNewEmployee(int empNumber) {
        JDialog dialog = new JDialog(this, "Add New Employee", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField nameField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField companyField = new JTextField();
        JButton residenceBtn = createStyledButton("Select Residence Card", new Color(46, 131, 219));
        JButton unifiedBtn = createStyledButton("Select Unified ID", new Color(46, 131, 219));
        JButton passportBtn = createStyledButton("Select Passport", new Color(46, 131, 219));

        String[] imagePaths = new String[3];

        residenceBtn.addActionListener(e -> {
            String path = selectImage("Residence Card");
            if (!path.isEmpty()) imagePaths[0] = path;
        });

        unifiedBtn.addActionListener(e -> {
            String path = selectImage("Unified ID");
            if (!path.isEmpty()) imagePaths[1] = path;
        });

        passportBtn.addActionListener(e -> {
            String path = selectImage("Passport");
            if (!path.isEmpty()) imagePaths[2] = path;
        });

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Position:"));
        panel.add(positionField);
        panel.add(new JLabel("Company:"));
        panel.add(companyField);
        panel.add(new JLabel("Residence Card:"));
        panel.add(residenceBtn);
        panel.add(new JLabel("Unified ID:"));
        panel.add(unifiedBtn);
        panel.add(new JLabel("Passport:"));
        panel.add(passportBtn);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", new Color(76, 175, 80));
        JButton cancelButton = createStyledButton("Cancel", new Color(244, 67, 54));

        saveButton.addActionListener(e -> {
            try {
                Employee newEmployee = new Employee(
                    empNumber,
                    nameField.getText(),
                    positionField.getText(),
                    companyField.getText(),
                    imagePaths[0] != null ? imagePaths[0] : "",
                    imagePaths[1] != null ? imagePaths[1] : "",
                    imagePaths[2] != null ? imagePaths[2] : ""
                );

                if (dbConnection.saveEmployee(newEmployee)) {
                    showSuccessDialog("Employee added successfully");
                    dialog.dispose();
                    displayEmployeeDetails(newEmployee);
                    updateStatus("Employee added: " + newEmployee.getName());
                } else {
                    showErrorDialog("Failed to add employee");
                }
            } catch (SQLException ex) {
                showErrorDialog("Database error: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private String selectImage(String documentType) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select " + documentType + " Image");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif"));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().toURI().toString();
        }
        return "";
    }

    private void clearFields() {
        empNumberField.setText("");
        nameLabel.setText("");
        positionLabel.setText("");
        companyLabel.setText("");
        
        setDefaultImage(residenceImagePanel);
        setDefaultImage(unifiedImagePanel);
        setDefaultImage(passportImagePanel);
        
        updateStatus("Fields cleared");
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, 
            message, 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, 
            message, 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStatus(String message) {
        ((JLabel)statusBar.getComponent(0)).setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementUI app = new EmployeeManagementUI();
            app.setVisible(true);
        });
    }
}