package ksu.cis.wumpus;

/** An agent is something that perceives and acts. 
 * @author Peter Norvig
 */

public interface Agent {

  public Action       getAction();
  public Percept      getPercept();
	public AgentProgram getProgram();
  public float        getScore();
  public void         setAction(Action action);
  public void         setPercept(Percept p);
  public void         setScore(float s);
}
