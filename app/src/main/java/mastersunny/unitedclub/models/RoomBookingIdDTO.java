package mastersunny.unitedclub.models;

import java.io.Serializable;


public class RoomBookingIdDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private UserDTO user;

	private RoomDTO room;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public RoomDTO getRoom() {
		return room;
	}

	public void setRoom(RoomDTO room) {
		this.room = room;
	}
}
