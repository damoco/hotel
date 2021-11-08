package hotel

import arrow.core.Either
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.inspectors.forExactly
import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

private const val THREADS = 1000
private val date = LocalDate.of(2021, 11, 7)

@DelicateCoroutinesApi
internal class HotelControllerTest {
	@Test
	fun bookRoomConcurrency() = bookRoomsConcurrency(1)

	@Test
	fun book2RoomsConcurrency() = bookRoomsConcurrency(2)

	@Test
	fun bookXRoomsConcurrency() = repeat(3) { bookRoomsConcurrency((3..100).random()) }

	private fun bookRoomsConcurrency(
		size: Int,
		runConcurrently: (function: (Int) -> Either<Throwable, Unit>) -> List<Either<Throwable, Unit>> = ::run
	) {
		val c = HotelController()
		c.configRoomSize(size)
		val eitherList = runConcurrently { i: Int ->
//			println("Thread $i start at: ${LocalTime.now()}")
			Either.catch { c.bookRoom(date, i % size + 1, "guest-$i") }
//				.also { println("Thread $i end at: ${LocalTime.now()}") }
//					.also { println("Result: $it") }
		}
		eitherList.forExactly(size) { it.shouldBeRight() }
	}

	private fun run(createEither: (Int) -> Either<Throwable, Unit>): List<Either<Throwable, Unit>> {
		val deferredList = (1..THREADS).map { i -> GlobalScope.async { createEither(i) } }
		return runBlocking { deferredList.map { it.await() } }
	}
}