package hotel.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record HotelData(Set<Integer> rooms, Set<Booking> bookings) {

	public HotelData withSize(int size) {
		return new HotelData(IntStream.rangeClosed(1, size).boxed().collect(Collectors.toUnmodifiableSet()), bookings);
	}

	public HotelData withBookings(Set<Booking> bookings) {
		return new HotelData(rooms, bookings);
	}

//	public HotelData addBooking(Booking booking) {
//		return new HotelData(rooms, bookings);
//	}
}
