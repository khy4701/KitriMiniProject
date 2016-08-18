package exam.dao;

public class FriendVO {
	private String id;
	private String friendId;	

	public FriendVO(){}

	public FriendVO(String id, String friendId) {
		super();
		this.id = id;
		this.friendId = friendId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}	
}
