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

public class PartiDeleteAction implements Action{
	PrintWriter out;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out= null;
		
		// 1. 요청 파라미터 처리
		int roomNum = Integer.valueOf(request.getParameter("room_num"));
		String userId = request.getParameter("user_id");
		PartiVO vo = new PartiVO(roomNum, userId);
		
		// 2. DB 처리
		PartiDAO dao = PartiDAO.getInstance();	
		try {
			 // 데이터 송신		
			out = response.getWriter();
			int result = dao.exitRoom(vo);			
			if (result == 1)
				out.print("success");
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
