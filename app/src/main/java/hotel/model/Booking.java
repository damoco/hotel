package hotel.model;

import java.time.LocalDate;

public record Booking(String guestName, int room, LocalDate date) {
	public Booking(String guestName, int room, String date) {
		this(guestName, room, LocalDate.parse(date));
	}

	public Bookable bookable() {
		return new Bookable(date, room);
	}
}
