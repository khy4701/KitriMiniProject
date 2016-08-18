package exam.dao;

public class RoomVO {
	private int roomNum;
	private String roomName;
	private String roomOwner;

	public RoomVO(){}	

	public RoomVO(int roomNum) {
		super();
		this.roomNum = roomNum;
	}

	public RoomVO(String roomName, String roomOwner) {
		super();
		this.roomName = roomName;
		this.roomOwner = roomOwner;
	}	

	public RoomVO(int roomNum, String roomName, String roomOwner) {
		super();
		this.roomNum = roomNum;
		this.roomName = roomName;
		this.roomOwner = roomOwner;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(String roomOwner) {
		this.roomOwner = roomOwner;
	}
	

}
