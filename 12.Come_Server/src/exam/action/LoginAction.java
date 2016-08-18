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
import exam.util.*;

public class LoginAction implements Action {
	HashMap<String, GPSInfo> conMemberList;
	
	public LoginAction(HashMap<String, GPSInfo> hashMap)
	{
		conMemberList = hashMap;
		
	}
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		MovePage movePage = null;
		SystemLog.printLog("execute()");
		// 1. �슂泥� �뙆�씪誘명꽣 泥섎━
		String id = (String)request.getParameter("id");
		String pwd = (String)request.getParameter("pwd");			
		
		// 2. DB 泥섎━		
		int result =0;
		try{
			MemberDAO dao = MemberDAO.getInstance();	
			SystemLog.printLog("Check Login Data : id -"+id + " pw -"+pwd);	

			result = dao.login(id, pwd);					
			SystemLog.printLog("濡쒓렇�씤 寃곌낵 : "+result);	
			//if(result != 0 && (!isConnectedMember(id)) ) { // 濡쒓렇�씤 �꽦怨듭씠硫�									
			if(result != 0 ) { // 濡쒓렇�씤 �꽦怨듭씠硫�
				
				// �젒�냽 由ъ뒪�듃�뿉 異붽�
				conMemberList.put(id, new GPSInfo(0.0,0.0));
				
				JSONArray arr = new JSONArray();
				
				//�꽦怨듦껐怨� JSON�삎�깭濡� 蹂대궡二쇨린
				JSONObject jsResult = new JSONObject();
				jsResult.put("result", "success");
				arr.add(jsResult);
								
				System.out.println(arr);			
				
				//移쒓뎄紐⑸줉 JSON�삎�깭濡� 蹂대궡二쇨린
				PrintWriter out = response.getWriter();
				out.print(arr);
			} else {
				//�떎�뙣寃곌낵 JSON�삎�깭濡� 蹂대궡二쇨린
				PrintWriter out = response.getWriter();
				JSONObject jsResult = new JSONObject();
				jsResult.put("result", "fail");
				out.print(jsResult);
			}

		}catch(SQLException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public boolean isConnectedMember(String id)
	{
		Set<Entry<String, GPSInfo>> set = conMemberList.entrySet();
		Iterator<Entry<String, GPSInfo>> itr = set.iterator();
		while (itr.hasNext()) {
			Map.Entry<String, GPSInfo> e = (Map.Entry<String, GPSInfo>) itr.next();
			System.out.println("id :"+e.getKey());
			// e.getKey() : id
			// e.getValue() : GPS �젙蹂�			
			if(e.getKey().equals(id))
				return true;				
		}		
		return false;		
	}
}
