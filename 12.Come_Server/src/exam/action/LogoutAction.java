package exam.action;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exam.dao.PartiDAO;
import exam.util.GPSInfo;

public class LogoutAction implements Action {
	HashMap<String, GPSInfo> conMemberList;

	public LogoutAction( HashMap<String, GPSInfo> conMemberList)
	{
		this.conMemberList = conMemberList;
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String id = (String)request.getParameter("id");
		
		conMemberList.remove(id);
		PartiDAO dao = PartiDAO.getInstance();	
		int result = 0;
		try {
			result = dao.exitRoom(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( result ==0 ) System.out.println("LogoutAction exitRoom Fail");
		else
			System.out.println("LogoutAction exitRoom Success");
	}

}
