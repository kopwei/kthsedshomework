package ksu.cis.wumpus;

// This example is from the book "Java in a Nutshell, Second Edition".
// Written by David Flanagan.  Copyright (c) 1997 O'Reilly & Associates.
// You may distribute this source code for non-commercial purposes only.
// You may study, modify, and use this example for any purpose, as long as
// this notice is retained.  Note that this example is provided "as is",
// WITHOUT WARRANTY of any kind either expressed or implied.
import java.awt.Point;
 import java.applet.Applet;
 import java.awt.*;               // ScrollPane,
import java.awt.event.*;         // New event model.
import java.io.*;                // Object serialization streams.
import java.util.*;
import java.lang.Math;
import java.applet.*;


/**
 * This class is a custom component that supports 2D-grids.  It also has
 * a popup menu that allows ?.
 * Note that it extends Component rather than Canvas, making it "lightweight."
 */
class GridDisplay2 extends Component implements ActionListener {
  protected short last_x, last_y;                // Coordinates of last click.
  protected Color current_color = Color.black;   // Current drawing color.
  protected int width, height;                   // The preferred size.
  protected PopupMenu popup;                     // The popup menu.
  protected Editor frame;    					 // The frame we are within.
  protected int currentx;
  protected int currenty;
  int xSize = 10, ySize = 10;
  protected int position[][] = new int[xSize][ySize];
  protected ScrollPane pane;
  Image image1;

  int boxSize = 30, minBoxSize = 20;
  GridEnvironment gridE;
  /** This constructor requires a Frame and a desired size */
  public GridDisplay2(Editor frame, int width, int height, ScrollPane pane, int size) {
	this.frame = frame;
	this.width = width;
	this.height = height;
	this.pane = pane;
	this.xSize = size;
	this.ySize = size;
	// We handle scribbling with low-level events, so we must specify
	// which events we are interested in.
	this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);


	
	// Create the popup menu using a loop.  Note the separation of menu
	// "action command" string from menu label.  Good for internationalization.
	String[] labels   = new String[] {"Clear All", "Save" };
	String[] commands = new String[] {"Clear All", "Save"};
	
	popup = new PopupMenu();                   // Create the menu
	for(int i = 0; i< xSize; i++) {
		position[i][0] = 1;
		position[0][i] = 1;
		position[9][i] = 1;
		position[i][9] = 1;
	}
	for(int i = 0; i < labels.length; i++) {
	  MenuItem mi = new MenuItem(labels[i]);   // Create a menu item.
	  mi.setActionCommand(commands[i]);        // Set its action command.
	  mi.addActionListener(this);				// And its action listener.
	  popup.add(mi); 
	}
	
	Menu choice = new Menu("Object");           // Create a submenu.
	popup.add(choice);                         // And add it to the popup.
	String[] choicenames = new String[] {"Wall", "Pit", "Gold", "Wumpus", "Clear"};
	for(int i = 0; i < choicenames.length; i++) {
	  MenuItem mi = new MenuItem(choicenames[i]);  // Create the submenu items
	  mi.setActionCommand(choicenames[i]);         // in the same way.
	  mi.addActionListener(this);
	  choice.add(mi);
	}
	// Finally, register the popup menu with the component it appears over
	this.add(popup);
  }
  /** This is the ActionListener method invoked by the popup menu items */
  public void actionPerformed(ActionEvent event) {
	// Get the "action command" of the event, and dispatch based on that.
	// This method calls a lot of the interesting methods in this class.
	String command = event.getActionCommand();
	
	
	if (command.equals("Clear All")) {
		clear();
	}

	if (command.equals("Save")) {
		Create.main("name", position);				
	}												
	else if (command.equals("Wall")) {				
		int x = getPosition(last_x);				
		int y = getPosition(last_y);				
		position[x][y] = 1;
		repaint();
	}
	else if (command.equals("Pit")) {
		int x = getPosition(last_x);
		int y = getPosition(last_y);
		position[x][y] = 2;
		repaint();
	}
		else if (command.equals("Wumpus")) {
		int x = getPosition(last_x);
		int y = getPosition(last_y);
		position[x][y] = 4;
		repaint();
	}
	else if (command.equals("Gold")) {
		int x = getPosition(last_x);
		int y = getPosition(last_y);
		position[x][y] = 8;
		repaint();
	}

	else if (command.equals("Clear")) {
		int x = getPosition(last_x);
		int y = getPosition(last_y);
		position[x][y] = 0;
		repaint();
	}

}
  /** Clear the gridDisplay.  Invoked by popup menu */
  void clear() {
	                   // and redraw everything.
	                   // set all numbers to 0, so when drawn they will be clear
	for (int i = 0; i< xSize; i++) {
		for (int j = 0; j < ySize; j++) {
			position[i][j] = 0;
		}
	}

						// redraw walls around perimeter of grid
	for(int i = 0; i< xSize; i++) {
		position[i][0] = 1;
		position[0][i] = 1;
		position[9][i] = 1;
		position[i][9] = 1;
	}
	repaint();
  }
/**
 * This method was created in VisualAge.
 * @return java.lang.String
 */
