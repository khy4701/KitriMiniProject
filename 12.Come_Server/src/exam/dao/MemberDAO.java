package exam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exam.util.DBManager;
import exam.util.SystemLog;

public class MemberDAO {
	// 싱글턴 패턴
	private static MemberDAO dao = new MemberDAO();
	private MemberDAO() {
		//SystemLog.printLog("MemberDAO() : " + dao);		
	}
	
	public static MemberDAO getInstance(){
		return dao;
	}	

	// 로그인
	public int login(String id, String pwd) throws SQLException{
		int result = 0;
		
		String sql = "select * from member where id=? and pwd=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				result = 1;
			}
			SystemLog.printLog(result+"");	

		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}		
		return result;		
	}
	
	// 회원가입
	public int insertMember(MemberVO vo) throws SQLException {
		int result = 0;
		
		String sql = "insert into member values(?, ?, ?)";
		
		Connection con = null;		
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();		
			pstmt = con.prepareStatement(sql);
	
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getName());	
	
			result = pstmt.executeUpdate();
			
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}
		return result;
	}
	
	
	// 리스트
	public List<MemberVO> selectAll() throws SQLException{
		List<MemberVO> list = null;
		
		String sql = "select * from member";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while(rs.next()){
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				
				MemberVO vo = new MemberVO(id, pwd, name);
				
				list.add(vo);
			}	
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}
		
		return list;
	}

	// 회원 정보
	public MemberVO selectById(String user_id) throws SQLException {

		String sql = "select * from member where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;

		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");

				member = new MemberVO(id, pwd, name);

			}
		} finally { // 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt, rs);
		}

		return member;
	}
	
	// 계정 삭제
	public int deleteMemeber(String id) throws SQLException{
		int result = 0;
		String sql = "delete from member where id=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);		
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}
		
		return result;	
	}		
	
	// 멤버 확인
	public int isValidMember(String id) throws SQLException {
		String sql = "select * from member where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			
			if (rs.next()) {
				result = 1;
			}
		} finally { // 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt, rs);
		}

		return result;
	}
	
}
