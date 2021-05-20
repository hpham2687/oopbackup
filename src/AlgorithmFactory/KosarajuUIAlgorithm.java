package AlgorithmFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.PrimitiveIterator.OfDouble;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.Controller;
import main.main;
import main.shape.Edge;
import main.shape.Node;

public class KosarajuUIAlgorithm extends BaseUIAlgorithm implements AlgorithmFactory {
	
//	Graph graph;

    public KosarajuUIAlgorithm(int v, SequentialTransition st) {
    	this.st = st;
        graph = new KosarajuGraphLogicSolver(v);
       // graph = (Graph) graph;
        ((KosarajuGraphLogicSolver) graph).initEdges();

    }




    public void solve() {
        // TODO Auto-generated method stub	    st = new SequentialTransition();


//        st = new SequentialTransition();

        ((KosarajuGraphLogicSolver) graph).solveSCCs();

       // System.out.print("siza =" + st.getChildren().size());
        st.play();
        //  main.scene1 = new Scene(root);
        main.window.setScene(main.scene1);

    }
    
    public class KosarajuGraphLogicSolver extends GraphLogicSolver{


//        private int V; // No. of vertices
//        private LinkedList < Integer > adj[]; //Adjacency List


        public KosarajuGraphLogicSolver(int v, int Transpose) {
            V = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i)
                adj[i] = new LinkedList();
        }
        public KosarajuGraphLogicSolver(int v) {

            //get Controller
            FXMLLoader loader = new FXMLLoader(main.class.getResource("view/MainScene.fxml"));


            try {
                loader.setController(Controller.getInstance());
                root = loader.load();
                myController = (Controller) loader.getController();

               // appendLogger("tesst  bai");
                
                //st = new SequentialTransition();

                V = v;
                adj = new LinkedList[v];
                for (int i = 0; i < v; ++i)
                    adj[i] = new LinkedList();


                generateNodes(7);
                st.play();
                main.scene1 = new Scene(root);
                main.window.setScene(main.scene1);
                //	    
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println(e);
                e.printStackTrace();
            }



        }



        public void initEdges() {
          //  st = new SequentialTransition();
            //		  FXMLLoader loader = new FXMLLoader(KosarajuAlgorithm.class.getResource("../main/view/MainScene.fxml"));

            addEdge(0, 1);
            addEdge(1, 2);
            addEdge(2, 0);
            addEdge(1, 3);
            addEdge(1, 4);
            addEdge(1, 6);
            addEdge(3, 5);
            addEdge(4, 5);

            st.play();

            // main.scene1 = new Scene(root);
            //main.window.setScene(main.scene1);




        }
        //Function to add an edge into the graph
        public void addEdge(int v, int w) {


            adj[v].add(w);

            Edge newEdge = new Edge(nodes.get(v), nodes.get(w));

            // add to list adges
            edges.add(newEdge);

            // generate animation for show edge
            myController.display_graph.getChildren().add(newEdge.line);
            FadeTransition ft = new FadeTransition(Duration.millis(100), newEdge.line);
            ft.setNode(newEdge.line);
            ft.setFromValue(0);
            ft.setToValue(1);

            st.getChildren().add(ft);



        }

