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

public class FriendDAO {
	// 싱글턴 패턴
	private static FriendDAO dao = new FriendDAO();
	private FriendDAO() {
	}
	
	public static FriendDAO getInstance(){
		return dao;
	}
	
	// 친구추가
	public int insertFriend(FriendVO vo) throws SQLException {
		int result = 0;
		
		String sql = "insert into friend values(?, ?)";
		
		Connection con = null;		
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();		
			pstmt = con.prepareStatement(sql);
	
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getFriendId());
	
			result = pstmt.executeUpdate();
			
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}
		return result;
	}
	
	
	// 친구 리스트 아이디로 검색
	public List<FriendVO> selectById(String myId) throws SQLException{
		List<FriendVO> list = null;
		
		String sql = "select * from friend where user_id=?";
		Connection con = null;
		PreparedStatement pstmt = null;		
		
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, myId);
			
			ResultSet rs = pstmt.executeQuery();
			
			list = new ArrayList();
			
			while(rs.next()){
				String id = rs.getString("user_id");
				String f_id = rs.getString("friend_id");
				
				FriendVO vo = new FriendVO(id, f_id);
				
				list.add(vo);
			}	
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}
		
		return list;
	}
	
	
	// 친구 삭제
	public int deleteMemeber(FriendVO vo) throws SQLException{
		int result = 0;
		String sql = "delete from friend where user_id=? and friend_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);		
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getFriendId());
			
			result = pstmt.executeUpdate();
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}		
		return result;	
	}		
}
