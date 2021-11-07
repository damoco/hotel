package hotel

import hotel.model.Booking
import hotel.model.HotelData
import hotel.service.Hotel
import java.lang.RuntimeException
import java.time.LocalDate

class HotelController {
	private var data = HotelData(emptySet(), emptySet())

	fun configRoomSize(size: Int) {
		data = data.withSize(size)
	}

	fun setBookings(bookings: Set<Booking>) {
		data = data.withBookings(bookings)
	}

	fun findAvailableRoomsOn(date: LocalDate): Set<Int> = Hotel.findAvailableRoomsOn(data, date)

	fun bookRoom(date: LocalDate, room: Int, guestName: String) {
		val previousData = data
		val nextData = Hotel.bookRoom(data, date, room, guestName)
		synchronized(this) {
			if (previousData == data)
				data = nextData
			else throw RuntimeException("System state has been changed. Please try again.")
		}
		println("book result: $data")
	}

	fun bookingsByGuest(guestName: String): Set<Booking> = Hotel.bookingsByGuest(data, guestName)
}