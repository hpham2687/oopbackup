package AlgorithmFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

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

public class TarjanUIAlgorithm extends BaseUIAlgorithm implements AlgorithmFactory {
//	Graph graph;
	    
	    public TarjanUIAlgorithm(int v, SequentialTransition st) {
	    	this.st = st;
	        graph = new TarjanGraphLogicSolver(v);
	        ((TarjanGraphLogicSolver) graph).initEdges();
	        
	    }
	    
	    @Override
		public void solve() {
	    	// st = new SequentialTransition();

	         ((TarjanGraphLogicSolver) graph).solveSCCs();

	         st.play();
	         main.window.setScene(main.scene1);			
		}
	public class TarjanGraphLogicSolver extends GraphLogicSolver {
		  
		
		private int Time;
		  
		// Constructor 
		@SuppressWarnings("unchecked")
		
		 public TarjanGraphLogicSolver(int v, int Transpose) {
            V = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i)
                adj[i] = new LinkedList();
		    Time = 0;

        }
        public TarjanGraphLogicSolver(int v) {

            //get Controller
            FXMLLoader loader = new FXMLLoader(main.class.getResource("view/MainScene.fxml"));


            try {
                loader.setController(Controller.getInstance());
                root = loader.load();
                myController = (Controller) loader.getController();

                //st = new SequentialTransition();

                V = v; 
    		    adj = new LinkedList[v];
    		      
    		    for(int i = 0; i < v; ++i) 
    		        adj[i] = new LinkedList(); 
    		          
    		    Time = 0;


                generateNodes(v);
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
     
		// Function to add an edge into the Graph 
		void addEdge(int v,int w) 
		{ 
			
		    adj[v].add(w); 
		    Edge newEdge = new Edge(nodes.get(v), nodes.get(w));

            // add to list adges
            edges.add(newEdge);

            // generate animation for show edge
            myController.display_graph.getChildren().add(newEdge.line);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), newEdge.line);
            ft.setNode(newEdge.line);
            ft.setFromValue(0);
            ft.setToValue(1);

            st.getChildren().add(ft);

		} 
		
		 public void initEdges() {
	         //   st = new SequentialTransition();
	            //		  FXMLLoader loader = new FXMLLoader(KosarajuAlgorithm.class.getResource("../main/view/MainScene.fxml"));type name = new type();
	            
	           System.out.print("vaof dayyyyy initedge");
	            addEdge(0, 1);
	            addEdge(1, 2);
	            addEdge(2, 0);
	            addEdge(1, 3);
	            addEdge(1, 4);
	            addEdge(1, 6);
	            addEdge(3, 5);
	            addEdge(4, 5);


	            st.play();




	        }
		  
		// A recursive function that finds and prints strongly 
		// connected components using DFS traversal 
		// u --> The vertex to be visited next 
		// disc[] --> Stores discovery times of visited vertices 
		// low[] -- >> earliest visited vertex (the vertex with
//		             minimum discovery time) that can be reached
//		             from subtree rooted with current vertex 
		
