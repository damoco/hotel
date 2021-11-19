package hotel.model;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public record Booking(String guestName, int room, LocalDate date) {
	public Booking {
		requireNonNull(guestName, "guestName");
		requireNonNull(date, "date");
	}

	public Booking(String guestName, int room, String date) {
		this(guestName, room, parseDate(date));
	}

	private static LocalDate parseDate(String date) {
		requireNonNull(date, "date");
		return LocalDate.parse(date);
	}

	public Bookable bookable() {
		return new Bookable(date, room);
	}
}
