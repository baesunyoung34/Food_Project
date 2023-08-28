package edu.java.restaurtant.controller;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import edu.java.restaurtant.model.RestaurtantSharing;
import oracle.jdbc.OracleDriver;

import static edu.java.restaurtant.model.RestaurtantSharing.Entity.*;
import static edu.java.restaurtant.ojdbc.OracleConnect.*;

public class RestaurtantSharingDaoImpl implements RestaurtantSharingDao {
	
	private static RestaurtantSharingDaoImpl instance = null;
	private RestaurtantSharingDaoImpl() {}
	public static RestaurtantSharingDaoImpl getInstance() {
		if(instance == null) {
			instance = new RestaurtantSharingDaoImpl();
		}
		return instance;
	}
	
	
	// 오라클 JDBC 연동
	private Connection getConnection() throws SQLException {
	DriverManager.registerDriver(new OracleDriver());
	
	Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	
	return conn;
	}
	
	private void closeResources(Connection conn, Statement stmt) throws SQLException {
		stmt.close();
		conn.close();
	}
	private void closeResources(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		rs.close();
		closeResources(conn, stmt);
	}

	// select * from LUNCHMENUDB order by cid;
	private static final String SQL_SELECT_ALL =
			"select * from " + TBL_NAME + " order by " + COL_CID;
	
	@Override
	public List<RestaurtantSharing> read() {
		ArrayList<RestaurtantSharing> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			System.out.println(SQL_SELECT_ALL); // 확인용
			stmt = conn.prepareStatement(SQL_SELECT_ALL);
			rs = stmt.executeQuery();
			while (rs.next()) { // select 결과에서 행 (row) 데이터가 있으면 
				// 각 값들을 읽는다.
				Integer cid = rs.getInt(COL_CID);
				String name = rs.getString(COL_NAME);
				String phone = rs.getString(COL_PHONE);
				String address = rs.getString(COL_ADDRESS);
				String memo = rs.getString(COL_MEMO);
				
				RestaurtantSharing restaurtantsharing = new RestaurtantSharing(cid, name, phone, address, memo);
				list.add(restaurtantsharing);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeResources(conn, stmt, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		return list;
	}
	
	// select * from LUNCHMENUDB where cid =?
	private static final String SQL_SELECT_BY_ID =
			"select * from " + TBL_NAME + " where " + COL_CID + " = ?";

	@Override
	public RestaurtantSharing read(int cid) {
		RestaurtantSharing restaurtantSharing = null; // select 결과 저장 리턴을 위한 변수
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			System.out.println(SQL_SELECT_BY_ID); // 확인용
			stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			stmt.setInt(1, cid);
			
			rs = stmt.executeQuery();
			if(rs.next()) { // 검색된 행 row 데이터가 있다면
				Integer id = rs.getInt(COL_CID);
				String name = rs.getString(COL_NAME);
				String phone = rs.getString(COL_PHONE);
				String address = rs.getString(COL_ADDRESS);
				String memo = rs.getString(COL_MEMO);
				restaurtantSharing = new RestaurtantSharing(id, name, phone, address, memo);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				closeResources(conn, stmt, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return restaurtantSharing;
	}
	
	private static final String SQL_SELECT_BY_KEYWORD = 
			"select * from RESTAURTANTSHARING " + "where lower(NAME) like lower(?)" 
	+ " or lower(PHONE) like lower(?) " + "order by CID";

	@Override
	public List<RestaurtantSharing> read(String keyword) {
		ArrayList<RestaurtantSharing>list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			System.out.println(SQL_SELECT_BY_KEYWORD);
			stmt = conn.prepareStatement(SQL_SELECT_BY_KEYWORD);
			
			
			String key = "%" + keyword + "%";
			System.out.println("keyword= " + keyword + ", key=" + key);
			
			stmt.setString(1, key);
			stmt.setString(2, key);
			
			
			rs = stmt.executeQuery();
			if (rs.next()) {
	            do {
	                Integer cid = rs.getInt(COL_CID);
	                String name = rs.getString(COL_NAME);
	                String phone = rs.getString(COL_PHONE);
	                String address = rs.getString(COL_ADDRESS);
	                String memo = rs.getString(COL_MEMO);

	                RestaurtantSharing restaurtantSharing = new RestaurtantSharing(cid, name, phone, address, memo);
	                list.add(restaurtantSharing);
	            } while (rs.next());
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeResources(conn, stmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// insert 
	private static final String SQL_INSERT = 
			"insert into " + TBL_NAME + "(" + COL_NAME + ", " + COL_PHONE + ", " + COL_ADDRESS 
			+ ", " + COL_MEMO + ")" + "values(?, ?, ?, ?)";
	@Override
	public int create(RestaurtantSharing restaurtantSharing) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = getConnection();
			System.out.println(SQL_INSERT);
			stmt = conn.prepareStatement(SQL_INSERT);
			stmt.setString(1, restaurtantSharing.getName());
			stmt.setString(2, restaurtantSharing.getPhone());
			stmt.setString(3, restaurtantSharing.getAddress());
			stmt.setString(4,restaurtantSharing.getMemo());
			
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeResources(conn, stmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// update
	private static final String SQL_UPDATE =
			"update " + TBL_NAME + " set " + COL_NAME + " =?, " + COL_PHONE + " =?, " 
			+ COL_ADDRESS + " =?," + COL_MEMO + " =?" + " where " + COL_CID + " =?";
	@Override
	public int upate(RestaurtantSharing restaurtantSharing) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = getConnection();
			System.out.println(SQL_UPDATE);
			stmt = conn.prepareStatement(SQL_UPDATE);
			stmt.setString(1, restaurtantSharing.getName());
			stmt.setString(2, restaurtantSharing.getPhone());
			stmt.setString(3, restaurtantSharing.getAddress());
			stmt.setString(4, restaurtantSharing.getMemo());
			stmt.setInt (5, restaurtantSharing.getCid());
			
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeResources(conn, stmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// delete 
	private static final String SQL_DELETE =
			"delete from " + TBL_NAME + " where " + COL_CID + " =?";
			
	@Override
	public int delete(Integer cid) {
		int result = 0;
		
		Connection conn =null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			System.out.println(SQL_DELETE);
			stmt = conn.prepareStatement(SQL_DELETE);
			stmt.setInt(1, cid);
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeResources(conn, stmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return 0;
	}

	
}
