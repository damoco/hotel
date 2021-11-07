package hotel

import arrow.core.Either
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.inspectors.forExactly
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.stream.IntStream

internal class HotelControllerTest {

	@Test
	fun bookRoom() {
		val c = HotelController()
		c.configRoomSize(1)
		val date = LocalDate.of(2021, 11, 7)
		val eitherList = IntStream.rangeClosed(1, 1000).parallel().boxed()
			.map { i ->
//				println("Thread $i start at: ${LocalTime.now()}")
				Either.catch { c.bookRoom(date, 1, "guest-$i") }
//					.also { println("Thread $i end at: ${LocalTime.now()}") }
			}
//			.also { println("Result: $it") }
			.toList()
		eitherList.forExactly(1) { it.shouldBeRight() }
	}
}