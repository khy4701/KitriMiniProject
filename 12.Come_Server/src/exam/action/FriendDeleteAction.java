package exam.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exam.dao.FriendDAO;
import exam.dao.FriendVO;
import exam.dao.MemberDAO;
import exam.util.SystemLog;

public class FriendDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		SystemLog.printLog("execute()");
		
		String user_id = (String)request.getParameter("id");
		String friendId = (String)request.getParameter("friendId");
		
		try {
			JSONArray arr = new JSONArray();
			
			//성공결과 JSON형태로 보내주기
			JSONObject jsResult = new JSONObject();
			FriendVO freindVO = new FriendVO(user_id, friendId);

			if (FriendDAO.getInstance().deleteMemeber(freindVO) == 1) {
				SystemLog.printLog("insertFriend() : Success");

				jsResult.put("result", "success");
				arr.add(jsResult);

				// 친구목록 JSON형태로 보내주기
				PrintWriter out = response.getWriter();
				out.print(arr);

			} else
				SystemLog.printLog("deleteFriend() : Failed");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
