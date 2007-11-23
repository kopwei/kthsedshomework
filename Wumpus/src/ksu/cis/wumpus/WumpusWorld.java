package ksu.cis.wumpus;

// This example is from the book "Java in a Nutshell, Second Edition".
// Written by David Flanagan.  Copyright (c) 1997 O'Reilly & Associates.
// You may distribute this source code for non-commercial purposes only.
// You may study, modify, and use this example for any purpose, as long as
// this notice is retained.  Note that this example is provided "as is",
// WITHOUT WARRANTY of any kind either expressed or implied.
import java.applet.*;
import java.awt.*; // ScrollPane,
import java.awt.event.*; // New event model.
import java.io.*; // Object serialization streams.
import java.util.*;
import java.lang.Math;
/**
 * This class places a GridDisplay component in a ScrollPane container,
 * puts the ScrollPane in a window, and adds a simple pulldown menu system.
 * The menu uses menu shortcuts.  Events are handled with anonymous classes.
 */
public class WumpusWorld extends Frame implements ActionListener {

	// Remember # of open windows so we can quit when last one is closed
	protected static int num_windows = 0;

	Label textLabel;
	GridEnvironment environment;
	Label cellLabel;
	boolean running = false;
	int delay = 1000;
	final Panel p = new Panel();
	final ScrollPane pane = new ScrollPane();
/**
 * Selection constructor comment.
 */
public WumpusWorld() {

	super("Selection"); // Create the window.
	num_windows++; // Count it.
	WumpusEnvironment environment;
	setLayout(new BorderLayout());

	p.add(cellLabel = new Label("Select a Wumpus World", java.awt.Label.LEFT));

	pane.setSize(394, 394); // Specify its size.

	add(pane, "Center"); // Add it to the frame.
	add(p, "North");

	MenuBar menubar = new MenuBar(); // Create a menubar.
	this.setMenuBar(menubar); // Add it to the frame.
	Menu file = new Menu("Selection"); // Create a File menu.
	menubar.add(file); // Add to menubar.

	// Create two menu items, with shortcuts, and add to the menu.
	MenuItem r, q;
	file.add(r = new MenuItem("Random", new MenuShortcut(KeyEvent.VK_R)));
	Menu c = new Menu("HardCoded");
	file.add(c);
	file.addSeparator(); // Put a separator in the menu
	file.addSeparator();

	file.add(q = new MenuItem("Quit", new MenuShortcut(KeyEvent.VK_Q)));
	
	// Create and register action listener objects for the two menu items.
	r.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			GridEnvironment environment;
			environment = WumpusEnvironment.random();
			gridApplet(environment, p, pane);
		}
	});

	q.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	});

	int k = 0;

	String list[] = Create.read(); // read in list of choices
	for (int j = 0; j < list.length; j++) { // count choices in list that aren't null
		if (list[j] != null) {
			k++;
		}
	}
	for (int i = 0; i < k; i++) { // create actionlisteners for each choice
		MenuItem ci = new MenuItem(list[i]);
		ci.addActionListener(this);
		c.add(ci);
	}

	// Another event listener, this one to handle window close requests.
	this.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			close();
		}
	});

	// Set the window size and pop it up.
	this.pack();
	this.show();

}
/**
 * This method was created in VisualAge.
 */
public void actionPerformed(ActionEvent ae) {

	GridEnvironment environment;
	environment = WumpusEnvironment.preset(ae.getActionCommand() + ".dat");
	gridApplet(environment, p, pane);
}
/**
 * This method was created in VisualAge.
 */
 private void button(Panel p, ActionListener a, String label) {
	Button button = new Button(label);
	p.add(button);
	button.addActionListener(a);
	
  }
/**
 * This method was created in VisualAge.
 */
 void close() {
	if (--num_windows == 0) System.exit(0);
	else this.dispose();
  }
/**
 * This method was created in VisualAge.
 */
public void gridApplet(GridEnvironment environment1, Panel p, ScrollPane pane) {

	this.environment = environment1;

	final GridComponent grid = new GridComponent(this, environment, 280, 280, pane);

	ActionListener a = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();
			if ((command.equals(">")) || (command.equals("Step"))) {
				environment.run(1);
				grid.repaint();
				repaint();
				running = false;
			} else if (command.equals("Stop")) {
				running = false;
			} else if (command.equals("Restart")) {
				environment = (GridEnvironment) AIMA.newOne(environment);
			}
		}
	};

	p.removeAll();
	button(p, a, "Step");
	p.add(cellLabel = new Label("0,0", java.awt.Label.LEFT));
	
	pane.removeAll();
	pane.add(grid);
	validate();
	repaint();
}
/**
 * This method was created in VisualAge.
 */
public static void main(String[] args) {
	new WumpusWorld();
}
}
