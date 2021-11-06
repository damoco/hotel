package hotel.module;

import hotel.model.HotelData;
import hotel.model.Room;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public interface Hotel {
	static List<Room> findAvailableRoomsOn(HotelData data, LocalDate date) {
		return Collections.emptyList();
	}
}
