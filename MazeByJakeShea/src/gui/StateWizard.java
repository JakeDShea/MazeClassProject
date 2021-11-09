package gui;

import gui.Constants.UserInput;
import gui.Robot.Direction;

/**
 * Refactored from Dr. Peter Kemper's StateWinning.java
 * 
 * This state was made to be able to switch the GUI
 * to be able to have the wizard that was made for
 * project 3 lose the maze.
 * 
 * @author Jake Shea
 */
public class StateWizard extends DefaultState {
    SimpleScreens view;
    MazePanel panel;
    Controller control;
    
    
    boolean started;
    int pathLength;
    float battery;
    
    public StateWizard() {
        pathLength = 0;
        battery = 0;
        started = false;
    }
    
    /**
     * Start the game by showing the final screen with a unique message
     * @param controller needed to be able to switch states, must be not null
     * @param panel is the UI entity to produce the screen on 
     */
    public void start(Controller controller, MazePanel panel) {
        started = true;
        // keep the reference to the controller to be able to call method to switch the state
        control = controller;
        // keep the reference to the panel for drawing
        this.panel = panel;
        // init mazeview, controller not needed for final screen
        view = new SimpleScreens();

        if (panel == null) {
    		System.out.println("StateLosing.start: warning: no panel, dry-run game without graphics!");
    		return;
    	}
        // otherwise show finish screen with winning message
        // draw content on panel
        // Check specifically if robot stopped
        
        if(control.getRobot().hasStopped())
        	view.redrawFinishLost(panel, pathLength, battery);
        else
        	view.redrawFinishWon(panel, pathLength, battery);
        
        // update screen with panel content
        panel.update();
    }
    
    /**
     * Method incorporates all reactions to keyboard input in original code, 
     * The simple key listener calls this method to communicate input.
     * Method requires {@link #start(Controller, MazePanel) start} to be
     * called before.
     * @param key provides the feature the user selected
     * @param value is not used, exists only for consistency across State classes
     */
    public boolean keyDown(UserInput key, int value) {
        if (!started)
            return false;
        
        // for any keyboard input switch to title screen
        control.switchToTitle();    
        return true;
    }

    @Override
    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }
    
    /**
     * Sets up the battery for the end screen
     * 
     * @param batteryUsage the amount of battery used by the robot driver
     */
    public void setBatteryLevel(float batteryUsage)
    {
    	battery = batteryUsage;
    }
}



