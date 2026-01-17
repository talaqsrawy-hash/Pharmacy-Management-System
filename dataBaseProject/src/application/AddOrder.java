package application;

import java.sql.*;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AddOrder {
    TextField Oid = new TextField();
    TextField Mid = new TextField();
    TextField Eid = new TextField();
    TextField Qid = new TextField();
    TextField Pid = new TextField();
    Label OidL = new Label("OrderID");
    Label MidL = new Label("MedicineID");
    Label EidL = new Label("EmployeeID");
    Label QidL = new Label("Quantity");
    Label PidL = new Label("Price");
    ViewTable v = new ViewTable();

    public Scene getScene(Stage stage) {
        BorderPane pane = new BorderPane();
        GridPane gp = new GridPane();
        gp.setVgap(20);
        gp.setHgap(15);
        gp.setStyle("-fx-background-color: linear-gradient(to bottom right, #077A7D, #004D4D);"
                  + "-fx-border-color: #ffffff;"
                  + "-fx-border-width: 6;"
                  + "-fx-border-radius: 25;"
                  + "-fx-background-radius: 5;"
                  + "-fx-padding: 30;");

        gp.add(OidL, 0, 0);
        gp.add(Oid, 1, 0);
        gp.add(MidL, 0, 1);
        gp.add(Mid, 1, 1);
        gp.add(EidL, 0, 2);
        gp.add(Eid, 1, 2);
        gp.add(QidL, 0, 3);
        gp.add(Qid, 1, 3);
        gp.add(PidL, 0, 4);
        gp.add(Pid, 1, 4);

        Oid.setDisable(true);
        Oid.setPromptText("Auto-generated");

        Oid.getStyleClass().add("custom-field");
        Mid.getStyleClass().add("custom-field");
        Eid.getStyleClass().add("custom-field");
        Qid.getStyleClass().add("custom-field");
        Pid.getStyleClass().add("custom-field");

        OidL.getStyleClass().add("custom-label");
        MidL.getStyleClass().add("custom-label");
        EidL.getStyleClass().add("custom-label");
        QidL.getStyleClass().add("custom-label");
        PidL.getStyleClass().add("custom-label");

        HBox hb = new HBox(gp, v.getAddOrderDetailsView());
        hb.setAlignment(Pos.CENTER);
        VBox vb = new VBox(hb);
        vb.setAlignment(Pos.CENTER);
        pane.setCenter(vb);

        ImageView im1 = new ImageView(new Image("save.png"));
        Button b1 = new Button("Save", im1);

        b1.setOnAction(e -> {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412")) {
                if (ViewTable.list4.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Order details list is empty.");
                    return;
                }

                Orders order = ViewTable.list4.get(0).getOrder();

                String sql = "INSERT INTO Orders (Employee_ID, Order_Date) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, order.getEmployee().getEmployeeId());
                pstmt.setDate(2, Date.valueOf(order.getOrderDate()));
                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                int generatedOrderID = -1;
                if (generatedKeys.next()) {
                    generatedOrderID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve Order ID.");
                }

                String sql2 = "INSERT INTO Order_Details (Order_ID, Medicines_ID, Quantity, Price) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);


                double totalAmount = 0.0;
                for (OrderDetails od : ViewTable.list4) {
                    pstmt2.setInt(1, generatedOrderID);
                    pstmt2.setInt(2, od.getMedicine().getMedicineId());
                    pstmt2.setInt(3, od.getQuantity());
                    pstmt2.setDouble(4, od.getPrice());
                    pstmt2.executeUpdate();

                    totalAmount += od.getQuantity() * od.getPrice();
                }

                String sql3 = "INSERT INTO Invoices (Order_ID, Total_Amount) VALUES (?, ?)";
                PreparedStatement pstmt3 = conn.prepareStatement(sql3);
                pstmt3.setInt(1, generatedOrderID); 
                pstmt3.setDouble(2, totalAmount);
                pstmt3.executeUpdate();
                
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order and details saved successfully.");
                ViewTable.list4.clear();
                stage.setScene(new Cashiers().getScene(stage));

            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", ex.getMessage());
            }
        });

        ImageView im2 = new ImageView(new Image("aoa.png"));
        Button b2 = new Button("Add New medicine", im2);

        b2.setOnAction(e -> {
            String midText = Mid.getText().trim();
            String eidText = Eid.getText().trim();
            String qidText = Qid.getText().trim();
            String pidText = Pid.getText().trim();
            LocalDate orderDate = LocalDate.now();

            if (midText.isEmpty() || eidText.isEmpty() || qidText.isEmpty() || pidText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
                return;
            }

            try {
                int quantity = Integer.parseInt(qidText);
                double price = Double.parseDouble(pidText);

                Employee emp = getEmployeeById(eidText);
                Medicines med = getMedicineById(midText);
                if (emp == null || med == null) {
                    showAlert(Alert.AlertType.ERROR, "Data Error", "Invalid Employee or Medicine ID.");
                    return;
                }

                Orders order = new Orders(0, emp, orderDate); // OrderID = 0 temporarily
                OrderDetails od = new OrderDetails(order, med, quantity, price);
                ViewTable.list4.add(od);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Order detail added successfully.");
                Mid.clear(); Eid.clear(); Qid.clear(); Pid.clear();

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Format Error", "Quantity and price must be numbers.");
            }
        });

        ImageView im3 = new ImageView(new Image("bob.png"));
        Button b3 = new Button("Back", im3);
        b3.setOnAction(e -> stage.setScene(new Cashiers().getScene(stage)));

        for (ImageView iv : new ImageView[]{im1, im2, im3}) {
            iv.setFitWidth(30); iv.setFitHeight(30);
        }

        String buttonStyle = "-fx-background-color: linear-gradient(to bottom right, #66B2B2, #339999);"
                           + "-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;"
                           + "-fx-background-radius: 12; -fx-border-color: white; -fx-border-width: 2px;"
                           + "-fx-border-radius: 12; -fx-padding: 10 20 10 20;"
                           + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 5, 0.0, 0, 4);"
                           + "-fx-cursor: hand;";
        b1.setStyle(buttonStyle);
        b2.setStyle(buttonStyle);
        b3.setStyle(buttonStyle);

        HBox buttonBox = new HBox(25, b1, b2, b3);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 20, 0));
        buttonBox.setStyle("-fx-background-color: transparent;");

        pane.setTop(buttonBox);
        pane.setStyle("-fx-background-color: linear-gradient(to bottom right, #077A7D, #004D4D);");
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.setFullScreen(true);
        return scene;
    }

    public Employee getEmployeeById(String idText) {
        try {
            int id = Integer.parseInt(idText.trim());
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Employee WHERE Employee_ID = " + id);
            Employee emp = rs.next() ? new Employee(id, rs.getString("Name"), rs.getString("phone"), rs.getString("role"), rs.getInt("shift_hours"), rs.getDouble("salary")) : null;
            conn.close(); return emp;
        } catch (Exception e) { System.err.println("Employee Error: " + e.getMessage()); return null; }
    }

    public Medicines getMedicineById(String idText) {
        try {
            int id = Integer.parseInt(idText.trim());
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Medicines WHERE Medicines_ID = " + id);
            Medicines med = rs.next() ? new Medicines(id, rs.getString("Name"), rs.getInt("Category_ID"), rs.getDouble("Price"), rs.getInt("Quantity"), rs.getDate("Expiry_Date").toLocalDate(), rs.getInt("Supplier_ID")) : null;
            conn.close(); return med;
        } catch (Exception e) { System.err.println("Medicine Error: " + e.getMessage()); return null; }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
