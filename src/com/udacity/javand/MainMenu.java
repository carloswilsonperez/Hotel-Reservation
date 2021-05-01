package com.udacity.javand;

import com.udacity.javand.model.Customer;
import com.udacity.javand.model.IRoom;
import com.udacity.javand.model.Reservation;
import com.udacity.javand.service.CustomerService;
import com.udacity.javand.service.ReservationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    public static final String SEPARATOR = "--------------------------------------------";

    /**
     * This method shows and manages the Main Menu
     */
    public static void mainMenu() {
        Scanner scan = new Scanner(System.in);

        // Main Menu
        while (true) {
            System.out.println();
            System.out.println("Welcome to the Hotel Reservation Application");
            System.out.println(SEPARATOR);
            System.out.println();
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");

            System.out.println();
            System.out.println(SEPARATOR);
            System.out.println("Please select a number for the menu option");
            String choice = scan.nextLine();

            try {
                int nextInt = Integer.parseInt(choice); //convert the string to an int

                switch (nextInt) {
                    case 1:
                        reserveRoom();
                        break;
                    case 2:
                        showMyReservations();
                        break;
                    case 3:
                        createAccount();
                        break;
                    case 4:
                        AdminMenu.adminMenu();
                        break;
                    case 5:
                        System.exit(0);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid Input. Please enter a number between 1 and 5");
                System.out.println();
            }
        }
    }

    /**
     * This method reserves a new room
     */
    public static void reserveRoom() {
        Scanner scan = new Scanner(System.in);
        Boolean isValidDate = false;
        Boolean isValidAnswer = false;
        Boolean isValidEmail = false;
        Boolean userPickedAValidRoom = false;
        Boolean isValidReservation = false;
        String checkIn;
        String checkOut;
        Date checkInDate = null;
        Date checkOutDate = null;

        while (!isValidReservation) {
            while (!isValidDate) {
                System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
                checkIn = scan.nextLine();

                // validate if a valid date was provided
                try {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    df.setLenient(false);
                    checkInDate = df.parse(checkIn);
                    // LocalDate.parse(checkIn, PARSE_FORMATTER);
                    isValidDate = true;
                } catch (ParseException e) {
                    System.out.println("Please enter a valid date");
                }
            }

            isValidDate = false;
            while (!isValidDate) {
                System.out.println("Enter CheckOut Date month/day/year example 2/21/2020");
                checkOut = scan.nextLine();

                // validate if a valid date was provided
                try {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    df.setLenient(false);
                    checkOutDate = df.parse(checkOut);

                    if (checkOutDate.compareTo(checkInDate) < 0) {
                        throw new IllegalArgumentException("Checkout date must be greater thant checkin date");
                    }
                    isValidDate = true;
                } catch (ParseException e) {
                    System.out.println("Please enter a valid date");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }

            // Loop through the reservations and remove the expired reservations
            Date todayDate = new Date();
            for (Reservation resv : ReservationService.getReservationService().getReservationsList()) {
                IRoom room = resv.getRoom();

                if (resv.getCheckOutDate().compareTo(todayDate) < 0) {
                    // Reservation is outdated; remove it
                    ReservationService.getReservationService().getReservationsList().remove(resv);
                }
            }

            // Create a map with the rooms available; key is the room number.
            Map<String, IRoom> mapOfAvailableRooms = new HashMap<String, IRoom>();

            // List available Rooms
            System.out.println("These are the available rooms for the requested period:");
            for (IRoom room : ReservationService.getReservationService().findRooms(checkInDate, checkOutDate)) {
                System.out.println(room.toString());

                mapOfAvailableRooms.put(room.getroomId(), room);
            }

            String reply = "";
            while (!isValidAnswer) {
                System.out.println("Would you like to book a room? y/n");
                reply = scan.nextLine();

                if (reply.equalsIgnoreCase("y") || reply.equalsIgnoreCase("n")) {
                    isValidAnswer = true;
                } else {
                    System.out.println("Please Enter y or n");
                }
            }

            if (reply.equals("y")) {
                isValidAnswer = false;
                String accountReply = "";
                while (!isValidAnswer) {
                    System.out.println("Do you have an account with us? y/n");
                    accountReply = scan.nextLine();

                    if (accountReply.equalsIgnoreCase("y") || accountReply.equalsIgnoreCase("n")) {
                        isValidAnswer = true;
                    } else {
                        System.out.println("Please Enter y or n");
                    }
                }

                if (accountReply.equals("y")) {
                    String emailRegex = "^(.+)@(.+).(.+)$";
                    String email = "";

                    while (!isValidEmail) {
                        System.out.println("Enter Email in format: name@domain.com");
                        email = scan.nextLine();
                        Pattern pattern = Pattern.compile(emailRegex);

                        if (pattern.matcher(email).matches()) {
                            isValidEmail = true;
                        }
                    }

                    // Check if account exists for that email
                    Customer customer = CustomerService.getCustomerService().getCustomer(email);

                    if (customer != null) {
                        String roomId = null;

                        while (!userPickedAValidRoom) {
                            System.out.println("What room number would you like to reserve?");
                            roomId = scan.nextLine();

                            if (mapOfAvailableRooms.get(roomId) != null) {
                                userPickedAValidRoom = true;
                            } else {
                                System.out.println("Please pick up a valid room number");
                            }
                        }

                        // Create Reservation instance
                        IRoom roomToReserve = ReservationService.getReservationService().getARoom(roomId);

                        try {
                            Reservation myReservation = ReservationService.getReservationService().reserveARoom(customer, roomToReserve, checkInDate, checkOutDate);
                            isValidReservation = true;

                            // Show reservation summary
                            System.out.println("Reservation Summary: ");
                            System.out.println(myReservation.toString());
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getLocalizedMessage());
                            isValidAnswer = false;

                            while (!isValidAnswer) {
                                System.out.println("Do you want to try a different reservation? y/n");
                                reply = scan.nextLine();

                                if (reply.equalsIgnoreCase("y") || reply.equalsIgnoreCase("n")) {
                                    isValidAnswer = true;
                                } else {
                                    System.out.println("Please Enter y or n");
                                }
                            }

                            if (reply.equalsIgnoreCase("n")) {
                                return;
                            }
                        }
                    } else {
                        System.out.println("You need to first create an account");
                        return;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    /**
     * This method shows all the reservations for a given customer
     */
    public static void showMyReservations() {
        Scanner scan = new Scanner(System.in);
        String emailRegex = "^(.+)@(.+).(.+)$";
        Boolean isValidEmail = false;
        Boolean isValidAccount = false;
        Boolean isValidAnswer = false;

        while (!isValidEmail || !isValidAccount) {
            System.out.println("Enter Email format: name@domain.com");
            String email = scan.nextLine();

            // Validate if email has right format
            Pattern pattern = Pattern.compile(emailRegex);

            try {
                if (!pattern.matcher(email).matches()) {
                    throw new IllegalArgumentException("Email has wrong format");
                }

                isValidEmail = true;

                Customer me = CustomerService.getCustomerService().getCustomer(email);

                if (me != null) {
                    isValidAccount = true;

                    // Show all reservations from customer "me"
                    for (Reservation resv : ReservationService.getReservationService().getCustomersReservation(me)) {
                        System.out.println(resv.toString());
                    }
                } else {
                    System.out.println("Your email is not registered. ");

                    while (!isValidAnswer) {
                        System.out.println("Do you want to try a different account y/n ?");
                        String reply = scan.nextLine();

                        if (reply.equalsIgnoreCase("y") || reply.equalsIgnoreCase("n")) {
                            isValidAnswer = true;

                            if (reply.equalsIgnoreCase("n")) {
                                return;
                            }
                        } else {
                            System.out.println("Please Enter y or n");
                        }
                    }
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid Email");
            }
        }
    }

    /**
     * This method creates a new account
     */
    public static void createAccount() {
        Scanner scan = new Scanner(System.in);
        Boolean isValidEmail = false;

        System.out.println("First Name");
        String firstName = scan.nextLine();

        System.out.println("Last Name");
        String lastName = scan.nextLine();

        while (!isValidEmail) {
            System.out.println("Enter Email format: name@domain.com");
            String email = scan.nextLine();

            // Create & store new Account
            try {
                CustomerService.getCustomerService().addCustomer(firstName, lastName, email);
                isValidEmail = true;

            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid Email");
            }
        }
    }
}