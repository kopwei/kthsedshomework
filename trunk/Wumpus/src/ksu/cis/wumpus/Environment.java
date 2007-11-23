package ksu.cis.wumpus;

import java.util.*;

/** The basic environment: the world in which agents and other things exist. */
public abstract class Environment {

	/** A collection of all the agents in this environment */
	Vector agents = null;

	/** The number of time steps simulated so far. */
	int step = 0;

	/** A descriptive name for the environment; used only for printing. */
	static String name = "Wumpus World";

	/** A thread manager for agents. */
	AgentManager agentManager = new AgentManager();
  /** Build an environment with a given set of agents */
  Environment(Agent[] agents) { 
	this.agents = new Vector();
	for(int i=0; i<agents.length; i++) this.agents.addElement(agents[i]);
  }
  /** @returns the ith agent in the environment. */
  Agent agent(int i) { return (Agent)agents.elementAt(i); }
  /** Execute the action chosen by the agent. Normally, an Environment designer
   * would override this method in the new Environment class. */
  public abstract void execute(Agent agent, Action action);
	public void p(Object x) {System.out.println(">>> "+x);} ///////////////
  /** Measure how well the agent is doing. */
  public float performanceMeasure(Agent agent) { return -step; }
  /** Return the percept that should be handed to this agent. */
  Percept presentPercept(Agent agent) { return null; }
  /** Run the environment for numSteps time steps. */

  public Environment run(int numSteps) {
	for(; numSteps > 0 && !termination(); step++, numSteps--) {
	  p("*** Step: " + step + "; "+agents.size()+" agents");
	  for(int i=0; i<agents.size(); i++) {
	      Agent agent = agent(i);
	      Percept percept = presentPercept(agent);
	      p("Agent "+agent+" saw "+percept);
	      agent.setPercept(percept);
	      agent.setAction(agentManager.execute(agent.getProgram(), percept, agent));
	      p(" and did "+agent.getAction());
	  }
	  update();
	  for(int i=0; i<agents.size(); i++) {
	      Agent agent = agent(i);     
	      agent.setScore(performanceMeasure(agent));
	      p(agent+" scores "+agent.getScore());
	  }
	}
	return this;
  }
  /** @returns true if the simulation should end now. */
  boolean termination() { return false; }
  public String toString () { return "[" + name + "]"; }
  /** Modify the environment, based on agent actions, etc. */
  void update () { 
	// Execute each agent's action
	for(int i=0; i<agents.size(); i++) {
	  Action action = agent(i).getAction();
	  if (action != null) {execute(agent(i), action);}
	}
  }
}
