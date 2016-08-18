package exam.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exam.dao.FriendDAO;
import exam.dao.FriendVO;
import exam.dao.MemberDAO;
import exam.dao.MemberVO;
import exam.dao.RoomVO;
import exam.dao.RoomDAO;
import exam.util.*;

public class RoomFindAction implements Action {	
	public RoomFindAction()	{ }
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		MovePage movePage = null;
		SystemLog.printLog("execute()");
		// 1. 요청 파라미터 처리
		String userId = (String)request.getParameter("user_id");					
		
		// 2. DB 처리		
		List<RoomVO> list = null;
		try{
			RoomDAO dao = RoomDAO.getInstance();	
			SystemLog.printLog("Check RoomFind Data : user_id -"+userId);	

			list = dao.selectById(userId);
			JSONArray arr = new JSONArray();
			
			// JSONArray 형태로 저장
			for(RoomVO select : list){		
				JSONObject jsResult = new JSONObject();
				jsResult.put("room_num", select.getRoomNum());
				jsResult.put("room_name", select.getRoomName());
				jsResult.put("room_owner", select.getRoomOwner());
				
				arr.add(jsResult);
			}
			// 데이터 보내기
			System.out.println(arr);							
			PrintWriter out = response.getWriter();
			out.print(arr);

		}catch(SQLException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
