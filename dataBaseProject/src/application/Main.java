package application;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
SecundInterface obj=new SecundInterface();
@Override
public void start(Stage primaryStage) {
	showLoginScreen(primaryStage);
}

private void showLoginScreen(Stage stage) {
	VBox leftPane = new VBox(20);
	leftPane.setAlignment(Pos.CENTER);
	leftPane.setPadding(new Insets(30));
	leftPane.setStyle("-fx-background-color: #077A7D;");

	ImageView logoImage = new ImageView(new Image("ph.jpg"));
	logoImage.setFitWidth(220);
	logoImage.setFitHeight(120);

	Label title = new Label("Welcome to Pharmacy");
	title.setTextFill(Color.WHITE);
	title.setFont(Font.font("Arial", 28));
	title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 28));
	title.setOnMouseClicked(e -> {

		title.setTextFill(Color.web("#00FF00"));
	});

	PasswordField passwordField = new PasswordField();
	passwordField.setPromptText("Enter Password");
	passwordField.setMaxWidth(220);
	passwordField.setStyle(
			"-fx-background-color: white; -fx-text-fill: black; -fx-border-color: #00FF00; -fx-border-width: 2; -fx-border-radius: 5;");

	Button startBtn = new Button("Start");
	startBtn.setStyle("""
			    -fx-background-color: white;
			    -fx-text-fill: #1abc9c;
			    -fx-font-size: 19px;
			    -fx-font-weight: bold;
			    -fx-background-radius: 20;
			    -fx-padding: 6 18;
			""");

	startBtn.setOnAction(e -> {
		String pass = passwordField.getText();
		if (pass.equals("1234")) {
			obj.getScene(stage);
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect password!");
			alert.showAndWait();
		}
	});

	leftPane.getChildren().addAll(logoImage, title, passwordField, startBtn);
	StackPane rightPane = new StackPane();
	rightPane.setStyle("-fx-background-color: #9ACBD0;");
	
	ImageView rightImage2 = new ImageView(new Image("ss.png"));
	rightImage2.setFitWidth(500);
	rightImage2.setFitHeight(500);

	StackPane.setAlignment(rightImage2,Pos.CENTER);
	rightPane.getChildren().addAll(rightImage2);

	HBox mainLayout = new HBox(leftPane, rightPane);
	HBox.setHgrow(leftPane, Priority.NEVER);
	HBox.setHgrow(rightPane, Priority.ALWAYS);
	leftPane.setMinWidth(400); 
	leftPane.setPrefWidth(550); 

	
	

	Scene scene = new Scene(mainLayout, 900, 500);
	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	stage.setTitle("Pharmacy Login");
	stage.setScene(scene);
	stage.setFullScreen(true); 
	stage.show();
}



public static void main(String[] args) {
	launch(args);
}
}
