package hotel.model;

import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableSet;

public record HotelData(Set<Integer> rooms, Set<Booking> bookings) {

	public HotelData withSize(int size) {
		final var rooms = IntStream.rangeClosed(1, size).boxed().collect(toUnmodifiableSet());
		return new HotelData(rooms, bookings);
	}

	public HotelData withBookings(Set<Booking> bookings) {
		return new HotelData(rooms, bookings);
	}

	public HotelData addBooking(Booking booking) {
		return new HotelData(rooms, Stream.concat(bookings.stream(), Stream.of(booking)).collect(toUnmodifiableSet()));
	}
}
