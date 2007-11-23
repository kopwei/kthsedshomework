package ksu.cis.wumpus;

public class WumpusPercept implements Percept {
  public boolean isStench,isGlitter,isBreeze,isBump,isScream; 
  public WumpusPercept(boolean stench, boolean glitter, boolean breeze, boolean bump, boolean scream) {
	isStench = stench;
	isGlitter = glitter; 
	isBreeze = breeze;
	isBump = bump;
	isScream = scream; 
  }
  public String toString() {
	return "[ " 
				+ (isStench  ? "stench "  : "")
				+ (isGlitter ? "glitter " : "")
				+ (isBreeze  ? "breeze "  : "")
				+ (isBump    ? "bump "    : "")
				+ (isScream  ? "scream "  : "") +"]";
  }
}
