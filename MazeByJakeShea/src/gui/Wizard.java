package gui;

import generation.Maze;

/**
 * Class: Wizard
 * Responsibilities: Figures out where to go for Robot
 * Collaborators: gui.RobotDriver.java
 * 				  generation.Maze.java
 * 				  gui.ReliableRobot.java
 * 
 * @Author Jake Shea
 */


public class Wizard implements RobotDriver {

	@Override
	public void setRobot(Robot r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaze(Maze maze) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean drive2Exit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return 0;
	}

}
