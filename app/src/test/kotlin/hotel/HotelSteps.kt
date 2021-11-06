package hotel

import hotel.model.Booking
import hotel.model.HotelData
import hotel.module.Hotel.findAvailableRoomsOn
import io.cucumber.java.ParameterType
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import java.time.LocalDate


class HotelSteps {
	private lateinit var availableRooms: Set<Int>
	private var data = HotelData(emptySet(), emptySet())

	@Given("The hotel has {int} rooms")
	fun the_hotel_has_rooms(size: Int) {
		data = data.withSize(size)
	}

	@Given("The following rooms are reserved:")
	fun the_following_rooms_are_reserved(bookings: List<Map<String, String>>) {
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

	@Then("I should be able to see the room list:")
	fun i_got_rooms(availableRooms: List<Map<String, Int>>) {
		this.availableRooms shouldBe availableRooms.map { it["number"] }
	}

	@io.cucumber.java.en.When("I book room {int}")
	fun i_book_room(room: Int) {

	}

	@Then("it is included in my bookings list")
	fun it_is_included_in_my_bookings_list() {
		// Write code here that turns the phrase above into concrete actions
		throw io.cucumber.java.PendingException()
	}

	@Given("another guy booked room {int}")
	fun another_guy_booked_room(int1: Int?) {
		// Write code here that turns the phrase above into concrete actions
		throw io.cucumber.java.PendingException()
	}

	@Then("I should get warn that the room was booked")
	fun i_get_warn_the_room_was_already_booked_by_other_user() {
		// Write code here that turns the phrase above into concrete actions
		throw io.cucumber.java.PendingException()
	}

}