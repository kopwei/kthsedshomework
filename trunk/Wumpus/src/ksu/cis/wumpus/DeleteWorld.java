package ksu.cis.wumpus;

/**
 * This type was created in VisualAge.
 */
public class DeleteWorld {
	static String string[];
/**
 * DeleteWorld constructor comment.
 */
public static void main(String args[]) {
	String[] string = Create.read();

	int k=0;
	System.out.println("Your current environments are: ");
	for (int i = 0; i< string.length; i++) {
		if (string[i] != null) {
			System.out.println(string[i]);
		}
	}

	System.out.println("Which world would you like to delete");

	String type = Create.readKey();
	k = 1;
	for (int l = 0; l< string.length; l++){
		if(type.equals(string[l])) {
			k=l;
		}
	}
	for (int j = k; j+1 < string.length; j++) {
			string[j] = string [j+1];
	}

	Create.write("null", string);
	
	System.out.println("Your new list is: ");

	for (int m = 0; m< string.length; m++) {
		if ((string[m] != null) && ("null" != string[m])) {
			System.out.println(string[m]);
		}
	}
}
}
