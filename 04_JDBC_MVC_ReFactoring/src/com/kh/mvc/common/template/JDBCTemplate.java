package com.kh.mvc.common.template;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

// JDBC 과정 중 자주 사용하는 구문을 메서드로 정의한 클래스
public class JDBCTemplate {

	// DB와 접속된 Conncection을 생성하여 반환하는 메서드
	public static Connection getConnection() {
		Connection conn = null;
		
		// 드라이버 정보 로딩용 Properties 객체 생성
		Properties prop = new Properties();
		
		try {
			/*
				BasicDataSource
					- javax.sql.DataSource를 구현한 클래스
					- DataSource는 db 연결 및 커넥션풀 관리, 트랜잭션 관리를 위한 방법들을 정의한 인터페이스
					- db에 연결 및 커넥션풀 생성 등 커넥션을 관리할 수 있는 효율적인 메서드를 제공
			*/
			BasicDataSource dataSource = new BasicDataSource();
			
			prop.load(new FileInputStream("resources/driver.properties"));
			
			// 생성하고자하는 커넥션 정보 기술
//			dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//			dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
//			dataSource.setUsername("JDBC");
//			dataSource.setPassword("JDBC");
			
			dataSource.setDriverClassName(prop.getProperty("driver"));
			dataSource.setUrl(prop.getProperty("url"));
			dataSource.setUsername(prop.getProperty("username"));
			dataSource.setPassword(prop.getProperty("password"));
			dataSource.setInitialSize(10); // 초기 커넥션풀 사이즈(기본값 : 0)
			dataSource.setMaxTotal(50); // 최대 커넥션 수(기본값 : 8)
			dataSource.setDefaultAutoCommit(false);
			dataSource.setMaxWaitMillis(10000); // 최대 대기시간 설정
			dataSource.setRemoveAbandonedTimeout(300); // 사용하고 있지 않은 커넥션 삭제
			
			conn = dataSource.getConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// 전달받은 JDBC용 객체를 close하는 메서드
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 트랜잭션 처리(commit/rollback) 메서드
	public static void commit(Connection conn) {
		try {
			if(conn != null & !conn.isClosed()) {
				conn.commit();				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void rollback(Connection conn) {
		try {
			if(conn != null & !conn.isClosed()) {
				conn.rollback();				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 
	
}
