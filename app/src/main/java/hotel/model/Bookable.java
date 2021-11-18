package hotel.model;

import java.time.LocalDate;

public record Bookable(LocalDate date, int room) {
	public Booking toBooking(String guestName) {
		return new Booking(guestName, room(), date());
	}
}