public int getPosition(int size) {
	int r = 0;
	for (int i = 0; i < xSize; i++) {
		if ((size < (i+1)*boxSize) && (size > i*boxSize)) {
			r= i;
		}
	}
	return r;
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

	
	// 1 = Wall
	// 2 = Pit
	// 4 = Wumpus
	// 8 = Gold
	// 16= DeadWumpus
	 for (int x = 0; x < xSize; x++) {
		 for (int y = 0; y < ySize; y++) {
			if (position[x][y] > 0) {
	 			if (position[x][y] == 1) {
				g.setColor(Color.gray);
		  		g.fillRect(x * boxSize + 1, y * boxSize + 1, boxSize-1, boxSize-1);
		  		g.setColor(Color.black);
				}
	 			
			if (position[x][y] == 2) {
				g.setColor(Color.black);
				g.drawLine(x*boxSize+(boxSize/4),y*boxSize+(boxSize/4),
						   x*boxSize+(boxSize*3/4),y*boxSize+(boxSize*3/4));
				g.drawLine(x*boxSize+(boxSize*3/4),y*boxSize+(boxSize/4),
						   x*boxSize+(boxSize/4),y*boxSize+(boxSize*3/4));
				g.drawLine(x*boxSize+(boxSize/4),y*boxSize+(boxSize/4),
						   x*boxSize+(boxSize/4),y*boxSize+(boxSize*3/4));
				g.drawLine(x*boxSize+(boxSize/4),y*boxSize+(boxSize/4),
						   x*boxSize+(boxSize*3/4),y*boxSize+(boxSize/4));
				g.drawLine(x*boxSize+(boxSize*3/4),y*boxSize+(boxSize*3/4),
						   x*boxSize+(boxSize/4),y*boxSize+(boxSize*3/4));
				g.drawLine(x*boxSize+(boxSize*3/4),y*boxSize+(boxSize*3/4),
						   x*boxSize+(boxSize*3/4),y*boxSize+(boxSize/4));
	
			}

			if (position[x][y] == 4) {
				g.setColor(Color.green);
				g.fillRect(x*boxSize+(boxSize/4),y*boxSize+(boxSize/3), boxSize/2, boxSize/2);
				g.fillArc(x*boxSize+(boxSize/4), y*boxSize+(boxSize/4), boxSize/2, boxSize/3, 0, 180);
				g.setColor(Color.red);
				int x1Values[]={x*boxSize+(boxSize*3/10), x*boxSize+(boxSize*3/10), x*boxSize+(boxSize*5/10)};
				int y1Values[]={y*boxSize+(boxSize/2), y*boxSize+(boxSize*4/10), y*boxSize+(boxSize/2)};
				g.fillPolygon(x1Values,y1Values,3);
				int x2Values[]={x*boxSize+(boxSize*7/10), x*boxSize+(boxSize*7/10), x*boxSize+(boxSize*5/10)};
				int y2Values[]={y*boxSize+(boxSize/2), y*boxSize+(boxSize*4/10), y*boxSize+(boxSize/2)};
				g.fillPolygon(x2Values,y2Values,3);
				g.fillArc(x*boxSize+(boxSize*3/10), y*boxSize+(boxSize*6/10), boxSize*4/10, boxSize/3, 0,180);
			}

			if (position[x][y] == 8) {
				g.setColor(Color.yellow);
				int xValues[]={x*boxSize+(boxSize/2), x*boxSize+(boxSize*1/6), x*boxSize+(boxSize*5/6) };
				int yValues[]={y*boxSize+(boxSize/6), y*boxSize+(boxSize*5/6), y*boxSize+(boxSize*5/6)};
				g.fillPolygon(xValues,yValues,3);
				g.setColor(Color.lightGray);
				g.drawPolygon(xValues,yValues,3);
				g.drawLine(x*boxSize+(boxSize/5),y*boxSize+(boxSize/5),
					       x*boxSize+(boxSize/4),y*boxSize+(boxSize/4));
				g.drawLine(x*boxSize+(boxSize*4/5),y*boxSize+(boxSize/5),
					       x*boxSize+(boxSize*3/4),y*boxSize+(boxSize/4));
			}
		
			if (position[x][y] == 16) {
				g.setColor(Color.cyan);
				g.fillRect(x*boxSize+(boxSize/4), y*boxSize+(boxSize/3), boxSize/2, boxSize/2);
				g.fillArc(x*boxSize+(boxSize/4), y*boxSize+(boxSize/4), boxSize/2, boxSize/3, 0, 180);
				g.setColor(Color.red);
			
				g.drawLine(x*boxSize+(boxSize*3/10),y*boxSize+(boxSize*4/10),
						   x*boxSize+(boxSize/2),y*boxSize+(boxSize/2));
				g.drawLine(x*boxSize+(boxSize/2),y*boxSize+(boxSize*4/10),
						   x*boxSize+(boxSize*3/10),y*boxSize+(boxSize/2));
			
				g.drawLine(x*boxSize+(boxSize*7/10),y*boxSize+(boxSize*4/10),
						   x*boxSize+(boxSize/2),y*boxSize+(boxSize/2));
				g.drawLine(x*boxSize+(boxSize/2),y*boxSize+(boxSize*4/10),
						   x*boxSize+(boxSize*7/10),y*boxSize+(boxSize/2));
			
				g.drawArc(x*boxSize+(boxSize*3/10), y*boxSize+(boxSize*6/10), boxSize*4/10, boxSize/3, 0,180);
			}
		
		
		  }
		}
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
	  currentx= last_x; currenty = last_y;
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
