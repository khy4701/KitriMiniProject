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

public class RoomAddAction implements Action{
	PrintWriter out;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out= null;
		
		// 1. 요청 파라미터 처리
		String roomName = request.getParameter("room_name");
		String userId = request.getParameter("user_id");
		RoomVO vo = new RoomVO(roomName, userId);
		
		// 2. DB 처리
		RoomDAO dao = RoomDAO.getInstance();
		PartiDAO dao2 = PartiDAO.getInstance();	
		try {
			 // 데이터 송신		
			out = response.getWriter();
			int result = dao.insertRoom(vo);

			// 성공하면 방에 입장도 처리
			if (result != -1){
				result = dao2.enterRoom(new PartiVO(result, userId));
				if(result == 1){
					SystemLog.printLog("Participant 추가 성공");
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
