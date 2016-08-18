package exam.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exam.dao.MemberDAO;
import exam.dao.MemberVO;

public class RegisterAction implements Action{
	PrintWriter out;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out= null;
		
		String id = request.getParameter("id");
		System.out.println("id: "+id);
		
		MemberVO vo = new MemberVO();
		vo.setId(request.getParameter("id"));
		vo.setPwd(request.getParameter("pwd"));
		vo.setName(request.getParameter("name"));		

		MemberDAO dao = MemberDAO.getInstance();
				
		try {
			 // 데이터 송신		
			out = response.getWriter();
			int result = dao.insertMember(vo);
			
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
