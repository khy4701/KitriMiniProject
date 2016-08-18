package exam.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exam.dao.FriendDAO;
import exam.dao.FriendVO;
import exam.dao.MemberDAO;
import exam.dao.MemberVO;
import exam.util.GPSInfo;
import exam.util.SystemLog;

public class FriendFindAction implements Action {
	HashMap<String, GPSInfo> conMemberList;
	
	public FriendFindAction(HashMap<String, GPSInfo> hashMap)
	{
		conMemberList = hashMap;
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		SystemLog.printLog("execute()");

		String id = (String) request.getParameter("id");
		List<FriendVO> friendList = null;
		try {
			if ((friendList = FriendDAO.getInstance().selectById(id)) != null) {
				SystemLog.printLog("selectById() : Success");

				JSONArray arr = new JSONArray();

				// 성공결과 JSON형태로 보내주기
				JSONObject jsResult = new JSONObject();
				jsResult.put("result", "success");
				arr.add(jsResult);

				for (FriendVO friends : friendList) {
					JSONObject object = new JSONObject();					
					MemberVO friendInfo = MemberDAO.getInstance().selectById(friends.getFriendId());
										
					object.put("friend_id", friendInfo.getId() );
					object.put("friend_name", friendInfo.getName());		
					
					// 접속 유저인지 파악
					GPSInfo gpsInfo;
					if( (gpsInfo = isConnectedMember(friendInfo.getId())) != null )
					{				
						object.put("connect_status", "true" );
						object.put("gps_lat", gpsInfo.getLatitude() );
						object.put("gps_lon", gpsInfo.getLongitude() );
					}else
					{
						object.put("connect_status", "false" );
						object.put("gps_lat", 0.0 );
						object.put("gps_lon", 0.0 );
					}					
					arr.add(object);
				}

				SystemLog.printLog(arr.toString());

				// 친구목록 JSON형태로 보내주기
				PrintWriter out = response.getWriter();
				out.print(arr);

			} else
				SystemLog.printLog("selectById() : Failed");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GPSInfo isConnectedMember(String id)
	{
		Set<Entry<String, GPSInfo>> set = conMemberList.entrySet();
		Iterator<Entry<String, GPSInfo>> itr = set.iterator();
		while (itr.hasNext()) {
			Map.Entry<String, GPSInfo> e = (Map.Entry<String, GPSInfo>) itr.next();
			
			// e.getKey() : id
			// e.getValue() : GPS 정보			
			if(e.getKey().equals(id))
				return e.getValue();				
		}		
		return null;		
	}

}
