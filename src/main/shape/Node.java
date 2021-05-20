package main.shape;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Node extends StackPane {
    private Point2D point;
    public Circle circle;
    private Label label;
    private Label lowLink;
	public Label getLowLink() {
		return lowLink;
	}
	public void setLowLink(Label lowLink) {
		this.lowLink = lowLink;
	}

	//public List<Edge> adjacents = new ArrayList<Edge>();
    boolean isVisited = false;
    
    // Node: label + circle in a stack panel
    
    public Node(double x, double y, double rad, String name) {
    	       
    	// create circle
        circle = new Circle(x, y, rad);
        label = new Label(name);
        lowLink = new Label("Uninit");
        
        circle.setOpacity(1);
        ColorAdjust colorAdjust = new ColorAdjust();
        
        
		colorAdjust.setBrightness(0.5);
		circle.setEffect(colorAdjust);
		
		// set color 
		Color c = Color.web("000000");// default: black
        circle.setFill(c);
        
        label.setLayoutX(x - 18);
        label.setLayoutY(y - 18);
        
        circle.setId(name);
      //  circle.radiusProperty().bind(label.widthProperty());
        // add to stack panel
        this.getChildren().addAll(circle, label);
        point = new Point2D.Double(x,y);
        
        this.setLayoutX(x);
        this.setLayoutY(y);
        
    }
    public void initLowLinkLabel(Pane display_graph, SequentialTransition animation) {
    	lowLink.setTextFill(Color.RED);
        lowLink.setText("0");
        lowLink.setLayoutX(this.point.getX() + 30);
        lowLink.setLayoutY(this.point.getY() + 30);
        
        display_graph.getChildren().add(lowLink);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), lowLink);
	    fadeIn.setFromValue(0.0);
	    fadeIn.setToValue(1.0);
	    
	    animation.getChildren().add(fadeIn);


	}
    public void setLowLinkLabel(int value, Pane display_graph, SequentialTransition animation) {
        display_graph.getChildren().remove(lowLink);
        
        
        lowLink.setText(value+ "");
        display_graph.getChildren().add(lowLink);

        
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), lowLink);
	    fadeIn.setFromValue(0.0);
	    fadeIn.setToValue(1.0);
//	    fadeIn.setOnFinished(e->{
//	        nodes.get(u).setLowLinkLabel(low[u]);
//
//	    });

	    animation.getChildren().add(fadeIn);
        

	}
    public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public void MoveTo(Point2D newPosition) {

    	this.setPoint(newPosition);
    	 this.setLayoutX(newPosition.getX());
         this.setLayoutY(newPosition.getY());
    }
    
	public Point2D getPoint() {
		return point;
	}

	public void setPoint(Point2D point) {
		this.point = point;
	}

//	public List<Edge> getAdjacents() {
//		return adjacents;
//	}
//
//	public void setAdjacents(List<Edge> adjacents) {
//		this.adjacents = adjacents;
//	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
}
