package nameSorter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*

NAME SORTER

Grab a text file and sort the words in alphabetical order. Store it as a new file under the same directory.
The path of the file should be included as an argument when calling the .class file

*/
public class NameSorter {
	
	//Declaration of input(f) and output(g) files
	static File f, g;
	
	//This method takes the input path and creates the new file in the same path with "NamesSorted.txt" name.
	static void setPath (String s) {
		
		//Take the string and split it by the backslashes
		String[] parts = s.split("\\\\");
		
		//Create an arraylist and store each word from our array into it
		ArrayList<String> partsArray = new ArrayList<>();
		
		for (String st: parts) {
			partsArray.add(st);
		}
		
		//Delete the last word, which is the file name
		partsArray.remove(partsArray.size()-1);
		
		//And replace it with the new name
		partsArray.add("NamesSorted.txt");
		
		//Use the string builder to append each word from our array list with the backslashes, creating our output path
		StringBuilder sb = new StringBuilder();
		
		for (String st: partsArray) {
			sb.append(st + "\\\\");
		}
		
		//Finally create the file named with the new path
		g = new File(sb.toString());
	}
	
	//Definitely may be a better sorting method. Something like sorting as we read the file.
	public static void main (String[] args) throws IOException {
		
		//Input File
		f = new File(args[0]);
		
		//Output file
		setPath(args[0]);
		
		//Object to write on output
		FileWriter fw = new FileWriter(g);
		
		//Arraylist and scanner to read input and store its stuff
		ArrayList<String> names = new ArrayList<>();
		Scanner reader = new Scanner(f);
		
		//Read the input and store each line in our arraylist
		while (reader.hasNext()) {
			names.add(reader.nextLine());
		}
		reader.close();
		
		//Sort the arraylist
		names.sort( (String a, String b) -> a.compareTo(b));
		
		//Write each string stored in our arraylist into the output file
		for (String s: names) {
			//System.out.println(s);
			fw.write(s + System.getProperty("line.separator"));
		}
		fw.close();
		
		
	}

}
