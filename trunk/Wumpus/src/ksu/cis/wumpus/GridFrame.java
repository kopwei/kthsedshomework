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
 * This class places a GridDisplay component in a ScrollPane container,
 * puts the ScrollPane in a window, and adds a simple pulldown menu system.
 * The menu uses menu shortcuts.  Events are handled with anonymous classes.
 */
public class GridFrame extends Frame {
  /** Remember # of open windows so we can quit when last one is closed */
  protected static int num_windows = 0;
  
  Label textLabel;

  /** Create a Frame, Menu, and ScrollPane for the gridDisplay component */
  public GridFrame() {
	super("GridFrame");                  // Create the window.
	num_windows++;                           // Count it.

	setLayout(new BorderLayout());
	Panel p = new Panel();
	p.add(new Button("Wall"));
	p.add(new Button("Pit"));
	//p.add(new Button("Restart"));
	p.add(textLabel = new Label("Here is some output info",java.awt.Label.LEFT));
	ScrollPane pane = new ScrollPane();      // Create a ScrollPane.
	pane.setSize(300, 300); // Specify its size.
	
	
	add(p,"North");
	add(pane,"Center");                // Add it to the frame.
	GridDisplay gridDisplay;
	gridDisplay = new GridDisplay(this, 500, 500); // Create a bigger gridDisplay area.
	pane.add(gridDisplay);// Add it to the ScrollPane.

	MenuBar menubar = new MenuBar();         // Create a menubar.
	this.setMenuBar(menubar);                // Add it to the frame.
	Menu file = new Menu("File");            // Create a File menu.
	menubar.add(file);                       // Add to menubar.

	// Create three menu items, with menu shortcuts, and add to the menu.
	MenuItem n, c, q;
	file.add(n = new MenuItem("New Window", new MenuShortcut(KeyEvent.VK_N)));
	file.add(c = new MenuItem("Close Window",new MenuShortcut(KeyEvent.VK_W)));
	file.addSeparator();                     // Put a separator in the menu
	file.add(q = new MenuItem("Quit", new MenuShortcut(KeyEvent.VK_Q)));

	// Create and register action listener objects for the three menu items.
	n.addActionListener(new ActionListener() {     // Open a new window
	  public void actionPerformed(ActionEvent e) { new GridFrame(); }
	});
	c.addActionListener(new ActionListener() {     // Close this window.
	  public void actionPerformed(ActionEvent e) { close(); }
	});
	q.addActionListener(new ActionListener() {     // Quit the program.
	  public void actionPerformed(ActionEvent e) { System.exit(0); }
	});

	// Another event listener, this one to handle window close requests.
	this.addWindowListener(new WindowAdapter() {
	  public void windowClosing(WindowEvent e) { close(); }
	});

	// Set the window size and pop it up.
	this.pack();
	this.show();


}
  /** Close a window.  If this is the last open window, just quit. */
  void close() {
	if (--num_windows == 0) System.exit(0);
	else this.dispose();
  }
}
