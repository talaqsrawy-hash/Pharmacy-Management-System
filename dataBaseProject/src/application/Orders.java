package application;

import java.time.LocalDate;

public class Orders {
    private int orderId;
    private Employee employee;  
    private LocalDate orderDate;

   
    public Orders(int orderId, Employee employee, LocalDate orderDate) {
        this.orderId = orderId;
        this.employee = employee;
        this.orderDate = orderDate;
    }
    

	public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

   
    @Override
    public String toString() {
    	    return String.format("Order #%d | Date: %s | Employee: %s", 
    	        orderId, 
    	        orderDate, 
    	        (employee != null ? employee.getName() : "Unknown")
    	    );
    	}
}
