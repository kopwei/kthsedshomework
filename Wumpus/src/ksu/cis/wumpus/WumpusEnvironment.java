package ksu.cis.wumpus;

import java.io.*;
import java.util.*;
import java.awt.Point;

public class WumpusEnvironment extends GridEnvironment {

	private static int gridInfo[][];

	private Percept perceptArg = null;

	private boolean screaming = false;

	private	RandomAccessFile infile;
	private DataOutputStream output;

	private int arrows = 1;
	static int j = 1;
	static String file[] = new String[j] ;
		
/*
This constructor creates a random environment

float probPit = the chance a particular square will contain a pit
*/

  public WumpusEnvironment(AgentThing[] agents, int xSize, int ySize,
						   float probPit) {
	super(agents, xSize, ySize);

	this.addWalls(); // surround the Grid with walls
	
	this.addWithProbability(Pit.class, probPit/2);
	this.addWithProbability(Wall.class, probPit/2);
	
	// find a spot for the gold
	// not adjacent to or on the agent spot, and not in a pit or wall
	Point tpoint;
	tpoint = new Point(start);
	while ((findAdjacentObjectOfType(Agent.class,tpoint) != null) ||
		   (findObjectOfType(Pit.class,tpoint) != null) ||
		   (findObjectOfType(Wall.class,tpoint) != null))
	{
		tpoint.x = randomInt(xSize-2)+1; // gives range 1..9 on xSize=10
		tpoint.y = randomInt(ySize-2)+1;
	}
	this.placeObject(new Gold(), new Point(tpoint));

	// find a spot for the Wumpus
	// not adjacent to or on the agent spot and not in a pit
	// not in a wall either! -- added 2 Feb 99 - MW
	tpoint = new Point(start);
	while ((findAdjacentObjectOfType(Agent.class,tpoint) != null) ||
		   (findObjectOfType(Pit.class,tpoint) != null) ||
		   (findObjectOfType(Wall.class,tpoint) != null))
	{
		tpoint.x = randomInt(xSize-2)+1;
		tpoint.y = randomInt(ySize-2)+1;
	}	
	this.placeObject(new Wumpus(), new Point(tpoint));

	//cleanup section
	//added 3 Dec 98 - MW
	// - remove illegal pits that may have been created with this.addWithProbability()
	//   (those adjacent to the agent start point)
	Thing pit = findAdjacentObjectOfType(Pit.class, start);
	while (pit != null) {
		removeObject(pit);
		pit = findAdjacentObjectOfType(Pit.class, start);
	}

	// remove a wall if on the start spot!
	// added 2 Feb 99 -- MW
	Thing wall = findObjectOfType(Wall.class, start);
	if (wall != null) removeObject(wall);

	// if pit and wall on same spot, remove the wall
	// added 2 Feb 99 - MW	
	for (int x=1; x<xSize-1; x++)
	for (int y=1; y<ySize-1; y++)
	{
		tpoint.x = x; tpoint.y = y;
		wall = findObjectOfType(Wall.class, tpoint);
		pit = findObjectOfType(Pit.class, tpoint);
		if (wall!=null && pit!=null) removeObject(wall);
	}
	
  }
/*
 This constructor creates a preset environment from data in file filename
 that was created with WumpusEnvironment(AgentThing[],int,int) constructor
 */
public WumpusEnvironment(AgentThing[] agents, int xSize, int ySize, String filename) {
	super(agents, xSize, ySize);

	xSize = 10;
	ySize = 10;
	int temp = 0;
	
	//open file
	try {
		infile = new RandomAccessFile(filename, "r");
	} catch (IOException e) {
		System.err.println("Error opening file: " + filename + "\n" + e.toString() );
		System.exit(1);
	}

	// read data		
	for (int x=0; x<xSize; x++)
	for (int y=0; y<ySize; y++)
	{
		try {
			temp = infile.readInt();
		} catch (IOException e) {
			System.err.println("Error reading from file: " + filename + "\n" + e.toString() );
			System.exit(1);
		}

		//create grid
		if (temp>=16)
		{
			this.placeObject(new DeadWumpus(), new Point(x,y));
			temp-=16;
		}
		if (temp>=8) 
		{
			this.placeObject(new Gold(), new Point(x,y));
			temp-=8;
		}
		if (temp>=4) 
		{
			this.placeObject(new Wumpus(), new Point(x,y));
			temp-=4;
		}
		if (temp>=2)
		{
			this.placeObject(new Pit(), new Point(x,y));
			temp-=2;
		}
		if (temp>=1)
		{
			this.placeObject(new Wall(), new Point(x,y));
			temp-=1;
		}
		
	} //end for y
		
	//close file
	try {
		infile.close();
	} catch (IOException e) {
		System.err.println("Error closing file: " + filename + "\n" + e.toString() );
		System.exit(1);
	}
	
	}
/**
 This constructor creates a preset environment, and
 outputs that environment to file "out.dat" for later inputting
 */
public WumpusEnvironment(String name, AgentThing[] agents, int xSize, int ySize, int[][] gridInfo) {
	super(agents, xSize, ySize);

	String filename = name + ".dat";
	
	//xSize = 10;
	//ySize = 10;
	int temp = 0;

	// open the file
	try {
		output = new DataOutputStream( new FileOutputStream(filename));
	} catch (IOException e) {
		System.err.println("Error opening file: " + filename + "\n" + e.toString() );
		System.exit(1);
	}

	// a square will contain the objects corresponding to the sum of:
	// 1 = Wall
	// 2 = Pit
	// 4 = Wumpus
	// 8 = Gold
	// 16= DeadWumpus
	// be sure to surround the grid with something unpassable (walls, pits)


	// easy
	/*int[][] gridInfo = {{1,1,1,1,1,1,1,1,1,1},
						{1,0,0,0,0,0,1,0,0,1},
						{1,1,1,0,0,0,1,0,2,1},
						{1,8,0,2,0,0,1,0,0,1},
						{1,0,0,0,0,0,0,0,0,1},
						{1,0,4,0,0,1,1,1,0,1},
						{1,0,0,0,0,0,0,0,0,1},
						{1,0,0,0,0,1,0,2,0,1},
						{1,0,0,0,2,1,0,0,0,1},
						{1,1,1,1,1,1,1,1,1,1}}; 
	
/*	// medium
	int[][] gridInfo = {{1,1,1,1,1,1,1,1,1,1},
						{1,0,0,2,0,0,0,0,0,1},
						{1,0,0,0,0,0,0,0,2,1},
						{1,4,0,1,0,0,1,0,0,1},
						{1,0,0,0,0,0,0,0,2,1},
						{1,8,0,2,1,0,0,0,0,1},
						{1,0,0,0,0,0,1,0,0,1},
						{1,0,1,0,1,0,1,0,0,1},
						{1,0,1,0,0,0,1,2,0,1},
						{1,1,1,1,1,1,1,1,1,1}}; */

/*	//hard
	int[][] gridInfo = {{1,1,1,1,1,1,1,1,1,1},
						{1,0,0,0,0,1,0,0,0,1},
						{1,0,0,0,0,1,0,0,2,1},
						{1,2,0,0,0,0,0,0,0,1},
						{1,0,0,0,0,0,1,0,0,1},
						{1,0,0,0,2,1,1,0,1,1},
						{1,0,0,2,0,1,0,0,0,1},
						{1,0,0,0,2,1,0,4,0,1},
						{1,0,0,1,0,0,0,0,8,1},
						{1,1,1,1,1,1,1,1,1,1}}; */
						

	for (int x=0; x<xSize; x++)
	for (int y=0; y<ySize; y++)
	{
		try {
			temp = gridInfo[x][y];
			output.writeInt(temp);
		} catch (IOException e) {
			System.err.println("Error writing to file: " + filename + "\n" + e.toString() );
			System.exit(1);
		}
		
		if (temp>=16)
		{
			this.placeObject(new DeadWumpus(), new Point(x,y));
			temp-=16;
		}

		if (temp>=8) 
		{
			this.placeObject(new Gold(), new Point(x,y));
			temp-=8;
		}
		if (temp>=4) 
		{
			this.placeObject(new Wumpus(), new Point(x,y));
			temp-=4;
		}
		if (temp>=2)
		{
			this.placeObject(new Pit(), new Point(x,y));
			temp-=2;
		}
		if (temp>=1)
		{
			this.placeObject(new Wall(), new Point(x,y));
			temp-=1;
		}
				
	} //end for y
		
	// close the file
	try {
		output.close();
	} catch (IOException e) {
		System.err.println("Error closing file: " + filename + "\n" + e.toString() );
		System.exit(1);
	}
	
	
	
	}
  /** Handle the "Grab" and "Exit" actions, or call super  */

