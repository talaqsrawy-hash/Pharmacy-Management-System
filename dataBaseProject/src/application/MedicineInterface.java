package application;

import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MedicineInterface {
	ViewTable obj = new ViewTable();
	TextField tfid;
	public Scene getScene(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		VBox vb = new VBox(10);
		vb.setAlignment(Pos.CENTER);
		bp.setCenter(vb);
		bp.setStyle("-fx-background-color: #077A7D;");

		ImageView im1 = new ImageView(new Image("addm1.png"));
		im1.setFitWidth(60);
		im1.setFitHeight(60);
		Button add = new Button("AddMedicine", im1);
		add.setContentDisplay(ContentDisplay.TOP);
		add.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + "-fx-text-fill: #2D3436;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;"
				+ "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");

		add.setOnAction(event -> {
			AddMedicine medicineInterface = new AddMedicine();
			primaryStage.setScene(medicineInterface.getScene(primaryStage));
		});
		ImageView im2 = new ImageView(new Image("de3.png"));
		im2.setFitWidth(60);
		im2.setFitHeight(60);
		
		//remove action to remove the medicine information
		Button remove = new Button("RemoveMedicine", im2);
		remove.setContentDisplay(ContentDisplay.TOP);
		remove.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);"
				+ "-fx-text-fill: #2D3436;" + "-fx-font-weight: bold;" + "-fx-font-size: 14px;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;"); 
		remove.setOnAction(event -> {
		    String idText = tfid.getText().trim();
		    if (idText.isEmpty()) {
		        showAlert(Alert.AlertType.WARNING, "Warning", "Please enter Medicine ID.");
		        return;
		    }

		    int id;
		    try {
		        id = Integer.parseInt(idText);
		    } catch (NumberFormatException e) {
		        showAlert(Alert.AlertType.ERROR, "Error", "Invalid Medicine ID. Please enter a valid number.");
		        return;
		    }

		    try {
		        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
		        Statement stmt = conn.createStatement();

		        String checkSql = "SELECT * FROM Medicines WHERE Medicines_ID = " + id;
		        ResultSet rs = stmt.executeQuery(checkSql);

		        if (!rs.next()) {
		            showAlert(Alert.AlertType.ERROR, "Error", "Medicine ID not found.");
		            conn.close();
		            return;
		        }

		        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
		        confirmAlert.setTitle("Confirm Deletion");
		        confirmAlert.setHeaderText(null);
		        confirmAlert.setContentText("Are you sure you want to delete Medicine ID " + id + "?");

		        confirmAlert.showAndWait().ifPresent(response -> {
		            if (response == ButtonType.OK) {
		                try {
		                    String deleteSql = "DELETE FROM Medicines WHERE Medicines_ID = " + id;
		                    int rowsDeleted = stmt.executeUpdate(deleteSql);
		                    if (rowsDeleted > 0) {
		                        showAlert(Alert.AlertType.INFORMATION, "Success", "Medicine deleted successfully.");
		                        tfid.clear();
		                    } else {
		                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Medicine.");
		                    }
		                } catch (SQLException ex) {
		                    showAlert(Alert.AlertType.ERROR, "Database Error", "Error deleting Medicine: " + ex.getMessage());
		                }
		            }
		            ViewTable.list.clear();
		            ViewTable.tv.setItems(ViewTable.getMedicinesList());
		        });

		        conn.close();

		    } catch (SQLException ex) {
		        showAlert(Alert.AlertType.ERROR, "Database Error", "Error connecting to database: " + ex.getMessage());
		    }
		    
		});


		ImageView im3 = new ImageView(new Image("up2.png"));
		im3.setFitWidth(60);
		im3.setFitHeight(60);
		Button update = new Button("UpdateMedicine", im3);
		update.setContentDisplay(ContentDisplay.TOP);
		update.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);"
				+ "-fx-text-fill: #2D3436;" + "-fx-font-weight: bold;" + "-fx-font-size: 14px;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");
		
