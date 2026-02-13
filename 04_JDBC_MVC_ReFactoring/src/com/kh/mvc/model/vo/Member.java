package com.kh.mvc.model.vo;

import java.sql.Date;

public class Member {
	
	private String memberId;
	private String memberPW;
	private String memnberName;
	private String gender;
	private String email;
	private String phone;
	private String address;
	private int age;
	private Date enrollDate;
	
	public Member() {

	}
	
	public Member(String memberId, String memberPW, String memnberName, String gender, String email, String phone,
			String address, int age, Date enrollDate) {
		super();
		this.memberId = memberId;
		this.memberPW = memberPW;
		this.memnberName = memnberName;
		this.gender = gender;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.age = age;
		this.enrollDate = enrollDate;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPW() {
		return memberPW;
	}

	public void setMemberPW(String memberPW) {
		this.memberPW = memberPW;
	}

	public String getMemnberName() {
		return memnberName;
	}

	public void setMemnberName(String memnberName) {
		this.memnberName = memnberName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberPW=" + memberPW + ", memnberName=" + memnberName + ", gender="
				+ gender + ", email=" + email + ", phone=" + phone + ", address=" + address + ", age=" + age
				+ ", enrollDate=" + enrollDate + "]";
	}
	
}
