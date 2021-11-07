package hotel

import hotel.model.Booking
import hotel.model.HotelData
import hotel.service.Hotel.*
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicReference

class HotelController {
	private var data = AtomicReference(HotelData(emptySet(), emptySet()))

	fun configRoomSize(size: Int) {
		data.getAndUpdate { it.withSize(size) }
	}

	fun setBookings(bookings: Set<Booking>) {
		data.getAndUpdate { it.withBookings(bookings) }
	}

	fun findAvailableRoomsOn(date: LocalDate): Set<Int> = findAvailableRoomsOn(data.get(), date)

	fun bookRoom(date: LocalDate, room: Int, guestName: String) {
		data.getAndUpdate { bookRoom(it, date, room, guestName) }
		println("book result: $data")
	}

	fun bookingsByGuest(guestName: String): Set<Booking> = bookingsByGuest(data.get(), guestName)
}