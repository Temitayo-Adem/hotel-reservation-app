package userInterface;

import api.HotelResource;
import model.Customer;
import model.IRoom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * MainMenu serves are the User Interface of the program
 * Methods in this class are used to guide the user through the application
 */
public class MainMenu {
//  Getting instance of HotelResource api
    private static final HotelResource hotelResource = HotelResource.getInstance();
    static String mainMenu = """
            -------------------------------------
            Welcome to U Hotel's Booking Service!
            1. Find and reserve a room
            2. See my reservations
            3.Create an account
            4. Admin
            5. Exit
            -------------------------------------
            """;

//    Constant variable used to check for available rooms x days later if no rooms are available for the time period user requested
    static final int DEFAULT_DAYS_ADDED = 7;
    public static void getMainMenu() {
//        Creating a while loop that will prompt the user for an entry. If the user entry is invalid then an exception is thrown
//        and the user is prompted again
        boolean isActive = true;
        while (isActive) {
            try {
//              Using Scanner object to take user input
                Scanner scanner = new Scanner(System.in);
                System.out.println(mainMenu);
                String userInput = scanner.nextLine();
                int userInt = Integer.parseInt(userInput);
//                Using switch statement to respond to user input.
                switch (userInt) {
                    case 1:

                        reserveRoom();

                        break;

                    case 2:
                        seeMyReservations();


                        break;
                    case 3:
                        createAnAccount();


                        break;

                    case 4:
                        AdminMenu.getAdminMenu();

                        break;

                    case 5:
                        System.out.println("Thank you for using U Hotel's booking service.");

                        break;

                    default:

                        throw new IllegalArgumentException();
                }
                isActive = false;
            } catch (Exception ex) {
                System.out.println("Please enter a number between 1 and 5");
            }

        }

    }
// reserveRoom method receives two dates from the user and validates if the dates are in the correct form
    public static void reserveRoom() {
        boolean isValid = true;
//        Regular expression for a valid date (Month between 01 and 12, Day between 1 and 31, and year between 2022 and 2029)
        String regex = "^(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/202[2-9]$";
        Pattern pattern = Pattern.compile(regex);
        while (isValid) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter Valid Check-in date mm/dd/yyyy: ");
                String userCheckIn = scanner.nextLine();
                if (pattern.matcher(userCheckIn).matches()) {
                    System.out.println("Enter Valid Check-out date mm/dd/yyyy");
                    String userCheckOut = scanner.nextLine();
                    if (pattern.matcher(userCheckOut).matches()) {
                        isValid = false;
                        validReservation(userCheckIn, userCheckOut);


                    } else {
                        System.out.println("Enter valid check-in/check-out date");
                    }



            } else {
                    System.out.println("Enter valid check-in/check-out date");
                }

            } catch (Exception ex) {
                ex.getLocalizedMessage();
            }
        }




    }

//    This method parses the user input and turns it into two Date objects.
//    The method also checks to make sure Check-in date is before Check-out date
    public static void validReservation(String userCheckIn, String userCheckOut) {
        try {
            Date checkIn = new SimpleDateFormat("MM/dd/yyyy").parse(userCheckIn);
            Date checkOut = new SimpleDateFormat("MM/dd/yyyy").parse(userCheckOut);
            double difference = checkOut.getTime() - checkIn.getTime();
            if (difference <= 0) {
                System.out.println("Check-in Date must be before Check-out Date.");
                reserveRoom();
            } else {
                reservationHelper(checkIn, checkOut);
            }
        } catch (ParseException e) {
            e.getLocalizedMessage();
        }


    }

