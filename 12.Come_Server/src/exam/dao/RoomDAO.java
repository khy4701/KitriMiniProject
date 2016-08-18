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

public class RoomDAO {
	// 싱글턴 패턴
	private static RoomDAO dao = new RoomDAO();
	private RoomDAO() {
		//SystemLog.printLog("MemberDAO() : " + dao);		
	}
	
	public static RoomDAO getInstance(){
		return dao;
	}	

	// 방찾기
	public List<RoomVO> selectById(String user_id) throws SQLException{
		List<RoomVO> list = null;
		int result = 0;
		
		String sql = "select * from room where room_num IN (select room_num from participant where user_id=?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			
			ResultSet rs = pstmt.executeQuery();
			
			list = new ArrayList<>();
			
			while(rs.next()){
				int roomNum = rs.getInt("room_num");
				String roomName = rs.getString("room_name");
				String roomOwner = rs.getString("room_owner");
				
				list.add(new RoomVO(roomNum, roomName, roomOwner));
			}
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}		
		return list;
	}	
	
	// 방만들기
	public int insertRoom(RoomVO vo) throws SQLException{
		int result = -1;		
		String sql = "insert into room values(room_id_seq.nextval, ?, ?)";
		String sql2 = "select room_id_seq.currval as room_num from dual";
		
		Connection con = null;		
		PreparedStatement pstmt = null;
		try{
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, vo.getRoomName());
			pstmt.setString(2, vo.getRoomOwner());			
	
			result = pstmt.executeUpdate();
			if(result == 1){
				SystemLog.printLog("RoomList추가 성공");				
				pstmt = con.prepareStatement(sql2);	
				ResultSet rs = pstmt.executeQuery();
				rs.next();				
				result = rs.getInt("room_num");
			}			
		}finally{	// 정상 종료든지 비정상 종료든지 수행(throws로 예외처리를 넘겼지만 끝나면 이 부분이 수행됨)
			DBManager.close(con, pstmt);
		}
		return result;
	}		

	// 방 삭제하기
	public int deleteRoom(RoomVO vo) throws SQLException{
		int result = 0;		
		String sql = "delete from room where room_num = ?";		
		
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
