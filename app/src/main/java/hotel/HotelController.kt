package hotel

import hotel.model.Booking
import hotel.model.HotelData
import hotel.service.Hotel.*
import java.time.LocalDate
import java.util.stream.Collectors
import java.util.stream.Collectors.toUnmodifiableSet
import java.util.stream.Stream

class HotelController {
	private var data = HotelData(emptySet(), emptySet())

	fun configRoomSize(size: Int) {
		data = data.withSize(size)
	}

	fun setBookings(bookings: Set<Booking>) {
		data = data.withBookings(bookings)
	}

	fun findAvailableRoomsOn(date: LocalDate): Set<Int> = findAvailableRoomsOn(data, date)

	fun bookRoom(date: LocalDate, room: Int, guestName: String) {
		val previousBookings = data.bookings
		val nextData = bookRoom(data, date, room, guestName)
		// Optimistic Concurrency Control
		synchronized(this) {
			if (previousBookings == data.bookings) {
				data = nextData
			} else if (data.bookings.none { it.date == date && it.room == room }) {
				println("Will merge state: $nextData to $data")
				data = data.withBookings(data.bookings + nextData.bookings)
			} else bookingConflictException(date, room)
		}
		println("book result: $data")
	}

	fun bookingsByGuest(guestName: String): Set<Booking> = bookingsByGuest(data, guestName)
}