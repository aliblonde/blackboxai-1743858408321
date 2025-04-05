public class Employee {
    private int empNumber;
    private String name;
    private String position;
    private String company;
    private String residenceCardUrl;
    private String unifiedIdUrl;
    private String passportUrl;

    // Constructor
    public Employee(int empNumber, String name, String position, String company, 
                   String residenceCardUrl, String unifiedIdUrl, String passportUrl) {
        this.empNumber = empNumber;
        this.name = name;
        this.position = position;
        this.company = company;
        this.residenceCardUrl = residenceCardUrl;
        this.unifiedIdUrl = unifiedIdUrl;
        this.passportUrl = passportUrl;
    }

    // Getters and Setters
    public int getEmpNumber() {
        return empNumber;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getCompany() {
        return company;
    }

    public String getResidenceCardUrl() {
        return residenceCardUrl;
    }

    public String getUnifiedIdUrl() {
        return unifiedIdUrl;
    }

    public String getPassportUrl() {
        return passportUrl;
    }

    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setResidenceCardUrl(String residenceCardUrl) {
        this.residenceCardUrl = residenceCardUrl;
    }

    public void setUnifiedIdUrl(String unifiedIdUrl) {
        this.unifiedIdUrl = unifiedIdUrl;
    }

    public void setPassportUrl(String passportUrl) {
        this.passportUrl = passportUrl;
    }
}