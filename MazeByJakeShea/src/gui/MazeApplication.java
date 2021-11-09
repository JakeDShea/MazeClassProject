/**
 * 
 */
package gui;

import generation.Order;

import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFrame;

/**
 * This class is a wrapper class to startup the Maze game as a Java application
 * 
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 * 
 * TODO: use logger for output instead of Sys.out
 */
public class MazeApplication extends JFrame {
	// Sets up command-line parameters for the program with defaults
	private static String argGen = "DFS";
	private static String argDrive = "Manual";
	private static String argSensors = "1111";

	// not used, just to make the compiler, static code checker happy
	private static final long serialVersionUID = 1L;
	
	// developments vs production version
	// for development it is more convenient if we produce the same maze over an over again
	// by setting the following constant to false, the maze will only vary with skill level and algorithm
	// but not on its own
	// for production version it is desirable that we never play the same maze 
	// so even if the algorithm and skill level are the same, the generated maze should look different
	// which is achieved with some random initialization
	private static final boolean DEVELOPMENT_VERSION_WITH_DETERMINISTIC_MAZE_GENERATION = false;

	/**
	 * Constructor
	 */
	public MazeApplication() {
		init();
	}

	//////////////////////////////////////////////////////////////////
	///////////////////////NOW DEPRECATED/////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Constructor that loads a maze from a given file or uses a particular method to generate a maze
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that stores an already generated maze that is then loaded, or can be null
	 */
	/*public MazeApplication(String parameter) {
		init(parameter);
	}*/

	/**
	 * Instantiates a controller with settings according to the given parameter.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
	 * or a filename that contains a generated maze that is then loaded,
	 * or can be null
	 * @return the newly instantiated and configured controller
	 */
	 Controller createController() {
	    // need to instantiate a controller to return as a result in any case
	    Controller result = new Controller() ;
	    // can decide if user repeatedly plays the same mazes or 
	    // if mazes are different each and every time
	    // set to true for testing purposes
	    // set to false for playing the game
	    if (DEVELOPMENT_VERSION_WITH_DETERMINISTIC_MAZE_GENERATION)
	    	result.setDeterministic(true);
	    else
	    	result.setDeterministic(false);
	    String msg = null; // message for feedback
	    
	    // First creates the generation of the Maze
	    // Case 1: no input
	    if ("DFS".equalsIgnoreCase(argGen)) {
	        msg = "MazeApplication: maze will be generated with a randomized algorithm"; 
	    }
	    // Case 2: Prim
	    else if ("Prim".equalsIgnoreCase(argGen))
	    {
	        msg = "MazeApplication: generating random maze with Prim's algorithm";
	        result.setBuilder(Order.Builder.Prim);
	    }
	    // Case 3 a and b: Eller, Kruskal, Boruvka or some other generation algorithm
	    else if ("Kruskal".equalsIgnoreCase(argGen))
	    {
	    	// TODO: for P2 assignment, please add code to set the builder accordingly
	        throw new RuntimeException("Don't know anybody named Kruskal ...");
	    }
	    else if ("Eller".equalsIgnoreCase(argGen))
	    {
	    	// TODO: for P2 assignment, please add code to set the builder accordingly
	        throw new RuntimeException("Don't know anybody named Eller ...");
	    }
	    else if ("Boruvka".equalsIgnoreCase(argGen))
	    {
	    	// TODO: for P2 assignment, please add code to set the builder accordingly
	    	msg = "MazeApplication: generating random maze with Boruvka's algorithm";
	        result.setBuilder(Order.Builder.Boruvka);
	    }
	    // Case 4: a file
	    else {
	        File f = new File(argGen) ;
	        if (f.exists() && f.canRead())
	        {
	            msg = "MazeApplication: loading maze from file: " + argGen;
	            result.setFileName(argGen);
	        }
	        else {
	            // None of the predefined strings and not a filename either: 
	            msg = "MazeApplication: unknown parameter value: " + argGen + " ignored, operating in default mode.";
	        }
	    }
	    
	    Robot robot;
	    String robotMsg = "";
	    
	    // Next creates the robot as necessary
	    // Case 0: A reliable robot is called for
	    if(argSensors.equalsIgnoreCase("1111"))
	    {
	    	// Will cheat a bit as WallFollower works weird with Reliable Robots
	    	if(argDrive.equalsIgnoreCase("WallFollower"))
	    	{
	    		// Creates a robot with only the back unreliable, as this will not affect
	    		// the algorithm and it will look like a reliable robot from an abstracted point of view
	    		argSensors = "1110";
	    		robot = new UnreliableRobot(1, 1, 1, 0);
	    	}
	    	else
	    		robot = new ReliableRobot();
	    	
	    	robotMsg = " with a reliable robot.";
	    }
	    // Case 1: An unreliable robot is called for
	    else
	    {
	    	// Gets the integer for a forward parameter
			int forward = Integer.parseInt(argSensors.substring(0,1));
			
			// Gets the integer for a left parameter
			int left = Integer.parseInt(argSensors.substring(1,2));
			
			// Gets the integer for a right parameter
			int right = Integer.parseInt(argSensors.substring(2,3));
				
			// Gets the integer for a back parameter
			int back = Integer.parseInt(argSensors.substring(3,4));
			
			robot = new UnreliableRobot(forward, left, right, back);
			robotMsg = " with an unreliable robot.";
		}
	    
	    // Finally creates the driver for the maze
	    // Case 0: A wizard is used
	    if("Wizard".equalsIgnoreCase(argDrive))
	    {
	    	msg += " and will be driven with a Wizard";
	    	msg += robotMsg;
	    	Wizard wizard = new Wizard();
	    	wizard.setRobot(robot);
	        result.setRobotAndDriver(robot, wizard);
	    }
	    // Case 1: A WallFollower is used
	    else if("Wallfollower".equalsIgnoreCase(argDrive))
	    {
	    	msg += " and will be driven with a WallFollower";
	    	msg += robotMsg;
	    	WallFollower follower = new WallFollower();
	    	follower.setRobot(robot);
	        result.setRobotAndDriver(robot, follower);
	    }
	    // Case 2: Driven manually
	    else
	    {
	    	msg += " and will be driven manually.";
		}
	    
	    // controller instantiated and attributes set according to given input parameter
	    // output message and return controller
	    System.out.println(msg);
	    return result;
	}

