package service;

import model.IRoom;
import model.Reservation;
import model.Customer;
import java.util.*;
import java.util.stream.*;

/**
 * This class uses the model classes to create Reservation objects, and stores them in two maps and a List
 */
public class ReservationService {
// Turning ReservationService into a Singleton class that can only be instantiated once
    private ReservationService() {}
    private static final ReservationService RESERVATION = new ReservationService();

    public static ReservationService getInstance() {

        return RESERVATION;
    }
//  The rooms Map ensures quick look-up of details of an individual room
    private Map<String, IRoom> rooms = new HashMap<>();
//  The reservations Map keeps track of all the current reservations by user E-mail
//  The values are stored as a Collection of reservations. This allows for Customers to have more than one reservation on file
//    at any given time.
    private Map<String, Collection<Reservation>> reservations = new HashMap<>();
//    The list of reservations is used broadly to check for open rooms. If there is no reservation
//    on file for the requested date, then the room is open.
     List<Reservation> listOfReservations = new ArrayList<>();

//    Admin function used to create Rooms
    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(),room);
    }

//    Function for room look-up
    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }
//  This function is used to reserve a room. Since the map stores a collection of reservations,
//  the function checks whether the customer already has reservations on file, and if so,
//   the new reservation is added to the collection. If not, then a new Collection is made.
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {

        Reservation newReservation = new Reservation(customer,room, checkInDate, checkOutDate);
        if (reservations.get(customer.getEmail()) == null) {
            Collection<Reservation> reservation = new ArrayList<>();
            reservation.add(newReservation);
            reservations.put(customer.getEmail(), reservation);

        } else {
            reservations.get(customer.getEmail()).add(newReservation);
        }
        listOfReservations.add(newReservation);
        return newReservation;



    }
//  Using the listOfReservations to look for open rooms
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
//        Creating a list that represents the rooms at are not available when the user wants to stay
        List<IRoom> notAvailableRooms = new LinkedList<>();
        for (Reservation reservation : listOfReservations) {
//            Checking for date overlap between prospective reservation and current reservations on file
//            If there is overlap, add the room to the list of unavailable rooms
            if (findRoomHelper(reservation,checkInDate,checkOutDate)) {
                notAvailableRooms.add(reservation.getRoom());
            }
        }
//      Returning a List of available rooms after utilizing helper function.

        return availRoomHelper(notAvailableRooms);

    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public void printAllReservations() {
        for (Reservation reservation : listOfReservations) {
            System.out.println(reservation);
        }

    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();

    }
// Default
    boolean findRoomHelper(Reservation reservation,Date checkInDate, Date checkOutDate) {
        return checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate());
    }
// Private
    private List<IRoom> availRoomHelper(List<IRoom> notAvailableRooms) {


//        Filter out all the rooms that are occupied and return a list of unoccupied rooms

        return  rooms.values().stream().filter(s -> !notAvailableRooms.contains(s)).collect(Collectors.toList());


    }

}
