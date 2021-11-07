package hotel

import hotel.model.Booking
import hotel.model.HotelData
import hotel.service.Hotel
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
		data = Hotel.bookRoom(data, date, room, guestName)
		println("book result: $data")
	}

	fun bookingsByGuest(guestName: String): Set<Booking> = Hotel.bookingsByGuest(data, guestName)
}