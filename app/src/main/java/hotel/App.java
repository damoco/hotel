/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package hotel;

import java.time.LocalDate;

public class App {
	public static void main(String[] args) {
		final HotelController controller = new HotelController(10);
		final LocalDate date = LocalDate.now();
		controller.bookRoom(date, 3, "Bob");
		System.out.printf("Bobs booking: %s\nAvailable rooms on %s are: %s", controller.bookingsByGuest("Bob"), date, controller.findAvailableRoomsOn(date));
	}
}
