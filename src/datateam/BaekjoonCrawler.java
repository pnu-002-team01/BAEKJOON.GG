package datateam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BaekjoonCrawler {
	
	private static final String MAINURL = "http://www.acmicpc.net/";
	private static final boolean SHOW_LOG = false;
	private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";
	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public Document problemPageDocument = null;
	private Map<String,String> loginCookie = null;
	
	// Constructor
	public BaekjoonCrawler(String userID, String userPassword) {
		checkInternetConnection();
		acquireLoginCookie(userID,userPassword);
	}
	
	// Methods
	
	public static void checkInternetConnection() {
		Document document = null;
		try {
			document = Jsoup.connect(MAINURL)
							.get();
		} catch(IOException e) {
			System.err.println("Unable to connect.");
		}
		if(document != null) {
			System.err.println("Successfully connected to " + MAINURL);
		}
		else {
			System.err.println("Failed to receive main page document.");
		}
	}
	
	public void acquireLoginCookie(String userID, String userPassword) {

		// 전송할 폼 데이터
		Map<String, String> data = new HashMap<>();
		data.put("login_user_id", userID);
		data.put("login_password", userPassword);
		data.put("auto_login", "0");
		
		// 로그인(POST)
		Connection.Response response = null;
		try {
		response = Jsoup.connect("https://www.acmicpc.net/signin")
                        .userAgent(userAgent)
                        .timeout(3000)
                        .data(data)
                        .method(Connection.Method.POST)
                        .execute();
		} catch (IOException e) {
			System.err.println("Failed to connect login server.");
		}
		
		// 로그인 성공 후 얻은 쿠키를 멤버 변수로 저장.
		// 쿠키 중 TSESSION 이라는 값을 확인할 수 있다.
		loginCookie = response.cookies();
	}
	
	public void receiveProblemDocument(String problemID) {
		Document document = null;
		if(loginCookie == null) {
			System.err.println("Login cookie is not acquired.");
		}
		else {
			try {
				final String problemURL = "https://www.acmicpc.net/problem/" + problemID;
				document = Jsoup.connect(problemURL)
				                .userAgent(userAgent)
				                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
				                .header("Upgrade-Insecure-Requests", "1")	
				                .cookies(loginCookie) // acquireLoginCookie에서 얻은 '로그인 된' 쿠키
				                .get();
			} catch(IOException e) {
				System.err.println("Failed to crawl problem page");
			}
		}
		problemPageDocument = document;
	}
	
	public ArrayList<String> crawlAlgorithms() {
		ArrayList<String> algorithms = null;
		final String TARGET_CSS = "a.spoiler-link";
		if(problemPageDocument == null) {
			System.err.println("Problem page document is empty.");
		}
		else {
			algorithms = new ArrayList<String>();
			Elements algorithmTagList = problemPageDocument.select(TARGET_CSS);
			for(Element algorithm : algorithmTagList) {
				if(SHOW_LOG) {
					System.err.println(algorithm.text());
				}
				algorithms.add(algorithm.text());
			}
		}
		
		return algorithms;
	}
	
	public Map<String, String> crawlProblemSubmitState() {
		Map<String, String> problemState = null;
		final String TARGET_CSS = "table#problem-info";
		final int CATEGORY_LIMIT = 6;
		if(problemPageDocument == null) {
			System.err.println("Problem page document is empty.");
		}
		else {
			problemState = new HashMap<String, String>();
			Element table = problemPageDocument.select(TARGET_CSS).get(0);
			Elements categories = table.select("thead").get(0).select("tr").get(0).select("th");
			Elements values = table.select("tbody").get(0).select("tr").get(0).select("td");
			for(int i=0; i<CATEGORY_LIMIT; i++) {
				String category = categories.get(i).text();
				String value = values.get(i).text();
				problemState.put(category,value);
				if(SHOW_LOG) {
					System.err.println(category + ": " + value);
				}
			}

		}
		return problemState;
	}
	
	public ArrayList<String> crawlSolvedProblem(String userID){
		String UserPageURL = MAINURL + "/user/" +  userID;
		Document doc = null;
		ArrayList < String > res = new ArrayList< String >();
		
		if(loginCookie == null) {
			System.err.println("Login cookie is not acquired.");
		}
		
		try {
			doc = Jsoup.connect(UserPageURL)
	                .userAgent(userAgent)
	                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
	                .header("Upgrade-Insecure-Requests", "1")	
	                .cookies(loginCookie) // acquireLoginCookie에서 얻은 '로그인 된' 쿠키
	                .get();
			
			final String TARGET_CLASS = "panel-body";
			final String SPLIT_CLASS = "span.problem_number";
			
			
			Elements myProblemList = doc.getElementsByClass(TARGET_CLASS);
			Elements solvedProblem = myProblemList.get(0).select(SPLIT_CLASS);
			
			
			
			for( int i = 0; i < solvedProblem.size(); ++i ) {
				res.add(solvedProblem.get(i).text());
			}  

			
			
		} catch(IOException e) {
			System.err.println("Fail to get User Information");
		}
		return res;
	}

	public ArrayList<String> crawlUnsolvedProblem(String userID){
		String UserPageURL = MAINURL + "/user/" +  userID;
		Document doc = null;
		ArrayList < String > res = new ArrayList< String >();
		
		if(loginCookie == null) {
			System.err.println("Login cookie is not acquired.");
		}
		
		try {
			doc = Jsoup.connect(UserPageURL)
	                .userAgent(userAgent)
	                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
	                .header("Upgrade-Insecure-Requests", "1")	
	                .cookies(loginCookie) // acquireLoginCookie에서 얻은 '로그인 된' 쿠키
	                .get();
			
			final String TARGET_CLASS = "panel-body";
			final String SPLIT_CLASS = "span.problem_number";
			
			
			Elements myProblemList = doc.getElementsByClass(TARGET_CLASS);
			Elements unsolvedProblem = myProblemList.get(1).select(SPLIT_CLASS);
			
			
			
			for( int i = 0; i < unsolvedProblem.size(); ++i ) {
				res.add(unsolvedProblem.get(i).text());
			}  

			
			
		} catch(IOException e) {
			System.err.println("Fail to get User Information");
		}
		return res;
	}
	
	public static ArrayList<String> crawlProblemNumbers() {
		Document doc = null;		

		ArrayList<String> problemIDList = new ArrayList<>();
		
		for(int pageNumber = 1;; pageNumber++) {
			String problemPageURL = MAINURL + "/problemset/" + String.valueOf(pageNumber);
			try {
				doc = Jsoup.connect(problemPageURL).get();
			} catch(IOException e) {
				break;
			}
			final String TARGET_CSS = "td.list_problem_id";
			Elements currentProblemIDList = doc.select(TARGET_CSS);
			
			if(SHOW_LOG) {
				System.err.println("Page number " + pageNumber);
			}
			if(currentProblemIDList.isEmpty()) {
				System.err.println("Problem Number Crawling Finished.");
				break;
			}
			for(Element problemID : currentProblemIDList) {
				if(SHOW_LOG) {
					System.err.println(problemID.text());
				}
			}
		}
		return problemIDList;
	}
	
	public void writeProblemJson(String problemID) {
		
		ArrayList<String> json_categories = new ArrayList<String>();
		json_categories.add("percentCorrect");
		json_categories.add("memoryLimit");
		json_categories.add("correctNumber");
		json_categories.add("submittedNumber");
		json_categories.add("solvedPeopleNumber");
		json_categories.add("timeLimit");
		
		this.receiveProblemDocument(problemID);
		String jsonResult = "{\n\t\"problemNumber\" : \""+problemID+"\",\n";
		
		int i=0;
		Map <String,String> problemState = this.crawlProblemSubmitState();
		for(String category : problemState.keySet()) {
			String categoryContent = "\t\"" + json_categories.get(i) + "\" :\"" + problemState.get(category) + "\",\n";
			i+=1;
			jsonResult += categoryContent;
		}
		
		LocalDate localDate = LocalDate.now();
		jsonResult += "\t\"updateDate\" : \""+DTF.format(localDate) + "\",\n";
		
		ArrayList<String> problemAlgorithms = this.crawlAlgorithms();
		jsonResult += "\t\"algorithms\" : [\n";
		int algorithmTagSize = problemAlgorithms.size();
		for(i=0; i<algorithmTagSize; i++) {
			jsonResult += "\t\t\""+ problemAlgorithms.get(i) + "\"";
			if(i != algorithmTagSize-1) {
				jsonResult += ",\n";
			}
		}
		jsonResult += "\n\t]\n}";
		
		if(SHOW_LOG) {
			System.out.print(jsonResult);
		}
		
		//Write problem json as problemID.json
		File file = new File("data/problems/"+problemID+".json");
		
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(jsonResult);
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {			
		String userID = "id";
		String userPW = "password";
		
		BaekjoonCrawler bojcrawl = new BaekjoonCrawler(userID,userPW);
		bojcrawl.writeProblemJson("1001");
	}

}
