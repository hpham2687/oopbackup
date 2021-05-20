package main.shape;

import javafx.scene.control.Label;
import javafx.scene.shape.Shape;

public class Edge {
	 public Node from, to;
	 public Arrow line;
	 private boolean isReach;

	 public Edge(Node from, Node to) {
	        this.from = from;
	        this.to = to;
	        line = new Arrow(from.getPoint().getX()+7.5, from.getPoint().getY()+7.5, to.getPoint().getX()+7.5, to.getPoint().getY()+7.5);
	}
	 public void reverse() {
	        line = new Arrow(to.getPoint().getX()+7.5, to.getPoint().getY()+7.5,from.getPoint().getX()+7.5, from.getPoint().getY()+7.5);

	 }
}