  public void execute(Agent agent, Action action) {
	executeWumpusAction((AgentThing)agent, (AnAction)action);
  }
/* handled actions:
	grab
	shoot
	climb
*/

  void executeWumpusAction(AgentThing agent, AnAction action) {
	String name = action.getName();
	exiting = false;
	screaming = false; // added MW - 23 Nov 98
	if (name.equalsIgnoreCase("Grab")) {
	  Thing gold = findObjectOfType(Gold.class, agent.location);
	  if (gold != null) { placeInContainer(gold, agent); }

	// shoot action added MW 23 Nov 98
	} else if ((name.equalsIgnoreCase("Shoot")) && (arrows >0)){
	    arrows--;
		Point p = new Point(agent.location);
		while ((findObjectOfType(Obstacle.class,p)==null) && (findObjectOfType(Wumpus.class, p)==null)) {
			p.translate(agent.heading.x, agent.heading.y);
			System.out.println(">>>Arrow: ["+p.x+","+p.y+"]");
		}
		Thing wumpus = findObjectOfType(Wumpus.class, p);
		if (wumpus != null) {
			removeObject(wumpus);
			placeObject(new DeadWumpus(),p);
			screaming = true;
			p("*** The Wumpus is Dead!***");
		}
	} else if (name.equalsIgnoreCase("Climb")) {
		if ((agent.location.x == start.x) && (agent.location.y == start.y))
		{
			exiting = true;
			p("*** Agent Climbs out of the Maze ***");
			for (int i = 0; i < agent.contents.size(); i++)
			   if (agent.contents.elementAt(i) instanceof Gold)
			   p("*** Agent Recovers the Gold! ***");

		} else {
			p("* Agent must be at start point to Exit");
		}
	} else {
	  executeGridAction(agent, action);
	}

	// added 20 Nov 98 - MW
	// if you hit here, you ran into a Pit or a Wumpus
	// pity the poor, dead Agent :(
	if (findObjectOfType(Pit.class, agent.location)!=null) {
		alive = false;
		p("*** Agent fell into a pit ***");
	}
	
	if (findObjectOfType(Wumpus.class, agent.location)!=null) {
		alive = false;
		p("*** Agent is eaten by a Wumpus ***");	
	}

	//an attempt to display current percepts - added MW 23 Nov 98
	//WumpusPercept percept = (WumpusPercept) perceptArg;
	//perceptBox.setText(percept);
	
  }
/**
 * This method was created in VisualAge.
 *
 * This method is used to output a hardcoded WumpusEnvironment to a file
   to be later imported using the preset() or preset(String) methods

   The wall, pit, Wumpus, and Gold positions are saved
   
 */
public static WumpusEnvironment output() {

	// Create the Environment as well as outputting the file
	// (might as well)
	
	WumpusHunterAgent fa = new WumpusHunterAgent(10, 10);

	start = new Point(1,1);

	AgentThing a = new AgentThing(fa, start);

	AgentThing agents[] = { a };
	
	return new WumpusEnvironment("out.dat", agents, 10, 10, gridInfo);
  }
  /** Scoring: Modified by SAD 8 Feb 99
  		 10,000   points for exiting with gold
  		-1      points per action
  		-1,000 points for getting killed*/

