package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

public class Cashiers {
	ViewTable obj = new ViewTable();
	TextField tfid;
	public Scene getScene(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		VBox vb = new VBox(10);
		vb.setAlignment(Pos.CENTER);
		bp.setCenter(vb);
		bp.setStyle("-fx-background-color: #077A7D;");

		ImageView im1 = new ImageView(new Image("oa.png"));
		im1.setFitWidth(60);
		im1.setFitHeight(60);
		Button add = new Button("AddOrder", im1);
		add.setContentDisplay(ContentDisplay.TOP);
		add.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + "-fx-text-fill: #2D3436;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;"
				+ "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");

		add.setOnAction(event -> {
			AddOrder ad = new AddOrder();
			
			primaryStage.setScene(ad.getScene(primaryStage));
		});
		ImageView im2 = new ImageView(new Image("or.png"));
		im2.setFitWidth(60);
		im2.setFitHeight(60);
		Button remove = new Button("RemoveOrder", im2);
		remove.setContentDisplay(ContentDisplay.TOP);
		
		remove.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);"
				+ "-fx-text-fill: #2D3436;" + "-fx-font-weight: bold;" + "-fx-font-size: 14px;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;"); 
		
		remove.setOnAction(event -> {
		    String idText = tfid.getText().trim();
		    if (idText.isEmpty()) {
		        showAlert(Alert.AlertType.WARNING, "Warning", "Please enter Order ID.");
		        return;
		    }

		    int id;
		    try {
		        id = Integer.parseInt(idText);
		    } catch (NumberFormatException e) {
		        showAlert(Alert.AlertType.ERROR, "Error", "Invalid Order ID. Please enter a valid number.");
		        return;
		    }

		    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412")) {

		        String checkSql = "SELECT * FROM Orders WHERE Order_ID = ?";
		        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
		            checkStmt.setInt(1, id);
		            try (ResultSet rs = checkStmt.executeQuery()) {
		                if (!rs.next()) {
		                    showAlert(Alert.AlertType.ERROR, "Error", "Order ID not found.");
		                    return;
		                }
		            }
		        }

		        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
		        confirmAlert.setTitle("Confirm Deletion");
		        confirmAlert.setHeaderText(null);
		        confirmAlert.setContentText("Are you sure you want to delete Order_ID " + id + "?");

		        confirmAlert.showAndWait().ifPresent(response -> {
		            if (response == ButtonType.OK) {
		                String deleteSql = "DELETE FROM Orders WHERE Order_ID = ?";
		                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
		                    deleteStmt.setInt(1, id);
		                    int rowsDeleted = deleteStmt.executeUpdate();
		                    if (rowsDeleted > 0) {
		                        showAlert(Alert.AlertType.INFORMATION, "Success", "Order deleted successfully.");
		                        tfid.clear();
		                    } else {
		                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Order.");
		                    }
		                    ViewTable.list3.clear();
		                    ViewTable.tv3.setItems(ViewTable.getOrderDList());
		                } catch (SQLException ex) {
		                    showAlert(Alert.AlertType.ERROR, "Database Error", "Error deleting Order: " + ex.getMessage());
		                    ex.printStackTrace();
		                }
		            }
		        });

		    } catch (SQLException ex) {
		        showAlert(Alert.AlertType.ERROR, "Database Error", "Error connecting to database: " + ex.getMessage());
		        ex.printStackTrace();
		    }
		});



		ImageView im3 = new ImageView(new Image("ou.png"));
		im3.setFitWidth(60);
		im3.setFitHeight(60);
		Button update = new Button("UpdateOrder", im3);
		update.setContentDisplay(ContentDisplay.TOP);
		update.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);"
				+ "-fx-text-fill: #2D3436;" + "-fx-font-weight: bold;" + "-fx-font-size: 14px;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");

