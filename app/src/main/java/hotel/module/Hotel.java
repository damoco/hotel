package hotel.module;

import hotel.model.Booking;
import hotel.model.HotelData;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.toUnmodifiableSet;

public interface Hotel {
	static Set<Integer> findAvailableRoomsOn(HotelData data, LocalDate date) {
		final Set<Integer> booked4Date = data.bookings().stream().filter(booking -> booking.date().equals(date))
				.map(Booking::room).collect(toUnmodifiableSet());
		final Set<Integer> allRooms = data.rooms();
		final Set<Integer> r = new TreeSet<>(allRooms);
		r.removeAll(booked4Date);
		System.out.printf("findAvailableRoomsOn: %s, %s, %s%n", allRooms, booked4Date, r);
		return r;
	}
}
