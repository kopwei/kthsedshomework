package ksu.cis.wumpus;

import java.util.*;
import java.awt.Point;

/** AgentThing is a simple implementation of Agent, as a Thing. */

public class AgentThing extends Thing implements Agent {

  // Instance Variables
  private Percept      percept = null;
  private Action       action = null;
  private float        score = 0;
  private AgentProgram program;
  public AgentThing(AgentProgram program) { 
	this.program = program; 
	location = new Point(1,1);
	heading = new Point(1,0);
  }
/**
 * This method was created in VisualAge.
 */
 // Added 2 Dec 98 - MW
 // this creator has startpoint as a paramater, so it doesnt always start at 1,1
  public AgentThing(AgentProgram program, Point startpoint) {
	this.program = program;
	location = new Point(startpoint); 
	heading = new Point(1,0);
  }
  public Action        getAction() { return action; }
  public Percept       getPercept() { return percept; }
  // The Agent Interface
  public AgentProgram  getProgram() { return program; }
  public float         getScore() { return score; }
  public void          setAction(Action a) { action = a; }
  public void          setPercept(Percept p) { percept = p; }
  public void          setScore(float s) { score = s; }
}
