package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import AlgorithmFactory.AlgorithmFactory;
import AlgorithmFactory.KosarajuUIAlgorithm;
import AlgorithmFactory.TarjanUIAlgorithm;
public class Controller implements Initializable {
	 SwitchButton switchButton;
	 
	 boolean playing = true, paused = false;
    private static Controller single_instance = null;

    public static Controller getInstance()
    {
        if (single_instance == null)
            single_instance = new Controller();
  
        return single_instance;
    }
    
    
  public SequentialTransition st;
//  Kosaraju g;  uncomment this line
  AlgorithmFactory algorithmFactory;
  
  @FXML
  AnchorPane left_menu_options;
  @FXML
  public TextArea log_text;
  @FXML
  public VBox display_stack;
  @FXML
public Pane display_graph;
  @FXML
  public Button clickme;
  @FXML
  public Circle justDoneDfs;
  @FXML
  public Button next_btn;
  @Override
  public void initialize(URL url, ResourceBundle rb) {
	  runFlashAnimationForJustDoneDFS();
	  renderSwitchButton();
  }
  
  public void renderSwitchButton() {
	  Pane groupStackPane = new Pane();
	  Label manualAutomaticLabel = new Label("Mode: Manual");
	  
	  switchButton = new SwitchButton();
	  
	 next_btn.disableProperty().bind(Bindings.createBooleanBinding(() -> !switchButton.isSelected(),switchButton.selectedProperty()));
	  
//	 next_btn.textProperty().bind(null);
	  groupStackPane.getChildren().add(manualAutomaticLabel);
	  groupStackPane.getChildren().add(switchButton);
	  switchButton.setLayoutX(0);
	  switchButton.setLayoutY(30);
	   
	  groupStackPane.setLayoutX(14);
	  groupStackPane.setLayoutY(120);
	   
	   
	   left_menu_options.getChildren().add(groupStackPane);
}
  
//  public void runAlgorithm(AlgorithmFactory algo) {
//	  algorithmFactory
//}
  public void runFlashAnimationForJustDoneDFS() {
	  justDoneDfs.setOpacity(0.5);
      Timeline timeline = new Timeline(
    		  new KeyFrame(Duration.seconds(0.5), ev -> {
			      justDoneDfs.setFill(Color.web("#ff0000"));
			      

    	}),
    				  new KeyFrame(Duration.seconds(1), ev -> {
    				      justDoneDfs.setFill(Color.web("#00ff00"));
    			    	}
    		  ));
  

    	timeline.setCycleCount(Animation.INDEFINITE);
    	timeline.play();
}
  // view handler
@FXML
  public void handleClickResetBtn() {
	playing = true;
	paused = false;
	st = null;
	log_text.clear();
	
	
    if(display_stack != null)
    display_stack.getChildren().clear();
    if(display_graph != null)
    display_graph.getChildren().clear();
//    positionLastNode = new Point2D.Double(10, 150);

  }
  public void handleDragNode() {

  }
  @FXML 
  public void handleClickTarjan() {
	  st = new SequentialTransition();
	  algorithmFactory = new TarjanUIAlgorithm(7, st);

	  
  }

  
  @FXML
  public void handleClicKosaraju() {
	  st = new SequentialTransition();

	  algorithmFactory = new KosarajuUIAlgorithm(7, st);
    // g = new KosarajuAlgorithm(7);
	 // algorithmFactory.solve();
  }
  @FXML
  public void handleClickPrint() {
	  algorithmFactory.solve();
//g2.solve();
  }
  @FXML
  public void handleClickNextBtn() {
	  System.out.println("IN PLAYPAUSE");
      System.out.println(playing + " " + paused);
      
      try{
          if (playing && st != null && st.getStatus() == Animation.Status.RUNNING) {
              System.out.println("Pausing");
              st.pause();
              paused = true;
              playing = false;
              next_btn.setText("Paused");
              return;
          } else if (paused && st != null) {
            
              if(st.getStatus() == Animation.Status.PAUSED) {
            	  st.play();
              }
              else if(st.getStatus() == Animation.Status.STOPPED)
                  st.playFromStart();
              playing = true;
              paused = false;
              next_btn.setText("Playing");

              return;
          }
      } catch(Exception e){
          System.out.println("Error while play/pause: " + e);
          //ClearHandle(null);
      }//g2.solve();
  }
  
  
  @FXML
  public void handleClickSolve() {
	  algorithmFactory.solve();
  }

  public void appendLogger(String log) {
    String currentText = log_text.getText();
    log_text.setText("\n" + log + currentText);
  }
  
  
  


  
}