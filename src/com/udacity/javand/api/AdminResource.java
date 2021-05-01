package com.udacity.javand.api;

import com.udacity.javand.model.Customer;
import com.udacity.javand.model.IRoom;
import com.udacity.javand.model.Reservation;
import com.udacity.javand.service.CustomerService;
import com.udacity.javand.service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static AdminResource singletonAR = new AdminResource( );

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private AdminResource() { }

    /* Static 'instance' method */
    public static AdminResource getInstance( ) {
        return singletonAR;
    }

    /* Other methods protected by singleton-ness */
    protected static void demoMethod( ) {
        System.out.println("demoMethod for singleton");
    }

    public Customer getCustomer(String email) {
        return new Customer("John", "Doe", "j@domain.com");
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom rm : rooms) {
            ReservationService.getReservationService().addRoom(rm);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return ReservationService.getReservationService().getRoomsList();
    }

    public Collection<Customer> getAllCustomers() {
        return CustomerService.getCustomerService().getAllCustomers();
    }

    public void displayAllReservations() {
        for (Reservation resv : ReservationService.getReservationService().getReservationsList()) {
            resv.toString();
        }
    }
}
