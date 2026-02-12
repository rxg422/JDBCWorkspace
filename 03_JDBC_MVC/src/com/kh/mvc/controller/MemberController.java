package com.kh.mvc.controller;

import com.kh.mvc.model.service.MemberService;
import com.kh.mvc.model.vo.Member;
import com.kh.mvc.view.MemberView;

/*
 	Contorller
 		- View를 통해 요청받은 기능 처리 담당
 		- View로부터 전달받은 데이터를 가공처리 후, Service 메서드에게 전달
 		- Service로 부터 반환받은 결과에 따라 사용자 응답화면을 지정
*/
public class MemberController {
	
	private MemberService mService = new MemberService();
//	private MemberView view = new MemberView();
	
	public void insertMember() {
		
	}

	public void insertMember(String userId, String userPwd, String userName, String gender, int age, String email, String phone, String address) {
		// 전달받은 데이터 가공
		Member m = new Member(userId, userPwd, userName, gender, email, phone, address, age, null);
		
		// service의 insertMember를 처리할 메서드 호출
		int result = mService.insertMember(m);
				
		if(result > 0) {
			new MemberView().displaySuccess("회원 추가 성공");
		}
		else {
			new MemberView().displayFail("회원 추가 실패");
		}
	}
	
}
