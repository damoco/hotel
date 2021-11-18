package hotel.service;

import hotel.exception.BookingConflictException;
import hotel.exception.RoomNotExistException;
import hotel.model.Booking;
import hotel.model.HotelData;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class HotelService {
	private HotelData data;

	public HotelService(int size) {
		if (size < 1) throw new IllegalArgumentException("Room size cannot less than 1. Current: " + size);
		final Set<Integer> rooms = IntStream.rangeClosed(1, size).boxed().collect(toUnmodifiableSet());
		System.out.println("configRoomSize: " + size);
		data = new HotelData(rooms, new ConcurrentHashMap<>());
	}

	public void setBookings(Set<Booking> bookings) {
		final var newBookings = bookings.stream()
				.collect(groupingByConcurrent(Booking::date, toConcurrentMap(Booking::room, Booking::guestName)));
		data = new HotelData(data.rooms(), newBookings);
	}

	public Set<Integer> findAvailableRoomsOn(LocalDate date) {
		final Set<Integer> bookedOnDate = data.bookings().getOrDefault(date, new ConcurrentHashMap<>()).keySet();
		final Set<Integer> r = new TreeSet<>(data.rooms());
		r.removeAll(bookedOnDate);
		System.out.printf("findAvailableRoomsOn: %s, %s, %s%n", data.rooms(), bookedOnDate, r);
		return r;
	}

	public Booking bookRoom(LocalDate date, int room, String guestName) {
		final Set<Integer> rooms = data.rooms();
		if (!rooms.contains(room)) throw new RoomNotExistException(room, rooms);
		final ConcurrentMap<LocalDate, ConcurrentMap<Integer, String>> bookings = data.bookings();
		bookings.compute(date, (k, v) -> {
			if (v == null) return new ConcurrentHashMap<>(Map.of(room, guestName));
			if (v.containsKey(room)) throw new BookingConflictException(date, room);
			v.put(room, guestName);
			return v;
		});
		return new Booking(guestName, room, date);
	}

	public Set<Booking> bookingsByGuest(String guestName) {
		return data.bookings().entrySet().stream()
				.flatMap(dateMap -> dateMap.getValue().entrySet().stream()
						.map(roomGuest -> new Booking(roomGuest.getValue(), roomGuest.getKey(), dateMap.getKey())))
				.filter(booking -> booking.guestName().equals(guestName))
				.collect(toUnmodifiableSet());
	}
}
