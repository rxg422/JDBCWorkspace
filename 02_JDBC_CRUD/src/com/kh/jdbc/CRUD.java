package com.kh.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.model.vo.Member;

public class CRUD {

	public static void main(String[] args) {
		CRUD crud = new CRUD();
//		crud.insert();
		
//		crud.update();
		
//		crud.delete("user07");
		
		// SQL 인젝션 발생
//		crud.delete("' OR 1=1 --");
		
//		crud.selectOne("user01");
		
//		crud.selectAll();
		
//		crud.execPlSql();
		
		crud.execProcedure();
	}
	
	public void insert() {
		try {
			// 1) Driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) DBMS 연결
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) autoCommit 설정(기본값 : true)
			conn.setAutoCommit(false);
			
			// 4) Statement 생성
			Statement stmt = conn.createStatement();
			
			// 5) 완성된 SQL문 전달 및 실행 후 결과값 반환받기
			int result = stmt.executeUpdate("INSERT INTO MEMBER VALUES('user07','pass02','홍길동','M','user01@naver.com','010-4121-3393','서울시 목동 ','20', SYSDATE)");
			
			// 6) 트랜잭션 처리
			if(result > 0) {
				conn.commit();
			}
			else {
				conn.rollback();
			}
			
			// 7) 자원 반납 : 사용한 자원의 역순으로 반환
			stmt.close();
			conn.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		try {
			// 1~3)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			conn.setAutoCommit(false);
			
			// 4) PreparedSatement 생성 : 쿼리문 준비가 필요한 객체. Statement 인터페이스를 상속받은 인터페이스, Statement의 단점을 개선
			// 미완성 sql문 : 쿼리의 실제 대입값은 ?(위치홀더)로 표시
			String sql = "UPDATE member SET email=?, phone=?, address=? WHERE member_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 미완성 sql문 완성 : pstmt.set자료형(? 위치, 값)
			pstmt.setString(1, "dlapdf@naver.com");
			pstmt.setString(2, "010-2422-7574");
			pstmt.setString(3, "서울시 양천구 목동");
			pstmt.setString(4, "user07");
			
			// 5~7)
			int updateCount = pstmt.executeUpdate();
			if(updateCount > 0) {
				conn.commit();
			}
			else {
				conn.rollback();
			}
			pstmt.close();
			conn.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String userId) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			conn.setAutoCommit(false);
			
			int result;
			
//			Statement stmt = conn.createStatement();
//			result = stmt.executeUpdate("DELETE FROM member WHERE member_id = '"+userId+"'");	
			
			// PreparedStatement : 바인딩 과정에서 sql 인젠션이 발생하는 키워드는 안전한 코드로 변환
			String sql = "DELETE FROM member WHERE member_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			result = pstmt.executeUpdate();
			
			System.out.println(result);
			
			if(result > 0) {
				conn.commit();
			}
			else {
				conn.rollback();
			}
//			stmt.close();
			pstmt.close();
			conn.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void selectOne(String userId) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			String sql = "SELECT * FROM member WHERE member_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			ResultSet rset = pstmt.executeQuery();
			
			if(rset.next()) {
				// 현재 커서가 가리키고 있는 행의 데이터를 추출하여 vo 객체로 변환
				Member m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setMemberPW(rset.getString("member_pwd"));
				m.setMemnberName(rset.getString("member_name"));
				m.setEmail(rset.getString("email"));
				m.setAge(rset.getInt("age"));
				m.setEnrollDate(rset.getDate("enroll_date"));
				
				System.out.println(m);
			}
			
			rset.close();
			pstmt.close();
			conn.close();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void selectAll() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			String sql = "SELECT * FROM member";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			
			List<Member> list = new ArrayList();
			while(rset.next()) {
				Member m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setMemberPW(rset.getString("member_pwd"));
				m.setMemnberName(rset.getString("member_name"));
				m.setEmail(rset.getString("email"));
				m.setAge(rset.getInt("age"));
				m.setEnrollDate(rset.getDate("enroll_date"));
				
				list.add(m);
			}
			
			System.out.println(list);
			
			rset.close();
			pstmt.close();
			conn.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void execPlSql() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// RreparedStatement로 PL/SQL 실행
			String sql = "BEGIN UPDATE member SET enroll_date = ? WHERE member_id = ?; END;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "2026/02/11");
			pstmt.setString(2, "user01");
			pstmt.execute();
			
			pstmt.close();
			conn.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void execProcedure() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "KH", "KH");
			
			// CallableStatement : 저장된 프로시져를 호출할 때 사용, ?(위치홀더) 사용 가능
			String sql = "{call pro_emp(?, ?, ?, ?)}";
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, 200);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.registerOutParameter(4, Types.DOUBLE);
			cstmt.execute();
			
			String name = cstmt.getString(2);
			int salary = cstmt.getInt(3);
			double bonus = cstmt.getDouble(4);
			
			System.out.println(name + " " + salary + " " + bonus);
			
			cstmt.close();
			conn.close();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}