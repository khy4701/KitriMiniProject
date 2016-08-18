package exam.action;

public class MovePage {
	private String pageName;	//이동 페이지 이름
	private boolean isRedirect;	// true -> 리다이렉트, false -> 포워드
	
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	
}