  public float performanceMeasure(Agent agentArg) {
	AgentThing agent = (AgentThing) agentArg;
	int result = 0;
	if (alive == false) {result += -1000;}
	for (int i = 0; i < agent.contents.size(); i++)
	  if ((agent.contents.elementAt(i) instanceof Gold) &&
		  (exiting) &&
		  (agent.location.equals(start)) )result += 10000;
	return result - step;   
  }
  public Percept presentPercept(Agent agentArg) {
	AgentThing agent = (AgentThing) agentArg;
	Point loc = (Point) agent.location;
	//isStench,isGlitter,isBreeze,isBump,isScream
	return new WumpusPercept((findAdjacentObjectOfType(Wumpus.class,loc) != null ||
							 (findAdjacentObjectOfType(DeadWumpus.class,loc) != null)),
							 findObjectOfType(Gold.class,loc) != null,
							 findAdjacentObjectOfType(Pit.class,loc) != null,
							 agent.isBump,
							 screaming);
  }
/**
 * This method was created in VisualAge.
 *
 * created a predefined WumpusEnvironment using info from file "data.dat"
 */
public static WumpusEnvironment preset() {

	WumpusHunterAgent agent = new WumpusHunterAgent(10, 10);

	start = new Point(1,1);// has to be hardcoded due to static/dynamic variable problems - MW

	AgentThing a = new AgentThing(agent, start);

	AgentThing agents[] = { a };
	return new WumpusEnvironment(agents, 10, 10, "data.dat");
  }
/**
 * Takes a filename as a string and creates a new WumpusEnvironment
 * based on info from that file
 */

public static WumpusEnvironment preset(String filename) {

	WumpusHunterAgent fa = new WumpusHunterAgent(10, 10);

	start = new Point(1,1);// has to be hardcoded due to static/dynamic variable problems - MW

	AgentThing a = new AgentThing(fa, start);

	AgentThing agents[] = { a };
	return new WumpusEnvironment(agents, 10, 10, filename);
  }
/* 
Creates a random Wumpus Environment
*/
  public static WumpusEnvironment random() {

	start = new Point(1,1);// has to be hardcoded due to static/dynamic variable problems - MW
	  
	WumpusHunterAgent rf = new WumpusHunterAgent(10, 10);
	AgentThing a = new AgentThing(rf,start);
	AgentThing agents[] = { a };
	
	// the final parameter (float) is the chance of any particular square being a pit
	
	return new WumpusEnvironment(agents, 10, 10, 0.2f);
  }
}
