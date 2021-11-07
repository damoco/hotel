package hotel

import hotel.model.Booking
import hotel.model.HotelData
import hotel.service.Hotel.*
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicReference

class HotelController {
	private var current = AtomicReference(HotelData(emptySet(), emptySet()))

	fun configRoomSize(size: Int) {
		current.getAndUpdate { it.withSize(size) }
	}

	fun setBookings(bookings: Set<Booking>) {
		current.getAndUpdate { it.withBookings(bookings) }
	}

	fun findAvailableRoomsOn(date: LocalDate): Set<Int> = findAvailableRoomsOn(current.get(), date)

	fun bookRoom(date: LocalDate, room: Int, guestName: String) {
		current.updateAndGet { bookRoom(it, date, room, guestName) }
			.also { println("book result: $current") }
	}

	fun bookingsByGuest(guestName: String): Set<Booking> = bookingsByGuest(current.get(), guestName)
}