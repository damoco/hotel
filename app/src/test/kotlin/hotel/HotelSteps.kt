package hotel

import hotel.model.HotelData
import hotel.model.Room
import hotel.module.Hotel
import hotel.module.Hotel.findAvailableRoomsOn
import io.cucumber.java.ParameterType
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.kotest.matchers.shouldBe
import java.time.LocalDate


class HotelSteps {
	private lateinit var rooms: List<Room>
	private val data = HotelData()

	@Given("Room availability:")
	fun room_availability(status: List<Map<String, String>>) {
//		println("" + status.get(0).get("number")?.javaClass)
	}

	@ParameterType(".*")
	fun date(date: String): LocalDate {
		return LocalDate.parse(date)
	}

	@When("I find available rooms on {date}")
	fun i_find_available_rooms_on(date: LocalDate) {
		rooms = findAvailableRoomsOn(data, date)
	}

	@Then("I should be able to see the room list:")
	fun i_got_rooms(availableRooms: List<Map<String, String>>) {
		rooms.map { it.number } shouldBe availableRooms.map { it["number"] }
	}

	@io.cucumber.java.en.When("I book room {int}")
	fun i_book_room(int1: Int?) {
		// Write code here that turns the phrase above into concrete actions
		throw io.cucumber.java.PendingException()
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