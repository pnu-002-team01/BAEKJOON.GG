package swTeam;

import java.util.ArrayList;

public class test {
	
	static String code1 = "import java.io.BufferedReader;\r\n" + 
			"import java.io.InputStreamReader;\r\n" + 
			"\r\n" + 
			"public class no01002 {\r\n" + 
			"\r\n" + 
			"	public static void main(String[] args) throws Exception {\r\n" + 
			"		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));\r\n" + 
			"		\r\n" + 
			"		int test = Integer.parseInt(read.readLine());\r\n" + 
			"		for ( int t = 1; t <= test; t++ ) {\r\n" + 
			"			String[] data = read.readLine().split(\" \");\r\n" + 
			"			\r\n" + 
			"			int x1 = Integer.parseInt(data[0]);\r\n" + 
			"			int y1 = Integer.parseInt(data[1]);\r\n" + 
			"			int r1 = Integer.parseInt(data[2]);\r\n" + 
			"			int x2 = Integer.parseInt(data[3]);\r\n" + 
			"			int y2 = Integer.parseInt(data[4]);\r\n" + 
			"			int r2 = Integer.parseInt(data[5]);\r\n" + 
			"			\r\n" + 
			"			double dis = Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));\r\n" + 
			"			\r\n" + 
			"			if ( x1 == x2 && y1 == y2 ) {\r\n" + 
			"				if ( r1 == r2 )\r\n" + 
			"					System.out.println(-1);\r\n" + 
			"				else\r\n" + 
			"					System.out.println(0);\r\n" + 
			"			}\r\n" + 
			"			else if ( dis == r1+r2 )\r\n" + 
			"				System.out.println(1);\r\n" + 
			"			else if ( dis > r1+r2 )\r\n" + 
			"				System.out.println(0);\r\n" + 
			"			else {\r\n" + 
			"				if ( dis == Math.abs(r1-r2) )\r\n" + 
			"					System.out.println(1);\r\n" + 
			"				else if ( dis < Math.abs(r1-r2) )\r\n" + 
			"					System.out.println(0);\r\n" + 
			"				else\r\n" + 
			"					System.out.println(2);\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"	}\r\n" + 
			"\r\n" + 
			"}\r\n" + 
			"";
	static String code2 = "#include <cstdio>\r\n" + 
			"#include <queue>\r\n" + 
			"#include <cstring>\r\n" + 
			"#include <cmath>\r\n" + 
			"\r\n" + 
			"using namespace std;\r\n" + 
			"\r\n" + 
			"int test, K, tmp;\r\n" + 
			"\r\n" + 
			"class wheel {\r\n" + 
			"	deque<int> que;\r\n" + 
			"	public:\r\n" + 
			"		wheel() { }\r\n" + 
			"		void push(int i) {\r\n" + 
			"			que.push_back(i);\r\n" + 
			"		}\r\n" + 
			"		void turn(int direct) {\r\n" + 
			"			if ( direct == 1 ) {\r\n" + 
			"				int tmp = que.back();	que.pop_back();\r\n" + 
			"				que.push_front(tmp);\r\n" + 
			"			} else if ( direct == -1 ) {\r\n" + 
			"				int tmp = que.front();	que.pop_front();\r\n" + 
			"				que.push_back(tmp);\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"		int top() {\r\n" + 
			"			return que.at(0);\r\n" + 
			"		}\r\n" + 
			"		int get(int i) {\r\n" + 
			"			if ( i == 1 )\r\n" + 
			"				return que.at(2);\r\n" + 
			"			else if ( i == -1 )\r\n" + 
			"				return que.at(6);\r\n" + 
			"		}\r\n" + 
			"};\r\n" + 
			"\r\n" + 
			"struct inst {\r\n" + 
			"	int index, d;\r\n" + 
			"};\r\n" + 
			"\r\n" + 
			"void bfs(wheel w[4], int index, int d) {\r\n" + 
			"	bool visit[4];\r\n" + 
			"	memset(visit, false, 4);\r\n" + 
			"	visit[index] = true;\r\n" + 
			"	queue<inst> que;\r\n" + 
			"	que.push({index, d});\r\n" + 
			"	while ( !que.empty() ) {\r\n" + 
			"		inst tmp = que.front();\r\n" + 
			"		for ( int i = -1; i <= 1; i+=2) {\r\n" + 
			"			if ( tmp.index+i >= 0 && tmp.index+i < 4 && visit[tmp.index+i] == false ) {\r\n" + 
			"				if ( w[tmp.index].get(i) != w[tmp.index+i].get(-i) ) {\r\n" + 
			"					visit[tmp.index+i] = true;\r\n" + 
			"					que.push({tmp.index+i, -tmp.d});\r\n" + 
			"				}\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"		w[tmp.index].turn(tmp.d);\r\n" + 
			"		que.pop();\r\n" + 
			"	}\r\n" + 
			"}\r\n" + 
			"\r\n" + 
			"int main() {\r\n" + 
			"	scanf(\"%d\", &test);\r\n" + 
			"	for ( int t = 1; t <= test; t++ ) {\r\n" + 
			"		scanf(\"%d\", &K);\r\n" + 
			"		wheel w[4];\r\n" + 
			"		for ( int r = 0; r < 4; r++ ) {\r\n" + 
			"			for ( int c = 0; c < 8; c++ ) {\r\n" + 
			"				scanf(\"%d\", &tmp);\r\n" + 
			"				w[r].push(tmp);\r\n" + 
			"			}\r\n" + 
			"		}\r\n" + 
			"		for ( int i = 0; i < K; i++ ) {\r\n" + 
			"			int index, d;\r\n" + 
			"			scanf(\"%d %d\", &index, &d);\r\n" + 
			"			bfs(w, index-1, d);\r\n" + 
			"		}\r\n" + 
			"		int sum = 0;	\r\n" + 
			"		for ( int i = 0; i < 4; i++ ) {\r\n" + 
			"			sum += pow(2,i)*w[i].top();\r\n" + 
			"		}\r\n" + 
			"		printf(\"#%d %d\\n\", t, sum);\r\n" + 
			"	}\r\n" + 
			"	return 0;\r\n" + 
			"}";
	
	static String UserId = "bjh6654";
	static int PNumber = 1002;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SourceAnalysis sa = new SourceAnalysis(1);						// SourceAnalysis(int type) : if type == 0 : cpp, type == 1 : java
		ArrayList<String> result = sa.Analysis(UserId, PNumber, code1);	// Userid, ProblemNumber, SourceCode 를 입력 받음.
		for ( int i = 0; i < result.size()-1; i++ )						// 아이디와 문제번호로 소스파일 및 분석결과 저장. ex) userid_number.java
			System.out.println(result.get(i));							// ArrayList<String> 으로 반환됨.
	}

}
