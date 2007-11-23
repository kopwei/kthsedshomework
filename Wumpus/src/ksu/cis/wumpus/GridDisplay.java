package ksu.cis.wumpus;

// This example is from the book "Java in a Nutshell, Second Edition".
// Written by David Flanagan.  Copyright (c) 1997 O'Reilly & Associates.
// You may distribute this source code for non-commercial purposes only.
// You may study, modify, and use this example for any purpose, as long as
// this notice is retained.  Note that this example is provided "as is",
// WITHOUT WARRANTY of any kind either expressed or implied.
import java.awt.*;               // ScrollPane, 
import java.awt.event.*;         // New event model.
import java.io.*;                // Object serialization streams.
import java.util.*;
import java.lang.Math;

/**
 * This class is a custom component that supports 2D-grids.  It also has
 * a popup menu that allows ?.
 * Note that it extends Component rather than Canvas, making it "lightweight."
 */
class GridDisplay extends Component implements ActionListener {
  protected short last_x, last_y;                // Coordinates of last click.
  protected Color current_color = Color.black;   // Current drawing color.
  protected int width, height;                   // The preferred size.
  protected PopupMenu popup;                     // The popup menu.
  protected GridFrame frame;                         // The frame we are within.

  int xSize = 8, ySize = 8, boxSize = 30, minBoxSize = 20;
  /** This constructor requires a Frame and a desired size */
  public GridDisplay(GridFrame frame, int width, int height) {
	this.frame = frame;
	this.width = width;
	this.height = height;

	// We handle scribbling with low-level events, so we must specify
	// which events we are interested in.
	this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

	// Create the popup menu using a loop.  Note the separation of menu
	// "action command" string from menu label.  Good for internationalization.
	String[] labels   = new String[] {"Clear" };
	String[] commands = new String[] {"clear"};
	popup = new PopupMenu();                   // Create the menu
	for(int i = 0; i < labels.length; i++) {
	  MenuItem mi = new MenuItem(labels[i]);   // Create a menu item.
	  mi.setActionCommand(commands[i]);        // Set its action command.
	  mi.addActionListener(this);              // And its action listener.
	  popup.add(mi);                           // Add item to the popup menu.
	}
	Menu colors = new Menu("Color");           // Create a submenu.
	popup.add(colors);                         // And add it to the popup.
	String[] colornames = new String[] { "Black", "Red", "Green", "Blue"};
	for(int i = 0; i < colornames.length; i++) {
	  MenuItem mi = new MenuItem(colornames[i]);  // Create the submenu items
	  mi.setActionCommand(colornames[i]);         // in the same way.
	  mi.addActionListener(this);
	  colors.add(mi);
	}
	// Finally, register the popup menu with the component it appears over
	this.add(popup);
  }
  /** This is the ActionListener method invoked by the popup menu items */
  public void actionPerformed(ActionEvent event) {
	// Get the "action command" of the event, and dispatch based on that.
	// This method calls a lot of the interesting methods in this class.
	String command = event.getActionCommand();
	if (command.equals("clear")) clear();
	else if (command.equals("Black")) current_color = Color.black;
	else if (command.equals("Red")) current_color = Color.red;
	else if (command.equals("Green")) current_color = Color.green;
	else if (command.equals("Blue")) current_color = Color.blue;
  }
  /** Clear the gridDisplay.  Invoked by popup menu */
  void clear() {
	repaint();                   // and redraw everything.
  }
  /** Specifies how big the component would like to be.  It always returns the
   *  preferred size passed to the GridDisplay() constructor */
  public Dimension getPreferredSize() { return new Dimension(width, height); }
  /** Draw all the saved lines of the gridDisplay, in the appropriate colors */
  public void paint(Graphics g) {
	// Draw the grid lines
	int xWin = this.getSize().width, yWin = this.getSize().height;
	boxSize = java.lang.Math.max(minBoxSize, java.lang.Math.min(xWin/xSize, yWin/ySize));
	for(int i = 0; i <= xSize; i++) {
	  g.drawLine(i*boxSize, 0, i*boxSize, ySize*boxSize);
	  g.drawLine(0, i*boxSize,  xSize*boxSize, i*boxSize);
	}
  }
  /**
   * This is the low-level event-handling method called on mouse events
   * that do not involve mouse motion.  Note the use of isPopupTrigger()
   * to check for the platform-dependent popup menu posting event, and of
   * the show() method to make the popup visible.  If the menu is not posted,
   * then this method saves the coordinates of a mouse click or invokes
   * the superclass method.
   */
  public void processMouseEvent(MouseEvent e) {
	if (e.isPopupTrigger())                               // If popup trigger,
	  popup.show(this, e.getX(), e.getY());               // pop up the menu.
	else if (e.getID() == MouseEvent.MOUSE_PRESSED) {
	  last_x = (short)e.getX(); last_y = (short)e.getY(); // Save position.
	}
	else super.processMouseEvent(e);  // Pass other event types on.
  }
  /**
   * This method is called for mouse motion events.  It adds a line to the
   * gridDisplay, on screen, and in the saved representation
   */
  public void processMouseMotionEvent(MouseEvent e) {

	  frame.textLabel.setText(e.getX()/boxSize + "," + e.getY()/boxSize);
   super.processMouseMotionEvent(e);  // Important!
  }
}
