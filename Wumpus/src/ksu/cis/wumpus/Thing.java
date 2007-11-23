package ksu.cis.wumpus;

import java.awt.*;
import java.util.Vector;

/** A Thing is a physical object.  Some things are alive, some are not.
 * Each thing has a size, color, shape, and other attributes.
 * @author Peter Norvig
 */
 
public abstract class Thing {
	
	public String  name = "?";
	public boolean isAlive = false;
	public Point   location;
	public boolean isBump = false;
	public float   size = 0.5f;
	public Color   color = Color.black;
	public String  shape = "rectangle";
	public String  sound = null;
	public Vector  contents = new Vector();
	public Thing   holding = null;
	public Thing   containedIn = null;
	public Point   heading = new Point(0,0);

  public static String PointString(Point p) {
	return (p == null) ? "?" : ("[" + p.x + "," + p.y + "]");
  }
  public String toString() {
	return getClass().getName() + "@" + PointString(location) + ":" 
	   + PointString(heading);
  }
}
