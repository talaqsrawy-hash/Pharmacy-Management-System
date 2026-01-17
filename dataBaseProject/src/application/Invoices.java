package application;

import java.time.LocalDateTime;

public class Invoices {
    private int invoiceId;
    private Orders order;            
    private LocalDateTime date;
    private double totalAmount;

    
    public Invoices(int invoiceId, Orders order, LocalDateTime date, double totalAmount) {
        this.invoiceId = invoiceId;
        this.order = order;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

   
    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", orderId=" + (order != null ? order.getOrderId() : "null") +
                ", date=" + date +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
