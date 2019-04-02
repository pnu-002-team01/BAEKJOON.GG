package baekjoonCrawler;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BaekjoonCrawler {
	
	final static String MAINURL = "http://www.acmicpc.net/";
	
	public ArrayList<String> crawlProblemNumbers() {
		Document doc = null;
		ArrayList<String> problemIDList = new ArrayList<>();
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
			String targetCSS = "td.list_problem_id";
			Elements currentProblemIDList = doc.select(targetCSS);
			
			System.out.println("Page number " + pageNumber);
			if(currentProblemIDList.isEmpty()) {
				System.out.println("Problem Number Crawling Finished.");
				break;
			}
			for(Element problemID : currentProblemIDList) {
				System.out.println(problemID.text());
			}
		}
		return problemIDList;
	}
	
	public static void main(String[] args) {			
	}

}
