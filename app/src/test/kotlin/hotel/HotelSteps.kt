package hotel

import hotel.model.Booking
import hotel.model.HotelData
import hotel.module.Hotel.*
import io.cucumber.java.ParameterType
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.lang.Exception
import java.time.LocalDate


class HotelSteps {
	private lateinit var availableRooms: Set<Int>
	private lateinit var lastBooking: Booking
	private var lastException: Exception? = null
	private var data = HotelData(emptySet(), emptySet())

	@Given("The hotel has {int} rooms")
	fun the_hotel_has_rooms(size: Int) {
		data = data.withSize(size)
	}

	@Given("The following rooms are booked:")
	fun the_following_rooms_are_booked(bookings: List<Map<String, String>>) {
		data = data.withBookings(bookings.map { Booking("somebody", it["room"]!!.toInt(), it["date"]) }.toSet())
	}

//	@Given("Room availability:")
//	fun room_availability(status: List<Map<String, String>>) {
//		val occupied = status.groupBy { it["date"]!! }.mapKeys { LocalDate.parse(it.key) }
//			.mapValues { (_, dateOccupied) ->
//				dateOccupied.associateBy { it["room"]!! }.mapKeys { it.key.toInt() }
//					.mapValues { it.value["available"] }
//					.filterValues { it == "false" }
//					.mapValues { 0 }
//			}
//		data = HotelData(occupied)
//		println(data)
//	}

	@ParameterType(".*")
	fun date(date: String): LocalDate {
		return LocalDate.parse(date)
	}

	@When("I find available rooms on {date}")
	fun i_find_available_rooms_on(date: LocalDate) {
		availableRooms = findAvailableRoomsOn(data, date)
	}

	@Then("I should be able to see the following room list:")
	fun i_got_rooms(availableRooms: List<Map<String, Int>>) {
		this.availableRooms shouldBe availableRooms.map { it["number"] }
	}

	@io.cucumber.java.en.When("I book room {int} on {date}")
	fun i_book_room(room: Int, date: LocalDate) {
		lastException = null
		lastBooking = Booking("me", room, date)
		try {
			data = bookRoom(data, date, room, "me")
		} catch (e: Exception) {
			lastException = e
		}
	}

	@Then("it is included in my bookings list")
	fun it_is_included_in_my_bookings_list() {
		val bookingsByGuest = bookingsByGuest(data, "me")
		bookingsByGuest shouldContain lastBooking
	}

	@Given("another guy booked room {int} on {date}")
	fun another_guy_booked_room(room: Int, date: LocalDate) {
		data = bookRoom(data, date, room, "another_guy")
	}

	@Then("I should get warn that {string}")
	fun i_get_warn(msg: String) {
		lastException?.message shouldBe msg
	}

}