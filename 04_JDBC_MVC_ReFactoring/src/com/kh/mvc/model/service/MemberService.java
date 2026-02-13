package com.kh.mvc.model.service;

import java.sql.Connection;
import java.util.List;

import static com.kh.mvc.common.template.JDBCTemplate.*;
import com.kh.mvc.model.dao.MemberDao;
import com.kh.mvc.model.vo.Member;

/*
	Service
		- Controller에 의해 호출되는 최초의 메서드
		- 여러 dao에 존재하는 메서드를 호출하여 논리적으로 연관이 있는 비지니스 로직을 만듬
		- 처리결과값을 컨트롤러에 반환
*/
public class MemberService {
	
	private MemberDao mDao = new MemberDao();

	public int insertMember(Member m) {
		Connection conn = getConnection();
		int result = mDao.insertMember(conn, m);
		
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	public List<Member> selectAll() {
		Connection conn = getConnection();
		List<Member> list = mDao.selectAll(conn);

		close(conn);
		
		return list;
	}
	
	public Member selectByUserId(String userId) {
		Connection conn = getConnection();
		Member m = mDao.selectByUserId(conn, userId);

		close(conn);
		
		return m;
	}

	public List<Member> selectByUserName(String keyword) {
		Connection conn = getConnection();
		List<Member> list = mDao.selectByUserName(conn, keyword);

		close(conn);
		
		return list;
	}
	
	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = mDao.updateMember(conn, m);
		
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	public int deleteMember(String userId) {
		Connection conn = getConnection();
		int result = mDao.deleteMember(conn, userId);
		
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

}
