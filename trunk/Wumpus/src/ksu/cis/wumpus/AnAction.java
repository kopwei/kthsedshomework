package ksu.cis.wumpus;

/** An implementation of Action consisting of a name for the operator,
 * and one optional argument. */
public class AnAction implements Action {

  private String name;
  private Object arg;
  public AnAction(String name) { this.name = name; arg = null; }
  public AnAction(String name, Object x) { this.name = name; arg = x; }
  public Object getArg() { return arg; }
  public String getName() { return name; }
  public String toString() {
	return name + "(" + (arg == null ? "" : arg) + ")";
  }
}
