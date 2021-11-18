package hotel

import hotel.model.Booking
import hotel.service.HotelService
import java.time.LocalDate

class HotelController(size: Int, private val service: HotelService = HotelService(size)) {

	fun setBookings(bookings: Set<Booking>) = service.setBookings(bookings)

	fun findAvailableRoomsOn(date: LocalDate): Set<Int> = service.findAvailableRoomsOn(date)

	fun bookRoom(date: LocalDate, room: Int, guestName: String) = service.bookRoom(date, room, guestName)

	fun bookingsByGuest(guestName: String): Set<Booking> = service.bookingsByGuest(guestName)
}