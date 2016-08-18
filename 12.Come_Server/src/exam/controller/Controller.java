package exam.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exam.action.Action;
import exam.action.FriendAddAction;
import exam.action.FriendDeleteAction;
import exam.action.FriendFindAction;
import exam.action.GPSUpdateAction;
import exam.action.LoginAction;
import exam.action.LogoutAction;
import exam.action.PartiAddAction;
import exam.action.PartiDeleteAction;
import exam.action.PartiFindAction;
import exam.action.RegisterAction;
import exam.action.RoomAddAction;
import exam.action.RoomDeleteAction;
import exam.action.RoomFindAction;
import exam.util.GPSInfo;
import exam.util.SystemLog;

/**
 * Servlet implementation class Controller
 */
@WebServlet("*.come")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, GPSInfo> hashMap = new HashMap<String, GPSInfo>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub		
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SystemLog.printLog("doGet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SystemLog.printLog("doPost");
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		
		String cmd = uri.substring(path.length()+1);		
		SystemLog.printLog("uri: "+cmd);
		
		Action action = null;
		
		// 명령어 구분
		// Action 수행 -> 이동할 페이지 반환받음		
		/*
		 *	회원가입 : register.and
		 *	로그인 : login.and
		 *	
		 *	[Controller에서 수행하는 프로토콜]
		 *	Action을 수행하는 클래스로 이동 -> 수행 후 어디로 이동할지를 반환 -> 해당 페이지로 이동 		
		 */				
		switch(cmd){
		case "register.come" : // 회원가입
			action = new RegisterAction();
			action.execute(request, response);
			break;			
			
		case "login.come" : // 로그인		
			synchronized(this)
			{
				action = new LoginAction(hashMap);
				action.execute(request, response);			
			}
			break;		
			
		case "logout.come":
			synchronized(this)
			{
				action = new LogoutAction(hashMap);
				action.execute(request, response);			
			}			
			break;
			
		case "gps.come": // GPS 받기	
			synchronized(this)
			{
				action = new GPSUpdateAction(hashMap);
				action.execute(request, response);	
			}								
			break;
			
		case "friend_add.come": // 친구 추가		
			action = new FriendAddAction();
			action.execute(request, response);			
			break;
			
		case "friend_find.come": // 친구 찾기
			synchronized(this)
			{
				action = new FriendFindAction(hashMap);
				action.execute(request, response);
			}
			break;	
			
		case "friend_delete.come": // 친구 삭제		
			action = new FriendDeleteAction();
			action.execute(request, response);
			break;
			
		case "room_add.come": // 방 만들기
			action = new RoomAddAction();
			action.execute(request, response);
			break;
			
		case "room_find.come": // 방목록 불러오기
			action = new RoomFindAction();
			action.execute(request, response);
			break;
		
		case "parti_add.come": // 방 입장
			action = new PartiAddAction(hashMap);
			action.execute(request, response);
			break;
		
		case "parti_find.come": // 방에 참여한 인원 불러오기
			action = new PartiFindAction(hashMap);
			action.execute(request, response);
			break;
		
		case "room_delete.come": // 방을 삭제
			action = new RoomDeleteAction();
			action.execute(request, response);
			break;
		
		case "room_exit.come": // 방을 나가기
			action = new PartiDeleteAction();
			action.execute(request, response);
			break;			
		}		
	}
}