        // A recursive function to print DFS starting from v
        void DFSUtil(int v, boolean visited[], SequentialTransition stPopStackLoop) {
            // Mark the current node as visited and print it
            visited[v] = true;
            System.out.print(v + " ");
            
            //generate animation for set visited = true (set color to RED)
//            System.out.print(nodes.size() + " =size");

            FillTransition ft2 = new FillTransition(Duration.millis(ANIMATION_DURATION_TIME), nodes.get(v).circle);
            //  Color c = groupColor; // red	

            ft2.setToValue(groupColor);

            stPopStackLoop.getChildren().add(ft2);

            //generate animation for set visited = true

            int n;

            // Recur for all the vertices adjacent to this vertex
            Iterator < Integer > i = adj[v].iterator();
            
            stPopStackLoop.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
    	      	  appendLogger("Checking if node "+ v+ " has neighbors...");
    	        } )));
        	  
            
           if (!i.hasNext()) {
        	   stPopStackLoop.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
     	      	  appendLogger("Found that node "+ v+ " has no neighbors. End DFS this node.");
     	        } )));
         	  
             
            
           }
            while (i.hasNext()) {
            	
                n = i.next();
                int temp = n;
                stPopStackLoop.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
      	      	  appendLogger("Found that "+ temp+ " is neighbors of " +v);
      	        } )));
                
                if (!visited[n])
                    DFSUtil(n, visited, stPopStackLoop);
            }
            
            
            
            
            
            
            
        }

        // Function that returns reverse (or transpose) of this Kosaraju
        KosarajuGraphLogicSolver getTranspose() {
        	KosarajuGraphLogicSolver g = new KosarajuGraphLogicSolver(V, 1);
            for (int v = 0; v < V; v++) {
                // Recur for all the vertices adjacent to this vertex
                Iterator < Integer > i = adj[v].listIterator();
                while (i.hasNext())
                    g.adj[i.next()].add(v);
            }
            getTransposeView();
            return g;
        }

        void fillOrder(int v, boolean visited[], Stack stack, SequentialTransition fillOrderAnimation) {

            // scale when begin DFS

            ScaleTransition st2 = new ScaleTransition(Duration.millis(ANIMATION_DURATION_TIME), nodes.get(v).circle);
            st2.setToX(1.3f);
            st2.setToY(1.3f);

            st2.setOnFinished(ev -> {
                appendLogger("Start DFS node " + v);

            });
            fillOrderAnimation.getChildren().add(st2);
            // scale when begin DFS

            // Mark the current node as visited and print it
            visited[v] = true;

            // AtomicInteger ordinal = new AtomicInteger(v);

            //generate animation for set visited = true (set color to RED)
            FillTransition ft2 = new FillTransition(Duration.millis(ANIMATION_DURATION_TIME), nodes.get(v).circle);
            Color c = Color.web("ff0000"); // red	

            ft2.setToValue(c);

            ft2.setOnFinished(event -> {
                //         String currentText= log_text.getText();
                ////         log_text.setText("\n+currentText);
                appendLogger("Set node " + v + " visited.");
                appendLogger("Visiting neighbors of node " + v + ".");

            });

            fillOrderAnimation.getChildren().add(ft2);

            //generate animation for set visited = true

            // Recur for all the vertices adjacent to this vertex

            Iterator < Integer > i = adj[v].iterator();
            
            
            if (!i.hasNext()) {
            	fillOrderAnimation.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
      	      	  appendLogger("Found that node "+ v+ " has no neighbors. End DFS this node.");
      	        } )));
          	  
              
             
            }
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n])
                    fillOrder(n, visited, stack, fillOrderAnimation);
            }

            // All vertices reachable from v are processed by now,
            // push v to Stack


            //push to stack
            stack.push(v);


        	fillOrderAnimation.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
    	      	  appendLogger("End DFS node " + v + " (end visiting neighbors) - Push " + v + "to stack.");
    	        } )));
        	
        	
        	  
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), evt -> nodes.get(v).setVisible(false)),
                new KeyFrame(Duration.seconds(0.5), evt -> nodes.get(v).setVisible(true)),
                new KeyFrame(Duration.seconds(0.75), evt -> nodes.get(v).setVisible(false)),
                new KeyFrame(Duration.seconds(1), evt -> nodes.get(v).setVisible(true)));

