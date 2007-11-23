package ksu.cis.wumpus;

/**
 * This type was created in VisualAge.
 */
import java.io.*;
 
 public class ReadConsole {
	 static String file[];
/**
 * ReadConsole constructor comment.
 */
public static void main(String args[]) {
	String string[] = {"easy", "hard", "medium", "easy1", "hard1", "medium1"};
	
	try {
		FileWriter fw = new FileWriter("another");
		for(int i=0; i<6; i++) {
		
			fw.write(string[i] + "\n");
		
			
		
		}
	//	fw.write(add + "\n");
		
		
		fw.close();
		
	}
	catch(Exception e) {
		System.out.println("JException: " + e);
	}

}
}
