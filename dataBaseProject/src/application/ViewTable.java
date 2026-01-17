package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewTable {
	static ObservableList<Medicines> list = FXCollections.observableArrayList();
	static TableView<Medicines> tv = new TableView<Medicines>();
	static ObservableList<Employee> list2 = FXCollections.observableArrayList();
	static TableView<Employee> tv2 = new TableView<Employee>();
	static ObservableList<OrderDetails> list3 = FXCollections.observableArrayList();
	static TableView<OrderDetails> tv3 = new TableView<OrderDetails>();
	static ObservableList<OrderDetails> list4 = FXCollections.observableArrayList();
	static TableView<OrderDetails> tv4 = new TableView<OrderDetails>();
	static ObservableList<Invoices> list5 = FXCollections.observableArrayList();
	static TableView<Invoices> tv5 = new TableView<Invoices>();

	public TableView<Medicines> getMedicinesView() {
		if (tv.getColumns().isEmpty()) {
			TableColumn<Medicines, Integer> idCol = new TableColumn<>("ID");
			idCol.setCellValueFactory(new PropertyValueFactory<>("medicineId"));

			TableColumn<Medicines, String> nameCol = new TableColumn<>("Name");
			nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Medicines, Integer> categoryCol = new TableColumn<>("Category");
			categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

			TableColumn<Medicines, Double> priceCol = new TableColumn<>("Price");
			priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

			TableColumn<Medicines, Integer> qtyCol = new TableColumn<>("Quantity");
			qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

			TableColumn<Medicines, LocalDate> expiryCol = new TableColumn<>("Expiry Date");
			expiryCol.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

			TableColumn<Medicines, Integer> supplierCol = new TableColumn<>("Supplier");
			supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));

			tv.getColumns().addAll(idCol, nameCol, categoryCol, priceCol, qtyCol, expiryCol, supplierCol);
		}

		list.clear();
		tv.setItems(getMedicinesList());

		return tv;
	}

	public static ObservableList<Medicines> getMedicinesList() {
		String q = """
				    SELECT m.Medicines_ID, m.name, c.Category_ID AS category,
				           m.Price, m.Quantity, m.Expiry_Date, m.Supplier_ID
				    FROM Medicines m
				    JOIN Medicine_Category c ON m.Category_ID = c.Category_ID
				    JOIN Suppliers s ON m.Supplier_ID = s.Supplier_ID
				""";

		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
				Statement s = c.createStatement();
				ResultSet r = s.executeQuery(q)) {

			while (r.next()) {
				int id = r.getInt("Medicines_ID");
				String name = r.getString("name");
				int category = r.getInt("category");
				double price = r.getDouble("Price");
				int qty = r.getInt("Quantity");
				LocalDate expiry = r.getDate("Expiry_Date").toLocalDate();
				int supplier = r.getInt("Supplier_ID");

				list.add(new Medicines(id, name, category, price, qty, expiry, supplier));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}

	// Employee Table View
	public TableView<Employee> getEmployeeView() {
		if (tv2.getColumns().isEmpty()) {
			TableColumn<Employee, Integer> id = new TableColumn<>("ID");
			id.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

			TableColumn<Employee, String> name = new TableColumn<>("Name");
			name.setCellValueFactory(new PropertyValueFactory<>("name"));

			TableColumn<Employee, String> phone = new TableColumn<>("Phone");
			phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

			TableColumn<Employee, String> role = new TableColumn<>("Role");
			role.setCellValueFactory(new PropertyValueFactory<>("role"));

			TableColumn<Employee, Integer> shiftHours = new TableColumn<>("ShiftHours");
			shiftHours.setCellValueFactory(new PropertyValueFactory<>("shiftHours"));

			TableColumn<Employee, Double> salary = new TableColumn<>("Salary");
			salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

			tv2.getColumns().addAll(id, name, phone, role, shiftHours, salary);
		}

		list2.clear();
		tv2.setItems(getEmployeeList());

		return tv2;
	}

	public static ObservableList<Employee> getEmployeeList() {
		String q = """
				    SELECT *
				    FROM Employee
				""";

		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
				Statement s = c.createStatement();
				ResultSet r = s.executeQuery(q)) {

			while (r.next()) {
				int id = r.getInt("Employee_ID");
				String name = r.getString("Name");
				String phone = r.getString("Phone");
				String role = r.getString("Role");
				int shift = r.getInt("Shift_Hours");
				double salary = r.getDouble("Salary");

				list2.add(new Employee(id, name, phone, role, shift, salary));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list2;
	}

	// Order Table View
	public TableView<OrderDetails> getOrderDetailsView() {
		if (tv3.getColumns().isEmpty()) {
			TableColumn<OrderDetails, Orders> order = new TableColumn<>("Order");
			order.setCellValueFactory(new PropertyValueFactory<>("order"));
			TableColumn<OrderDetails, Medicines> medicine = new TableColumn<>("Medicine");
			medicine.setCellValueFactory(new PropertyValueFactory<>("medicine"));

			TableColumn<OrderDetails, Integer> quantity = new TableColumn<>("Quantity");
			quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

			TableColumn<OrderDetails, Double> price = new TableColumn<>("Price");
			price.setCellValueFactory(new PropertyValueFactory<>("price"));

			tv3.getColumns().addAll(order, medicine, quantity, price);
		}

		list3.clear();
		tv3.setItems(getOrderDList());

		return tv3;
	}

	public static ObservableList<OrderDetails> getOrderDList() {
		String q = """
				    SELECT od.Order_ID, od.Medicines_ID, od.Quantity, od.Price,
				           o.Order_Date,
				           m.Name, m.Category_ID AS category, m.Price AS medicine_price, m.Quantity AS medicine_qty, m.Expiry_Date, m.Supplier_ID,
				           e.Employee_ID, e.Name AS emp_name, e.Phone, e.Role, e.Shift_Hours, e.Salary
				    FROM Order_Details od
				    JOIN Orders o ON od.Order_ID = o.Order_ID
				    JOIN Medicines m ON od.Medicines_ID = m.Medicines_ID
				    JOIN Employee e ON o.Employee_ID = e.Employee_ID
				""";

		try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
				Statement s = c.createStatement();
				ResultSet r = s.executeQuery(q)) {
			while (r.next()) {
				int orderId = r.getInt("Order_ID");
				LocalDate orderDate = r.getDate("Order_Date").toLocalDate();

				int medId = r.getInt("Medicines_ID");
				String medicineName = r.getString("Name"); // m.Name
				int category = r.getInt("category");
				double medPrice = r.getDouble("medicine_price");
				int qty = r.getInt("medicine_qty");
				LocalDate expiry = r.getDate("Expiry_Date").toLocalDate();
				int supplier = r.getInt("Supplier_ID");

				int quantity = r.getInt("Quantity");
				double orderPrice = r.getDouble("Price");

				int empId = r.getInt("Employee_ID");
				String employeeName = r.getString("emp_name"); // e.Name AS emp_name
				String phone = r.getString("Phone");
				String role = r.getString("Role");
				int shift = r.getInt("Shift_Hours");
				double salary = r.getDouble("Salary");

				Employee employee = new Employee(empId, employeeName, phone, role, shift, salary);
				Medicines medicine = new Medicines(medId, medicineName, category, medPrice, qty, expiry, supplier);
				Orders order = new Orders(orderId, employee, orderDate);
				OrderDetails od = new OrderDetails(order, medicine, quantity, orderPrice);

				list3.add(od);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list3;
	}

	// Order Table View
	public TableView<OrderDetails> getAddOrderDetailsView() {
		list4 = FXCollections.observableArrayList();
		if (tv4.getColumns().isEmpty()) {
			TableColumn<OrderDetails, Orders> order = new TableColumn<>("Order");
			order.setCellValueFactory(new PropertyValueFactory<>("order"));

			order.setPrefWidth(330);
			TableColumn<OrderDetails, Medicines> medicine = new TableColumn<>("Medicine");
			medicine.setCellValueFactory(new PropertyValueFactory<>("medicine"));
			medicine.setPrefWidth(350);
			TableColumn<OrderDetails, Integer> quantity = new TableColumn<>("Quantity");
			quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

			TableColumn<OrderDetails, Double> price = new TableColumn<>("Price");
			price.setCellValueFactory(new PropertyValueFactory<>("price"));

			tv4.getColumns().addAll(order, medicine, quantity, price);
		}

		list4.clear();
		tv4.setItems(list4);

		return tv4;
	}

	// Invoices Table View
	public TableView<Invoices> getInvoices() {
		if (tv5.getColumns().isEmpty()) {
			TableColumn<Invoices, Integer> invoiceId = new TableColumn<>("InvoiceId");
			invoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));

			TableColumn<Invoices, Orders> order = new TableColumn<>("Order");
			order.setCellValueFactory(new PropertyValueFactory<>("order"));

			TableColumn<Invoices, LocalDate> date = new TableColumn<>("Date");
			date.setCellValueFactory(new PropertyValueFactory<>("date"));

			TableColumn<Invoices, Double> totalAmount = new TableColumn<>("TotalAmount");
			totalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

			tv5.getColumns().addAll(invoiceId, order, date, totalAmount);
		}

		list5.clear();
		tv5.setItems(getInvoicesList());

		return tv5;
	}

	public static ObservableList<Invoices> getInvoicesList() {
	    String q = """
	        SELECT i.Invoice_ID, i.Order_ID, i.date AS invoice_date, i.Total_Amount,
	               o.Order_Date,
	               e.Employee_ID, e.Name AS emp_name, e.Phone, e.Role, e.Shift_Hours, e.Salary
	        FROM Invoices i
	        JOIN Orders o ON i.Order_ID = o.Order_ID
	        JOIN Employee e ON o.Employee_ID = e.Employee_ID
	    """;

	    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
	         Statement s = c.createStatement();
	         ResultSet r = s.executeQuery(q)) {

	        while (r.next()) {
	            int invoiceId = r.getInt("Invoice_ID");
	            int orderId = r.getInt("Order_ID");
	            LocalDateTime invoiceDate = r.getTimestamp("invoice_date").toLocalDateTime();
	            double totalAmount = r.getDouble("Total_Amount");

	            int empId = r.getInt("Employee_ID");
	            String empName = r.getString("emp_name");
	            String phone = r.getString("Phone");
	            String role = r.getString("Role");
	            int shift = r.getInt("Shift_Hours");
	            double salary = r.getDouble("Salary");

	            Employee employee = new Employee(empId, empName, phone, role, shift, salary);

	            LocalDate orderDate = r.getDate("Order_Date").toLocalDate();
	            Orders order = new Orders(orderId, employee, orderDate);

	            Invoices invoice = new Invoices(invoiceId, order, invoiceDate, totalAmount);
	            list5.add(invoice);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return list5;
	}

}
