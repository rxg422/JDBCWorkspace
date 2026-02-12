package com.kh.mvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kh.mvc.model.vo.Member;

/*
	DAO(Data Access Object)
		- Service에 의해 호출, 맡은 기능 수행을 위해 DB에 직접 접근하여 sql문 호출 후 처리 결과값 반환
*/
public class MemberDao {

	/*
		사용자가 View에서 입력한 값을 DB에 insert하는 메서드
		@param conn : service에서 생성하여 전달
		@param m : controller에서 래핑하여 전달
		@return 처리된 행의 갯수(int)
	*/
	public int insertMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO member VALUES(?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
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
	
}
