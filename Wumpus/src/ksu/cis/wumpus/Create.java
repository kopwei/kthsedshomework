package ksu.cis.wumpus;

/**
 * This type was created in VisualAge.
 */
import java.io.*;
import java.awt.Point;
class Create {
	static String string[];
	
	static int j;

	static int size = 20;   //allows for 20 different environments

	static String name;

	static int length = 10;  // the length of one side
/**
 * Create constructor comment.
 */
public static void main(String arg, int[][] grid) {
	WumpusHunterAgent fa = new WumpusHunterAgent(length, length);
	int[][] grid2 = grid;
	Point start = new Point(1,1);

	AgentThing a = new AgentThing(fa, start);

	AgentThing agents[] = { a };
	System.out.println("What would you like to call this?");
	String name = readKey();
	System.out.println("You have named your file: " + name);
	
	WumpusEnvironment f = new WumpusEnvironment(name, agents, length, length, grid2);
	string = read();
	string[j] = name;

	write(name, string);

	string = read();
	System.out.println(name + " has been saved.");
	System.out.println("Updated list is: ");
		 
	for (int i = 0; i<string.length; i++){
		if (string[i] != null) {
			System.out.println(string[i]);
		}
	}
}
/**
 * This method was created in VisualAge.
 */
public static String[] read() {
 String strin[] = new String[size]; 
 int k = 0;
	try{

		FileReader fr = new FileReader("wumpusEnvironments");
		BufferedReader br = new BufferedReader(fr);

		while((strin[k] = br.readLine()) != null){
			
			
			if ("null".equals(strin[k])) {
				k--;
			}
			k++;
			
		
		}
		j=k++;
		
	}
	catch (Exception e) {
		System.out.println("Exception:: " + e);
	}
	
	return strin;
}
/**
 * This method was created in VisualAge.
 */
public static String readKey() {
	
	try {
		InputStreamReader isr = new InputStreamReader(System.in);

		BufferedReader br = new BufferedReader(isr);

		name = br.readLine();
		//if (name != null) {
			isr.close();
		//}
	}
	catch (Exception e) {
		System.out.println("Exception: " + e);
	}

	return name;
}
/**
 * This method was created in VisualAge.
 */
public static void write(String add, String[] string) {
	try {
		FileWriter fw = new FileWriter("wumpusEnvironments");
		for(int i=0; i<j+1; i++) {
		
			fw.write(string[i] + "\n");	
		}

		
		fw.close();
		
	}
	catch(Exception e) {
		System.out.println("Exception: " + e);
	}

}
}
