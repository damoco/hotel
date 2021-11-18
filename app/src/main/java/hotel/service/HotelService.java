package hotel.service;

import hotel.model.Booking;
import hotel.model.HotelData;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hotel.service.Hotel.bookingConflictException;
import static java.util.Collections.emptySet;

public class HotelService {
	private final AtomicReference<HotelData> current;

	public HotelService(int size) {
		current = new AtomicReference<>(new HotelData(size, emptySet()));
	}

	public void setBookings(Set<Booking> bookings) {
		current.updateAndGet(data -> data.withBookings(bookings));
	}

	public Set<Integer> findAvailableRoomsOn(LocalDate date) {
		return Hotel.findAvailableRoomsOn(current.get(), date);
	}

	public void bookRoom(LocalDate date, int room, String guestName) {
		var previous = current.get();
		var next = Hotel.bookRoom(previous, date, room, guestName);
//		current.set(next)// concurrency problem
		current.updateAndGet(data -> {
					if (data == previous) return next;
					else if (data.bookings().stream().noneMatch(booking -> date.equals(booking.date()) && booking.room() == room)) {
						final Set<Booking> sum = Stream.of(data.bookings(), next.bookings())
								.flatMap(Set::stream)
								.collect(Collectors.toUnmodifiableSet());
						return data.withBookings(sum);
					}
					throw bookingConflictException(date, room);
				}
		);
	}

	public Set<Booking> bookingsByGuest(String guestName) {
		return Hotel.bookingsByGuest(current.get(), guestName);
	}
}
