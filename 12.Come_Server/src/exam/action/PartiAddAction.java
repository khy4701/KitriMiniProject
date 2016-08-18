package exam.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exam.dao.MemberDAO;
import exam.dao.MemberVO;
import exam.dao.PartiDAO;
import exam.dao.PartiVO;
import exam.dao.RoomDAO;
import exam.dao.RoomVO;
import exam.util.GPSInfo;

public class PartiAddAction implements Action{
	private HashMap<String, GPSInfo> hashMap ;
	PrintWriter out;
	
	public PartiAddAction(HashMap<String, GPSInfo> hashMap){this.hashMap = hashMap;}
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
			out = response.getWriter();

			 // 데이터 송신		
			if(isConnectedMember(userId))
			{
				System.out.println("userId :"+userId );
				int result = dao.enterRoom(vo);			
				if (result == 1)
					out.print("success");
				else
					out.print("fail");	
			}else
			{
				out.println("fail");
				System.out.println("userId :"+userId );
				System.out.println("Non isConnectedMember");
			}
							
					
		} catch (SQLException e) {
			out.print("fail");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isConnectedMember(String id)
	{
		Set<Entry<String, GPSInfo>> set = hashMap.entrySet();
		Iterator<Entry<String, GPSInfo>> itr = set.iterator();
		while (itr.hasNext()) {
			Map.Entry<String, GPSInfo> e = (Map.Entry<String, GPSInfo>) itr.next();
			System.out.println(e.getKey());
			// e.getKey() : id
			// e.getValue() : GPS 정보			
			if(e.getKey().equals(id))
				return true;	
			
		}		
		return false;		
	}
}
