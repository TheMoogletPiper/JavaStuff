package wikiFacts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.hc.core5.http.ParseException;

public class Main {
	
	public static void main(String[] args) {
		
		//Thoughts: the idea was to create a simple program to retrieve the fact of the day. Originally, intended to be a program to retrieve a random sentence from the internet,
		// but I couldn't find any interesting webs to work with, which made me think about the wikipedia as a nice source of information. Also discovered the wikimedia api, 
		// and took my time to learn how to use it properly. The program needs a few tweaks, as the actual "production" version lies on another server in ABAP,
		// which I've taken more care of than I would even like to disclosure.
		//
		// To be honest, too much time for me to keep an eye on this, as I would need to test every day of the year to see the different patterns,
		// because every day is a surprise, and this has given me enough headaches. Fun program, 7/10, needs more TODO!
		
		//Get facts from wikipedia
		ArrayList<String> wikiFacts = new ArrayList<>();
		try {
			wikiFacts = WikiFacts.getFacts();
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		
		
		//Show them all in the console.
		if (wikiFacts.size() == 0 ) {
			System.out.println("No facts today :(");
		} else {
		wikiFacts.forEach( (fact) -> System.out.println(fact.toString()));
		}
		
		//Send a random fact to a phone number :D
		WhatsappSender.sendMsg(wikiFacts.get(new Random().nextInt(wikiFacts.size())));
		
	}

}
