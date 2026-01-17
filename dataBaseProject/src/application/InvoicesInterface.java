package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InvoicesInterface {
	ViewTable obj = new ViewTable();
	TextField tfid;
	public Scene getScene(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		VBox vb = new VBox(10);
		vb.setAlignment(Pos.CENTER);
		bp.setCenter(vb);
		bp.setStyle("-fx-background-color: #077A7D;");
		//combo box 
		ComboBox<String> cb=new ComboBox<String>();
		cb.getItems().addAll("Today","Last month","Last year");
		cb.getStyleClass().add("combo-box");
		cb.setOnAction(e->{
		    if (cb.getValue().equals("Today")) {
		        LocalDate today = LocalDate.now();

		        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412")) {
		            String sql = """
		                SELECT i.Invoice_ID, i.Order_ID, i.date, i.Total_Amount, o.Order_Date
		                FROM Invoices i
		                JOIN Orders o ON i.Order_ID = o.Order_ID
		                WHERE DATE(o.Order_Date) = ?
		            """;

		            PreparedStatement pstmt = conn.prepareStatement(sql);
		            pstmt.setDate(1, java.sql.Date.valueOf(today));

		            ResultSet rs = pstmt.executeQuery();

		            VBox v = new VBox(10);
		            v.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 20;");
		            boolean hasData = false;

		            while (rs.next()) {
		                hasData = true;
		                Label label = new Label(
		                    "Invoice_ID: " + rs.getInt("Invoice_ID") + "\n" +
		                    "Order_ID: " + rs.getInt("Order_ID") + "\n" +
		                    "Order Date: " + rs.getDate("Order_Date").toLocalDate() + "\n" +
		                    "Invoice Date: " + rs.getTimestamp("date").toLocalDateTime() + "\n" +
		                    "Total Amount: " + rs.getDouble("Total_Amount")
		                );
		                label.setStyle("-fx-font-size: 16px; -fx-text-fill: #0D47A1; -fx-font-weight: bold; -fx-background-color: #BBDEFB; -fx-padding: 10; -fx-border-color: #90CAF9; -fx-border-width: 1;");
		                v.getChildren().add(label);
		            }

		            if (hasData) {
		                ScrollPane scrollPane = new ScrollPane(v);
		                scrollPane.setFitToWidth(true);
		                scrollPane.setStyle("-fx-background: #E3F2FD;");
		                Scene scene = new Scene(scrollPane, 400, 350);
		                Stage stage = new Stage();
		                stage.setTitle("Invoices for Orders Today");
		                stage.setScene(scene);
		                stage.showAndWait();
		            } else {
		                showAlert(Alert.AlertType.INFORMATION, "No Invoices", "No invoices found for orders placed today.");
		            }

		        } catch (SQLException ex) {
		            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not fetch data: " + ex.getMessage());
		        } catch (Exception ex) {
		            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + ex.getMessage());
		        }
		    }
		    if (cb.getValue().equals("Last month")) {
		        LocalDate today = LocalDate.now();
		        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
		        LocalDate lastMonthStart = firstDayOfThisMonth.minusMonths(1);
		        LocalDate lastMonthEnd = firstDayOfThisMonth.minusDays(1);

		        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412")) {
		            String sql = """
		                SELECT i.Invoice_ID, i.Order_ID, i.date, i.Total_Amount, o.Order_Date
		                FROM Invoices i
		                JOIN Orders o ON i.Order_ID = o.Order_ID
		                WHERE DATE(o.Order_Date) BETWEEN ? AND ?
		            """;

		            PreparedStatement pstmt = conn.prepareStatement(sql);
		            pstmt.setDate(1, java.sql.Date.valueOf(lastMonthStart));
		            pstmt.setDate(2, java.sql.Date.valueOf(lastMonthEnd));

		            ResultSet rs = pstmt.executeQuery();

		            VBox v = new VBox(10);
		            v.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 20;");
		            boolean hasData = false;

		            while (rs.next()) {
		                hasData = true;
		                Label label = new Label(
		                    "Invoice_ID: " + rs.getInt("Invoice_ID") + "\n" +
		                    "Order_ID: " + rs.getInt("Order_ID") + "\n" +
		                    "Order Date: " + rs.getDate("Order_Date").toLocalDate() + "\n" +
		                    "Invoice Date: " + rs.getTimestamp("date").toLocalDateTime() + "\n" +
		                    "Total Amount: " + rs.getDouble("Total_Amount")
		                );
		                label.setStyle("-fx-font-size: 16px; -fx-text-fill: #0D47A1; -fx-font-weight: bold; -fx-background-color: #BBDEFB; -fx-padding: 10; -fx-border-color: #90CAF9; -fx-border-width: 1;");
		                v.getChildren().add(label);
		            }

		            if (hasData) {
		                ScrollPane scrollPane = new ScrollPane(v);
		                scrollPane.setFitToWidth(true);
		                scrollPane.setStyle("-fx-background: #E3F2FD;");
		                Scene scene = new Scene(scrollPane, 400, 350);
		                Stage stage = new Stage();
		                stage.setTitle("Invoices for Orders Last Month");
		                stage.setScene(scene);
		                stage.showAndWait();
		            } else {
		                showAlert(Alert.AlertType.INFORMATION, "No Invoices", "No invoices found for orders placed last month.");
		            }

		        } catch (SQLException ex) {
		            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not fetch data: " + ex.getMessage());
		        } catch (Exception ex) {
		            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + ex.getMessage());
		        }
		    }

		    if (cb.getValue().equals("Last year")) {
		    	LocalDate now = LocalDate.now();
		    	LocalDate oneYearAgo = now.minusYears(1);

		        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412")) {
		        	String sql = """
		        		    SELECT i.Invoice_ID, i.Order_ID, i.date, i.Total_Amount, o.Order_Date
		        		    FROM Invoices i
		        		    JOIN Orders o ON i.Order_ID = o.Order_ID
		        		    WHERE o.Order_Date BETWEEN ? AND ?
		        		""";

		        		PreparedStatement pstmt = conn.prepareStatement(sql);
		        		pstmt.setDate(1, java.sql.Date.valueOf(oneYearAgo));
		        		pstmt.setDate(2, java.sql.Date.valueOf(now));

		            ResultSet rs = pstmt.executeQuery();

		            VBox v = new VBox(10);
		            v.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 20;");
		            boolean hasData = false;

		            while (rs.next()) {
		                hasData = true;
		                Label label = new Label(
		                    "Invoice_ID: " + rs.getInt("Invoice_ID") + "\n" +
		                    "Order_ID: " + rs.getInt("Order_ID") + "\n" +
		                    "Order Date: " + rs.getDate("Order_Date").toLocalDate() + "\n" +
		                    "Invoice Date: " + rs.getTimestamp("date").toLocalDateTime() + "\n" +
		                    "Total Amount: " + rs.getDouble("Total_Amount")
		                );
		                label.setStyle("-fx-font-size: 16px; -fx-text-fill: #0D47A1; -fx-font-weight: bold; -fx-background-color: #BBDEFB; -fx-padding: 10; -fx-border-color: #90CAF9; -fx-border-width: 1;");
		                v.getChildren().add(label);
		            }

		            if (hasData) {
		                ScrollPane scrollPane = new ScrollPane(v);
		                scrollPane.setFitToWidth(true);
		                scrollPane.setStyle("-fx-background: #E3F2FD;");
		                Scene scene = new Scene(scrollPane, 400, 350);
		                Stage stage = new Stage();
		                stage.setTitle("Invoices for Last Year Orders");
		                stage.setScene(scene);
		                stage.showAndWait();
		            } else {
		                showAlert(Alert.AlertType.INFORMATION, "No Invoices", "No invoices found for orders placed last year.");
		            }

		        } catch (SQLException ex) {
		            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not fetch data: " + ex.getMessage());
		        } catch (Exception ex) {
		            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + ex.getMessage());
		        }
		    }
		});
		//back button
		ImageView im5 = new ImageView(new Image("eback.png"));
		im5.setFitWidth(80);
		im5.setFitHeight(60);
		Button back = new Button("Back", im5);
		back.setContentDisplay(ContentDisplay.TOP);
		back.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);"
				+ "-fx-text-fill: #2D3436;" + "-fx-font-weight: bold;" + "-fx-font-size: 14px;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");
		 back.setOnAction(event->{
			   SecundInterface s=new SecundInterface();
			   primaryStage.setScene(s.getScene(primaryStage));
		       });
		
		HBox box = new HBox(15);
		box.setAlignment(Pos.CENTER);
		box.getChildren().addAll(cb,back);
		bp.setTop(box);

		vb.getChildren().addAll(box, obj.getInvoices());

		Scene second = new Scene(bp);
		second.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(second);
		primaryStage.setFullScreen(true);

		return second;
	}
	private void showAlert(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
