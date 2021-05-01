package com.udacity.javand.service;

import com.udacity.javand.model.Customer;
import com.udacity.javand.model.IRoom;
import com.udacity.javand.model.Reservation;

import java.util.*;

public class ReservationService {

    private static final ReservationService reservationService = new ReservationService( );

    /** A private Constructor prevents any other
     * class from instantiating.
     */
    private ReservationService() { }

    public static ReservationService getReservationService(){ return reservationService; }

    // The collection type chosen for rooms ensures that two rooms cannot be booked at the same time.
    private static Set<IRoom> roomsList = new HashSet<IRoom>();
    private static ArrayList<Reservation> reservationsList = new ArrayList<Reservation>();

    /**
     * This method returns the set of all available rooms
     *
     * @return the set of all room
     */
    public static Set<IRoom> getRoomsList() {
        return roomsList;
    }

    /**
     * Getter for the list of all currently existing reservations
     *
     * @return a list of reservations
     */
    public static ArrayList<Reservation> getReservationsList() {
        return reservationsList;
    }

    /**
     * Method to add a room to the pool of rooms
     *
     * @param room a room instance to add
     */
    public static void addRoom(IRoom room ) {
        roomsList.add(room);
    }

    /**
     * Method to get a room instance having a certain room number
     *
     * @param roomId the room number
     * @return a room instance having the desired room number
     */
    public static IRoom getARoom(String roomId) {
        IRoom room = null;
        for (IRoom rm : roomsList) {
            if (rm.getroomId().equals(roomId)) {
                room = rm;
            }
        }
        return room;
    }

    /**
     * This method will create a new reservation instance
     *
     * @param customer a customer instance
     * @param room a room instance
     * @param checkInDate a date instance
     * @param checkOutDate a date instance
     * @return a reservation instance
     * @throws IllegalArgumentException
     */
    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) throws IllegalArgumentException {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationsList.add(reservation);

        return reservation;
    }

    /**
     * This method finds all the rooms available for the given time period
     *
     * @param checkInDate a date instance
     * @param checkOutDate a date instance
     * @return a collection of rooms
     */
    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        ArrayList<IRoom> availableRoomsList = new ArrayList<IRoom>();

        for (IRoom rm : roomsList) {
            for (Reservation resv : reservationsList) {
                if (resv.getRoom().equals(rm)) {
                    if (!(resv.getCheckInDate().compareTo(checkOutDate) > 0) && !(resv.getCheckOutDate().compareTo(checkInDate) < 0)) {
                        // Room is not available for the provided time interval
                        rm.isFree();
                    }
                }
            }
        }

        for (IRoom rm : roomsList) {
            if (!rm.isFree()) {
                rm.isFree();
                availableRoomsList.add(rm);
            }
        }

        return availableRoomsList;
    }

    /**
     * This method returns all the reservations for a given customer
     * @param customer a customer instance
     * @return a collection of reservations
     */
    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        ArrayList<Reservation> customerReservationsList = new ArrayList<Reservation>();
        for (Reservation resv : reservationsList) {
            if (resv.getCustomer().equals(customer)) {
                customerReservationsList.add(resv);
            }
        }
        return customerReservationsList;
    }

    /**
     * This method just prints all the existing reservations
     */
    public static void printAllReservation() {
        for (Reservation resv : reservationsList) {
            System.out.println(resv);
        }
    }
}
