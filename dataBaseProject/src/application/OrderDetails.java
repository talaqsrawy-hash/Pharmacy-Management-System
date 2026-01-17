package application;

public class OrderDetails {
    private Orders order;             
    private Medicines medicine;       
    private int quantity;
    private double price;

    
    public OrderDetails(Orders order, Medicines medicine, int quantity, double price) {
        this.order = order;
        this.medicine = medicine;
        this.quantity = quantity;
        this.price = price;
    }

    
    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Medicines getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicines medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    
    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + (order != null ? order.getOrderId() : "null") +
                ", medicineId=" + (medicine != null ? medicine.getMedicineId() : "null") +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
