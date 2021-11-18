package hotel.service;

import hotel.model.Booking;
import hotel.model.HotelData;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toUnmodifiableSet;

public class HotelService {
	private final AtomicReference<HotelData> current;

	public HotelService(int size) {
		current = new AtomicReference<>(new HotelData(size, emptySet()));
	}

	private static RuntimeException bookingConflictException(LocalDate date, int room) {
		return new RuntimeException("""
				the room %s on date %s is already been booked""".formatted(room, date));
	}

	public void setBookings(Set<Booking> bookings) {
		current.updateAndGet(data -> data.withBookings(bookings));
	}

	public Set<Integer> findAvailableRoomsOn(LocalDate date) {
		HotelData data = current.get();
		final Set<Integer> bookedOnDate = data.bookings().stream().filter(booking -> booking.date().equals(date))
				.map(Booking::room).collect(toUnmodifiableSet());
		final Set<Integer> r = new TreeSet<>(data.rooms());
		r.removeAll(bookedOnDate);
		System.out.printf("findAvailableRoomsOn: %s, %s, %s%n", data.rooms(), bookedOnDate, r);
		return Set.copyOf(r);
	}

	public void bookRoom(LocalDate date, int room, String guestName) {
		var previous = current.get();
		if (previous.bookings().stream().anyMatch(booking -> booking.date().equals(date) && booking.room() == room))
			throw bookingConflictException(date, room);
		var next = previous.addBooking(new Booking(guestName, room, date));
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
		return current.get().bookings().stream().filter(booking -> booking.guestName().equals(guestName)).collect(toUnmodifiableSet());
	}
}
