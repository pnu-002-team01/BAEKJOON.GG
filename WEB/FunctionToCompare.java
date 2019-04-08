package WEB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FunctionToCompare {

	public static void main(String[] args) {
		 ArrayList<String>[] list = new ArrayList[2];
		 for(int i = 0; i < 2; i++) {
			 list[i] = new ArrayList<String>();
		 }
		Scanner scan = new Scanner(System.in);
		List userIDs = new ArrayList<String>();

		for (int i = 0; i < 2; ++i) {
			System.out.print("아이디를 입력하세요 ");
			String userID = scan.nextLine();
			userIDs.add(userID);
		}
		
		
		try {
			for (int i = 0; i < 2; ++i) {
				String connUrl = "https://www.acmicpc.net/user/" + userIDs.get(i);
				System.out.println(connUrl);
				Connection.Response response = Jsoup.connect(connUrl).method(Connection.Method.GET).execute();
				Document doc = response.parse();
			
				Element CorrectNum = doc.select(".panel-body").first();
				Elements problems = CorrectNum.select(".panel-body").select(".problem_number");

				for (Element e : problems) {
					list[i].add(e.select("a").text().toString());
				}

			}
			System.out.println("=========================\n");
			System.out.println(userIDs.get(0) + " 와 "+ userIDs.get(1) + "가 같이 푼 문제");
			System.out.println("");
			List<String> interList = new compare().intersection(list[0], list[1]);
			List<String> uniList = new compare().union(list[0], list[1]);
			
			list[0].removeAll(interList);
			list[1].removeAll(interList);
			
			System.out.println(interList);
			System.out.println("");
			System.out.println("=========================\n");
			System.out.println(userIDs.get(0) +" 만  푼 문제");
			System.out.println("");
			System.out.println(list[0]);
			System.out.println("");
			System.out.println("=========================\n");
			System.out.println(userIDs.get(1) +" 만  푼 문제");
			System.out.println("");
			System.out.println(list[1]);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
    public <T> List<T> union(List<T> list1, List<T> list2) { // 합집합 메소드
        Set<T> set = new HashSet<T>(); 
        set.addAll(list1);
        set.addAll(list2); 
        return new ArrayList<T>(set);
    }

 

    public <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>(); 
        for (T t : list1) {
            if (list2.contains(t)) {
                list.add(t);
            }
        } 
        return list;
    }



}
