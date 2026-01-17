package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SecundInterface {
	MedicineInterface objj=new MedicineInterface();
	EmployeeInterFace oo=new EmployeeInterFace();
	Cashiers cc=new Cashiers();
	InvoicesInterface ii=new InvoicesInterface();
	public Scene getScene(Stage primaryStage) {
		BorderPane root = new BorderPane();
		HBox hb = new HBox(10);
		//button for medicine
		ImageView im1 = new ImageView(new Image("mid.png"));
		im1.setFitWidth(100);
		im1.setFitHeight(100);
		Button b = new Button("medicine", im1);
		b.setContentDisplay(ContentDisplay.TOP);
		b.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" +  
				"-fx-text-fill: #2D3436;" + 
				"-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;" + 
				"-fx-padding: 10 15 15 15;" +  
				"-fx-cursor: hand;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + 
				"-fx-border-color: #B03A74;" + 
				"-fx-border-width: 2;" +  
				"-fx-border-radius: 15;" 
		);
		b.setOnAction(e->{
			primaryStage.setScene(objj.getScene(primaryStage));
		});
		//button for out of stock
		ImageView im2 = new ImageView(new Image("sold.png"));
		im2.setFitWidth(100);
		im2.setFitHeight(100);
		Button b2 = new Button("out of stock", im2);
		b2.setContentDisplay(ContentDisplay.TOP);
		b2.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + 
				"-fx-text-fill: #2D3436;" +  
				"-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;" +  
				"-fx-padding: 10 15 15 15;" + 
				"-fx-cursor: hand;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + 
				"-fx-border-color: #B03A74;" + 
				"-fx-border-width: 2;" + 
				"-fx-border-radius: 15;" 
		);
		b2.setOnAction(e -> {
		    try {
		        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
		        Statement stmt = conn.createStatement();
		        String sql = "SELECT * FROM Medicines WHERE Quantity = 0";
		        ResultSet rs = stmt.executeQuery(sql);

		        boolean found = false;
		        VBox v = new VBox(15);
		        v.setStyle("-fx-padding: 20; -fx-background-color: #f4f9ff;");

		        while (rs.next()) {
		            found = true;

		            VBox card = new VBox(5);
		            card.setStyle(
		                "-fx-background-color: #ffffff;" +
		                "-fx-border-color: #90caf9;" +
		                "-fx-border-width: 2;" +
		                "-fx-padding: 10;" +
		                "-fx-background-radius: 10;" +
		                "-fx-border-radius: 10;" +
		                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);"
		            );

		            Label label = new Label(
		                "Medicine ID: " + rs.getInt("Medicines_ID") + "\n" +
		                "Name: " + rs.getString("Name") + "\n" +
		                "Category ID: " + rs.getInt("Category_ID") + "\n" +
		                "Price: $" + rs.getDouble("Price") + "\n" +
		                "Quantity: " + rs.getInt("Quantity") + "\n" +
		                "Expiry Date: " + rs.getDate("Expiry_Date") + "\n" +
		                "Supplier ID: " + rs.getInt("Supplier_ID")
		            );
		            label.setStyle("-fx-font-size: 14px; -fx-text-fill: #1565c0; -fx-font-weight: bold;");
		            card.getChildren().add(label);
		            v.getChildren().add(card);
		        }

		        if (found) {
		            ScrollPane scrollPane = new ScrollPane(v);
		            scrollPane.setFitToWidth(true);
		            scrollPane.setStyle("-fx-background: #f4f9ff;");

		            Scene scene = new Scene(scrollPane, 500, 500);
		            Stage stage = new Stage();
		            stage.setTitle("Out of Stock Medicines");
		            stage.setScene(scene);
		            stage.showAndWait();
		        } else {
		            showAlert(Alert.AlertType.INFORMATION, "Not Found", "No medicine with quantity = 0.");
		        }

		        conn.close();

		    } catch (SQLException ex) {
		        showAlert(Alert.AlertType.ERROR, "Database Error", "Could not fetch data: " + ex.getMessage());
		        ex.printStackTrace();
		    } catch (Exception ex) {
		        showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + ex.getMessage());
		        ex.printStackTrace();
		    }
		});

		//button for Expiry soon
		ImageView im3 = new ImageView(new Image("time.png"));
		im3.setFitWidth(100);
		im3.setFitHeight(100);
		Button b3 = new Button("Expiry soon", im3);
		b3.setContentDisplay(ContentDisplay.TOP);
		b3.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + 
				"-fx-text-fill: #2D3436;" +  
				"-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;" + 
				"-fx-padding: 10 15 15 15;" + 
				"-fx-cursor: hand;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + 
				"-fx-border-color: #B03A74;" +  
				"-fx-border-width: 2;" + 
				"-fx-border-radius: 15;" 
		);
		b3.setOnAction(e -> {
		    try {
		        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
		        LocalDate day = LocalDate.now();
		        LocalDate after = day.plusDays(10);
		        java.sql.Date sqlAfter = java.sql.Date.valueOf(after); // تحويل التاريخ لصيغة SQL

		        String sql = "SELECT * FROM Medicines WHERE Expiry_Date <= ?";
		        PreparedStatement pstmt = conn.prepareStatement(sql);
		        pstmt.setDate(1, sqlAfter); // ربط التاريخ بالاستعلام
		        ResultSet rs = pstmt.executeQuery();

		        boolean found = false;
		        VBox v = new VBox(15);
		        v.setStyle("-fx-padding: 20; -fx-background-color: #f4f9ff;");

		        while (rs.next()) {
		            found = true;

		            VBox card = new VBox(5);
		            card.setStyle(
		                "-fx-background-color: #ffffff;" +
		                "-fx-border-color: #90caf9;" +
		                "-fx-border-width: 2;" +
		                "-fx-padding: 10;" +
		                "-fx-background-radius: 10;" +
		                "-fx-border-radius: 10;" +
		                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);"
		            );

		            Label label = new Label(
		                "Medicine ID: " + rs.getInt("Medicines_ID") + "\n" +
		                "Name: " + rs.getString("Name") + "\n" +
		                "Category ID: " + rs.getInt("Category_ID") + "\n" +
		                "Price: $" + rs.getDouble("Price") + "\n" +
		                "Quantity: " + rs.getInt("Quantity") + "\n" +
		                "Expiry Date: " + rs.getDate("Expiry_Date") + "\n" +
		                "Supplier ID: " + rs.getInt("Supplier_ID")
		            );
		            label.setStyle("-fx-font-size: 14px; -fx-text-fill: #1565c0; -fx-font-weight: bold;");
		            card.getChildren().add(label);
		            v.getChildren().add(card);
		        }

		        if (found) {
		            ScrollPane scrollPane = new ScrollPane(v);
		            scrollPane.setFitToWidth(true);
		            scrollPane.setStyle("-fx-background: #f4f9ff;");

		            Scene scene = new Scene(scrollPane, 500, 500);
		            Stage stage = new Stage();
		            stage.setTitle("Medicines Expiring Soon");
		            stage.setScene(scene);
		            stage.showAndWait();
		        } else {
		            showAlert(Alert.AlertType.INFORMATION, "Not Found", "No medicines expire within the next 10 days.");
		        }

		        conn.close();

		    } catch (SQLException ex) {
		        showAlert(Alert.AlertType.ERROR, "Database Error", "Could not fetch data: " + ex.getMessage());
		        ex.printStackTrace();
		    } catch (Exception ex) {
		        showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + ex.getMessage());
		        ex.printStackTrace();
		    }
		});

		HBox hb2=new HBox(10);
		//button for employee
		ImageView im4 = new ImageView(new Image("employee.png"));
		im4.setFitWidth(100);
		im4.setFitHeight(100);
		Button b4 = new Button("Employee", im4);
		b4.setContentDisplay(ContentDisplay.TOP);
		b4.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + 
				"-fx-text-fill: #2D3436;" + 
				"-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;" +  
				"-fx-padding: 10 15 15 15;" + 
				"-fx-cursor: hand;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" +  
				"-fx-border-color: #B03A74;" +
				"-fx-border-width: 2;" + 
				"-fx-border-radius: 15;"  
		);
		b4.setOnAction(e->{
			primaryStage.setScene(oo.getScene(primaryStage));
		});
		//button for CASHIERS
				ImageView im5 = new ImageView(new Image("cashe.png"));
				im5.setFitWidth(100);
				im5.setFitHeight(100);
				Button b5 = new Button("Cashiers", im5);
				b5.setContentDisplay(ContentDisplay.TOP);
				b5.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + 
						"-fx-text-fill: #2D3436;" + 
						"-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;" +  
						"-fx-padding: 10 15 15 15;" + 
						"-fx-cursor: hand;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" +  
						"-fx-border-color: #B03A74;" +
						"-fx-border-width: 2;" + 
						"-fx-border-radius: 15;"  
				);
				b5.setOnAction(e->{
					primaryStage.setScene(cc.getScene(primaryStage));
				});
			//lable
				Label title = new Label("Pharmacy System");

				title.setStyle(
				    "-fx-text-fill: #B03A74;" +
				    "-fx-font-size: 32px;" +
				    "-fx-font-weight: bold;" +
				    "-fx-font-family: 'Arial';" +
				    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 2, 0.5, 1, 1);"
				);
				//button for Invoices
				ImageView im6 = new ImageView(new Image("in.png"));
				im6.setFitWidth(100);
				im6.setFitHeight(100);
				Button b6 = new Button("Invoices", im6);
				b6.setContentDisplay(ContentDisplay.TOP);
				b6.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + 
					"-fx-text-fill: #2D3436;" + 
						"-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;" +  
						"-fx-padding: 10 15 15 15;" + 
						"-fx-cursor: hand;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" +  
						"-fx-border-color: #B03A74;" +
						"-fx-border-width: 2;" + 
						"-fx-border-radius: 15;"  
				);
				b6.setOnAction(e->{
					primaryStage.setScene(ii.getScene(primaryStage));
				});
	    //top menu
				MenuBar top=new MenuBar();
				//exit
				Menu exit=new Menu("Exit");
				MenuItem ex=new MenuItem("Exit");
				exit.getItems().add(ex);
				ex.setOnAction(e->{
					primaryStage.close();
				});
				top.getMenus().addAll(exit);
		VBox vb=new VBox(20);
		hb.getChildren().addAll(b, b2, b3);
		hb.setAlignment(Pos.CENTER);
		hb2.getChildren().addAll(b4,b5,b6);
		hb2.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(title,hb,hb2);
		vb.setAlignment(Pos.CENTER);
		root.setCenter(vb);
		root.setTop(top);
		root.setStyle("-fx-background-color: #077A7D;");
		Scene second = new Scene(root);
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
