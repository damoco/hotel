package hotel

import arrow.core.Either
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.inspectors.forExactly
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.Callable
import java.util.concurrent.Executors

private const val THREADS = 1000
private val date = LocalDate.of(2021, 11, 7)

internal class HotelControllerTest {
	@Test
	fun bookRoomConcurrency() = bookRoomsConcurrency(1)

	@Test
	fun book2RoomsConcurrency() = bookRoomsConcurrency(2)

	@Test
	fun bookXRoomsConcurrency() = repeat(3) { bookRoomsConcurrency((3..100).random()) }

	private fun bookRoomsConcurrency(size: Int) {
		val c = HotelController()
		c.configRoomSize(size)
		val eitherList = fixedThreadPool.invokeAll((1..THREADS).map { i ->
			Callable {
				println("Thread $i start at: ${LocalTime.now()}")
				Either.catch { c.bookRoom(date, i % size + 1, "guest-$i") }
					.also { println("Thread $i end at: ${LocalTime.now()}") }
			}
			//					.also { println("Result: $it") }
		}).map { it.get() }
		eitherList.forExactly(size) { it.shouldBeRight() }
	}

	companion object {
		private val fixedThreadPool = Executors.newFixedThreadPool(THREADS)

		@AfterAll
		internal fun tearDown() {
			fixedThreadPool.shutdown()
		}
	}
}