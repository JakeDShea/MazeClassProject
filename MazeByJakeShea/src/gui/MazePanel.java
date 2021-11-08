package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import gui.Constants;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author Peter Kemper
 *
 */
public class MazePanel extends Panel implements P5PanelF21 {
	private static final long serialVersionUID = 2787329533730973905L;
	/* Panel operates a double buffer see
	 * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
	 * for details
	 */
	// bufferImage can only be initialized if the container is displayable,
	// uses a delayed initialization and relies on client class to call initBufferImage()
	// before first use
	private Image bufferImage;  
	private Graphics2D graphics; // obtained from bufferImage, 
	// graphics is stored to allow clients to draw on the same graphics object repeatedly
	// has benefits if color settings should be remembered for subsequent drawing operations
	
	// Font object for the compass rose
	private final Font markerFont;
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel() {
		setFocusable(false);
		bufferImage = null; // bufferImage initialized separately and later
		graphics = null;	// same for graphics
		
		markerFont = Font.decode(("Serif-PLAIN-16"));
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	/**
	 * Method to draw the buffer image on a graphics object that is
	 * obtained from the superclass. 
	 * Warning: do not override getGraphics() or drawing might fail. 
	 */
	public void update() {
		paint(getGraphics());
	}
	
	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 * The given graphics object is the one that actually shows 
	 * on the screen.
	 */
	@Override
	public void paint(Graphics g) {
		if (null == g) {
			System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation");
		}
		else {
			g.drawImage(bufferImage,0,0,null);	
		}
	}

	/**
	 * Obtains a graphics object that can be used for drawing.
	 * This MazePanel object internally stores the graphics object 
	 * and will return the same graphics object over multiple method calls. 
	 * The graphics object acts like a notepad where all clients draw 
	 * on to store their contribution to the overall image that is to be
	 * delivered later.
	 * To make the drawing visible on screen, one needs to trigger 
	 * a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on, null if impossible to obtain image
	 */
	public Graphics getBufferGraphics() {
		// if necessary instantiate and store a graphics object for later use
		if (null == graphics) { 
			if (null == bufferImage) {
				bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
				if (null == bufferImage)
				{
					System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
					return null; // still no buffer image, give up
				}		
			}
			graphics = (Graphics2D) bufferImage.getGraphics();
			if (null == graphics) {
				System.out.println("Error: creation of graphics for buffered image failed, presumedly container not displayable");
			}
			else {
				// System.out.println("MazePanel: Using Rendering Hint");
				// For drawing in FirstPersonDrawer, setting rendering hint
				// became necessary when lines of polygons 
				// that were not horizontal or vertical looked ragged
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			}
		}
		return graphics;
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////INTERFACE METHODS///////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Commits all accumulated drawings to the UI. 
	 */
	@Override
	public void commit() {
		paint(getGraphics());
	}

	/**
	 * Tells if instance is able to draw. This ability depends on the
	 * context, for instance, in a testing environment, drawing
	 * may be not possible and not desired.
	 * @return true if drawing is possible, false if not.
	 */
	@Override
	public boolean isOperational() {
		if (graphics == null)
			return false;
		else
			return true;
	}

	/**
	 * Sets the color for future drawing requests. The color setting
	 * will remain in effect until this method is called again and
	 * with a different color.
	 * @param rgb gives the red, green, and blue encoded value of the color
	 */
	@Override
	public void setColor(int rgb) {
		Color color = new Color(rgb);
		graphics.setColor(color);
	}

	/**
     * Returns the RGB value for the current color setting. 
     * @return integer RGB value
     */
	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return graphics.getColor().getRGB();
	}

	/**
	 * Draws two solid rectangles to provide a background.
	 * Note that this also erases any previous drawings.
	 * The color setting adjusts to the distance to the exit to 
	 * provide an additional clue for the user.
	 * Colors transition from black to gold and from grey to green.
	 * @param percentToExit gives the distance to exit
	 */
	@Override
	public void addBackground(float percentToExit) {
		graphics.setColor(getBackgroundColor(percentToExit, true));
		graphics.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT/2);
		
		graphics.setColor(getBackgroundColor(percentToExit, false));
		graphics.fillRect(0, Constants.VIEW_HEIGHT/2, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT/2);
	}

