package hotel.exception;

import java.time.LocalDate;

public class BookingConflictException extends RuntimeException {
	public BookingConflictException(LocalDate date, int room) {
		super("""
				the room %s on date %s is already been booked""".formatted(room, date));
	}
}
