package exam.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exam.util.GPSInfo;
import exam.util.SystemLog;

public class GPSUpdateAction implements Action {
	HashMap<String, GPSInfo> hashMap;
		
	public GPSUpdateAction(){}
	public GPSUpdateAction(HashMap<String, GPSInfo> hashMap)
	{
		SystemLog.printLog("ServiceAction()");

		this.hashMap = hashMap;
		//hashMap.remove("test2");
		hashMap.put("test2", new GPSInfo(37.48931010,126.8917490));
		hashMap.put("test3", new GPSInfo(37.45935567,126.8914412));
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		SystemLog.printLog("execute()");
						
		String id = (String)request.getParameter("id");
		String latitude = (String)request.getParameter("lat");
		String longitude = (String)request.getParameter("lon");
		
		SystemLog.printLog("�뜲�씠�꽣 �닔�떊 �셿猷� : "+ id + " , " + latitude + ", "+ longitude);
		
		GPSInfo gpsInfo = new GPSInfo(Double.parseDouble(latitude),Double.parseDouble(longitude));		
		hashMap.put(id, gpsInfo);
		
		
		Set<Entry<String, GPSInfo>> set = hashMap.entrySet();
		Iterator<Entry<String, GPSInfo>> itr = set.iterator();
		while (itr.hasNext()) {
			Map.Entry<String, GPSInfo> e = (Map.Entry<String, GPSInfo>) itr.next();
			
			SystemLog.printLog("HashMap : id - "+ e.getKey() + "lat : " + e.getValue().getLatitude() +" lon : "+ e.getValue().getLongitude());
			
		}		
		
	}

}