	/**
	 * Initializes some internals and puts the game on display.
	 * @param parameter can identify a generation method (Prim, Kruskal, Eller)
     * or a filename that contains a generated maze that is then loaded, or can be null
	 */
	private void init() {
	    // instantiate a game controller and add it to the JFrame
	    Controller controller = createController();
	    
	    // Saves the command-line argument
	    controller.setReliability(argSensors);
	    
	    // Sets robot Energy to a constant
	    controller.robot.setBatteryLevel(controller.getEnergyMax());
	    
		add(controller.getPanel()) ;
		// instantiate a key listener that feeds keyboard input into the controller
		// and add it to the JFrame
		KeyListener kl = new SimpleKeyListener(this, controller) ;
		addKeyListener(kl) ;
		// set the frame to a fixed size for its width and height and put it on display
		setSize(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT+22) ;
		setVisible(true) ;
		// focus should be on the JFrame of the MazeApplication and not on the maze panel
		// such that the SimpleKeyListener kl is used
		setFocusable(true) ;
		// start the game, hand over control to the game controller
		controller.start();
	}
	
	/**
	 * Main method to launch Maze game as a java application.
	 * The application can be operated in three ways. 
	 * 1) The intended normal operation is to provide no parameters
	 * and the maze will be generated by a randomized DFS algorithm (default). 
	 * 2) If a filename is given that contains a maze stored in xml format. 
	 * The maze will be loaded from that file. 
	 * This option is useful during development to test with a particular maze.
	 * 3) A predefined constant string is given to select a maze
	 * generation algorithm, currently supported is "Prim".
	 * @param args is optional, first string can be a fixed constant like Prim or
	 * the name of a file that stores a maze in XML format
	 */
	public static void main(String[] args) {
	    JFrame app ; 
		// All possible arguments
		for(int i = 0; i < args.length; i++)
		{
			// Checks if this command line arguments is a key argument
			if(args[i].charAt(0) == ('-'))
			{
				//Sets up command line parameters
				switch(args[i].charAt(1))
				{
				case 'g' : {
					argGen = args[i+1];
					break;
				}
				case 'd' : {
					argDrive = args[i+1];
					break;
				}
				case 'r' : {
					argSensors = args[i+1];
					break;
				}
				}
			}
		}
		app = new MazeApplication() ;
		
		app.repaint() ;
	}
}
