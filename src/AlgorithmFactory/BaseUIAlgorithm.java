package AlgorithmFactory;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import helper.NumberHelper;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.Controller;
import main.shape.Edge;
import main.shape.Node;

public abstract class BaseUIAlgorithm {
	 public SequentialTransition st;
	 protected List < Node > nodes = new ArrayList < > ();
	    protected List < Edge > edges = new ArrayList < > ();
	    protected Stack < Node > stackNodes = new Stack < > ();
	    protected Color[] ballColors = {
	        Color.BLUE,
	        Color.RED,  
	        Color.GREEN,
	        Color.PURPLE,
	        Color.BLACK,
	        Color.ALICEBLUE,
	        Color.AQUA,
	        Color.BEIGE,
	        Color.BLUEVIOLET,
	        Color.BLANCHEDALMOND,
	        Color.CHOCOLATE,
	        Color.CORAL,
	        Color.IVORY,
	        Color.GOLD
	    };
	    protected int indexGroupColor = 0;
	    protected Color groupColor = ballColors[indexGroupColor];
	    protected int stackPosition = 0; // += diameter of circle (15) - calculate next Position of node to place
	    protected final int ANIMATION_DURATION_TIME = 1000;
	    protected Point2D positionLastNode = new Point2D.Double(10, 150);// position of next node to show
	    protected NumberHelper numberHelper = new NumberHelper();
	    protected GraphLogicSolver graph;

	    protected Parent root;
	    protected Controller myController;
	    
	    //abstract void solve();
	    
	    
	    public void appendLogger(String log) {
            String currentText = myController.log_text.getText();
            myController.log_text.setText("\n" + log + currentText);
        }
	    public int findStackNodeByValue(int value) {
            for (int i = 0; i < stackNodes.size(); i++) {
                if (Integer.parseInt(stackNodes.get(i).getLabel().getText()) == value) {
                    return i;
                }
            }
            return -1;

        }
		//generate nodes & edges
        public void generateNodes(int numberOfNodes) {
            // generate nodes    	
            for (int i = 0; i < numberOfNodes; i++) {
                double xPosition = numberHelper.getRandomNumber((int) positionLastNode.getX() + 60, (int) positionLastNode.getX() + 80);
                double YPosition = numberHelper.getRandomNumber((int) positionLastNode.getY() - 120, (int) positionLastNode.getY() + 120);
                // Ko ap dung random cong them cho phan tu dau tien
                if (i == 0) {
                    xPosition = positionLastNode.getX();
                    YPosition = positionLastNode.getY();
                }

                // validate neu ra ngoai man hinh
                if (xPosition > 460) {
                    xPosition = numberHelper.getRandomNumber(150, 260);
                }
                if (YPosition < 0 || YPosition > 430) {
                    YPosition = numberHelper.getRandomNumber(150, 300);
                }

                Node node = new Node(xPosition, YPosition, 20, String.valueOf(i + ""));
                // add node to list node
                nodes.add(node);

                positionLastNode.setLocation(xPosition, YPosition);



                myController.display_graph.getChildren().add(node);

                // Create a sequential transition
                FadeTransition ft = new FadeTransition(Duration.millis(100), node);
                ft.setNode(node);
                ft.setFromValue(0);
                ft.setToValue(1);

                st.getChildren().add(ft);

            }


        }
}
