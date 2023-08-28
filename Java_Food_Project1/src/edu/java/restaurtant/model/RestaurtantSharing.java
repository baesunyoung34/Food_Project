package edu.java.restaurtant.model;

import java.io.Serializable;

// MVC 데이터만 설계 클래스
public class RestaurtantSharing implements Serializable {
	
	// 테이블 이름들을 상수로 정의
	public interface Entity {
		String TBL_NAME = "RESTAURTANTSHARING";
		String COL_CID = "CID";
		String COL_NAME = "NAME";
		String COL_PHONE = "PHONE";
		String COL_ADDRESS = "ADDRESS";
		String COL_MEMO = "MEMO";
	}
	
	// fields
	private int cid; // 테이블의 pk
	private String name;
	private String phone;
	private String address;
	private String memo;
	
	
	// constructor
	public RestaurtantSharing(){}

	public RestaurtantSharing(int cid, String name, String phone, String address, String memo) {
		this.cid = cid;
		this.name = name;
		this.phone = phone; 
		this.address = address;
		this.memo = memo;
	}

	
	// getter setter

	public int getCid() {
		return cid;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	};
	
	@Override
	public String toString() {
		return "(RestaurtantSharing(cid = " + this.cid + " name = " + this.name + " phone = " + this.phone 
				+ " address = " + this.address + " memo = " + this.memo + ")";
	}
	
	
}
