package main;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class main extends Application {
	public static Stage window;
	public static Scene scene1;
	Scene scene2;
	 static Parent root;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
    	this.window = primaryStage;
    	// Set window title
    	this.window.setTitle("Hello World!");
      //  Controller controller = new Controller(this.window);
    	
    	showMainView();
    
      
    }
    private void showMainView() throws IOException {
    	FXMLLoader loader = new FXMLLoader(main.class.getResource("view/MainScene.fxml"));
    	//loader.setLocation(main.class.getResource("view/MainScene.fxml"));
    	loader.setController(Controller.getInstance());
         root = loader.load();
         //Controller myController = (Controller)loader.getController();
       //  myController.testAccess();
    	scene1 = new Scene(root);
    	window.setScene(scene1);
    	window.show();

    }
    public static void setSceneBack() {
    	scene1 = null;
    	scene1 = new Scene(root);

    	window.setScene(scene1);

	}
}
