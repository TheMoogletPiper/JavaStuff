package wikiFacts;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class WikiFacts {
	
	final static String testText = "{{pp-pc1}}\\n{{pp-move-indef}}\\n{{calendar}}\\n{{This date in recent years}}\\n{{Day}}\\n\\n==Events==\\n===Pre-1600===\\n*[[1358]] &ndash; The [[Republic of Ragusa]] is founded.\\n*[[1497]] &ndash; [[Cornwall|Cornish]] rebels [[Michael An Gof]] and [[Thomas Flamank]] are executed at [[Tyburn]], London, England.\\n*[[1499]] &ndash; [[Amerigo Vespucci]] sights what is now [[Amapá]] State in Brazil.<ref>{{cite book|first=Clifton|last=Daniel|title=Chronicle of America|publisher=Chronicle publication|year=1989|page=18|isbn=0-13-133745-9}}</ref> \\n*[[1556]] &ndash; The thirteen [[Stratford Martyrs]] are [[burned at the stake]] near London for their [[Protestant]] beliefs.\\n===1601–1900===\\n*[[1743]] &ndash; In the [[Battle of Dettingen]], [[George II of Great Britain|George II]] becomes the last reigning British monarch to participate in a battle.\\n*[[1760]] &ndash; [[Anglo-Cherokee War]]: [[Cherokee]] warriors defeat [[British Empire|British]] forces at the [[Battle of Echoee]] near present-day [[Otto, North Carolina]].\\n*[[1806]] &ndash; British forces take [[Buenos Aires]] during the first of the [[British invasions of the River Plate]].\\n*[[1844]] &ndash; [[Joseph Smith]], founder of the [[Latter Day Saint movement]], and his brother [[Hyrum Smith]], are [[Death of Joseph Smith|killed by a mob]] at the [[Carthage, Illinois]] jail.\\n*[[1864]] &ndash; [[American Civil War]]: Confederate forces defeat Union forces during the [[Battle of Kennesaw Mountain]] during the [[Atlanta Campaign]].\\n*[[1895]] &ndash; The inaugural run of the [[Baltimore and Ohio Railroad]]'s [[Royal Blue (train)|''Royal Blue'']] from Washington, D.C., to New York City, the first U.S. passenger train to use [[electric locomotives]].\\n*[[1898]] &ndash; The first solo [[circumnavigation]] of the globe is completed by [[Joshua Slocum]] from Briar Island, [[Nova Scotia]].\\n===1901–present===\\n*[[1905]] &ndash; During the [[Russo-Japanese War]], sailors start a mutiny aboard the [[Russian battleship Potemkin|Russian battleship ''Potemkin'']].\\n*[[1914]] &ndash; The [[Illinois Monument]] is dedicated at [[Cheatham Hill]] in what is now the [[Kennesaw Mountain National Battlefield Park]].<ref>{{Cite web|title=The Illinois Monument (U.S. National Park Service)|url=https://www.nps.gov/places/the-illinois-monument.htm|access-date=2021-05-18|website=www.nps.gov|language=en|archive-date=2021-05-17|archive-url=https://web.archive.org/web/20210517210832/https://www.nps.gov/places/the-illinois-monument.htm|url-status=live}}</ref> \\n*";
	final static String regexPattern = "\\[\\[(\\d{4})\\]\\]: (.+?)\\\\n\\*";
	static String wikiURL = "https://es.wikipedia.org/w/api.php?action=parse&format=json&page={sysdate}&prop=wikitext&formatversion=2";
	static String wikiResponseString;
	static ArrayList<String> wikiFacts;
	
	//Get the facts from Wikipedia
	static ArrayList<String> getFacts() throws IOException, ParseException{
		
		//Aux vars
		String auxString;
		boolean badRequest = false;
		
		//Prepare the URL, getting the day and month from the system.
		//Month should be literal & lowercase.
		String day = Integer.toString(LocalDate.now().getDayOfMonth());
		String month = getMonthLiteral(LocalDate.now().getMonthValue());
		auxString = wikiURL.replace("{sysdate}", day + "_de_" + month);
		wikiURL = auxString;
		
		//Create client
		CloseableHttpClient cHttpClient = HttpClients.createDefault();
		
		try {		
		
			//Create URL instance
			HttpGet httpGet = new HttpGet(wikiURL);
				
			//Execute request and get response
			@SuppressWarnings("deprecation")
			CloseableHttpResponse cHttpResponse = cHttpClient.execute(httpGet);
			
			try {
				
				//Extract content to string
				HttpEntity httpEntity = cHttpResponse.getEntity();
				if (httpEntity != null) {
					wikiResponseString = EntityUtils.toString(httpEntity); 
				}
				else {
					//Bad request or no useful data! Mark it but don't leave until closing!
					badRequest = true;	
				}
			
			//Close response
			} finally {
				cHttpResponse.close();
			}
		
		//Close client
		} finally {
			cHttpClient.close();
		}
		
		//Leave if needed, wikiFacts empty handled on exit.
		if (badRequest == true) {
			return wikiFacts;
		}
		
		//Parse HTTP response string into an array of facts
		parseFacts();
		
		//Return facts as an ArrayList<String>
		return wikiFacts;
	}
	
	//Return an array of facts
	static void parseFacts(){
		
		//Aux variables
		int auxIndex = 0;
		String auxFact = null;
		
		//Init String to return
		wikiFacts = new ArrayList<String>();
		
		//Prepare regex pattern
		Pattern pattern = Pattern.compile(regexPattern);
		
		//Set pattern to text
		Matcher matcher = pattern.matcher(wikiResponseString);
		
		//Get them to array
		Iterator<MatchResult> matchIterator = matcher.results().iterator();
		while (matchIterator.hasNext() == true) {
			
			MatchResult result = matchIterator.next();
			String year = result.group(1);
			String fact = result.group(2);
			wikiFacts.add(year + ": " + fact);
			
			//Remove content from the string that we don't want.
			//This needs to be adapted as more cases are detected.
			auxFact = wikiFacts.get(auxIndex).replaceAll("<ref>.*?</ref>", "");
			auxFact = auxFact.replaceAll("\\\\n===.*?===", "");
			auxFact = auxFact.replaceAll("\\[|\\]", "");
			auxFact = auxFact.replaceAll("\\|", " o ");
			auxFact = auxFact.replaceAll("&nbsp;", " ");
			//auxFact = auxFact.replaceAll("\\", "");
			wikiFacts.set(auxIndex, auxFact);
			auxIndex++;
		}
		
	}
	
	//Yes I had to do this, definitely quicker than find some obscure method.
	static String getMonthLiteral(int month) {
		String monthLiteral = null;
		switch(month) {
		case (1):
			monthLiteral = "enero";
			break;
		case (2):
			monthLiteral = "febrero";
			break;
		case (3):
			monthLiteral = "marzo";
			break;
		case (4):
			monthLiteral = "abril";
			break;
		case (5):
			monthLiteral = "mayo";
			break;
		case (6):
			monthLiteral = "junio";
			break;
		case (7):
			monthLiteral = "julio";
			break;
		case (8):
			monthLiteral = "agosto";
			break;
		case (9):
			monthLiteral = "septiembre";
			break;
		case (10):
			monthLiteral = "octubre";
			break;
		case (11):
			monthLiteral = "noviembre";
			break;
		case (12):
			monthLiteral = "diciembre";
			break;
		}
		return monthLiteral;
	}
	
}
