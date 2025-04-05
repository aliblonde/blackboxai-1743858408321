import java.sql.*;
import org.h2.jdbcx.JdbcDataSource;

public class DBConnection {
    private static final String DB_URL = "jdbc:h2:mem:employee_db;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "";
    
    private Connection conn;
    
    public Connection connect() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                JdbcDataSource ds = new JdbcDataSource();
                ds.setURL(DB_URL);
                ds.setUser(USER);
                ds.setPassword(PASS);
                conn = ds.getConnection();
                initializeDatabase();
            } catch (SQLException e) {
                System.err.println("Error connecting to database: " + e.getMessage());
                throw e;
            }
        }
        return conn;
    }

    private void initializeDatabase() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS employees (" +
                "emp_number INT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "position VARCHAR(255) NOT NULL, " +
                "company VARCHAR(255) NOT NULL, " +
                "residence_card_url VARCHAR(255), " +
                "unified_id_url VARCHAR(255), " +
                "passport_url VARCHAR(255))");
            
            // Insert sample data
            stmt.execute("MERGE INTO employees KEY(emp_number) VALUES " +
                "(1001, 'John Doe', 'Developer', 'Tech Corp', " +
                "'https://example.com/residence1.jpg', " +
                "'https://example.com/unified1.jpg', " +
                "'https://example.com/passport1.jpg')");
                
            stmt.execute("MERGE INTO employees KEY(emp_number) VALUES " +
                "(1002, 'Jane Smith', 'Manager', 'Business Inc', " +
                "'https://example.com/residence2.jpg', " +
                "'https://example.com/unified2.jpg', " +
                "'https://example.com/passport2.jpg')");
        }
    }
    
    public Employee getEmployeeByNumber(int empNumber) throws SQLException {
        Employee employee = null;
        String query = "SELECT * FROM employees WHERE emp_number = ?";
        
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, empNumber);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                employee = new Employee(
                    rs.getInt("emp_number"),
                    rs.getString("name"),
                    rs.getString("position"),
                    rs.getString("company"),
                    rs.getString("residence_card_url"),
                    rs.getString("unified_id_url"),
                    rs.getString("passport_url")
                );
            }
        }
        return employee;
    }
    
    public boolean saveEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO employees VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, employee.getEmpNumber());
            stmt.setString(2, employee.getName());
            stmt.setString(3, employee.getPosition());
            stmt.setString(4, employee.getCompany());
            stmt.setString(5, employee.getResidenceCardUrl());
            stmt.setString(6, employee.getUnifiedIdUrl());
            stmt.setString(7, employee.getPassportUrl());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}