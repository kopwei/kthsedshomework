package ksu.cis.wumpus;

/**
 * This type was created in VisualAge.
 */
import java.awt.*; // ScrollPane, 
import java.awt.event.*; // New event model.
import java.io.*; // Object serialization streams.
import java.util.*;
import java.lang.Math;
import java.applet.*;
/**
 * This class places a GridDisplay component in a ScrollPane container,
 * puts the ScrollPane in a window, and adds a simple pulldown menu system.
 * The menu uses menu shortcuts.  Events are handled with anonymous classes.
 */
public class Editor extends Frame {
	/** Remember # of open windows so we can quit when last one is closed */
	protected static int num_windows = 0;

	Label textLabel;
	public Vector objects = new Vector();
	private Vector[][] grid;
	ScrollPane pane = new ScrollPane();
/**
 * Quick2 constructor comment.
 */
public Editor() {
	super("Quick2");                  // Create the window.
	num_windows++;                           // Count it.

	setLayout(new BorderLayout());
	Panel p = new Panel();
	/*Choice Size = new Choice();
	Size.addItem("6");
	Size.addItem("8");
	Size.addItem("10");
	Size.addItem("12");
	Size.addItem("14");*/
	//p.add(Size);
	//p.add(new Button("Pit"));
	//p.add(new Button("Save"));
	p.add(textLabel = new Label("now",java.awt.Label.LEFT));
	ScrollPane pane = new ScrollPane();  // Create a ScrollPane.
	pane.setSize(400,400);               // Add it to the frame.
	GridDisplay2 gridDisplay;
	gridDisplay = new GridDisplay2(this, 400, 400, pane, 10); // Create a bigger gridDisplay area.
	pane.add(gridDisplay);// Add it to the ScrollPane.
	add(pane);
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
  public static void main(String[] args) { new Editor(); }
}
