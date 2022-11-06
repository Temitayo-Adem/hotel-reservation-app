package api;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import model.Customer;
import model.IRoom;
import java.util.*;

/**
 * HotelResource api serves as the intermediary between UI and backend components of the application for customers
 * Methods take directions from UI and return information from the backend to the user.
 */
public class HotelResource {
//    Making HotelResource a singleton class
    private static final HotelResource STATICREFERENCE = new HotelResource();

//    Creating an instance of CustomerService
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    private HotelResource() {}

    public static HotelResource getInstance() {
        return STATICREFERENCE;
    }
    public Customer getCustomer(String email) {

        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(customerService.getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservation(String customerEmail) {
        return reservationService.getCustomersReservation(customerService.getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);

    }
}
