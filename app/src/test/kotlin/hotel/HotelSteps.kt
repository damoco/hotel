package hotel

import hotel.model.Booking
import io.cucumber.java.ParameterType
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import java.time.LocalDate


class HotelSteps {
	private lateinit var availableRooms: Set<Int>
	private lateinit var lastBooking: Booking
	private var lastException: Exception? = null
	private val controller = HotelController()

	@Given("The hotel has {int} rooms")
	fun the_hotel_has_rooms(size: Int) {
		controller.configRoomSize(size)
	}

	@Given("The following rooms are booked:")
	fun the_following_rooms_are_booked(bookingTable: List<Map<String, String>>) {
		val bookings = bookingTable.map { Booking("somebody", it["room"]!!.toInt(), it["date"]) }.toSet()
		controller.setBookings(bookings)
	}

	@ParameterType(".*")
	fun date(date: String): LocalDate = LocalDate.parse(date)

	@When("I find available rooms on {date}")
	fun i_find_available_rooms_on(date: LocalDate) {
		availableRooms = controller.findAvailableRoomsOn(date)
	}

	@Then("I should be able to see the following room list:")
	fun i_got_rooms(availableRooms: List<Map<String, Int>>) {
		this.availableRooms shouldBe availableRooms.map { it["number"] }
	}

	@io.cucumber.java.en.When("I book room {int} on {date}")
	fun i_book_room(room: Int, date: LocalDate) {
		lastException = null
		lastBooking = Booking(MY_GUEST_NAME, room, date)
		try {
			controller.bookRoom(date, room, MY_GUEST_NAME)
		} catch (e: Exception) {
			lastException = e
		}
	}

	@Then("it is included in my bookings list")
	fun it_is_included_in_my_bookings_list() {
		controller.bookingsByGuest(MY_GUEST_NAME) shouldContain lastBooking
	}

	@Given("another guy booked room {int} on {date}")
	fun another_guy_booked_room(room: Int, date: LocalDate) {
		controller.bookRoom(date, room, "another_guy")
	}

	@Then("I should get warn that {string}")
	fun i_get_warn(msg: String) {
		lastException?.message shouldBe msg
	}

	companion object {
		private const val MY_GUEST_NAME = "me"
	}
}