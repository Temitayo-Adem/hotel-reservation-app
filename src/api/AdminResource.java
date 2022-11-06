package api;
import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

/**
 * AdminResource api serves as the intermediary between UI and backend components of the application
 * Methods in this class are used by Administrator to examine the state of the application and add rooms.
 */
public class AdminResource {
//  Instantiated CustomerService/Reservation Service class
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

//   Making AdminResource a singleton class
    private static final AdminResource INSTANCE = new AdminResource();
    private AdminResource() {}

    public static AdminResource getInstance() {
        return INSTANCE;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }
//    Method that is called by UI and then calls backend to add rooms to the system
    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }

    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }
}
