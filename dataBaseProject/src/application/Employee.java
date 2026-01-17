package application;

public class Employee {
    private int employeeId;
    private String name;
    private String phone;
    private String role;
    private int shiftHours;
    private double salary;

    public Employee(int employeeId, String name, String phone, String role, int shiftHours, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.shiftHours = shiftHours;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getShiftHours() {
        return shiftHours;
    }

    public void setShiftHours(int shiftHours) {
        this.shiftHours = shiftHours;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", shiftHours=" + shiftHours +
                ", salary=" + salary +
                '}';
    }
}