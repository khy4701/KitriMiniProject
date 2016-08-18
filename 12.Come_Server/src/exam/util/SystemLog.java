package exam.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemLog {	
	/*
	 * 시간 불러오는 함수
	 */
	public static String getCurTime(){
		long time = System.currentTimeMillis();
	    SimpleDateFormat ctime = new SimpleDateFormat("hh:mm:ss"); // (yyyy-mm-dd hh:mm:ss)
	    String CurrentTime = ctime.format(new Date(time));
	    return CurrentTime;
	}
	/*
	 * 위치, 출력내용을 받아서 로그형태로 출력해주는 함수
	 */
	public static void printLog(String className, String contents){	
		StackTraceElement[] a = new Throwable().getStackTrace(); // 불러온 클래스 알아오기		

		String msg = className+ " " + getCurTime() + "] " + contents;
		System.out.println(msg);
	}
	/*
	 * 출력내용만을 받아서 로그형태로 출력해주는 함수
	 */
	
	public static void printLog(String contents){	
		StackTraceElement[] a = new Throwable().getStackTrace(); // 불러온 클래스 알아오기		
		
		String classFull = a[1].getClassName();		// 풀네임을 '.' 구분자로 분할		
		String[] splitName = classFull.split("\\.");     		
		String className = splitName[splitName.length-1];	// 마지막 클래스명만 저장
		
		String msg = className+ " " + getCurTime() + "] " + contents;
		System.out.println(msg);
	}
}
