package ksu.cis.wumpus;

import java.awt.Point;
import java.util.*;
import java.lang.*;

/** A GridEnvironment is an environment where objects exist in a
 * two-dimensional rectangular grid of squares.  More than one object
 * can occupy a square.
 */

public class GridEnvironment extends Environment {

	static final Point[] HEADINGS = { new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)};

	public int xSize;
	public int ySize;
	private Vector[][] grid;

	//altered 24Nov98 - MW
	//making start static allows it to be used calling the static agent creation
	//PROS: start can be a variable, and be set randomly rather than hardcoded
	//CONS: multiple agents will all have the same start (unless it is changed each time)
	//public Point start = new Point(1,1)
	public static Point start = new Point(1, 1); // (1,1) is now the default value

	public Vector objects = new Vector();

	public boolean alive; // added 20 Nov 98 - MW
	public boolean exiting; // added 3 Dec 98 - MW

  public GridEnvironment(AgentThing[] agents, int xSize, int ySize) {
	super(agents);
	this.xSize = xSize; this.ySize = ySize;
	grid = new Vector[xSize][ySize];
	for (int x = 0; x<xSize; x++)
	  for (int y = 0; y<ySize; y++)
		grid[x][y] = new Vector();
	for (int i=0; i<agents.length; i++)
	  placeObject(agents[i], agents[i].location);
	alive = true; // added 20 Nov 98 - MW
	exiting = false;
  }
  private void addWall(int x, int y) {
	placeObject(new Wall(), new Point(x,y));
  }
  /** Put walls all around the perimeter of the environment. */

  public GridEnvironment addWalls() {
	for (int x = 0; x < xSize; x++) {
	  addWall(x, 0); addWall(x, ySize-1);
	}
	for (int y =  1; y < ySize-1; y++) {
	  addWall(0, y); addWall(xSize - 1, y);
	}
	return this;
  }
  public void addWithProbability(Class objectClass, float prob) {
	for (int x = 1; x<xSize-1; x++)
	  for (int y = 1; y<ySize-1; y++)
		if (Math.random() < prob)
		  try {
			placeObject((Thing)objectClass.newInstance(), new Point(x,y));			
		  } catch (Exception e) {
			System.exit(1);
		  }
  }
  /** Handle basic actions */

  public void execute(Agent agent, Action action) {
	executeGridAction((AgentThing)agent, (AnAction)action);
  }
/* handled actions:
	turn(left|right)
	forward
	grab
	release
	speak
*/

  void executeGridAction(AgentThing agent, AnAction action) {
	agent.isBump = false;
	String name = action.getName();
	if (name.equalsIgnoreCase("turn")) {
	  int index = 0;
	  for(int i = 0; i < 4; i++) {
	if(agent.heading.equals(HEADINGS[i])) index = i;
	  }
	  if (((String)action.getArg()).equalsIgnoreCase("right"))
	index++;  // changed ... SAD 9 Oct 98
	  else if (((String)action.getArg()).equalsIgnoreCase("left"))
	index--;  // changed ... SAD 9 Oct 98
	  agent.heading = HEADINGS[(index+4) % 4];
	} else if (name.equalsIgnoreCase("forward")) {
	  Point p = new Point(agent.location);
	  p.translate(agent.heading.x, agent.heading.y);
	  moveObjectTo(agent, p);
	} else if (name.equalsIgnoreCase("grab")) {
	} else if (name.equalsIgnoreCase("release")) {
	  Thing thing = agent.holding;
	  if (thing != null) { placeObject(thing, agent.location); }
	} else if (name.equalsIgnoreCase("speak")) {
	  agent.sound = (String) action.getArg();
	} else {
	  System.out.println("Unknown Action ignored: " + action); 
	}
  }
/**
 * Added 10 Nov 98 - by MW
 *
 * Based on findObjectOfType, this returns the first object found of type
 *  that is adjacent to or on loc (directly above, below, right, left - in that order)
 */
protected Thing findAdjacentObjectOfType(Class type, Point loc) {

	Vector objs;
	Point tempLoc;

	for (int count=0; count<=4; count++) {
		tempLoc = new Point(loc);
		switch (count) {
			// case 0: loc.translate(0,0); // do nothing
			case 1: tempLoc.translate(1,0);
					break;
			case 2: tempLoc.translate(-1,0);
					break;
			case 3: tempLoc.translate(0,1);
					break;
			case 4: tempLoc.translate(0,-1);
					break;
			default:break;
		} // end switch
		objs = gridContents(tempLoc);
		for(int i=0; i<objs.size(); i++) {
	  		if (type.isInstance(objs.elementAt(i))) return (Thing)objs.elementAt(i);
		}
	} // end for
	return null;
 	} // end findAdjacentObjectOfType                                 
  /** Is there an object of this type here? */
  protected Thing findObjectOfType(Class type, Point loc) {
	Vector objs = gridContents(loc);
	for(int i=0; i<objs.size(); i++) {
	  if (type.isInstance(objs.elementAt(i))) return (Thing)objs.elementAt(i);
	}
	return null;
  }
	public Vector gridContents(int x, int y) {
	  return grid[x][y];
	}
  public Vector gridContents(Point loc) { // ??? contained?
	return grid[loc.x][loc.y];
  }
  private boolean isFreeLoc (Point loc) {
	return !loc.equals(start) && findObjectOfType(Obstacle.class, loc) == null;
  }
  //// Dealing with Objects
	
  /** Move a thing to a location, and return that location.
   * If there's an obstacle there, return nil and give object a bump. */
  public Point moveObjectTo(Thing object, Point loc) {
	if (findObjectOfType(Obstacle.class, loc) != null) {
	  object.isBump = true;
	  return null;
	} else {
	  removeObject(object);
	  placeObject(object, loc);
	  return loc;
	}
  }
  public void placeInContainer(Thing object, Thing container) {
	removeObject(object);
	container.contents.addElement(object);
	object.location = null;
	p("Placed " + object + " in " + container + "; now " + container.contents.size() + 
	  " inside");
  }
  /** Place an object at this location. */
  public void placeObject(Thing object, Point loc) {
	object.location = loc;
	gridContents(loc).addElement(object);
	//p("Placed " + object + " at " + loc);
	
	//changed 4 Dec 98 - MW
	// fixes painting problem
	objects.addElement(object);

  }
  /* return a random integer from 0..n-1, uniformly distributed */
  int randomInt(int n) {
	return (int)(java.lang.Math.random() * n);
	
	// changed: MW - 2 Dec 98
	// why divide by n?? 
	//return (int)java.lang.Math.round(java.lang.Math.random() * n) / n; 
  }
  /** Find a point somewhere in the environment that satisfies test.
   * After TRIES, give up and return null. */
  private Point randomLoc(UnaryPredicate test, int tries) {
	for (int i = 0; i < tries; i++) {
	  Point loc = new Point(randomInt(xSize), randomInt(ySize));
	  if (test.execute(loc)) {return loc;}
	}
	return null;
  }
  /* Remove this object from this location. */
  public void removeObject(Thing object) {
	gridContents(object.location).removeElement(object);

	//added 4 Dec 98 - MW try to fix painting problem	
	this.objects.removeElement(object);
	//it worked!

	//commented out 24Nov98 - MW (just cleaning up output)
	//p("Removed " + object + "; now " + gridContents(object.location).size() + " here");
		
  }
/**
 * This method was created in VisualAge.
 * @return java.awt.Point
 */
public Point startPoint() {
	return start;
}
/**
 * This method was created in VisualAge.
 * @return boolean
 */
boolean termination() {

	return ((alive==false) || (exiting==true));
}
}
