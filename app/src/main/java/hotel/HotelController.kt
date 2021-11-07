package hotel

import hotel.model.Booking
import hotel.model.HotelData
import hotel.module.Hotel
import java.time.LocalDate

class HotelController {
	private var data = HotelData(emptySet(), emptySet())

	fun configRoomSize(size: Int) {
		data = data.withSize(size)
	}

	fun setBookings(bookings: Set<Booking>) {
		data = data.withBookings(bookings)
	}

	fun findAvailableRoomsOn(date: LocalDate) = Hotel.findAvailableRoomsOn(data, date)

	fun bookRoom(date: LocalDate, room: Int, guestName: String) {
		data = Hotel.bookRoom(data, date, room, guestName)
	}

	fun bookingsByGuest(guestName: String) = Hotel.bookingsByGuest(data, guestName)
}