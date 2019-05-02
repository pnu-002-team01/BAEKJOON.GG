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
	static String code2 = "#include <iostream>\r\n" + 
			" #include <cstdio>\r\n" + 
			" #include <cstring>\r\n" + 
			" using namespace std;\r\n" + 
			" \r\n" + 
			" class MyTest\r\n" + 
			" {\r\n" + 
			"     private:\r\n" + 
			"         int a;\r\n" + 
			"         int b;\r\n" + 
			"         int c;\r\n" + 
			" \r\n" + 
			"     public:\r\n" + 
			"         MyTest();\r\n" + 
			"         void readFile();\r\n" + 
			"         void printString();\r\n" + 
			"         void checkString();\r\n" + 
			" };\r\n" + 
			" \r\n" + 
			" MyTest::MyTest()\r\n" + 
			"     : a(0), b(0)\r\n" + 
			" {\r\n" + 
			" }\r\n" + 
			" \r\n" + 
			" void MyTest::readFile()\r\n" + 
			" {\r\n" + 
			"     FILE* fd = fopen(\"test.txt\", \"r\");\r\n" + 
			"     char buf[10] = { 0, };\r\n" + 
			" \r\n" + 
			"     if (NULL == fd)\r\n" + 
			"     {\r\n" + 
			"         return;\r\n" + 
			"     }\r\n" + 
			"\r\n" + 
			"     if (0 == fread(buf, 1, 9, fd))\r\n" + 
			"     {\r\n" + 
			"         return;\r\n" + 
			"     }\r\n" + 
			"     free(fd);\r\n" + 
			"     fclose(fd);\r\n" + 
			"     return;\r\n" + 
			" }\r\n" + 
			" \r\n" + 
			" void MyTest::printString()\r\n" + 
			" {\r\n" + 
			"     char test1[10] = { 0 };\r\n" + 
			"     test1[11]=0;\r\n" + 
			"     char test2[10] = \"aaaaaaaaa\";\r\n" + 
			" \r\n" + 
			"     strcpy(test2, \"bbbbbbbbbb\");\r\n" + 
			" \r\n" + 
			"     cout << c;\r\n" + 
			"     cout << test1 << test2;\r\n" + 
			" \r\n" + 
			"     strcpy(test1, test2);\r\n" + 
			" }\r\n" + 
			" \r\n" + 
			" int main()\r\n" + 
			" {\r\n" + 
			"     MyTest test;\r\n" + 
			" \r\n" + 
			"     test.readFile();\r\n" + 
			"     test.printString();\r\n" + 
			" \r\n" + 
			"     return 0;\r\n" + 
			"}";
	
	static String UserId = "bjh6654";
	static int PNumber = 1002;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SourceAnalysis sa = new SourceAnalysis(0);						// SourceAnalysis(int type) : if type == 0 : cpp, type == 1 : java
		ArrayList<String> result = sa.Analysis(UserId, PNumber, code2);	// Userid, ProblemNumber, SourceCode 를 입력 받음.
		for ( int i = 0; i < result.size()-1; i++ )						// 아이디와 문제번호로 소스파일 및 분석결과 저장. ex) userid_number.java
			System.out.println(result.get(i));							// ArrayList<String> 으로 반환됨.
	}

}
