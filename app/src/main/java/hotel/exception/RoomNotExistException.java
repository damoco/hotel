package hotel.exception;

import java.util.Set;

public class RoomNotExistException extends RuntimeException {
	public RoomNotExistException(int room, Set<Integer> rooms) {
		super("""
				Room %s not exist in the room list: %s""".formatted(room, rooms));
	}
}
