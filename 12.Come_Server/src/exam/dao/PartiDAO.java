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

public class PartiDAO {
	// 싱글턴 패턴
	private static PartiDAO dao = new PartiDAO();
	private PartiDAO() {
		//SystemLog.printLog("MemberDAO() : " + dao);		
	}
	
	public static PartiDAO getInstance(){
		return dao;
	}	

	// 방에 참여한 목록
	public List<PartiVO> selectById(int roomNum) throws SQLException{
		List<PartiVO> list = null;
		int result = 0;
		
		String sql = "select * from participant where room_num = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			
			ResultSet rs = pstmt.executeQuery();
			
			list = new ArrayList<>();
			
			while(rs.next()){
				int roomNums = rs.getInt("room_num");
				String userId = rs.getString("user_id");				
				
				list.add(new PartiVO(roomNums, userId));
			}
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}		
		return list;
	}	
	
	// 방에 들어가기
	public int enterRoom(PartiVO vo) throws SQLException{
		int result = 0;		
		String sql = "insert into participant values(?, ?)";
		
		Connection con = null;		
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, vo.getRoomNum());
			pstmt.setString(2, vo.getUserId());			
	
			result = pstmt.executeUpdate();
			
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}
		return result;
	}
	
	// 한 유저 방에서 나가기
	public int exitRoom(PartiVO vo) throws SQLException{
		int result = 0;
		String sql = "delete from participant where room_num = ? and user_id = ?";
		
		Connection con = null;		
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, vo.getRoomNum());
			pstmt.setString(2, vo.getUserId());			
	
			result = pstmt.executeUpdate();
			
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}
		return result;
	}	
	
	// 한 유저 방에서 나가기
		public int exitRoom(String id) throws SQLException{
			int result = 0;
			String sql = "delete from participant where user_id = ?";
			
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
	
	// 방에서 전부 나가기
	public int emptyRoom(PartiVO vo) throws SQLException{
		int result = 0;		
		String sql = "delete from participant where room_num = ?";
		
		Connection con = null;		
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, vo.getRoomNum());
	
			result = pstmt.executeUpdate();
			
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}
		return result;
	}	
}