//		update.setOnAction(event -> {
//		    String idText = tfid.getText().trim();
//
//		    if (idText.isEmpty()) {
//		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//		        alert.setTitle("Warning");
//		        alert.setHeaderText(null);
//		        alert.setContentText("Please enter the Employee ID.");
//		        alert.getDialogPane().setStyle("-fx-background-color: #e6f0ff; -fx-font-size: 14px;");
//		        alert.showAndWait();
//		        return;
//		    }
//
//		    try {
//		        int inputId = Integer.parseInt(idText);
//		        boolean found = false;
//
//		        for (Employee med : ViewTable.list2) {
//		            if (med.getEmployeeId()== inputId) {
//		                found = true;
//		                UpdateEmployee updateM = new UpdateEmployee();
//		                primaryStage.setScene(updateM.getScene(primaryStage, med));
//		                break;
//		            }
//		        }
//
//		        if (!found) {
//		            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//		            alert.setTitle("Not Found");
//		            alert.setHeaderText(null);
//		            alert.setContentText("Employee not found.");
//		            alert.getDialogPane().setStyle("-fx-background-color: #e6f0ff; -fx-font-size: 14px;");
//		            alert.showAndWait();
//		        }
//
//		    } catch (NumberFormatException e) {
//		        // Invalid number entered
//		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//		        alert.setTitle("Invalid Input");
//		        alert.setHeaderText(null);
//		        alert.setContentText("Please enter a valid numeric ID.");
//		        alert.getDialogPane().setStyle("-fx-background-color: #e6f0ff; -fx-font-size: 14px;");
//		        alert.showAndWait();
//		    }
//		});


		ImageView im4 = new ImageView(new Image("os.png"));
		im4.setFitWidth(60);
		im4.setFitHeight(60);
		Button search = new Button("SearchOrder", im4);
		search.setContentDisplay(ContentDisplay.TOP);
		search.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);"
				+ "-fx-text-fill: #2D3436;" + "-fx-font-weight: bold;" + "-fx-font-size: 14px;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");
		
		search.setOnAction(e -> {
		    String idText = tfid.getText().trim();

		    if (idText.isEmpty()) {
		        showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter an Order ID to search.");
		        return;
		    }

		    try {
		        int id = Integer.parseInt(idText);
		        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
		        String sql = "SELECT * FROM Order_Details WHERE Order_ID = ?";
		        PreparedStatement pstmt = conn.prepareStatement(sql);
		        pstmt.setInt(1, id);
		        ResultSet rs = pstmt.executeQuery();

		        VBox v = new VBox(10);
		        v.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 20;");
		        boolean hasData = false;

		        while (rs.next()) {
		            hasData = true;
		            Label label = new Label(
		                "Order_ID: " + rs.getInt("Order_ID") + "\n" +
		                "Medicine_ID: " + rs.getInt("Medicines_ID") + "\n" +
		                "Quantity: " + rs.getInt("Quantity") + "\n" +
		                "Price: " + rs.getDouble("Price")
		            );
		            label.setStyle("-fx-font-size: 16px; -fx-text-fill: #0D47A1; -fx-font-weight: bold; -fx-background-color: #BBDEFB; -fx-padding: 10; -fx-border-color: #90CAF9; -fx-border-width: 1;");
		            v.getChildren().add(label);
		        }

		        conn.close();

		        if (hasData) {
		            ScrollPane scrollPane = new ScrollPane(v);
		            scrollPane.setFitToWidth(true);
		            scrollPane.setStyle("-fx-background: #E3F2FD;");
		            Scene scene = new Scene(scrollPane, 400, 350);
		            Stage stage = new Stage();
		            stage.setTitle("Order Details");
		            stage.setScene(scene);
		            stage.showAndWait();
		        } else {
		            showAlert(Alert.AlertType.WARNING, "Not Found", "No order found with ID: " + id);
		        }

		    } catch (NumberFormatException ex) {
		        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid numeric Order ID.");
		    } catch (SQLException ex) {
		        showAlert(Alert.AlertType.ERROR, "Database Error", "Could not fetch data: " + ex.getMessage());
		    } catch (Exception ex) {
		        showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + ex.getMessage());
		    }
		});


		ImageView im5 = new ImageView(new Image("oback.png"));
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
		box.getChildren().addAll(add, remove, update, search,back);
		bp.setTop(box);

		Label id = new Label("IDOrder:");
		id.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + "-fx-text-fill: #2D3436;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;"
				+ "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");

		tfid = new TextField();
		tfid.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + "-fx-text-fill: #2D3436;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;"
				+ "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");

		HBox idbox = new HBox(15);
		idbox.setAlignment(Pos.CENTER);
		idbox.getChildren().addAll(id, tfid);

		vb.getChildren().addAll(idbox, box, obj.getOrderDetailsView());

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
