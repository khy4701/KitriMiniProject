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
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exam.dao.FriendDAO;
import exam.dao.FriendVO;
import exam.dao.MemberDAO;
import exam.dao.MemberVO;
import exam.dao.PartiDAO;
import exam.dao.PartiVO;
import exam.dao.RoomVO;
import exam.dao.RoomDAO;
import exam.util.*;

public class PartiFindAction implements Action {	
	
	private HashMap<String, GPSInfo> hashMap ;

	public PartiFindAction(HashMap<String, GPSInfo> hashMap)	{ this.hashMap = hashMap; }
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		MovePage movePage = null;
		SystemLog.printLog("execute()");
		// 1. 요청 파라미터 처리
		int roomNum = Integer.valueOf(request.getParameter("room_num"));
		
		// 2. DB 처리		
		List<PartiVO> list = null;
		try{
			PartiDAO dao = PartiDAO.getInstance();	
			SystemLog.printLog("Check PartiFind Data : roomNum -"+roomNum);	

			list = dao.selectById(roomNum);
			JSONArray arr = new JSONArray();
			
			// JSONArray 형태로 저장
			for(PartiVO select : list){		
				MemberVO memberInfo = MemberDAO.getInstance().selectById(select.getUserId());
				
				JSONObject jsResult = new JSONObject();
				jsResult.put("user_id", memberInfo.getId());
				jsResult.put("user_name",memberInfo.getName());			
				
				GPSInfo gpsInfo;
				if( (gpsInfo = isConnectedMember(select.getUserId())) != null )
				{				
					jsResult.put("connect_status", "true" );
					jsResult.put("gps_lat", gpsInfo.getLatitude() );
					jsResult.put("gps_lon", gpsInfo.getLongitude() );
				}else
				{
					jsResult.put("connect_status", "false" );
					jsResult.put("gps_lat", 0.0 );
					jsResult.put("gps_lon", 0.0 );
				}									
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
	
	public GPSInfo isConnectedMember(String id)
	{
		Set<Entry<String, GPSInfo>> set = hashMap.entrySet();
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