//            timeline.setOnFinished(event -> {
//                appendLogger("End DFS node " + v + " (end visiting neighbors) - Push " + v + "to stack.");
//
//            });
            fillOrderAnimation.getChildren().add(timeline);

            // create node and move to stack
            Node node = new Node(nodes.get(v).getLayoutX(), nodes.get(v).getLayoutY(), 20, String.valueOf(v));
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.5);
            node.circle.setEffect(colorAdjust);
            //color for node
            Color c2 = Color.web("00ff00"); // blue as an rgb web value, explicit alpha
            node.circle.setFill(c2);

            myController.display_graph.getChildren().add(node);
            node.toBack();

            // transition move to stack
            TranslateTransition tt = new TranslateTransition(Duration.millis(ANIMATION_DURATION_TIME), node);
            tt.setByX(myController.display_graph.getWidth() - nodes.get(v).getPoint().getX() - 50);
            tt.setByY(myController.display_graph.getHeight() - nodes.get(v).getPoint().getY() - 50 - stackPosition);
            stackPosition += 40; // for next position of node
            ParallelTransition prl = new ParallelTransition(tt);

            fillOrderAnimation.getChildren().add(prl);

            // push to stack list
            //   stacknodes.add(0,node);
            stackNodes.push(node);

            // scale back when end DFS
            ScaleTransition st3 = new ScaleTransition(Duration.millis(ANIMATION_DURATION_TIME), nodes.get(v).circle);
            st3.setToX(1f);
            st3.setToY(1f);


            fillOrderAnimation.getChildren().add(st3);




        }

        // The main function that finds and prints all strongly
        // connected components


        public void solveSCCs() {
            //	  nodes.forEach(e->{
            //	  });
            Stack stack = new Stack();

            // Mark all the vertices as not visited (For first DFS)
            boolean visited[] = new boolean[V];

            // start add visited = false
            appendLogger("Setting all node as not visited...");


            SequentialTransition stVisitedLoop = new SequentialTransition();
            for (int i = 0; i < V; i++) {
                int index = i;

                visited[i] = false;

                //generate animation for set visited = false
                FillTransition ft2 = new FillTransition(Duration.millis(ANIMATION_DURATION_TIME), nodes.get(i).circle);

                Color c = Color.web("#a47c48"); // blue violet

                ft2.setToValue(c);

                ft2.setOnFinished(event -> {
                    //                String currentText= log_text.getText();
                    //                log_text.setText("\n+currentText);
                    appendLogger("Set Node " + index + " to be not visited yet.");

                });

                stVisitedLoop.getChildren().add(ft2);
                //generate animation for set visited = false

            }
            stVisitedLoop.setOnFinished(ev -> {
                appendLogger("End set all node as not visited.");
                appendLogger("Start DFS all node...");


            });
            st.getChildren().add(stVisitedLoop);


            // Fill vertices in stack according to their finishing
            // times
            SequentialTransition stFillOrderLoop = new SequentialTransition();

            for (int i = 0; i < V; i++)
                if (visited[i] == false)
                    fillOrder(i, visited, stack, stFillOrderLoop);

            stFillOrderLoop.setOnFinished(ev -> {
                appendLogger("End DFS all node.");
                appendLogger("Reversing direction...");


            });

            st.getChildren().add(stFillOrderLoop);



            // Create a reversed graph
            KosarajuGraphLogicSolver gr = getTranspose();


            SequentialTransition stFillMarkUnvistedLoop = new SequentialTransition();

            // Mark all the vertices as not visited (For second DFS)
            for (int i = 0; i < V; i++) {
                int index = i;
                visited[i] = false;
                //generate animation for set visited = false

                FillTransition ft2 = new FillTransition(Duration.millis(ANIMATION_DURATION_TIME), nodes.get(i).circle);

                Color c = Color.web("#a47c48"); // yellow

                ft2.setToValue(c);

                ft2.setOnFinished(event -> {
                    //                String currentText= log_text.getText();
                    //                log_text.setText("\n+currentText);
                    appendLogger("Set Node " + index + " as not visited yet.");

                });

                stFillMarkUnvistedLoop.getChildren().add(ft2);
                //generate animation for set visited = false 
            }

            stFillMarkUnvistedLoop.setOnFinished(e -> {
                appendLogger("Checking stack is empty or not...");
            });
            st.getChildren().add(stFillMarkUnvistedLoop);

            // Now process all vertices in order defined by Stack
            SequentialTransition stPopStackLoop = new SequentialTransition();

            //    stackNodes.forEach(s-> {
            //  	 // System.out.println(s.getLabel().getText());
            //    });
            while (stack.empty() == false) {
            	
            	//  Timeline timeline6 = ;// green
        		          
            	  stPopStackLoop.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
        	      	  appendLogger("Stack is not empty");
        	        } )));
            	  
                // Pop a vertex from stack
                int v = (int) stack.pop();
                
                stPopStackLoop.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
      	      	  appendLogger("Pop "+ v);
      	        } )));
          	  
                
                //  System.out.println("official pop = " + v);
                // animation for pop node
                FadeTransition ft = new FadeTransition(Duration.millis(ANIMATION_DURATION_TIME), stackNodes.get(findStackNodeByValue(v)));
                ft.setNode(stackNodes.get(findStackNodeByValue(v)));
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(e -> {
                    myController.display_graph.getChildren().remove(stackNodes.get(findStackNodeByValue(v)));

                });

                stPopStackLoop.getChildren().add(ft);


                // Print Strongly connected component of the popped vertex
                
                stPopStackLoop.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
         	      	  appendLogger("Cheking if node"+ v+ "has been visited...");
         	        } )));
                
                if (visited[v] == false) {
                	   stPopStackLoop.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
               	      	  appendLogger(v+ " has not been visited, DFS this node...");
               	        } )));
                   	  
                    gr.DFSUtil(v, visited, stPopStackLoop);
                    groupColor = ballColors[++indexGroupColor];

                }else {
                	  stPopStackLoop.getChildren().add(new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
             	      	  appendLogger("Node"+ v+ " has been visited. Skip this node.");
             	        } )));
                }
            }
            st.getChildren().add(stPopStackLoop);


            //	main.window.setScene(main.scene1);
        }


        public void getTransposeView() {

            SequentialTransition removeSequential = new SequentialTransition();

            for (Iterator iterator = edges.iterator(); iterator.hasNext();) {
                Edge edge2Show = (Edge) iterator.next();

                FadeTransition ft = new FadeTransition(Duration.millis(1000), edge2Show.line);
                ft.setNode(edge2Show.line);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished((event) -> {
                    myController.display_graph.getChildren().remove(edge2Show.line);
                    edge2Show.reverse();
                    myController.display_graph.getChildren().add(edge2Show.line);
                    appendLogger("Edge <" + edge2Show.from.getLabel().getText() + "," +
                        edge2Show.to.getLabel().getText() +
                        ">direction is reversed to <" + edge2Show.from.getLabel().getText() + ", " +
                        edge2Show.to.getLabel().getText() + ">");



                });


                removeSequential.getChildren().add(ft);

            }
            // edges.removeAll(edges);
            removeSequential.setOnFinished(e -> {
                appendLogger("Setting all node to be not visited...");
            });

            st.getChildren().add(removeSequential);



        }



    }

}