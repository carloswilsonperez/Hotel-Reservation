package com.udacity.javand.api;

import com.udacity.javand.model.Customer;
import com.udacity.javand.model.IRoom;
import com.udacity.javand.model.Reservation;
import com.udacity.javand.service.CustomerService;
import com.udacity.javand.service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource singletonHR = new HotelResource( );

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private HotelResource() { }

    /* Static 'instance' method */
    public static HotelResource getInstance( ) {
        return singletonHR;
    }

    /* Other methods protected by singleton-ness */
    protected static void demoMethod( ) {
        System.out.println("demoMethod for singleton");
    }

    public Customer getCustomer(String email) {
        return CustomerService.getCustomerService().getCustomer(email);
    }

    public void CreateACustomer(String firstName, String lastName, String email) {
        CustomerService.getCustomerService().addCustomer(firstName, lastName, email);
    }

    public IRoom getRoom(String roomId) {
        return ReservationService.getReservationService().getARoom(roomId);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        // Find the customer having the provided customerEmail
        Customer customer = CustomerService.getCustomerService().getCustomer(customerEmail);

        return ReservationService.getReservationService().reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        // Find the customer having the provided customerEmail
        Customer customer = CustomerService.getCustomerService().getCustomer(customerEmail);

        return ReservationService.getReservationService().getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return ReservationService.getReservationService().findRooms(checkInDate, checkOutDate);
    }
}
