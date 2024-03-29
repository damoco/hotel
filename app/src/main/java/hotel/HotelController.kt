package hotel

import hotel.model.Booking
import hotel.service.HotelService
import java.time.Clock
import java.time.LocalDate

class HotelController(size: Int) {
	private val service: HotelService = HotelService(size)
	var clock: Clock by service::clock

	fun setBookings(bookings: Set<Booking>) = service.setBookings(bookings)

	fun findAvailableRoomsOn(date: LocalDate): Set<Int> = service.findAvailableRoomsOn(date)

	fun bookRoom(date: LocalDate, room: Int, guestName: String): Booking = service.bookRoom(date, room, guestName)

	fun bookingsByGuest(guestName: String): Set<Booking> = service.bookingsByGuest(guestName)
}