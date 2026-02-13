package com.kh.mvc.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import static com.kh.mvc.common.template.JDBCTemplate.*;
import com.kh.mvc.model.vo.Member;

/*
	DAO(Data Access Object)
		- Service에 의해 호출, 맡은 기능 수행을 위해 DB에 직접 접근하여 sql문 호출 후 처리 결과값 반환
*/
public class MemberDao {
	
	private Properties prop = new Properties();
	{
		try {
			prop.loadFromXML(new FileInputStream("resources/member_mapper.xml"));
		}
		catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
		사용자가 View에서 입력한 값을 DB에 insert하는 메서드
		@param conn : service에서 생성하여 전달
		@param m : controller에서 래핑하여 전달
		@return 처리된 행의 갯수(int)
	*/
	public int insertMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberId());
			pstmt.setString(2, m.getMemberPW());
			pstmt.setString(3, m.getMemnberName());
			pstmt.setString(4, m.getGender());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getPhone());
			pstmt.setString(7, m.getAddress());
			pstmt.setInt(8, m.getAge());
			
			result = pstmt.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<Member> selectAll(Connection conn) {
		List<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAll");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setMemnberName(rset.getString("member_name"));
				m.setPhone(rset.getString("phone"));
				m.setAge(rset.getInt("age"));
				m.setEnrollDate(rset.getDate("enroll_date"));
				
				list.add(m);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				rset.close();
				pstmt.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
	}
	
	public Member selectByUserId(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectByUserId");
		Member m = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setMemnberName(rset.getString("member_name"));
				m.setPhone(rset.getString("phone"));
				m.setAge(rset.getInt("age"));
				m.setEnrollDate(rset.getDate("enroll_date"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rset);
			close(pstmt);
		}
		
		return m;
	}

	public List<Member> selectByUserName(Connection conn, String keyword) {
		List<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("slectByUserName");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setMemnberName(rset.getString("member_name"));
				m.setPhone(rset.getString("phone"));
				m.setAge(rset.getInt("age"));
				m.setEnrollDate(rset.getDate("enroll_date"));
				
				list.add(m);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int updateMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemberPW());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getMemberId());
			
			result = pstmt.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteMember");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(pstmt);
		}
		
		return result;
	}
	
}
