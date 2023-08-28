package edu.java.restaurtant.controller;

import java.util.List;

import edu.java.restaurtant.model.RestaurtantSharing;

public interface RestaurtantSharingDao {
	
	List<RestaurtantSharing> read(); // 전체검색
	
	
	// DB에서 레스토랑 검색
	RestaurtantSharing read(int cid); // db Primary key
	
	
	List<RestaurtantSharing> read(String keyword);
	
	// DB에 insert
	int create(RestaurtantSharing restaurtantSharing);
	
	// DB에 update
	int upate(RestaurtantSharing restaurtantSharing);
	
	// DB에서 삭제
	int delete(Integer cid);

}
