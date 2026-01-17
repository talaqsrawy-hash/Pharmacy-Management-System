package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddEmployee {
	public Scene getScene(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: linear-gradient(to bottom right, #077A7D, #004D4D);"
				+ "-fx-border-color: #ffffff;" + "-fx-border-width: 6;" + "-fx-border-radius: 25;"
				+ "-fx-background-radius: 25;" + "-fx-padding: 30;");

		// الصورة اللي فوق
		ImageView headerImage = new ImageView(new Image(getClass().getResourceAsStream("flower.png")));
		headerImage.setFitWidth(130);
		headerImage.setPreserveRatio(true);

		Label l = new Label("AddEmployee");
		l.setStyle("-fx-text-fill: #B03A74; -fx-font-size: 60px; -fx-font-weight: normal; -fx-font-style: italic;");

		VBox headerBox = new VBox(10, headerImage, l);
		headerBox.setAlignment(Pos.CENTER);

		HBox boxid = new HBox(10);
		Label labelID = new Label("EmployeeID:");
		labelID.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		int nextID = getNextMedicineID();
		TextField textID = new TextField();
		textID.setText(String.valueOf(nextID));
		textID.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		textID.setEditable(false);
		textID.setPrefWidth(300);
		textID.setPrefHeight(30);
		boxid.setAlignment(Pos.CENTER);
		boxid.getChildren().addAll(labelID, textID);

		HBox boxname = new HBox(10);
		Label labelName = new Label("EmployeeName:");
		labelName.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		TextField textName = new TextField();
		textName.setPromptText("Enter the EmployeeName...");
		textName.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		textName.setPrefWidth(300);
		textName.setPrefHeight(30);
		boxname.setAlignment(Pos.CENTER);
		boxname.getChildren().addAll(labelName, textName);

		HBox boxPhone = new HBox(10);
		Label labelPhone = new Label("EmployeePhone:");
		labelPhone
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		TextField textPhone = new TextField();
		textPhone.setPromptText("Enter the EmployeePhone...");
		textPhone
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		boxPhone.setAlignment(Pos.CENTER);
		textPhone.setPrefWidth(300);
		textPhone.setPrefHeight(30);
		boxPhone.getChildren().addAll(labelPhone, textPhone);

		HBox boxRole = new HBox(10);
		Label labelPrice = new Label("EmployeeRole:");
		labelPrice
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		TextField textRole = new TextField();
		textRole.setPromptText("Enter the EmployeeRole...");
		textRole.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		boxRole.setAlignment(Pos.CENTER);
		textRole.setPrefWidth(300);
		textRole.setPrefHeight(30);
		boxRole.getChildren().addAll(labelPrice, textRole);

		HBox boxShift = new HBox(10);
		Label labelQuantity = new Label("EmployeeShiftHours:");
		labelQuantity
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		TextField textShift = new TextField();
		textShift.setPromptText("Enter the EmployeeShiftHours...");
		textShift
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		boxShift.setAlignment(Pos.CENTER);
		textShift.setPrefWidth(300);
		textShift.setPrefHeight(30);
		boxShift.getChildren().addAll(labelQuantity, textShift);

		HBox boxSalary = new HBox(10);
		Label labelExpiryDate = new Label("EmployeeSalary:");
		labelExpiryDate
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		TextField textSalary = new TextField();
		textSalary.setPromptText("Enter the EmployeeSalary...");
		textSalary.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		textSalary.setPrefWidth(300);
		textSalary.setPrefHeight(30);
		boxSalary.setAlignment(Pos.CENTER);
		boxSalary.getChildren().addAll(labelExpiryDate, textSalary);

		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);

		ImageView im2 = new ImageView(new Image("eaa.png"));
		im2.setFitWidth(80);
		im2.setFitHeight(80);
		Button b2 = new Button("AddEmployee", im2);
		b2.setPrefWidth(150);
		b2.setPrefHeight(80);
		b2.setContentDisplay(ContentDisplay.TOP);
		b2.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + "-fx-text-fill: #2D3436;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;"
				+ "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");
		b2.setOnAction(e -> {
			try {
				int id = Integer.parseInt(textID.getText().trim());
				String name = textName.getText().trim();
				String phone = textPhone.getText().trim();
				String role = textRole.getText().trim();
				int shiftHours = Integer.parseInt(textShift.getText().trim());
				double salary = Double.parseDouble(textSalary.getText().trim());

				String insertQuery = "INSERT INTO Employee (Employee_ID, Name, Phone, Role, Shift_Hours, Salary) VALUES ("
						+ id + ", '" + name + "', '" + phone + "', '" + role + "', " + shiftHours + ", " + salary + ")";

				try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
					 Statement stmt = con.createStatement()) {

					int rowsInserted = stmt.executeUpdate(insertQuery);
					if (rowsInserted > 0) {
						showAlert(Alert.AlertType.INFORMATION, "Success", "Employee added successfully!");

						textID.clear();
						textName.clear();
						textPhone.clear();
						textRole.clear();
						textShift.clear();
						textSalary.clear();
					}
				} catch (SQLException ex) {
					showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add employee:\n" + ex.getMessage());
					ex.printStackTrace();
				}
			} catch (NumberFormatException ex) {
				showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numeric values for ID, Shift Hours, and Salary.");
			}
		});


		ImageView im3 = new ImageView(new Image("back1.png"));
		im3.setFitWidth(80);
		im3.setFitHeight(80);
		Button b3 = new Button("Back", im3);
		b3.setPrefWidth(80);
		b3.setPrefHeight(80);
		b3.setContentDisplay(ContentDisplay.TOP);
		b3.setStyle("-fx-background-color: linear-gradient(to bottom, #7AE2CF, #2EC4B6);" + "-fx-text-fill: #2D3436;"
				+ "-fx-font-weight: bold;" + "-fx-font-size: 14px;" + "-fx-background-radius: 15;"
				+ "-fx-padding: 10 15 15 15;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0.5, 0, 2);" + "-fx-border-color: #B03A74;"
				+ "-fx-border-width: 2;" + "-fx-border-radius: 15;");

		b3.setOnAction(event -> {
			ViewTable.list.clear();
			EmployeeInterFace mi = new EmployeeInterFace();
			primaryStage.setScene(mi.getScene(primaryStage));
		});

		HBox boxd = new HBox(20);
		boxd.setAlignment(Pos.CENTER);
		boxd.getChildren().addAll(b2, b3);
		box.getChildren().addAll(headerBox, boxid, boxname, boxPhone, boxRole, boxShift, boxSalary);

		pane.setCenter(box);
		pane.setRight(boxd);
		Scene add = new Scene(pane);
		add.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(add);
		primaryStage.setFullScreen(true);
		return add;
	}

	private void showAlert(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	public int getNextMedicineID() {
	    String sql = """
	        SELECT AUTO_INCREMENT
	        FROM information_schema.TABLES
	        WHERE TABLE_SCHEMA = 'PharmacyZ' AND TABLE_NAME = 'Employee'
	    """;

	    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root", "Tala1230412");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        if (rs.next()) {
	            return rs.getInt("AUTO_INCREMENT");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; 
	}
}