		// stack -- >> To store all the connected ancestors (could be part 
//		         of SCC) 
		// stackMember[] --> bit/index array for faster check
//		                   whether a node is in stack 
		void SCCUtil(int u, int low[], int disc[],
		             boolean stackMember[], 
		             Stack<Integer> stack, SequentialTransition wrapperAnimation)
		{
		     // st.pause();
		//	System.out.println("In "+ u);
			  //generate animation for set visited = true (set color to RED)
		      FillTransition ft3 = new FillTransition(Duration.millis(ANIMATION_DURATION_TIME), nodes.get(u).circle);
		      Color c = Color.web("ff0000"); // red	

		      ft3.setToValue(c);

		      ft3.setOnFinished(e->{
		          appendLogger("Set node "+u+" is visited - Push " +u+ " to stack");

		      });
		      wrapperAnimation.getChildren().add(ft3);

		      //generate animation for set visited = true

		      
		    // Initialize discovery time and low value 
		    disc[u] = Time; 
		    
		    low[u] = Time; 
			System.out.println("Low ["+u+"] = "+ Time);
		    
		    
		    Timeline timeline6 = new Timeline(new KeyFrame(Duration.seconds(0.25), evt -> {
	      	  appendLogger("Change low-link of node "+u+" to "+low[u]);
	        } ));// green
		          
		          wrapperAnimation.getChildren().add(timeline6);
		          
		    

	        
	        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), nodes.get(u).getLowLink());
		    fadeIn.setFromValue(0.0);
		    fadeIn.setToValue(1.0);
		    fadeIn.setOnFinished(e->{
		    	if (myController.display_graph.getChildren().contains(nodes.get(u))) {
		    		myController.display_graph.getChildren().remove(nodes.get(u).getLowLink());
		    	}
		    	    nodes.get(u).getLowLink().setText(low[u]+ "");
		    	    myController.display_graph.getChildren().add(nodes.get(u).getLowLink());


		    });
		    wrapperAnimation.getChildren().add(fadeIn);
		    
		    
		    
		    
		    Time += 1;
		   // System.out.println("push "+ u);
		    stackMember[u] = true;
		    stack.push(u);
		  
		    
		    
		    // create node and move to stack
		      Node node = new Node(nodes.get(u).getLayoutX(), nodes.get(u).getLayoutY(), 20, String.valueOf(u));
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
		      tt.setByX(myController.display_graph.getWidth() - nodes.get(u).getPoint().getX() - 50);
		      tt.setByY(myController.display_graph.getHeight() - nodes.get(u).getPoint().getY() - 50 - stackPosition);
		      stackPosition += 35; // for next position of node
		      ParallelTransition prl = new ParallelTransition(tt);

		      wrapperAnimation.getChildren().add(prl);

		      // push to stack list
		      stackNodes.add(0,node);
		      
		      
		    
		    int n;
		      
		    // Go through all vertices adjacent to this 
		    Iterator<Integer> i = adj[u].iterator(); 
		      
		    // scale when begin DFS

		      ScaleTransition st2 = new ScaleTransition(Duration.millis(ANIMATION_DURATION_TIME), nodes.get(u).circle);
		      st2.setToX(1.3f);
		      st2.setToY(1.3f);
		      
		     st2.setOnFinished(ev->{
		         appendLogger("Start DFS node "+ u);

		     });
		     wrapperAnimation.getChildren().add(st2);
		      // scale when begin DFS
		     
		    while (i.hasNext()) 
		    { 
		        n = i.next(); 
		          
		        if (disc[n] == -1) 
		        {
		        //	st.pause();
		            SCCUtil(n, low, disc, stackMember, stack, wrapperAnimation);
		              
		            // Check if the subtree rooted with v 
		            // has a connection to one of the 
		            // ancestors of u 
		            // Case 1 (per above discussion on
		            // Disc and Low value) 
		            low[u] = Math.min(low[u], low[n]);
		    		System.out.println("---Low ["+u+"] = "+ low[u]);
		    		int tempN = n;
		              Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(0.25), evt -> {
		            	  appendLogger("Change low-link of node "+u+" to min(low["+u+"], low["+tempN+"]) ="+low[u]);
		              } ));// green
		      	          
		      	          wrapperAnimation.getChildren().add(timeline3);
		      	          
		            //set lowlink value
		    	  // nodes.get(u).setLowLinkLabel(low[u], display_graph, wrapperAnimation);
		            
		          
		        }
		        else if (stackMember[n] == true)
		        {
			    		int tempN = n;

		              
		              Timeline timeline5 = new Timeline(new KeyFrame(Duration.seconds(0.25), evt -> {
		            	  appendLogger("Check that node "+tempN+ " is vistited. Skip!");
		              } ));// green
		      	          
		      	          wrapperAnimation.getChildren().add(timeline5);

		      	          
		            // Update low value of 'u' only if 'v' is
		            // still in stack (i.e. it's a back edge, 
		            // not cross edge). 
		            // Case 2 (per above discussion on Disc
		            // and Low value)
		            low[u] = Math.min(low[u], disc[n]);
		    		System.out.println("Low ["+u+"] = "+ low[u]);

		    		   Timeline timeline4 = new Timeline(new KeyFrame(Duration.seconds(0.25), evt -> {
			            	  appendLogger("Change low-link of node "+u+" to min(low["+u+"], low["+tempN+"]) ="+low[u]);
			              } ));// green
			      	          
			      	          wrapperAnimation.getChildren().add(timeline4);
		            //set lowlink value
			    	//   nodes.get(u).setLowLinkLabel(low[u], display_graph, wrapperAnimation);

		            
		            
		        }
		        FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(1), nodes.get(u).getLowLink());
			    fadeIn2.setFromValue(0.0);
			    fadeIn2.setToValue(1.0);
			    fadeIn2.setOnFinished(e->{
			    	if (myController.display_graph.getChildren().contains(nodes.get(u))) {
			    		myController.display_graph.getChildren().remove(nodes.get(u).getLowLink());
			    	}
			    	    nodes.get(u).getLowLink().setText(low[u]+ "");
			    	    myController.display_graph.getChildren().add(nodes.get(u).getLowLink());


			    });
			    wrapperAnimation.getChildren().add(fadeIn2);
		        
		        
		        
		        
		      
			    
	    	 
	    	    
		    } 
		    
		    
		    // flash effect to show that done dfs
		    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), evt -> nodes.get(u).circle.setFill(Color.web("#00ff00"))),// green
		            new KeyFrame(Duration.seconds(0.5), evt -> nodes.get(u).circle.setFill(Color.web("#ff0000"))),//red
		            new KeyFrame(Duration.seconds(0.75), evt -> nodes.get(u).circle.setFill(Color.web("#00ff00"))),//green
		            new KeyFrame(Duration.seconds(1), evt -> nodes.get(u).circle.setFill(Color.web("#ff0000"))));//red

		          wrapperAnimation.getChildren().add(timeline);

		    
		    // scale back when end DFS

		      ScaleTransition st3 = new ScaleTransition(Duration.millis(ANIMATION_DURATION_TIME), nodes.get(u).circle);
		      st3.setToX(1f);
		      st3.setToY(1f);
		      
		     st3.setOnFinished(ev->{
	             appendLogger("End DFS node "+ u+ " (end visiting neighbors)");

		     });
		     wrapperAnimation.getChildren().add(st3);
			    // scale back when end DFS
		  
		    // head node found, pop the stack and print an SCC 
		    // To store stack extracted vertices 
		    int w = -1; 

	   
		    if (low[u] == disc[u])
		    {
		        while (w != u)
		        {  
		            w = (int)stack.pop();
		           System.out.print(" -"+w + " = w");
		            //generate animation for set visited = true (set color to RED)
		            FillTransition ft2 = new FillTransition(Duration.millis(ANIMATION_DURATION_TIME), nodes.get(w).circle);
			           System.out.print(" -" +nodes.get(w).getLabel().getText() + " = node"+ " color= "+ groupColor);

		            
		          //  Color c = groupColor; // red	

		            ft2.setToValue(groupColor);

		            wrapperAnimation.getChildren().add(ft2);

		            //generate animation for pop stack
		            stackMember[w] = false;
		            int temp = w;

		            // animation for pop node 
		            
		            	 FadeTransition ft = new FadeTransition(Duration.millis(ANIMATION_DURATION_TIME), stackNodes.get(findStackNodeByValue(w)));
		 	            ft.setNode(stackNodes.get(findStackNodeByValue(w)));
		 	            ft.setFromValue(1);
		 	            ft.setToValue(0);
		 	            ft.setOnFinished(e->{
//		 	               stackNodes.forEach(s-> {
//		 	            	  System.out.println(s.getLabel().getText());
//		 	              });
//		 	              System.out.println("\nlocking for w="+temp);
		 	            	
		 	               myController.display_graph.getChildren().remove(stackNodes.get(findStackNodeByValue(temp)));


		 	            });

		 	            wrapperAnimation.getChildren().add(ft);
		            
		          //  stackNodes.pop();

		           


		            
		        }
		        System.out.println(); 
		        // change group color 
		    	 groupColor = ballColors[++indexGroupColor];

		    }
		    
		    
		}
		  
		// The function to do DFS traversal.
		// It uses SCCUtil() 
		void solveSCCs()
		{
		      
		    // Mark all the vertices as not visited 
		    // and Initialize parent and visited, 
		    // and ap(articulation point) arrays 
		    int disc[] = new int[V]; 
		    int low[] = new int[V]; 
		    for(int i = 0;i < V; i++)
		    {
		        disc[i] = -1; // id 
		        low[i] = -1;
		    }
		      
		    boolean stackMember[] = new boolean[V]; 
		    Stack<Integer> stack = new Stack<Integer>(); 
		      
		    // Call the recursive helper function 
		    // to find articulation points 
		    // in DFS tree rooted with vertex 'i' 
		    
		    
		    SequentialTransition wrapperAnimation=new SequentialTransition();

	        appendLogger("Start DFS all Node....");

		    for(int i = 0; i < V; i++)
		    {

		    //	st.pause();
		        if (disc[i] == -1) {

		        	SCCUtil(i, low, disc,
		                    stackMember, stack, wrapperAnimation);//  pass sequeintia=l animation here
		        }
		            
		    }
		    
		    wrapperAnimation.setOnFinished(ev->{
		          appendLogger("End DFS all node.");


		      });
		     
		      st.getChildren().add(wrapperAnimation);
		      
		      
		      
		  
		    
		}


	
	}
}