	/**
     * Adds a filled rectangle. 
     * The rectangle is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the 
     * x-axis and the height for the y-axis.
     * @param x is the x-coordinate of the top left corner
     * @param y is the y-coordinate of the top left corner
     * @param width is the width of the rectangle
     * @param height is the height of the rectangle
     */
	@Override
	public void addFilledRectangle(int x, int y, int width, int height) {
		graphics.fillRect(x, y, width, height);
	}
	
	/**
     * Adds a filled polygon. 
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have 
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
	@Override
	public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.fillPolygon(xPoints, yPoints, nPoints);
	}

	/**
     * Adds a polygon.
     * The polygon is not filled. 
     * The polygon is specified with {@code (x,y)} coordinates
     * for the n points it consists of. All x-coordinates
     * are given in a single array, all y-coordinates are
     * given in a separate array. Both arrays must have 
     * same length n. The order of points in the arrays
     * matter as lines will be drawn from one point to the next
     * as given by the order in the array.
     * @param xPoints are the x-coordinates of points for the polygon
     * @param yPoints are the y-coordinates of points for the polygon
     * @param nPoints is the number of points, the length of the arrays
     */
	@Override
	public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.drawPolygon(xPoints, yPoints, nPoints);
	}

	/**
     * Adds a line. 
     * A line is described by {@code (x,y)} coordinates for its 
     * starting point and its end point. 
     * Substitute for Graphics.drawLine method
     * @param startX is the x-coordinate of the starting point
     * @param startY is the y-coordinate of the starting point
     * @param endX is the x-coordinate of the end point
     * @param endY is the y-coordinate of the end point
     */
	@Override
	public void addLine(int startX, int startY, int endX, int endY) {
		graphics.drawLine(startX, startY, endX, endY);
	}

	/**
     * Adds a filled oval.
     * The oval is specified with the {@code (x,y)} coordinates
     * of the upper left corner and then its width for the 
     * x-axis and the height for the y-axis. An oval is
     * described like a rectangle. 
     * @param x is the x-coordinate of the top left corner
     * @param y is the y-coordinate of the top left corner
     * @param width is the width of the oval
     * @param height is the height of the oval
     */
	@Override
	public void addFilledOval(int x, int y, int width, int height) {
		graphics.fillOval(x, y, width, height);
	}

	/**
     * Adds the outline of a circular or elliptical arc covering the specified rectangle.
     * The resulting arc begins at startAngle and extends for arcAngle degrees, 
     * using the current color. Angles are interpreted such that 0 degrees 
     * is at the 3 o'clock position. A positive value indicates a counter-clockwise 
     * rotation while a negative value indicates a clockwise rotation.
     * The center of the arc is the center of the rectangle whose origin is 
     * (x, y) and whose size is specified by the width and height arguments.
     * The resulting arc covers an area width + 1 pixels wide 
     * by height + 1 pixels tall.
     * The angles are specified relative to the non-square extents of 
     * the bounding rectangle such that 45 degrees always falls on the 
     * line from the center of the ellipse to the upper right corner of 
     * the bounding rectangle. As a result, if the bounding rectangle is 
     * noticeably longer in one axis than the other, the angles to the start 
     * and end of the arc segment will be skewed farther along the longer 
     * axis of the bounds.
     * @param x the x coordinate of the upper-left corner of the arc to be drawn.
     * @param y the y coordinate of the upper-left corner of the arc to be drawn.
     * @param width the width of the arc to be drawn.
     * @param height the height of the arc to be drawn.
     * @param startAngle the beginning angle.
     * @param arcAngle the angular extent of the arc, relative to the start angle.
     */
	@Override
	public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		graphics.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	/**
     * Adds a string at the given position.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param str the string
     */
	@Override
	public void addMarker(float x, float y, String str) {
		GlyphVector gv = markerFont.createGlyphVector(graphics.getFontRenderContext(), str);
        Rectangle2D rect = gv.getVisualBounds();
        // need to update x, y by half of rectangle width, height
        // to serve as x, y coordinates for drawing a GlyphVector
        x -= rect.getWidth() / 2;
        y += rect.getHeight() / 2;
        
        graphics.drawGlyphVector(gv, x, y);
	}

	/**
     * Sets the value of a single preference for the rendering algorithms.
     * It internally maps given parameter values into corresponding java.awt.RenderingHints
     * and assigns that to the internal graphics object. 
     * Hint categories include controls for rendering quality
     * and overall time/quality trade-off in the rendering process.
     * Refer to the awt RenderingHints class for definitions of some common keys and values.
     * @param hintKey the key of the hint to be set.
     * @param hintValue the value indicating preferences for the specified hint category.
     */
	@Override
	public void setRenderingHint(P5RenderingHints hintKey, P5RenderingHints hintValue) {
		switch(hintKey)
		{
			case KEY_RENDERING:
			{
				setHintForRendering(hintValue);
				break;
			}
			case KEY_ANTIALIASING:
			{
				setHintForAliasing(hintValue);
				break;
			}
			case KEY_INTERPOLATION:
			{
				setHintForInterpolation(hintValue);
				break;
			}
			default:
				break;
		}
	}

	///////////////////////////////////////////////////////////////////////////
	/////////////////////////////PRIVATE METHODS///////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Determine the background color for the top and bottom
	 * rectangle as a blend between starting color settings
	 * of yellowWM and lightGray towards goldWM and greenWM as final
	 * color settings close to the exit
	 * @param percentToExit describes how far it is to the exit as a percentage value
	 * @param top is true for the top rectangle, false for the bottom
	 * @return the color to use for the background rectangle
	 */
	private Color getBackgroundColor(float percentToExit, boolean top)
	{
		// Uses wmGreen, wmGold, and wmYellow to blend into the background as originally given by FirstPersonView
		return top? blend(Color.decode("#FFFF99"), Color.decode("#916f41"), percentToExit) : 
			blend(Color.lightGray, Color.decode("#115740"), percentToExit);
	}
	
	/**
	 * Calculates the weighted average of the two given colors.
	 * The weight for the first color is expected to be between
	 * 0 and 1. The weight for the other color is then 1-weight0.
	 * The result is the weighted average of the red, green, and
	 * blue components of the colors. The resulting alpha value
	 * for transparency is the max of the alpha values of both colors.
	 * @param fstColor is the first color
	 * @param sndColor is the second color
	 * @param weightFstColor is the weight of fstColor, {@code 0.0 <= weightFstColor <= 1.0}
	 * @return blend of both colors as weighted average of their rgb values
	 */
	private Color blend(Color fstColor, Color sndColor, double weightFstColor) {
		if (weightFstColor < 0.1)
			return sndColor;
		if (weightFstColor > 0.95)
			return fstColor;
	    double r = weightFstColor * fstColor.getRed() + (1-weightFstColor) * sndColor.getRed();
	    double g = weightFstColor * fstColor.getGreen() + (1-weightFstColor) * sndColor.getGreen();
	    double b = weightFstColor * fstColor.getBlue() + (1-weightFstColor) * sndColor.getBlue();
	    double a = Math.max(fstColor.getAlpha(), sndColor.getAlpha());

	    return new Color((int) r, (int) g, (int) b, (int) a);
	}
	
	/**
	 * A private method for setting up the graphic's
	 * rendering hints.
	 * 
	 * @param hint Hint value to be set
	 */
	private void setHintForRendering(P5RenderingHints hint)
	{
		// Switches through possible values
		switch(hint)
		{
			// Checks each possible value
			case VALUE_RENDER_QUALITY:
			{
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				break;
			}
			case VALUE_ANTIALIAS_ON:
			{
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_ANTIALIAS_ON);
				break;
			}
			case VALUE_INTERPOLATION_BILINEAR:
			{
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				break;
			}
			default:
				break;
		}
	}
	
	/**
	 * A private method for setting up the graphic's
	 * rendering hints.
	 * 
	 * @param hint Hint value to be set
	 */
	private void setHintForAliasing(P5RenderingHints hint)
	{
		// Switches through possible values
		switch(hint)
		{
			// Checks each possible value
			case VALUE_RENDER_QUALITY:
			{
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_RENDER_QUALITY);
				break;
			}
			case VALUE_ANTIALIAS_ON:
			{
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				break;
			}
			case VALUE_INTERPOLATION_BILINEAR:
			{
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				break;
			}
			default:
				break;
		}
	}
	
	/**
	 * A private method for setting up the graphic's
	 * rendering hints.
	 * 
	 * @param hint Hint value to be set
	 */
	private void setHintForInterpolation(P5RenderingHints hint)
	{
		// Switches through possible values
		switch(hint)
		{
			// Checks each possible value
			case VALUE_RENDER_QUALITY:
			{
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_RENDER_QUALITY);
				break;
			}
			case VALUE_ANTIALIAS_ON:
			{
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_ANTIALIAS_ON);
				break;
			}
			case VALUE_INTERPOLATION_BILINEAR:
			{
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				break;
			}
			default:
				break;
		}
	}
}
