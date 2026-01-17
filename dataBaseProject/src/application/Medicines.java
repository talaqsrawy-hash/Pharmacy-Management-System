package application;
import java.time.LocalDate;

public class Medicines {
    private int medicineId;
    private String name;
    private int category;   
    private double price;
    private int quantity;
    private LocalDate expiryDate;
    private int supplier;   

    public Medicines(int medicineId, String name, int category,
                     double price, int quantity, LocalDate expiryDate, int supplier) {
        this.medicineId = medicineId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.supplier = supplier;
    }

   
    public int getMedicineId() { return medicineId; }
    public String getName() { return name; }
    public int getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public int getSupplier() { return supplier; }
    
    public String toString() {
        return String.format("ID: %d | Name: %s | Price: $%.2f | Qty: %d | Exp: %s", 
            medicineId, name, price, quantity, expiryDate);
    }
}
