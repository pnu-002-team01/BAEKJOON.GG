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
import org.json.simple.JSONObject;

public class BaekjoonCrawler {
	
	private static final String MAINURL = "http://www.acmicpc.net/";
	private static final boolean SHOW_LOG = true;
	private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";
	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public Document problemPageDocument = null;
	private Map<String,String> loginCookie = null;
	private Map<String,String> languageMap = getMap();
	
	// Constructor
	public BaekjoonCrawler(String userID, String userPassword) {
		checkInternetConnection();
		acquireLoginCookie(userID,userPassword);
	}
	
	public HashMap<String,String> getMap(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("C++14","88");
		map.put("Java","3");
		map.put("Python 3","28");
		map.put("C11","75");
		map.put("PyPy3","73");
		map.put("C","0");
		map.put("C++","1");
		map.put("C++11","49");
		map.put("C++17","84");
		map.put("Java (OpenJDK)","91");
		map.put("Java 11","91");
		map.put("Python 2","6");
		map.put("PyPy2","32");
		map.put("Ruby 2.5","68");
		map.put("Kotlin (JVM)","69");
		map.put("Kotlin (Native)","92");
		map.put("Swift","74");
		map.put("Text","74");
		map.put("C# 6.0","62");
		map.put("C# 6.0 (.NET)","86");
		map.put("node.js","17");
		map.put("Go","12");
		map.put("Go (gccgo)","90");
		map.put("D","29");
		map.put("F#","37");
		map.put("PHP","7");
		map.put("Rust","44");
		map.put("Rust 2018","94");
		map.put("Pascal","2");
		map.put("Scala","15");
		map.put("Lua","16");
		map.put("Perl","8");
		map.put("Perl6","42");
		map.put("Ruby 1.8","4");
		map.put("Ruby 1.9","65");
		map.put("R","72");
		map.put("Haskell","11");
		map.put("Object-C","10");
		map.put("Object-C++","11");
		map.put("C (Clang)","59");
		map.put("C++ (Clang)","60");
		map.put("C++11 (Clang)","66");
		map.put("C++14 (Clang)","67");
		map.put("C11 (Clang)","77");
		map.put("C++17 (Clang)","85");
		map.put("Ceylon","76");
		map.put("Golfscript","79");
		map.put("Octave","89");
		map.put("Assembly (32bit)","27");
		map.put("Assembly (64bit)","87");
		map.put("C# 3.0","9");
		map.put("VB.NET 2.0","20");
		map.put("VB.NET 4.0","63");
		map.put("Bash","5");
		map.put("Fortran","13");
		map.put("Scheme","14");
		map.put("CoffeeScript","18");
		map.put("Ada","19");
		map.put("awk","21");
		map.put("OCaml","22");
		map.put("Brainfuck","23");
		map.put("Whitespace","24");
		map.put("Groovy","25");
		map.put("Tcl","26");
		map.put("Commom Lisp","30");
		map.put("Erlang","31");
		map.put("Clojure","33");
		map.put("Rhino","34");  		
		map.put("Cobol","35");
		map.put("Smalltalk","36");
		map.put("SpiderMonkey","38");
		map.put("Falcon","39");
		map.put("Factor","40");
		map.put("Pike","41");
		map.put("sed","43");
		map.put("Dart","45");
		map.put("Boo","46");
		map.put("Intercal","47");
		map.put("bc","48");
		map.put("Oz","50");
		map.put("Alice","51");
		map.put("Prolog","52");
		map.put("Nemerle","53");
		map.put("Cobra","54");
		map.put("Nimrod","55");
		map.put("Forth","56");
		map.put("Julia","57");
		map.put("Io","61");
		map.put("Algol 68","70");
		map.put("Befunge","71");
		map.put("FreeBASIC","78");
		map.put("Gosu","80");
		map.put("Haxe","81");
		map.put("LOLCODE","82");
		map.put("아희","83");
	    return map;	
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

		Map<String, String> data = new HashMap<>();
		data.put("login_user_id", userID);
		data.put("login_password", userPassword);
		data.put("auto_login", "0");
		
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
		
		// �α��� ���� �� ���� ��Ű�� ��� ������ ����.
		// ��Ű �� TSESSION �̶�� ���� Ȯ���� �� �ִ�.
		this.loginCookie = response.cookies();
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
				                .cookies(loginCookie) // acquireLoginCookie��?��? �뼸�� '濡쒓?���?��?' ?�좏궎
				                .get();
			} catch(IOException e) {
				System.err.println("Failed to crawl problem page");
			}
		}
		this.problemPageDocument = document;
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
	                .cookies(loginCookie) // acquireLoginCookie��?��? �뼸�� '濡쒓?���?��?' ?�좏궎
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
	                .cookies(loginCookie) // acquireLoginCookie��?��? �뼸�� '濡쒓?���?��?' ?�좏궎
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
				problemIDList.add(problemID.text());
				if(SHOW_LOG) {
					System.err.println(problemID.text());
				}
			}
		}
		return problemIDList;
	}
	
	public void writeUserInfoJson( String userID) {
		
		if(loginCookie == null) {
			System.err.println("Login cookie is not acquired.");
		}
		
		String jsonResult = "{\n\t\"userID\" : \""+userID+"\",\n";
		ArrayList<String> solvedProblems = this.crawlSolvedProblem(userID);
		
		jsonResult += "\t\"countSolvedProblems\" : \""+ solvedProblems.size() + "\",\n";
		jsonResult += "\t\"solvedProblems\" : [\n";
		int problemTagSize = solvedProblems.size();
		for(int i=0; i<problemTagSize; i++) {
			jsonResult += "\t\t\""+ solvedProblems.get(i) + "\"";
			if(i != problemTagSize-1) {
				jsonResult += ",\n";
			}
		}
		jsonResult += "\n\t],\n";
		
		ArrayList<String> unsolvedProblems = this.crawlUnsolvedProblem(userID);

		jsonResult += "\t\"countUnsolvedProblems\" : \""+ unsolvedProblems.size() + "\",\n";
		jsonResult += "\t\"unsolvedProblems\" : [\n";
		problemTagSize = unsolvedProblems.size();
		for(int i=0; i<problemTagSize; i++) {
			jsonResult += "\t\t\""+ unsolvedProblems.get(i) + "\"";
			if(i != problemTagSize-1) {
				jsonResult += ",\n";
			}
		}
		jsonResult += "\n\t],\n";
		
		
		LocalDate localDate = LocalDate.now();
		jsonResult += "\t\"updateDate\" : \""+DTF.format(localDate) + "\"";
		jsonResult += "\n}";
		
		if(SHOW_LOG) {
			System.out.print(jsonResult);
		}
		
		//Write problem json as problemID.json
		File file = new File("data/users/"+userID+".json");
		
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(jsonResult);
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
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
	
	
	
	public String writeUserCode(String userID,String problemID){
		int pageNum = 1;
		String language = null;
		String MyCodePage = MAINURL + "/status?from_mine="+pageNum+"&problem_id=" +problemID+"&user_id="+  userID;
		Document doc = null;
		JSONObject jsonObject = new JSONObject();
		
		if(loginCookie == null) {
			System.err.println("Login cookie is not acquired.");
		}
		
		try {

			doc = Jsoup.connect(MyCodePage)
	                .userAgent(userAgent)
	                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
	                .header("Upgrade-Insecure-Requests", "1")	
	                .cookies(loginCookie) // acquireLoginCookie��?��? �뼸�� '濡쒓?���?��?' ?�좏궎
	                .get();
			
						
			Elements table = doc.getElementsByTag("tr");
			for(Element e: table) {
				Elements tdElements = e.select("td");
				if(tdElements.size() > 5) {
					if(tdElements.get(3).text().equals("맞았습니다!!") ) {
						language = tdElements.get(6).select("a").get(0).text();
						String relHref = tdElements.get(6).select("a").get(0).attr("href"); 
						
						doc = Jsoup.connect( MAINURL + relHref)
				                .userAgent(userAgent)
				                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
				                .header("Upgrade-Insecure-Requests", "1")	
				                .cookies(loginCookie) // acquireLoginCookie��?��? �뼸�� '濡쒓?���?��?' ?�좏궎
				                .get();
						
						Elements elements = doc.getElementsByClass("col-lg-12");
						jsonObject.put("code",elements.text());
						break;
					}
				}
			}
			
		} catch(IOException e) {
			System.err.println("Fail to get User Information");
		}
		
		File file = new File("data/sources/"+userID+"_"+problemID+".json");
		
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(jsonObject.toString());
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		String languageNumber = languageMap.get(language);
		
		return languageNumber;
	}
	
	public void writeProblemCodes(String problemID, String language){
		
		String pageNum = "1";
		int count = 0;		
		Document doc = null;
		JSONObject jsonObject = new JSONObject();
		
		if(loginCookie == null) {
			System.err.println("Login cookie is not acquired.");
		}
		
		try {
			
			String codePage = MAINURL + "problem/status/"+ problemID + "/" + language + "/"+pageNum;
			doc = Jsoup.connect(codePage)
	                .userAgent(userAgent)
	                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
	                .header("Upgrade-Insecure-Requests", "1")	
	                .cookies(loginCookie) 
	                .get();
						
			Elements elements = doc.getElementsByTag("tr");
			
			for( Element e: elements ) {
				Elements tdElements = e.select("td");
				if(tdElements.size() > 5) {
			
					Elements temp1 = tdElements.get(6).select("a");
		
					if(!temp1.isEmpty()) {
						String rank = tdElements.get(0).text();
						String relHref = temp1.get(0).attr("href");
						doc = Jsoup.connect( MAINURL + relHref)
					                .userAgent(userAgent)
					                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
					                .header("Upgrade-Insecure-Requests", "1")	
					                .cookies(loginCookie) // acquireLoginCookie��?��? �뼸�� '濡쒓?���?��?' ?�좏궎
					                .get();
						
						
						
						Elements temp = doc.getElementsByClass("col-lg-12");
						String key = "code"+rank;
						jsonObject.put(key, temp.text());
						count++;
						
					}
				}
				if(count >= 5) break;
			}
			
		} catch(IOException e) {
			System.err.println("Fail to get User Information");
		}

		
		File file = new File("data/sources/"+problemID+".json");
		
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(jsonObject.toString());
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return;
	}

	
	public void writeSource(String userID, String problemID) {
		String language = writeUserCode(userID,problemID);
		writeProblemCodes(problemID,language);
	}
	
	public static void main(String[] args) {			
		String userID = "userID";
		String userPW = "userPW";
		
		BaekjoonCrawler bojcrawl = new BaekjoonCrawler(userID,userPW);
		bojcrawl.writeSource("userID","1000");
		//bojcrawl.writeProblemJson("1001");
		//bojcrawl.writeUserInfoJson("userID");
	}

}
