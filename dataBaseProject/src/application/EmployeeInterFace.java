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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeInterFace {
	ViewTable obj = new ViewTable();
	TextField tfid;
	public Scene getScene(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		VBox vb = new VBox(10);
		vb.setAlignment(Pos.CENTER);
		bp.setCenter(vb);
		bp.setStyle("-fx-background-color: #077A7D;");

		ImageView im1 = new ImageView(new Image("ea.png"));
		im1.setFitWidth(60);
		im1.setFitHeight(60);
		Button add = new Button("AddEmployee", im1);
		add.setContentDisplay(ContentDisplay.TOP);
		add.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + "-fx-text-fill: #2D3436;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;"
				+ "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");

		add.setOnAction(event -> {
			AddEmployee ad = new AddEmployee();
			primaryStage.setScene(ad.getScene(primaryStage));
		});
		ImageView im2 = new ImageView(new Image("er.png"));
		im2.setFitWidth(60);
		im2.setFitHeight(60);
		Button remove = new Button("RemoveEmployee", im2);
		remove.setContentDisplay(ContentDisplay.TOP);
		remove.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);"
				+ "-fx-text-fill: #2D3436;" + "-fx-font-weight: bold;" + "-fx-font-size: 14px;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;"); 
		remove.setOnAction(event -> {
		    String idText = tfid.getText().trim();
		    if (idText.isEmpty()) {
		        showAlert(Alert.AlertType.WARNING, "Warning", "Please enter Employee ID.");
		        return;
		    }

		    int id;
		    try {
		        id = Integer.parseInt(idText);
		    } catch (NumberFormatException e) {
		        showAlert(Alert.AlertType.ERROR, "Error", "Invalid Employee ID. Please enter a valid number.");
		        return;
		    }

		    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412")) {

		        String checkSql = "SELECT * FROM Employee WHERE Employee_ID = ?";
		        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
		            checkStmt.setInt(1, id);
		            try (ResultSet rs = checkStmt.executeQuery()) {
		                if (!rs.next()) {
		                    showAlert(Alert.AlertType.ERROR, "Error", "Employee ID not found.");
		                    return;
		                }
		            }
		        }

		        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
		        confirmAlert.setTitle("Confirm Deletion");
		        confirmAlert.setHeaderText(null);
		        confirmAlert.setContentText("Are you sure you want to delete Employee ID " + id + "?");

		        confirmAlert.showAndWait().ifPresent(response -> {
		            if (response == ButtonType.OK) {
		                String deleteSql = "DELETE FROM Employee WHERE Employee_ID = ?";
		                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
		                    deleteStmt.setInt(1, id);
		                    int rowsDeleted = deleteStmt.executeUpdate();
		                    if (rowsDeleted > 0) {
		                        showAlert(Alert.AlertType.INFORMATION, "Success", "Employee deleted successfully.");
		                        tfid.clear();
		                    } else {
		                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Employee.");
		                    }
		                    ViewTable.list2.clear();
		                    ViewTable.tv2.setItems(ViewTable.getEmployeeList());
		                } catch (SQLException ex) {
		                    showAlert(Alert.AlertType.ERROR, "Database Error", "Error deleting Employee: " + ex.getMessage());
		                    ex.printStackTrace();
		                }
		            }
		        });

		    } catch (SQLException ex) {
		        showAlert(Alert.AlertType.ERROR, "Database Error", "Error connecting to database: " + ex.getMessage());
		        ex.printStackTrace();
		    }
		});



		ImageView im3 = new ImageView(new Image("eu.png"));
		im3.setFitWidth(60);
		im3.setFitHeight(60);
		Button update = new Button("UpdateEmployee", im3);
		update.setContentDisplay(ContentDisplay.TOP);
		update.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);"
				+ "-fx-text-fill: #2D3436;" + "-fx-font-weight: bold;" + "-fx-font-size: 14px;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");

		update.setOnAction(event -> {
		    String idText = tfid.getText().trim();

		    if (idText.isEmpty()) {
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("Warning");
		        alert.setHeaderText(null);
		        alert.setContentText("Please enter the Employee ID.");
		        alert.getDialogPane().setStyle("-fx-background-color: #e6f0ff; -fx-font-size: 14px;");
		        alert.showAndWait();
		        return;
		    }

		    try {
		        int inputId = Integer.parseInt(idText);
		        boolean found = false;

		        for (Employee med : ViewTable.list2) {
		            if (med.getEmployeeId()== inputId) {
		                found = true;
		                UpdateEmployee updateM = new UpdateEmployee();
		                primaryStage.setScene(updateM.getScene(primaryStage, med));
		                break;
		            }
		        }

		        if (!found) {
		            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		            alert.setTitle("Not Found");
		            alert.setHeaderText(null);
		            alert.setContentText("Employee not found.");
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


		ImageView im4 = new ImageView(new Image("es.png"));
		im4.setFitWidth(60);
		im4.setFitHeight(60);
		Button search = new Button("SearchEmployee", im4);
		search.setContentDisplay(ContentDisplay.TOP);
		search.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);"
				+ "-fx-text-fill: #2D3436;" + "-fx-font-weight: bold;" + "-fx-font-size: 14px;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");
		search.setOnAction(e -> {
		    String idText = tfid.getText().trim();

		    if (idText.isEmpty()) {
		        showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a Employee ID to search.");
		        return;
		    }

		    try {
		        int id = Integer.parseInt(idText);
		        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
		        Statement stmt = conn.createStatement();
		        String sql = "SELECT * FROM Employee WHERE Employee_ID = " + id;
		        ResultSet rs = stmt.executeQuery(sql);

		        if (rs.next()) {
		            VBox v = new VBox(10);
		            v.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 20;");
		            Label label = new Label(
		                "ID: " + rs.getInt("Employee_ID") + "\n" +
		                "Name: " + rs.getString("Name") + "\n" +
		                "phone: " + rs.getString("phone") + "\n" +
		                "role: " + rs.getString("role") + "\n" +
		                "shiftHours: " + rs.getInt("shift_hours") + "\n" +
		                "salary: " + rs.getDouble("salary")
		            );
		            label.setStyle("-fx-font-size: 16px; -fx-text-fill: #0D47A1; -fx-font-weight: bold;");
		            v.getChildren().add(label);
		            Scene scene = new Scene(v, 350, 300);
		            Stage stage = new Stage();
		            stage.setTitle("Employee Details");
		            stage.setScene(scene);
		            stage.showAndWait();
		        } else {
		            showAlert(Alert.AlertType.WARNING, "Not Found", "No Employee found with ID: " + id);
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
		box.getChildren().addAll(add, remove, update, search,back);
		bp.setTop(box);

		Label id = new Label("IDEmployee:");
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

		vb.getChildren().addAll(idbox, box, obj.getEmployeeView());

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
