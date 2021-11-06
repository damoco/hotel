package hotel

import io.cucumber.java.ParameterType
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import java.awt.print.Book
import java.time.LocalDate


class HotelSteps {
	@Given("there are status of rooms:")
	fun there_are_status(status: List<Map<String, String>>) {
//		println("" + status.get(0).get("number")?.javaClass)
	}

	@ParameterType(".*")
	fun date(date: String): LocalDate {
		return LocalDate.parse(date)
	}

	@io.cucumber.java.en.When("I find available rooms at {date}")
	fun i_find_available_rooms_at(date: LocalDate) {
		println(date)
	}

	@Then("I got")
	fun i_got(availableRooms: List<Map<String, String>>) {
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

	@Then("I get warn the room was already booked by other user")
	fun i_get_warn_the_room_was_already_booked_by_other_user() {
		// Write code here that turns the phrase above into concrete actions
		throw io.cucumber.java.PendingException()
	}

}