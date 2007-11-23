package ksu.cis.wumpus;

import java.util.Random;

public class AIMA {

  static Random seed = new Random();

  
  static Object newOne(Object x) {
	try {
	  return x.getClass().newInstance();
	} catch (Exception e) {
	  return null;
	}
  }
  static float random() { return seed.nextFloat(); }
  static Object randomChoice(Object[] choices) {
	return choices[randomInt(choices.length)];
  }
  static Object randomChoice(Object x, Object y) {
	return randomInt(2) == 1 ? x : y;
  }
  static Object randomChoice(Object x, Object y, Object z) {
	switch (randomInt(3)) { 
	  case 0: return x;
	  case 1: return y;
	  case 2: return z;
	}
	return x; // Shouldn't ever happen
  }
  static int randomInt(int n) {
	return Math.abs(seed.nextInt() % n);
  }
}
