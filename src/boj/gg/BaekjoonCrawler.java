package boj.gg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BaekjoonCrawler {
	
	static final String MAINURL = "http://www.acmicpc.net/";
	
	public static ArrayList<String> crawlProblemNumbers() {
		Document doc = null;
		
		
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";

		// 전송할 폼 데이터
		Map<String, String> data = new HashMap<>();
		data.put("login_user_id", "아이디");
		data.put("login_password", "비밀번호");
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
			System.err.println("Fail to have cookie");
		}
		
		// 로그인 성공 후 얻은 쿠키.
		// 쿠키 중 TSESSION 이라는 값을 확인할 수 있다.
		Map<String, String> loginCookie = response.cookies();
		Document adminPageDocument = null;
		try {
		adminPageDocument = Jsoup.connect("https://www.acmicpc.net/problem/1000")
                .userAgent(userAgent)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                .header("Upgrade-Insecure-Requests", "1")	
                .cookies(loginCookie) // 위에서 얻은 '로그인 된' 쿠키
                .get();
		} catch(IOException e) {
			System.err.println("fail to crawProblem page");
		}
		
		
		System.err.println(adminPageDocument);

		

		ArrayList<String> problemIDList = new ArrayList<>();
		try {
		 doc = Jsoup.connect(MAINURL).get();
		} catch(IOException e) {
			System.err.println("Unable to connect.");
		}
		String title = doc.title();
		System.err.println("Successfully connected to " + title);
		
		for(int pageNumber = 1;; pageNumber++) {
			String problemPageURL = MAINURL + "/problemset/" + String.valueOf(pageNumber);
			try {
				doc = Jsoup.connect(problemPageURL).get();
			} catch(IOException e) {
				break;
			}
			String targetCSS = "td.list_problem_id";
			Elements currentProblemIDList = doc.select(targetCSS);
			
			System.err.println("Page number " + pageNumber);
			if(currentProblemIDList.isEmpty()) {
				System.err.println("Problem Number Crawling Finished.");
				break;
			}
			for(Element problemID : currentProblemIDList) {
				System.err.println(problemID.text());
			}
		}
		return problemIDList;
	}
	
	public static void main(String[] args) {			
		ArrayList myProblemList = new ArrayList();
		myProblemList = crawlProblemNumbers();
	}

}