//    This method tells the user which rooms are available, if none are available,
//    the program checks for rooms available 7 days later
    public static void reservationHelper(Date checkIn, Date checkOut) {

        String answer;
        String userEmail;
        String input;

        try {
            do{
                Scanner scanner = new Scanner(System.in);
                System.out.println("Do you have an e-mail Account with us? (y/n)");
                answer = scanner.next().toLowerCase();
            } while (!answer.equals("y") && !answer.equals("n"));

            if (answer.equals("y")) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please Enter your account e-mail: e.g. name@email.com");
                userEmail = scanner.nextLine();
                Customer user = hotelResource.getCustomer(userEmail);
                if (user != null) {
                    Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);
                    if (availableRooms.isEmpty()) {

                        checkIn = addDays(checkIn);
                        checkOut = addDays(checkOut);
                        availableRooms = hotelResource.findARoom(checkIn, checkOut);

                        if (availableRooms.isEmpty()) {
                            System.out.println("No Available Rooms. Try a different date");
                            getUserHome();
                        } else {
                            System.out.println("No rooms available for those specific dates however rooms are available " + DEFAULT_DAYS_ADDED + " later");
                            System.out.println(checkIn + " through " + checkOut);
                            System.out.println("Updated Available Rooms " + availableRooms);
                            do {
                                System.out.println("Enter a Room number to book or enter 5 to return to main menu");
                                input = scanner.nextLine();
                            } while (!input.equals("5") && !availableRooms.contains(hotelResource.getRoom(input)));

                            if (input.equals("5")) {
                                getMainMenu();
                            } else {
                                System.out.println(hotelResource.bookARoom(userEmail, hotelResource.getRoom(input), checkIn, checkOut));
                                System.out.println("Thank you for booking a Room");
                                getUserHome();
                            }

                        }

                    } else {
                        System.out.println("Available Rooms: " + availableRooms);
                        do {
                            System.out.println("Enter Room number to book or enter 5 to return to main menu ");
                            input = scanner.nextLine();
                        } while (!input.equals("5") && !availableRooms.contains(hotelResource.getRoom(input)));

                        if (input.equals("5")) {
                            getMainMenu();
                        } else {
                            System.out.println(hotelResource.bookARoom(userEmail, hotelResource.getRoom(input),checkIn,checkOut));
                            System.out.println("Thank you for booking a Room.");
                            getUserHome();
                        }

                    }
                } else {
                    System.out.println("User does not exist in system.");
                    getUserHome();
                }
            } else {
                createAnAccount();
            }



        } catch (IllegalArgumentException ex) {
            ex.getLocalizedMessage();
            System.out.println("Enter a valid Email. (e.g. name@email.com)");
        }
    }


    public static void seeMyReservations() {
        boolean reservationValid = true;
        while (reservationValid) {
            try {

                Scanner scanner = new Scanner(System.in);
                System.out.println("Please Enter the E-mail address associated with you account: e.g. name@email.com");
                String userEntry = scanner.nextLine().toLowerCase();

                if (hotelResource.getCustomer(userEntry) == null) {
                    System.out.println("Account does not exist in system. Enter 4 to try again or enter 5 to return to main menu");
                    String response = scanner.nextLine();
                    reservationValid = false;
                    if (response.equals("5")) {

                        getMainMenu();
                    } else {
                        seeMyReservations();
                    }
                } else {
                    if (!hotelResource.getCustomersReservation(userEntry).isEmpty()) {
                        reservationValid = false;
                        System.out.println(hotelResource.getCustomersReservation(userEntry).toString());
                        getUserHome();

                    }

                }

            } catch (Exception ex) {
                reservationValid = false;
                System.out.println("Account does not have any reservations");
                getUserHome();

            }
        }

    }

    public static void createAnAccount() {
        boolean emailCheck = true;
        while (emailCheck) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter your email address: e.g. name@email.com ");
                String userEmail = scanner.nextLine().toLowerCase();
                if (hotelResource.getCustomer(userEmail) != null) {
                    emailCheck = false;
                    System.out.println("Account with same e-mail address already exists. You may book a reservation");
                } else {

                    System.out.println("Please enter your First name: ");
                    String firstName = scanner.next();
                    System.out.println("Please enter your Last name: ");
                    String lastName = scanner.next();
                    hotelResource.createACustomer(userEmail, firstName, lastName);
                    emailCheck = false;
                    System.out.println("Thank you for creating an account. You may now book a reservation");

                }

                getUserHome();

            } catch(IllegalArgumentException ex) {
                System.out.println("Invalid E-mail Address. Should be of type 'example@example.com' or example@example.co.uk'");
                System.out.println("Try again");

            }
        }
    }

    public static void getUserHome() {

        System.out.println("Enter 1 (or any character)to return to Main Menu");
        try {
            Scanner scanner = new Scanner(System.in);
            if (scanner.next() != null) {
                getMainMenu();
            }
        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }

    }

    public static Date addDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, DEFAULT_DAYS_ADDED);
        return calendar.getTime();

    }



}
