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

public class AddMedicine {
	public Scene getScene(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: linear-gradient(to bottom right, #077A7D, #004D4D);"
				+ "-fx-border-color: #ffffff;" + "-fx-border-width: 6;" + "-fx-border-radius: 25;"
				+ "-fx-background-radius: 25;" + "-fx-padding: 30;");

		// الصورة اللي فوق
		ImageView headerImage = new ImageView(new Image(getClass().getResourceAsStream("flower.png")));
		headerImage.setFitWidth(130);
		headerImage.setPreserveRatio(true);

		Label l = new Label("AddMedicine");
		l.setStyle("-fx-text-fill: #B03A74; -fx-font-size: 60px; -fx-font-weight: normal; -fx-font-style: italic;");

		VBox headerBox = new VBox(10, headerImage, l);
		headerBox.setAlignment(Pos.CENTER);

		HBox boxid = new HBox(10);
		Label labelID = new Label("MedicineID:");
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
		Label labelName = new Label("MedicineName:");
		labelName.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		TextField textName = new TextField();
		textName.setPromptText("Enter the MedicineName...");
		textName.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		textName.setPrefWidth(300);
		textName.setPrefHeight(30);
		boxname.setAlignment(Pos.CENTER);
		boxname.getChildren().addAll(labelName, textName);

		HBox boxcategory = new HBox(10);
		Label labelCategory = new Label("MedicineCategory:");
		labelCategory
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		TextField textCategory = new TextField();
		textCategory.setPromptText("Enter the MedicineCategory...");
		textCategory
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		boxcategory.setAlignment(Pos.CENTER);
		textCategory.setPrefWidth(300);
		textCategory.setPrefHeight(30);
		boxcategory.getChildren().addAll(labelCategory, textCategory);

		HBox boxPrice = new HBox(10);
		Label labelPrice = new Label("MedicinePrice:");
		labelPrice
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		TextField textPrice = new TextField();
		textPrice.setPromptText("Enter the MedicinePrice...");
		textPrice.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		boxPrice.setAlignment(Pos.CENTER);
		textPrice.setPrefWidth(300);
		textPrice.setPrefHeight(30);
		boxPrice.getChildren().addAll(labelPrice, textPrice);

		HBox boxQuantity = new HBox(10);
		Label labelQuantity = new Label("MedicineQuantity:");
		labelQuantity
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		TextField textQuantity = new TextField();
		textQuantity.setPromptText("Enter the MedicineQuantity...");
		textQuantity
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		boxQuantity.setAlignment(Pos.CENTER);
		textQuantity.setPrefWidth(300);
		textQuantity.setPrefHeight(30);
		boxQuantity.getChildren().addAll(labelQuantity, textQuantity);

		DatePicker dp = new DatePicker();
		HBox boxExpiryDate = new HBox(10);
		Label labelExpiryDate = new Label("MedicineExpiryDate:");
		labelExpiryDate
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		dp.setPromptText("Enter the MedicineExpiryDate...");
		dp.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		dp.setPrefWidth(300);
		dp.setPrefHeight(30);
		boxExpiryDate.setAlignment(Pos.CENTER);
		boxExpiryDate.getChildren().addAll(labelExpiryDate, dp);

		HBox boxSupplier = new HBox(10);
		Label labelSupplier = new Label("MedicineSupplier:");
		labelSupplier
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		TextField textSupplier = new TextField();
		textSupplier.setPromptText("Enter the MedicineQuantity...");
		textSupplier
				.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: black; -fx-font-size: 20px;");
		textSupplier.setPrefWidth(300);
		textSupplier.setPrefHeight(30);
		boxSupplier.setAlignment(Pos.CENTER);
		boxSupplier.getChildren().addAll(labelSupplier, textSupplier);

		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);

		ImageView im2 = new ImageView(new Image("add.png"));
		im2.setFitWidth(80);
		im2.setFitHeight(80);
		Button b2 = new Button("AddMedicine", im2);
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
				int id = Integer.parseInt(textID.getText());
				String name = textName.getText();
				int categoryId = Integer.parseInt(textCategory.getText());
				double price = Double.parseDouble(textPrice.getText());
				int quantity = Integer.parseInt(textQuantity.getText());
				LocalDate expiryDate = dp.getValue();
				int supplierId = Integer.parseInt(textSupplier.getText());

				if (name.isEmpty() || expiryDate == null) {
					showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all fields.");
					return;
				}

				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PharmacyZ", "root",
						"Tala1230412");
				Statement stmt = conn.createStatement();

				String sql = "INSERT INTO Medicines (Medicines_ID, Name, Category_ID, Price, Quantity, Expiry_Date, Supplier_ID) VALUES ("
						+ id + ", '" + name + "', " + categoryId + ", " + price + ", " + quantity + ", '" + expiryDate
						+ "', " + supplierId + ")";

				int rowsInserted = stmt.executeUpdate(sql);

				if (rowsInserted > 0) {
					showAlert(Alert.AlertType.INFORMATION, "Success", "Medicine added successfully!");

					textID.clear();
					textName.clear();
					textCategory.clear();
					textPrice.clear();
					textQuantity.clear();
					dp.setValue(null);
					textSupplier.clear();
				}

				conn.close();

			} catch (NumberFormatException ex) {
				showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid numeric values.");
			} catch (SQLException ex) {
				ex.printStackTrace();
				showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add medicine: " + ex.getMessage());
			} catch (Exception ex) {
				ex.printStackTrace();
				showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error: " + ex.getMessage());
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
			MedicineInterface mi = new MedicineInterface();
			primaryStage.setScene(mi.getScene(primaryStage));
		});

		HBox boxd = new HBox(20);
		boxd.setAlignment(Pos.CENTER);
		boxd.getChildren().addAll(b2, b3);
		box.getChildren().addAll(headerBox, boxid, boxname, boxcategory, boxPrice, boxQuantity, boxExpiryDate,
				boxSupplier);

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
	        WHERE TABLE_SCHEMA = 'PharmacyZ' AND TABLE_NAME = 'Medicines'
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
