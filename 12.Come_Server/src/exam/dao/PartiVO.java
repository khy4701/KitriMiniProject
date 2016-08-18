package exam.dao;

public class PartiVO {
	private int roomNum;
	private String userId;

	public PartiVO(){}

	public PartiVO(int roomNum) {
		super();
		this.roomNum = roomNum;
	}
	
	public PartiVO(int roomNum, String userId) {
		super();
		this.roomNum = roomNum;
		this.userId = userId;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
