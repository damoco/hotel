package hotel.model;

import java.time.LocalDate;

public record Bookable(LocalDate date, int room) {
}
