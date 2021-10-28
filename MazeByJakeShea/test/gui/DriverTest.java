package gui;

import generation.Maze;
import generation.MazeFactory;
import generation.StubOrder;
import generation.Order.Builder;

public class DriverTest
{
	//Sets up maze and stuff that robot needs.
	protected Maze maze;
	protected StubOrder order;
	protected MazeFactory factory;
	protected Controller controller;
	
	/**
	 * Creates a maze for robot testing
	 * This makes it so tests don't have to remake mazes multiple times.
	 */
	public void setUp(int seed, int skill)
	{
		//Set up factory object
		factory = new MazeFactory();
		
		//Sets up order object
		order = new StubOrder();
		order.setBuilder(Builder.DFS);
		order.setSeed(seed);
		order.setSkill(skill);
		
		//Orders maze
		factory.order(order);
		factory.waitTillDelivered();
		
		maze = order.getMaze();
		
		controller = new Controller();
	}
	
	/**
	 * This set-up method uses the interface type to allow for
	 * polymorphism in the testing classes, where both a wizard
	 * and a WallFollower can be used in place of the RobotDriver
	 * parameter, similar to the ReliableRobot and UnreliableRobot
	 * for the Robot parameter.
	 * 
	 * This method efficiently removes a lot of reused code
	 * through inheritance by setting up communication by the driver,
	 * robot, and the controller object.
	 * 
	 * @param driver : The automated RobotDriver that is being used during testing
	 * @param robot : The robot that is being used to run through the mazes
	 */
	public void setForTesting(RobotDriver driver, Robot robot)
	{
		driver.setRobot(robot);
		driver.setMaze(maze);
		controller.switchFromGeneratingToPlaying(maze);
		robot.setController(controller);
		controller.setRobotAndDriver(robot, driver);
	}
}
