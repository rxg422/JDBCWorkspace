package com.kh.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCProcess {

	public static void main(String[] args) {
		try {
			// 1) Oracle Driver 등록 : 어플리케이션에 오라클 드라이버 추가
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/*
			 	오라클 드라이버 등록 방법
					1. 프로젝트 우클릭 -> Properties -> Build Path 이동
			 		2. Libraries 이동 -> Module Path 선택
			 		3. ADD External Jars 선택후 ojdbc 파일 등록
			 		
			 	OracleDriver와 같은 Class파일 소스 확인 방법
			 		1. 디컴파일러 설치 : help -> 이클립스 마켓플레이스 -> decompiler검색
			 		2. window -> preference -> general -> editors -> file Association
			 */
			
			// 2) 드라이버 동록 확인
			DriverManager.drivers().forEach(System.out::println);
			
			// 3) Connection 객체 생성 : getConnection(url, 접소계정, 비밀번호)
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
		
			// 4) Statement 객체 생성
			Statement stmt = conn.createStatement();
			
			// 5) DB에 SQL 전달 및 실행
			boolean result = stmt.execute("SELECT 'Hello, JDBC!' AS test FROM dual");
			
			// 6) 결과값 반환 받기 : DQL = getResultSet(), DML = getUpdateCount()
			ResultSet rset = stmt.getResultSet();
			if(rset.next()) {
				System.out.println(rset.getString("test"));
			}
			
			// 5+6) excuteQuery() : excute() + getResultSet(), excuteUpdate() : excute() + getUpdateCount()
			ResultSet rset2 = stmt.executeQuery("SELECT 'Hello, JDBC!' AS test2 FROM dual");
			if(rset2.next()) {
				System.out.println(rset2.getString(1));
			}
			
			// 7) 자원 반납
			conn.close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
