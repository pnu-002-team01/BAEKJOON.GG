package baekjoonCrawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Initial {
	
	final static String MAINURL = "http://www.acmicpc.net/";
	
	public static void main(String[] args) {
		Document doc = null;
		try {
		 doc = Jsoup.connect(MAINURL).get();
		} catch(IOException e) {
			System.out.println("Unable to connect.");
		}
		String title = doc.title();
		System.out.println("Successfully connected to " + title);
		
		for(int pageNumber = 1;; pageNumber++) {
			String problemPageURL = MAINURL + "/problemset/" + String.valueOf(pageNumber);
			try {
				doc = Jsoup.connect(problemPageURL).get();
			} catch(IOException e) {
				break;
			}
			
		}
				
	}

}
