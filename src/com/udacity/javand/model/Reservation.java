package com.udacity.javand.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public void setRoom(IRoom room) {
        this.room = room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * @return customized string describing the instance
     */
    @Override
    public String toString() {
        String roomTypeDescription = null;
        switch (room.getRoomType()) {
            case SINGLE:
                roomTypeDescription = "Single bed";
                break;
            case DOUBLE:
                roomTypeDescription = "Double bed";
                break;
        }

        SimpleDateFormat x =  new SimpleDateFormat ("E MMM d yyyy");

        return "Customer: " + customer.getFirstName() + " " + customer.getLastName() +
                "\nRoom: " + room.getroomId() + " - " + roomTypeDescription +
                "\nCheckin Date: " + x.format(checkInDate) +
                "\nCheckout Date: " + x.format(checkOutDate);
    }
}
