package hotel.service;

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
		final Set<Integer> r = new TreeSet<>(data.rooms());
		r.removeAll(booked4Date);
		System.out.printf("findAvailableRoomsOn: %s, %s, %s%n", data.rooms(), booked4Date, r);
		return Set.copyOf(r);
	}

	static HotelData bookRoom(HotelData data, LocalDate date, int room, String guestName) {
		if (data.bookings().stream().anyMatch(booking -> booking.date().equals(date) && booking.room() == room))
			throw bookingConflictException(date, room);
		return data.addBooking(new Booking(guestName, room, date));
	}

	static RuntimeException bookingConflictException(LocalDate date, int room) {
		return new RuntimeException("""
				the room %s on date %s is already been booked""".formatted(room, date));
	}

	static Set<Booking> bookingsByGuest(HotelData data, String guestName) {
		return data.bookings().stream().filter(booking -> booking.guestName().equals(guestName)).collect(toUnmodifiableSet());
	}
}
