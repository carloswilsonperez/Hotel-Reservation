package com.udacity.javand;

import com.udacity.javand.model.*;
import com.udacity.javand.service.CustomerService;
import com.udacity.javand.service.ReservationService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu {
    public static final String SEPARATOR = "--------------------------------------------";

    /**
     * This method prints and manages the Admin Menu
     */
    public static void adminMenu(){
        System.out.println("Admin Menu");
        System.out.println(SEPARATOR);
        System.out.println();
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println(SEPARATOR);
        System.out.println("Please select a number for the menu option");
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();

        switch(choice){
            case 1:
                showAllCustomers();
                break;
            case 2:
                showAllRooms();
                break;
            case 3:
                showAllReservations();
                break;
            case 4:
                addRoom();
                break;
            case 5: System.out.println("Back to Main Menu");
                break;
            default: System.out.println("Incorrect input!!! Please re-enter choice from our menu");
        }
    }

    /**
     * This method shows all registered customers
     */
    public static void showAllCustomers() {
        // List all the customers; format: First Name: Carlos Last Name: Wilson Email: carlos@gmail.com
        for (Customer cmr : CustomerService.getCustomerService().getAllCustomers()) {
            System.out.println(cmr.toString());
        }
    }

    /**
     * This methos prints all available rooms
     */
    public static void showAllRooms() {
        // List all the available Rooms
        for (IRoom room : ReservationService.getReservationService().getRoomsList()) {
            System.out.println(room.toString());
        }
    }

    /**
     * This method prints all existing reservations
     */
    public static void showAllReservations() {
        // List all the Reservations
        ReservationService.getReservationService().printAllReservation();
    }

    /**
     * This method adds a new room to the pool of rooms
     */
    public static void addRoom() {
        Scanner scan = new Scanner(System.in);
        RoomType rt = null;
        String roomId = null;
        Double price = 0.0;
        Boolean isValidRoomType = false;
        Boolean isValidroomId = false;
        Boolean isValidRoomPrice = false;

        while (!isValidroomId) {
            System.out.print("Enter the room number ?: ");
            roomId = scan.nextLine();
            try {
                Integer.parseInt(roomId);
                isValidroomId = true;
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid positive number for rooms");
            }
        }

        while (!isValidRoomType) {
            System.out.print("Enter room type: single/double ?: ");
            String roomType = scan.nextLine();

            try {
                if (!roomType.toLowerCase().equals("single") && !roomType.toLowerCase().equals("double")) {
                    throw new IllegalArgumentException("Please enter a valid room type");
                }

                rt = RoomType.valueOf(roomType.toUpperCase());
                isValidRoomType = true;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

        while (!isValidRoomPrice) {
            // Scanner scan = new Scanner(System.in);
            System.out.print("Enter room price per night ?: ");

            try {
                price = scan.nextDouble();
                isValidRoomPrice = true;

            } catch (InputMismatchException e) {
                System.out.println("Please Enter a valid floating number for the price");
            }
        }

        // Create and add room
        IRoom room = new Room(roomId, price, rt);
        ReservationService.getReservationService().addRoom(room);
    }
}
