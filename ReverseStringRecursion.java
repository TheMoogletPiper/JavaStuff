package exercises;


public class ReverseStringRecursion {

	public static void main(String[] args) {
		
		final String WORD = "Ayo Sneako shut the fuck up cause now I'm really about to get to the packing dikkata dippin flippin floppin dipty doppin whippin slippin sippin you dirty as shit bitch. Look at yo haircut boy you got Plants vs Zombies map on your head you ugly ass shit. Shut yo nasty- you got that cha cha cha chia cha cha cha chia haircut boy. Nah nah nah. For real though you really thought I wasn't gonna get packing ya lil nasty ahh boy. You bout dirty as shit- you look like Barack Obamas autistic cousin Wataka Hamba dumbass boy. You ugly as shit shut yo ugly ass up boy, and the dude in the back you look like a suicide grunt from Halo 3. Yo ass ugly as shit boy shut yo nasty ass up boy. Now I'm about to get to the packing boy. You better get your shopping cart crippled fart got an A mom I'm smart Bubble Bass wax your ass Covids real wheres my mask chewed on pencil little freckle bought a bike got no pedals disabled crumpled bag dirty rag Fortnite lag absent dad hit the dab Scallywag Canadian flag humpback sweaty sack bubble wrap buckle flap its a trap broken down Tow Mater chewed on eraser 13 year old vaper Windows 10 wallpaper dumptruck Donald Duck tummy tuck ayo what the fuck ping pong wing wong hit the gong Extendo thong Hong Kong Disney sing along disrespectful to yo mom feel my arm is it strong lookin ass back boy- look at yo eyebrows that bitch look at the dead body on a fuckin dinner date. Ohoo, dumbass bitch little big fat nostrils- you got a booger in your fuckin nose named Hubert. Fuckin ass bean bag dirty as shit slimy ass mustache to your ass ugly cell boy- your teeth so yellow you spit piss out of yo mouth you throw rocks when you cry your tears go down your back ugly ass bitch. Look at yo hairtop boy you hit a Brawlhalla combo with yo hairline. Down down down down down left up right CTRL right down down down left 6. You ugly as shit boy shut yo nasty ass up you look like a Notorious Special easter egg they call you with an extra chromosome THE DOUBLE TROUBLE. Fuck boy you got nasty as shit boy you got dirty cell- your name is Sneako boy. Your mother put HotWheels cars in her asshole at night and they caused THE THUNDERDOME. Fuck ass boy you about nasty as shit you look like a disabled crumpled bag fuckin disco bottle in your esophageal boat fake disabled crooked walkin crawfish having an autistic arm wire. But you look like a disabled speakin like \"UUUUOOOOEEEAAA- EEUUUEAAAH OOEEEUHHHAA-HUUUAAJJJUH-AAAEEOOUU AEUUJEWWEHHH AARWWWEERR\". Don't matter boy you a nasty shit- why your forehead so glossy you big nasty ass boy and Jidion I'm about to get at yo ass. You look like a happy birthday but yo worn ass thought I wasn't invited into the packing you know I do boy. Your head so shiny boy you look like you went to Walter Whites car wash in Breaking Bad boy.";
		
		
		//Let's see what's faster.
		//set timer.
		long timer = System.currentTimeMillis();
		
		//Given a string, reverse it using recursion.
		System.out.println(reverseStringRecursive(WORD));
		
		//Print difference of time
		System.out.println(System.currentTimeMillis() - timer);
		
		//reset timer.
		timer = System.currentTimeMillis();
		
		//Do it as I would normally do.
		System.out.println(reverseStringAsANormalPerson(WORD));
		
		//Print difference of time
		System.out.println(System.currentTimeMillis() - timer);
	
	}
	
	public static String reverseStringRecursive(String str) {
		
		//Base case
		if (str.length() == 0) {
			return str;
		}
		
		//Get last character on the string parameter
		return str.charAt(str.length() - 1) + 
			   
		//And call again the function with the substring
			   reverseStringRecursive(str.substring(0, str.length()-1));

	}
	
	
	public static String reverseStringAsANormalPerson(String str) {
		
		String result = new String();
		
		//Get max index
		int strIndexLen = str.length() - 1;
		
		//Do a loop and get the last char into the new string
		for( int i = 0; i < str.length(); i++) {
			result += str.charAt(strIndexLen);
			strIndexLen--;
		}
		
		return result;
	}

}
