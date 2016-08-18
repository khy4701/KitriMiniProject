package exam.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exam.dao.MemberDAO;
import exam.dao.MemberVO;
import exam.dao.PartiDAO;
import exam.dao.PartiVO;
import exam.dao.RoomDAO;
import exam.dao.RoomVO;
import exam.util.SystemLog;

public class RoomDeleteAction implements Action{
	PrintWriter out;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out= null;
		
		// 1. 요청 파라미터 처리
		int roomNum = Integer.valueOf(request.getParameter("room_num"));		
		RoomVO vo = new RoomVO(roomNum);
		PartiVO vo2 = new PartiVO(roomNum);
		
		// 2. DB 처리
		RoomDAO dao = RoomDAO.getInstance();
		PartiDAO dao2 = PartiDAO.getInstance();	
		try {
			 // 데이터 송신		
			out = response.getWriter();
			
			// 방인원 비우기
			int result = dao2.emptyRoom(vo2);
			if (result != 0){
				result = dao.deleteRoom(vo);
				if(result == 1){
					SystemLog.printLog("방목록 삭제");
					out.print("success");
				}
				else
					out.print("fail");	
			}
			else
				out.print("fail");			
		} catch (SQLException e) {
			out.print("fail");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
