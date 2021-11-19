package hotel

import arrow.core.Either
import hotel.exception.RoomNotExistException
import hotel.model.Booking
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.inspectors.forExactly
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.should
import io.kotest.matchers.string.include
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId

private const val THREADS = 1000
private val DATE = LocalDate.of(2021, 11, 7)
private val zoneId = ZoneId.systemDefault()
private val clockYesterday = Clock.fixed(DATE.minusDays(1).atStartOfDay().atZone(zoneId).toInstant(), zoneId)

@DelicateCoroutinesApi
internal class HotelControllerTest {
	@Test
	fun clock() {
		val clock = Clock.systemDefaultZone()
		val instant = clock.instant()
		Thread.sleep(1)
		clock.instant() shouldBeAfter instant
	}

	@Test
	fun wrongRoomSize() {
		shouldThrowExactly<IllegalArgumentException> {
			HotelController(-1)
		}
		shouldThrowExactly<IllegalArgumentException> {
			HotelController(0)
		}
	}

	@Test
	fun bookingRoomNotExist() {
		shouldThrowExactly<RoomNotExistException> {
			val c = getController(1)
			c.bookRoom(DATE, 201, "guest")
		}.message should include("201 not exist")
	}

	@Test
	fun bookingFromPast() {
		shouldThrowExactly<BookingDateNotValidException> {
			val c = getController(1)
			c.bookRoom(LocalDate.MIN, 201, "guest")
		}.message should include(LocalDate.MIN.toString())
	}

	@Test
	fun bookingNullCheck() {
		shouldThrowExactly<NullPointerException> {
			Booking(null, 1, DATE)
		}.message should include("guestName")
		shouldThrowExactly<NullPointerException> {
			Booking("null", 1, null as LocalDate?)
		}.message should include("date")
		shouldThrowExactly<NullPointerException> {
			Booking("null", 1, null as String?)
		}.message should include("date")
	}

	@Test
	fun bookRoomConcurrency() = bookRoomsConcurrency(1)

	@Test
	fun book2RoomsConcurrency() = bookRoomsConcurrency(2)

	@Test
	fun bookXRoomsConcurrency() = repeat(3) { bookRoomsConcurrency((3..50).random(), (1..7).random()) }

	private fun bookRoomsConcurrency(
		size: Int,
		days: Int = 1,
		runConcurrently: (function: (Int) -> Either<Throwable, Any>) -> List<Either<Throwable, Any>> = ::run
	) {
		val c = getController(size)
		val eitherList = runConcurrently { i: Int ->
//			println("Thread $i start at: ${LocalTime.now()}")
			val plusDays = i / size % days
			val room = i % size + 1
			val guestName = "guest-$i"
//			println("$plusDays, $room, $guestName")
			Either.catch { c.bookRoom(DATE.plusDays(plusDays.toLong()), room, guestName) }
//				.also { println("Thread $i end at: ${LocalTime.now()}") }
//				.also { println("Result: $it") }
		}
		eitherList.forExactly(size * days) { it.shouldBeRight() }
	}

	private fun getController(size: Int): HotelController = HotelController(size).also { it.clock = clockYesterday }

	private fun run(createEither: (Int) -> Either<Throwable, Any>): List<Either<Throwable, Any>> {
		val deferredList = (1..THREADS).map { i -> GlobalScope.async { createEither(i) } }
		return runBlocking { deferredList.map { it.await() } }
	}
}