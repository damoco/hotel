package hotel.model;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public record Bookable(LocalDate date, int room) {
	public Bookable {
		requireNonNull(date, "date");
	}

	public Booking toBooking(String guestName) {
		return new Booking(guestName, room(), date());
	}
}
