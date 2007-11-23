package ksu.cis.wumpus;

public interface AgentProgram {

	/** The execute method takes a percept and returns an action. */
	public Action execute(Percept percept, Agent agent);
}
