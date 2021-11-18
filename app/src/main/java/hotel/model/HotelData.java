package hotel.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public record HotelData(Set<Integer> rooms, ConcurrentMap<LocalDate, ConcurrentMap<Integer, String>> bookings) {
}
