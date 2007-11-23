package ksu.cis.wumpus;

/** The AgentManager is in charge of running agent programs: managing threads
 * and doing whatever it takes to get back the agent's action, given a percept.
 * Subclasses can provide complex management; in this class we just call the
 * agent program; everything runs in one thread.
 */

public class AgentManager {
  public Action execute(AgentProgram program, Percept percept, Agent agent) {
	return program.execute(percept, agent);
  }
}
