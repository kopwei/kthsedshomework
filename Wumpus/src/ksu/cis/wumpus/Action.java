package ksu.cis.wumpus;

/** An action is something that can be executed by an agent.
 * the idea is that the agent returns its chosen action to the
 * environment (or maybe the AgentManager), which then executes the action
 */
public interface Action {
  public Object getArg();
  public String getName();
}
