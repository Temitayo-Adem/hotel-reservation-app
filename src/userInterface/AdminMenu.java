package userInterface;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Admin Menu serves as the User Interface for Hotel Administrators.
 * This class tells the admin information about customers, rooms, and reservations.
 * Administrators can also add available rooms and test data.
 */
public class AdminMenu {

//  Creating an instance of the AdminResource class and
//  using that instance/api to fetch information that will be displayed to the admin
    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final HotelResource hotelResource = HotelResource.getInstance();
    public static void getAdminMenu() {
        String adminMenu = """
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Back to Main Menu
                6. Add Test Data
                """;

//      "While loop" that serves as the Admin Navigation Menu.
//        Menu prompts user for an entry between 1 and 5. If entry is invalid then user is re-prompted.
        boolean adminActive = true;
        while (adminActive) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println(adminMenu);
                String adminInput = scanner.nextLine();
                int adminInt = Integer.parseInt(adminInput);
                switch (adminInt) {
                    case 1:

                        seeAllCustomers();
                        break;
                    case 2:

                        seeAllRooms();
                        break;
                    case 3:
                        seeAllReservations();

                        break;
                    case 4:

                        addARoom();
                        break;
                    case 5:

                        MainMenu.getMainMenu();
                        break;
                    case 6:
                        addTestData();
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
                adminActive = false;

            } catch (Exception ex) {
                ex.getLocalizedMessage();
                System.out.println("Enter a number between 1 and 5");
                getAdminMenu();
            }
        }

    }

    public static void seeAllCustomers() {

        System.out.println(adminResource.getAllCustomers().toString());
        getAdminHome();


    }

    public static void seeAllRooms() {

        System.out.println(adminResource.getAllRooms().toString());
        getAdminHome();

    }

    public static void seeAllReservations() {

        adminResource.displayAllReservations();
        getAdminHome();

    }

//    Method used to add rooms by appending user-generated room info to a List of Rooms
    public static void addARoom() {
        List<IRoom> rooms = new LinkedList<>();
        boolean check = true;
        while (check) {
            try {

                Scanner scanner = new Scanner(System.in);
                System.out.println("Please Enter a Room number to add");
                String userRoomNumber = scanner.nextLine();
                System.out.println("Enter Price per night (Numbers only)");
                String userPrice = scanner.nextLine();
                Double priceDouble = Double.parseDouble(userPrice);
                String roomType;
                do {
                    System.out.println("What type of room: Single or Double?");
                    roomType = scanner.nextLine().toUpperCase();
                } while (!roomType.equals("DOUBLE") && !roomType.equals("SINGLE") );

                IRoom room;
                if (roomType.equals("SINGLE")) {
                    if (priceDouble == 0) {
                        room = new FreeRoom(userRoomNumber, priceDouble, RoomType.SINGLE);
                    } else {
                        room = new Room(userRoomNumber, priceDouble, RoomType.SINGLE);
                    }

                } else  {
                    if (priceDouble == 0) {
                        room = new FreeRoom(userRoomNumber,priceDouble,RoomType.DOUBLE);
                    } else {
                        room = new Room(userRoomNumber, priceDouble, RoomType.DOUBLE);

                    }

                }
                rooms.add(room);
                String userRes;
                do {
                    System.out.println("Would you like to add another room? (y/n)");
                    userRes = scanner.next().toLowerCase();
                } while(!userRes.equals("y") && !userRes.equals("n"));

                if (userRes.equals("n")) {
                    adminResource.addRoom(rooms);
                    check = false;
                    getAdminMenu();

                }




            } catch (Exception ex) {
                ex.getLocalizedMessage();
                System.out.println("Something went wrong. Let's try again");
            }

        }




    }

//    Helper function used to return user to Admin or Main Menu
    public static void getAdminHome() {
        boolean check = true;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Press 1 to return to Admin Menu or 2 to return to Main Menu");
            String userResponse = scanner.next();
            if (userResponse.equals("1")) {
                check = false;
                getAdminMenu();
            } else if (userResponse.equals("2")) {
                check = false;
                MainMenu.getMainMenu();
            } else {
                System.out.println("Invalid entry. Try Again");
            }
        } while (check);

    }

//    Method used to add test Data to the Application
    public static void addTestData() {

//        Creating a 2D list of test customers and using that list to instantiate Customer objects
        String [][] testCustomers = {{"tom@test.com", "Tom", "Jones"}, {"sarah@test.com", "Sarah", "Miller"}, {"ike@test.com", "Ike", "Ayodele"},
                {"iggy@test.com", "Isaac", "Bowery"}, {"yinks@test.com", "Yinka", "Adu"}};

        List<Customer> testCustomersList = new ArrayList<>();

        for (String [] tester : testCustomers) {
            hotelResource.createACustomer(tester[0],tester[1],tester[2]);
            testCustomersList.add(adminResource.getCustomer(tester[0]));
        }

//        Creating a List of rooms and appending individual rooms
//        (from room # 100 - 109, alternating between single/double, with random prices)
        List<IRoom> testRooms = new ArrayList<>();
        int roomNumber = 100;
        RoomType singleDub = RoomType.SINGLE;

        for (int i = 0; i < 10; i++) {
//            Initializing new room using the roomNumber int (cast to String), a random Int (cast as a double), and our enumerate object
            IRoom room = new Room(String.valueOf(roomNumber), (double) ThreadLocalRandom.current().nextInt(100, 300),
                    singleDub);
            testRooms.add(room);
            if (singleDub.equals(RoomType.SINGLE)) {
                singleDub = RoomType.DOUBLE;
            } else {
                singleDub = RoomType.SINGLE;
            }
            roomNumber += 1;

        }
        adminResource.addRoom(testRooms);


//      Getting a calendar instance that will be used to set check-in/check-out dates

        Calendar calendar = Calendar.getInstance();


//        Creating reservations for each customer starting on the current date and ending on a random date
        for (Customer customer : testCustomersList) {
            Date checkInDate = new Date();
            calendar.setTime(checkInDate);
            int randomStayInt = ThreadLocalRandom.current().nextInt(3,10);

//            Using calendar.add to set a checkout date
            calendar.add(Calendar.DATE, randomStayInt);
            Date checkOutDate = calendar.getTime();

//            Giving each customer three reservations with random stay times.
            for (int i = 0; i < 3; i++) {
                hotelResource.bookARoom(customer.getEmail(), hotelResource.getRoom(String.valueOf(ThreadLocalRandom.current().nextInt(100, 109))),
                        checkInDate, checkOutDate);
                calendar.add(Calendar.DATE, 7);
                checkInDate = calendar.getTime();
                randomStayInt = ThreadLocalRandom.current().nextInt(3,10);
                calendar.add(Calendar.DATE, randomStayInt);
                checkOutDate = calendar.getTime();

            }

        }
        System.out.println("Test Data Added.");

        getAdminHome();

    }
}
