package hotel

import java.lang.IllegalArgumentException
import java.time.LocalDate

class BookingDateNotValidException(date: LocalDate) : IllegalArgumentException("$date is not a valid date to be booked")
