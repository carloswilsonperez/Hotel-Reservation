package com.udacity.javand.service;

import com.udacity.javand.model.Customer;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerService {
    private static final CustomerService customerService = new CustomerService( );

    private static ArrayList<Customer> customerList = new ArrayList<Customer>();

    /** A private Constructor prevents any other
     * class from instantiating.
     */
    private CustomerService() { }

    public static CustomerService getCustomerService(){ return customerService; }

    /**
     * This method creates and adds a new customer
     *
     * @param firsName user first name
     * @param lastName user last name
     * @param email user email
     */
    public static void addCustomer(String firsName, String lastName, String email) {
        Customer customer = new Customer(firsName, lastName, email);
        customerList.add(customer);
    }

    /**
     * This method returns a customer instance having a given email
     *
     * @param email the customer email
     * @return a customer instance
     */
    public static Customer getCustomer(String email) {
        Customer customer = null;
        for (Customer ctmr : customerList) {
            if (ctmr.getEmail().equals(email.toLowerCase())) {
                customer = ctmr;
            }
        }
        return customer;
    }

    /**
     * This method returns all the available customers
     *
     * @return A Collection of all customers
     */
    public static Collection<Customer> getAllCustomers() {
        return customerList;
    }
}