//update action to update the medicine information
		update.setOnAction(event -> {
		    String idText = tfid.getText().trim();

		    if (idText.isEmpty()) {
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("Warning");
		        alert.setHeaderText(null);
		        alert.setContentText("Please enter the medicine ID.");
		        alert.getDialogPane().setStyle("-fx-background-color: #e6f0ff; -fx-font-size: 14px;");
		        alert.showAndWait();
		        return;
		    }

		    try {
		        int inputId = Integer.parseInt(idText);
		        boolean found = false;

		        for (Medicines med : ViewTable.list) {
		            if (med.getMedicineId() == inputId) {
		                found = true;
		                UpdateMedicine updateM = new UpdateMedicine();
		                primaryStage.setScene(updateM.getScene(primaryStage, med));
		                break;
		            }
		        }

		        if (!found) {
		            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		            alert.setTitle("Not Found");
		            alert.setHeaderText(null);
		            alert.setContentText("Medicine not found.");
		            alert.getDialogPane().setStyle("-fx-background-color: #e6f0ff; -fx-font-size: 14px;");
		            alert.showAndWait();
		        }

		    } catch (NumberFormatException e) {
		        // Invalid number entered
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("Invalid Input");
		        alert.setHeaderText(null);
		        alert.setContentText("Please enter a valid numeric ID.");
		        alert.getDialogPane().setStyle("-fx-background-color: #e6f0ff; -fx-font-size: 14px;");
		        alert.showAndWait();
		    }
		});


		ImageView im4 = new ImageView(new Image("search.png"));
		im4.setFitWidth(60);
		im4.setFitHeight(60);
		//search action to search about medicine information
		Button search = new Button("SearchMedicine", im4);
		search.setContentDisplay(ContentDisplay.TOP);
		search.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);"
				+ "-fx-text-fill: #2D3436;" + "-fx-font-weight: bold;" + "-fx-font-size: 14px;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");
		search.setOnAction(e -> {
		    String idText = tfid.getText().trim();

		    if (idText.isEmpty()) {
		        showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a Medicine ID to search.");
		        return;
		    }

		    try {
		        int id = Integer.parseInt(idText);
		        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
		        Statement stmt = conn.createStatement();
		        String sql = "SELECT * FROM Medicines WHERE Medicines_ID = " + id;
		        ResultSet rs = stmt.executeQuery(sql);

		        if (rs.next()) {
		            VBox v = new VBox(10);
		            v.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 20;");
		            Label label = new Label(
		                "ID: " + rs.getInt("Medicines_ID") + "\n" +
		                "Name: " + rs.getString("Name") + "\n" +
		                "Category ID: " + rs.getInt("Category_ID") + "\n" +
		                "Price: " + rs.getDouble("Price") + "\n" +
		                "Quantity: " + rs.getInt("Quantity") + "\n" +
		                "Expiry Date: " + rs.getDate("Expiry_Date") + "\n" +
		                "Supplier ID: " + rs.getInt("Supplier_ID")
		            );
		            label.setStyle("-fx-font-size: 16px; -fx-text-fill: #0D47A1; -fx-font-weight: bold;");
		            v.getChildren().add(label);
		            Scene scene = new Scene(v, 350, 300);
		            Stage stage = new Stage();
		            stage.setTitle("Medicine Details");
		            stage.setScene(scene);
		            stage.showAndWait();
		        } else {
		            showAlert(Alert.AlertType.WARNING, "Not Found", "No medicine found with ID: " + id);
		        }

		        conn.close();

		    } catch (NumberFormatException ex) {
		        showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid numeric ID.");
		    } catch (SQLException ex) {
		        showAlert(Alert.AlertType.ERROR, "Database Error", "Could not fetch data: " + ex.getMessage());
		    } catch (Exception ex) {
		        showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + ex.getMessage());
		    }
		});


		ImageView im5 = new ImageView(new Image("back8.png"));
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

		Label id = new Label("IDMedicine:");
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

		vb.getChildren().addAll(idbox, box, obj.getMedicinesView());

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
