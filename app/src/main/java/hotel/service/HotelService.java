package hotel.service;

import hotel.exception.BookingConflictException;
import hotel.exception.RoomNotExistException;
import hotel.model.Bookable;
import hotel.model.Booking;
import hotel.model.HotelData;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

public class HotelService {
	private HotelData data;

	public HotelService(int size) {
		if (size < 1) throw new IllegalArgumentException("Room size cannot less than 1. Current: " + size);
		final Set<Integer> rooms = IntStream.rangeClosed(1, size).boxed().collect(toUnmodifiableSet());
		System.out.println("configRoomSize: " + size);
		data = new HotelData(rooms, new ConcurrentHashMap<>());
	}

	public void setBookings(Set<Booking> bookings) {
		data = new HotelData(data.rooms(),
				bookings.stream().collect(toConcurrentMap(Booking::bookable, Booking::guestName)));
	}

	public Set<Integer> findAvailableRoomsOn(LocalDate date) {
		final Set<Integer> bookedOnDate = data.bookings().keySet().stream()
				.filter(bookable -> bookable.date().equals(date))
				.map(Bookable::room)
				.collect(toUnmodifiableSet());
		final Set<Integer> r = new TreeSet<>(data.rooms());
		r.removeAll(bookedOnDate);
		System.out.printf("findAvailableRoomsOn: %s, %s, %s%n", data.rooms(), bookedOnDate, r);
		return r;
	}

	public Booking bookRoom(LocalDate date, int room, String guestName) {
		Objects.requireNonNull(date);
		Objects.requireNonNull(guestName);
		final Set<Integer> rooms = data.rooms();
		if (!rooms.contains(room)) throw new RoomNotExistException(room, rooms);
		data.bookings().compute(new Bookable(date, room), (k, v) -> {
			if (null != v) throw new BookingConflictException(date, room);
			return guestName;
		});
		return new Booking(guestName, room, date);
	}

	public Set<Booking> bookingsByGuest(String guestName) {
		return data.bookings().entrySet().stream()
				.filter(bookableGuest -> bookableGuest.getValue().equals(guestName))
				.map(bookableGuest -> bookableGuest.getKey().toBooking(guestName))
				.collect(toUnmodifiableSet());
	}

}
